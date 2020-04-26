package com.hjs.system.controller.student;

import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.base.utils.StringUtil;
import com.hjs.system.base.utils.TokenUtil;
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
    @RequestMapping(value = "/student/index", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String getStudentUserInfo() {

        try {

            Boolean isReccent = TokenUtil.IsRecentLogin(request, "current_user");
            if (isReccent) {
                Student student = (Student) SecurityUtils.getSubject().getPrincipal();
                return JSONUtil.returnEntityResult(student);
            } else {
                Student student = (Student) SecurityUtils.getSubject().getSession().getAttribute(request.getParameter("current_user"));
                return JSONUtil.returnEntityResult(student);
            }

        } catch (Exception e) { //抛出异常是由于session仍存在，但该用户已退出登录。就直接跳到登录界面
            return JSONUtil.returnForbiddenResult("登录失效");
        }


//        logger.info("------------------------------------------");
//        logger.info("request.getMethod()： " + request.getMethod());
//        logger.info("request.getRequestURL(): " + request.getRequestURL().toString());
//        logger.info("request.getRequestURI(): " + request.getRequestURI());
//        logger.info("request.getQueryString(): " + request.getQueryString());
//        logger.info("request.getParameter(\"current_user\"): " + request.getParameter("current_user"));
//        logger.info("request.getParameterMap(): " + request.getParameterMap().toString());
//        logger.info("request.getContentType(): " + request.getContentType());
//        logger.info("request.getParameterNames()： " + request.getParameterNames().toString());
//        logger.info("------------------------------------------");


//        Cookie[] cookies = request.getCookies();
//        for (Cookie cookie : cookies)
//        logger.info(cookie.getName() + ":" + cookie.getValue());

//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String name = headerNames.nextElement();
//            //通过请求头的名称获取请求头的值
//            String value = request.getHeader(name);
//            System.out.println(name + "----" + value);
//        }


//        logger.info(request.getHeader("Cookie"));

//        Subject subject = SecurityUtils.getSubject();

//        if(subject.hasRole("Student")) {
//            logger.info("subject.hasRole()");
//        }
//
//        if(subject.isPermitted("Student")) {
//            logger.info("subject.isPermitted()");
//        } else {
//            logger.info("no define subject.isPermitted()");
//        }

//        Student student = (Student) SecurityUtils.getSubject().getPrincipal();
//        return JSONUtil.returnEntityResult(student);

    }


}
