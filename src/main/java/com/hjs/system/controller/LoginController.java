package com.hjs.system.controller;

import com.hjs.system.base.BaseController;
import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.base.utils.StringUtil;
import com.hjs.system.config.shiro.LoginType;
import com.hjs.system.config.shiro.UserToken;
import com.hjs.system.model.Student;
import com.hjs.system.service.StudentService;
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


/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/4 13:05
 * @Modified By:
 */

@CrossOrigin
@Controller
public class LoginController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final String STUDENT_LOGIN_TYPE = LoginType.STUDENT.toString();

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String login(Student student) {
        logger.info("接收到请求");
        logger.info("登录Student: " + student);
        //后台校验提交的用户名和密码
        if (StringUtil.isEmpty(student.getStudentId()))
            return JSONUtil.returnFailResult("学号不能为空!");
        else if (student.getStudentId().trim().length() != 8)
            return JSONUtil.returnFailResult("请输入8位学号!");
        else if (StringUtil.isEmpty(student.getPassword()))
            return JSONUtil.returnFailResult("请输入密码!");

        Subject subject = SecurityUtils.getSubject();

        UserToken userToken = new UserToken(student.getStudentId(), student.getPassword(), STUDENT_LOGIN_TYPE);
        userToken.setRememberMe(false);
        logger.info("请求了方法");
        try {
            //登录认证
            subject.login(userToken);
            subject.getSession().setAttribute("student",subject.getPrincipal());
            return JSONUtil.returnSuccessResult("登陆成功");
        } catch (AuthenticationException e) {
            //认证失败就会抛出AuthenticationException这个异常，就对异常进行相应的操作，这里的处理是抛出一个自定义异常ResultException
            //到时候我们抛出自定义异常ResultException，用户名或者密码错误
            logger.info("认证失败");
            return JSONUtil.returnFailResult("学号或密码错误");
        }

    }

}
