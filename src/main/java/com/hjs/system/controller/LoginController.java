package com.hjs.system.controller;

import com.hjs.system.SystemApplication;
import com.hjs.system.base.BaseController;
import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.base.utils.StringUtil;
import com.hjs.system.config.shiro.common.LoginType;
import com.hjs.system.config.shiro.common.UserToken;
import com.hjs.system.model.Student;
import com.hjs.system.model.Teacher;
import com.hjs.system.service.StudentService;
import com.hjs.system.service.impl.StudentServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/4 13:05
 * @Modified By:
 */

@CrossOrigin
@Controller
public class LoginController extends BaseController {

    @Autowired
    private StudentService studentServiceImpl;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final String STUDENT_LOGIN_TYPE = LoginType.STUDENT.toString();
    private static final String TEACHER_LOGIN_TYPE = LoginType.TEACHER.toString();

    @RequestMapping(value = "/student/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String studentLogin(Student student) {
        logger.info("接收到登录请求");
        logger.info("登录Student: " + student);
        //后台校验提交的用户名和密码
        if (StringUtil.isEmpty(student.getStudentId()))
            return JSONUtil.returnFailResult("学生学号不能为空!");
        else if (student.getStudentId().trim().length() != 8)
            return JSONUtil.returnFailResult("请输入8位学号!");
        else if (StringUtil.isEmpty(student.getPassword()))
            return JSONUtil.returnFailResult("请输入学生密码!");

        Subject subject = SecurityUtils.getSubject();

        UserToken userToken = new UserToken(student.getStudentId(), student.getPassword(), STUDENT_LOGIN_TYPE);
        userToken.setRememberMe(false);
        try {
            // 登录认证
            // shiro 密码如何验证？ https://www.cnblogs.com/zuochengsi-9/p/10153874.html
            subject.login(userToken);//doGetAuthenticationInfo

            //servlet session 和 shiro session? : https://yq.aliyun.com/articles/114167?t=t1
            subject.getSession().setAttribute("student", (Student)subject.getPrincipal());

            //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            //System.out.println(request); 重新包装HttpServletRequest为: org.apache.shiro.web.servlet.ShiroHttpServletRequest@7a0ce4d4
            //request.getSession(): org.apache.catalina.session.StandardSessionFacade
            //subject.getSession(): org.apache.shiro.subject.support.DelegatingSubject$StoppingAwareProxiedSession


            //shiro session 是对 spring session 的封装，两者sessionId相同
            //logger.info("请求了/student/login: Apache shiro  的sessionId: {}",subject.getSession().getId().toString());
            //logger.info("请求了/student/login: Spring servlet的sessionId: {}",request.getSession().getId());


            return JSONUtil.returnEntityResult(subject.getSession().getId());
            //return JSONUtil.returnSuccessResult("登陆成功");
        } catch (AuthenticationException e) {
            //认证失败就会抛出AuthenticationException这个异常，就对异常进行相应的操作，这里的处理是抛出一个自定义异常ResultException
            //到时候我们抛出自定义异常ResultException，用户名或者密码错误
            logger.info("认证失败");
            return JSONUtil.returnFailResult("学号或密码错误");
        }

    }



    @RequestMapping(value = "/teacher/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String teacherLogin(Teacher teacher) {
        logger.info("接收到请求");
        logger.info("登录Student: " + teacher);
        if (StringUtil.isEmpty(teacher.getTeacherId()))
            return JSONUtil.returnFailResult("教师工号不能为空");
        else if (StringUtil.isEmpty(teacher.getPassword()))
            return JSONUtil.returnFailResult("教师密码不能为空");

        Subject subject = SecurityUtils.getSubject();
        UserToken userToken = new UserToken(teacher.getTeacherId(), teacher.getPassword(), TEACHER_LOGIN_TYPE);
        userToken.setRememberMe(false);
        logger.info("");

        try {
            subject.login(userToken);
            subject.getSession().setAttribute("teacher", (Teacher)subject.getPrincipal());

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            //经过测试好像shiro session 和 spring session 是同一个session，因为sessionId相同
            logger.info("请求了/teacher/login: Apache shiro  的sessionId: {}",subject.getSession().getId().toString());
            logger.info("请求了/teacher/login: Spring servlet的sessionId: {}",request.getSession().getId());

            return JSONUtil.returnEntityResult(subject.getSession().getId());


        } catch (AuthenticationException e) {
            // login()认证失败就会抛出AuthenticationException这个异常，就对异常进行相应的操作，
            // 这里的处理是：抛出一个自定义异常ResultException：用户名或者密码错误
            logger.info("认证失败");
            return JSONUtil.returnFailResult("工号或密码错误");

        }

    }

}
