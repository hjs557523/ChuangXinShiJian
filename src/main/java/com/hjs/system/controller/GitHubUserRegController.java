package com.hjs.system.controller;

import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.base.utils.MD5Util;
import com.hjs.system.config.shiro.common.LoginType;
import com.hjs.system.config.shiro.common.UserToken;
import com.hjs.system.model.Student;
import com.hjs.system.model.Teacher;
import com.hjs.system.service.StudentService;
import com.hjs.system.service.TeacherService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Security;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/13 15:06
 * @Modified By:
 */

@Controller
public class GitHubUserRegController {

    private static final Logger logger = LoggerFactory.getLogger(GitHubUserRegController.class);
    private static final String FREE_LOGIN = LoginType.FreeLogin.toString();
    private static final String STUDENT_LOGIN_TYPE = LoginType.STUDENT.toString();
    private static final String TEACHER_LOGIN_TYPE = LoginType.TEACHER.toString();

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private StudentService studentServiceImpl;

    @Autowired
    private TeacherService teacherServiceImpl;


    @RequestMapping(value = "/wx/githubBinding", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String RegistFromWeChatApp(@RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("name") String name) {

        // 插入，再执行登录
        if (request.getSession(false) == null) {
            return JSONUtil.returnFailResult("先执行github用户的验证操作!");
        }

        logger.info("当前session的id = " + request.getSession(false).getId());
        String githubName = (String) request.getSession().getAttribute("githubUserName");
        String githubAvatarUrl = (String) request.getSession().getAttribute("githubAvatarUrl");
        String openId = (String) request.getSession().getAttribute("openId");
        Integer userType = (Integer) request.getSession().getAttribute("userType");
        String md5Password = MD5Util.getMd5HashPassword(2, username, password);

        try {
            if (userType == 0) {
                Student student = new Student();
                student.setGithubName(githubName);
                student.setPicImg(githubAvatarUrl);
                student.setStudentId(username);
                student.setPassword(md5Password);
                student.setOpenId(openId);
                student.setName(name);
                if (studentServiceImpl.addStudent(student) > 0) {
                    // 执行真正的登录操作
                    UserToken userToken = new UserToken(username, password, STUDENT_LOGIN_TYPE);
                    Subject subject = SecurityUtils.getSubject();
                    try {
                        subject.login(userToken);
                        SecurityUtils.getSubject().getSession().setAttribute("Student", (Student) subject.getPrincipal());
                        SecurityUtils.getSubject().getSession().setTimeout(3 * 60 * 60 * 1000);
                        return JSONUtil.returnEntityResult(student.getSid());
                    } catch (Exception e) {
                        logger.info("新绑定的用户密码错误");
                        return JSONUtil.returnFailResult("绑定失败");
                    }
                }
                else
                    return JSONUtil.returnFailResult("绑定失败!");
            } else {
                Teacher teacher = new Teacher();
                teacher.setGithubName(githubName);
                teacher.setTeacherId(username);
                teacher.setPassword(md5Password);
                if (teacherServiceImpl.addTeacher(teacher) > 0) {
                    UserToken userToken = new UserToken(username, password, TEACHER_LOGIN_TYPE);
                    Subject subject = SecurityUtils.getSubject();
                    try {
                        subject.login(userToken);
                        SecurityUtils.getSubject().getSession().setAttribute("Teacher", (Teacher) subject.getPrincipal());
                        SecurityUtils.getSubject().getSession().setTimeout(3 * 60 * 60 * 1000);
                        logger.info("shiro session: " + SecurityUtils.getSubject().getSession().getId());
                        return JSONUtil.returnEntityResult(teacher.getTeacherId());
                    } catch (Exception e) {
                        logger.info("新绑定的用户密码错误");
                        return JSONUtil.returnFailResult("绑定失败");
                    }
                }
                else
                    return JSONUtil.returnFailResult("绑定失败");
            }

        } catch (Exception e) {
            logger.info("数据库异常：" + e.getMessage());
            return JSONUtil.returnFailResult("数据库异常");
        }

    }
}
