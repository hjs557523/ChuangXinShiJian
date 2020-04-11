package com.hjs.system.config.shiro.FreeLogin;

import com.hjs.system.config.shiro.common.UserToken;
import com.hjs.system.model.Student;
import com.hjs.system.model.Teacher;
import com.hjs.system.service.StudentService;
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
 * @Description:  第三方登录-免密登录用户进行验证授权
 * @date Created in 2020/4/11 19:21
 * @Modified By:
 */
public class FreeRealm extends AuthorizingRealm {

    private static final Logger logger = LoggerFactory.getLogger(FreeRealm.class);

    @Autowired
    @Lazy
    private StudentService studentServiceImpl;

    @Autowired
    @Lazy
    private TeacherService teacherServiceImpl;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("开始执行免密第三方用户 授权逻辑...doGetAuthorizationInfo()");
        if (principalCollection == null) {
            throw new AuthorizationException("principalCollection is null");
        }

        /* 给资源进行授权 */
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        if (principalCollection.getPrimaryPrincipal() instanceof Student) { // 如果github用户是学生
            info.addRole("Student");
            logger.info("该github用户是学生角色, 授权成功!");
            return info;
        } else if (principalCollection.getPrimaryPrincipal() instanceof Teacher) { // 如果github用户是老师
            info.addRole("Teacher");
            logger.info("该github用户是教师角色, 授权成功!");
            return info;
        }

        logger.info("授权失败");
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("执行认证逻辑，开始认证免密登录用户...doGetAuthenticationInfo()");
        UserToken userToken = (UserToken) authenticationToken;
        String githubName = userToken.getUsername();

        // 进行用户身份判断
        Student student = studentServiceImpl.findStudentByGitHubName(githubName);
        Teacher teacher = teacherServiceImpl.findTeacherByGitHubName(githubName);

        if (student == null && teacher == null) {
            logger.info("用户不存在");
            throw new UnknownAccountException("用户不存在！");
        } else if (student != null) {
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(student, student.getPassword(), getName());
            simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(student.getGithubName()));
            logger.info("进入学生用户认证:");
            logger.info("第三方github用户认证方法执行结束");
            return simpleAuthenticationInfo;
        } else {
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(teacher, teacher.getPassword(), getName());
            simpleAuthenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(teacher.getGithubName()));
            logger.info("进入老师用户认证:");
            logger.info("第三方github用户认证方法执行结束");
            return simpleAuthenticationInfo;
        }


    }
}
