package com.hjs.system.config.shiro.student;

import com.hjs.system.config.shiro.common.UserToken;
import com.hjs.system.model.Student;
import com.hjs.system.service.StudentService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

/**
 * @author 黄继升 16041321
 * @Description: 在realm中告诉shiro用户信息怎么获取，抽象了DAO层，充当了数据源
 * @date Created in 2020/3/6 22:54
 * @Modified By:
 */
public class StudentRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(StudentRealm.class);

    // 这里可作为项目难点1：
    // 自定义Realm注入的Service声明中加入@Lazy注解即可解决@Cacheable注解无效问题
    // 解决同时使用Redis缓存数据和缓存shiro时，@Cacheable无效的问题
    // https://stackoverflow.com/questions/21512791/spring-service-with-cacheable-methods-gets-initialized-without-cache-when-autowi
    // https://blog.csdn.net/elonpage/article/details/78965176
    @Autowired
    @Lazy
    private StudentService studentService;

    /**
     * 执行授权逻辑, 当请求访问的url配置了相应的权限, 或者shiro标签才会执行此方法，否则不会执行
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("开始执行Student 授权逻辑...doGetAuthorizationInfo()");
        if (principalCollection == null) {
            throw new AuthorizationException("principalCollection is null");
        }
        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (principalCollection.getPrimaryPrincipal() instanceof Student) {
            info.addRole("Student");
            logger.info("授权结束, 授权成功!");
            return info;
        }

        logger.info("授权失败");
        return null;//角色对应的权限信息
    }



    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("执行认证逻辑，开始认证Student身份...doGetAuthenticationInfo()");

        UserToken userToken = (UserToken) authenticationToken;
        String studentId = userToken.getUsername();
        Student student = studentService.findStudentByStudentId(studentId);

        if (student == null) {
            //也可以return null，因为直接return null的话，shiro底层会抛出UnKnowAccountException。也可以手动throw Excepton
            throw new UnknownAccountException("用户不存在！");
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                student, //用户信息，这里传studentId也可以，传Student对象也可以，区别在于Shiro为我们提供了获取当前用户信息的方法
                         //有些业务场景需要获取当前用户的信息进行业务操作，所以只传studentId那么其他用户信息就拿不到了
                student.getPassword(), //密码
                getName() //realm name
        );

        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(student.getStudentId())); //设置盐

        logger.info("student认证方法执行结束");
        return simpleAuthenticationInfo;
    }

}
