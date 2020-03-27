package com.hjs.system.config.shiro.teacher;

import com.hjs.system.config.shiro.common.UserToken;
import com.hjs.system.model.Teacher;
import com.hjs.system.service.TeacherService;
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
 * @Description:
 * @date Created in 2020/3/27 15:57
 * @Modified By:
 */
public class TeacherRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(TeacherRealm.class);

    // 自定义Realm注入的Service声明中加入@Lazy注解即可解决@Cacheable注解无效问题
    // 解决同时使用Redis缓存数据和缓存shiro时，@Cacheable无效的问题
    @Autowired
    @Lazy
    private TeacherService teacherService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("开始执行Teacher 授权逻辑...doGetAuthorizationInfo()");
        if (principalCollection == null) {
            throw new AuthorizationException("principalCollection is null");
        }
        //给资源进行授权
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if (principalCollection.getPrimaryPrincipal() instanceof  Teacher) {
            authorizationInfo.addRole("Teacher");
            logger.info("授权结束, 授权成功!");
            return authorizationInfo;
        }
        else {
            return null;
        }
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        logger.info("开始Teacher身份认证...");
        UserToken userToken = (UserToken) authenticationToken;
        String teacherId = userToken.getUsername();
        Teacher teacher = teacherService.findTeacherByTeacherId(teacherId);

        if (teacher == null) {
            //也可以return null，因为shiro底层会抛出UnKnowAccountException
            throw new UnknownAccountException("用户不存在!");
        }

        //验证通过返回一个封装了用户信息的AuthenticationInfo实例即可
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                teacher,
                teacher.getPassword(), //密码
                getName() //realm name
        );

        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(teacher.getTeacherId())); //设置盐

        return authenticationInfo;
    }
}
