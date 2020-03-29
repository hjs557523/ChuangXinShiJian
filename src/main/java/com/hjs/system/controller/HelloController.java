package com.hjs.system.controller;

import com.hjs.system.SystemApplication;
import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.model.Student;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/2 19:52
 * @Modified By:
 */

@Controller
public class HelloController {

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping("/hello")
    public ModelAndView sayHello() {

        // SecurityUtils.getSubject()是每个请求创建一个Subject, 并保存到ThreadContext的resources（ThreadLocal<Map<Object, Object>>）变量中，
        // 也就是一个http请求一个subject,并绑定到当前线程
        // logger.info(SecurityUtils.getSubject().toString());
        // 每个shiro拦截到的请求，都会根据sessionId创建Subject, 清除当前线程的绑定，然后重新绑定新线程中，之后执行过滤器
        // 所以我们在SecurityUtils.getSubject()中获取的一直是当前用户的信息
        Student s = new Student();
        s.setStudentId("16041321");
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", s);
        mv.setViewName("/test.html");

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.getSession().setAttribute("abc","Huang JiSheng");

        System.out.println("Hello, user 访问到了这个路径...");
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies)
        System.out.println(cookie.getName() + ": " + cookie.getValue());
        return mv;
    }


    @RequestMapping("/hello2")
    public ModelAndView sayHello2() {
        Student s = new Student();
        s.setStudentId("16041321");
        ModelAndView mv = new ModelAndView();
        mv.addObject("user", s);
        mv.setViewName("/test.html");
        return mv;
    }


    @RequestMapping("/hello3")
    @ResponseBody
    public String sayHello3(String access_token) {
        //System.out.println(access_token);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        System.out.println(request.getParameter("access_token"));

        if (SecurityUtils.getSubject().getSession().getId().toString().equals(access_token))
            return JSONUtil.returnSuccessResult("成功");
        else
            return JSONUtil.returnFailResult("失败");
    }
}
