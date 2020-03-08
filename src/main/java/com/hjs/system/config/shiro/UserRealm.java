package com.hjs.system.config.shiro;

import com.hjs.system.mapper.StudentMapper;
import com.hjs.system.model.Student;
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
 * @Description:
 * @date Created in 2020/3/6 22:54
 * @Modified By:
 */
public class UserRealm extends AuthorizingRealm {
    private final Logger logger = LoggerFactory.getLogger(UserRealm.class);

    // 自定义Realm注入的Service声明中加入@Lazy注解即可解决@Cacheable注解无效问题
    // 解决同时使用Redis缓存数据和缓存shiro时，@Cacheable无效的问题
    @Autowired
    @Lazy
    private StudentMapper studentMapper;

    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("执行授权逻辑");
        if (principalCollection == null) {
            throw new AuthorizationException("principalCollection is null");
        }
        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        Subject subject = SecurityUtils.getSubject();
        if (((String)subject.getSession().getAttribute("role")).equals("student")) {
            info.addRole("student");
        }
        return info;
    }


    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("执行认证逻辑");
        //判断身份
        //编写shiro判断逻辑，判断用户名和密码
        //1.判断用户名
        String role = (String) SecurityUtils.getSubject().getSession().getAttribute("role");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        if (role.equals("student")) {
            String studentId = token.getUsername();
            Student student = studentMapper.findStudentByStudentId(studentId);
            if (student == null) {
                //return null;//shiro底层会抛出UnKnowAccountException
                throw new UnknownAccountException("用户不存在！");
            }
            //2.判断密码 存入姓名
            SecurityUtils.getSubject().getSession().setAttribute("username",student.getStudentId());
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                    student, //用户信息，这里传username也可以，传Student对象也可以，区别在于Shiro为我们提供了获取当前用户信息的方法
                             //有些业务场景需要获取当前用户的信息进行业务操作，所以只传username那么其他用户信息就拿不到了
                    student.getPassword(), //密码
                    getName() //realm name
            );

            simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(studentId)); //设置盐

            return simpleAuthenticationInfo;
        }
        else {
            return null;
        }

    }
}
