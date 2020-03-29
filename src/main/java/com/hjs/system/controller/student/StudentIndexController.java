package com.hjs.system.controller.student;

import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.base.utils.StringUtil;
import com.hjs.system.model.Student;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author 黄继升 16041321
 * @Description: 进入学生系统界面后，基本基本信息获取
 * @date Created in 2020/3/15 21:59
 * @Modified By:
 */

@CrossOrigin
@Controller
public class StudentIndexController {
    private static final Logger logger = LoggerFactory.getLogger(StudentIndexController.class);

    @Autowired
    private HttpServletRequest request;


    //@RequiresRoles("Student")
    @RequestMapping(value = "/student/index", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getStudentUserInfo(String access_token) {

        if (StringUtil.isEmpty(access_token)) {
            logger.info("access_token 为 null");
        }
        Subject subject = SecurityUtils.getSubject();

        if(subject.hasRole("Student")) {
            logger.info("subject.hasRole()");
        }

        if(subject.isPermitted("Student")) {
            logger.info("subject.isPermitted()");
        } else {
            logger.info("no define subject.isPermitted()");
        }


        String sessionId = (String) subject.getSession().getId();
        if (access_token.equals(sessionId)) {
            //同一会话
            Student student = (Student) SecurityUtils.getSubject().getPrincipal();
            return JSONUtil.returnEntityResult(student);

        } else {
            return JSONUtil.returnFailResult("登录状态已失效，请重新登录!");
        }

    }

}
