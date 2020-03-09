package com.hjs.system.config.shiro;

import com.hjs.system.mapper.StudentMapper;
import com.hjs.system.model.Student;
import com.hjs.system.service.StudentService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
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

    // 自定义Realm注入的Service声明中加入@Lazy注解即可解决@Cacheable注解无效问题
    // 解决同时使用Redis缓存数据和缓存shiro时，@Cacheable无效的问题
    @Autowired
    @Lazy
    private StudentService studentService;

    /**
     * 执行授权逻辑, 当访问到页面时，链接配置了相应的权限或者shiro标签才会执行此方法，否则不会执行
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("执行Student 授权逻辑...");
        if (principalCollection == null) {
            throw new AuthorizationException("principalCollection is null");
        }
        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (principalCollection.getPrimaryPrincipal() instanceof Student) {
            info.addRole("Student");
            return info;
        }
        return info;//角色对应的权限信息
    }


    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("执行认证逻辑，开始认证Student身份...");

        UserToken userToken = (UserToken) authenticationToken;
        String studentId = userToken.getUsername();
        Student student = studentService.queryStudent(studentId);

        if (student == null) {
            //也可以return null，因为shiro底层会抛出UnKnowAccountException
            throw new UnknownAccountException("用户不存在！");
        }

        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                student, //用户信息，这里传studentId也可以，传Student对象也可以，区别在于Shiro为我们提供了获取当前用户信息的方法
                         //有些业务场景需要获取当前用户的信息进行业务操作，所以只传studentId那么其他用户信息就拿不到了
                student.getPassword(), //密码
                getName() //realm name
        );

        simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(studentId)); //设置盐

        return simpleAuthenticationInfo;
    }

}
