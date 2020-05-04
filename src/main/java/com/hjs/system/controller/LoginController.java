package com.hjs.system.controller;

import com.hjs.system.base.BaseController;
import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.base.utils.StringUtil;
import com.hjs.system.config.shiro.common.LoginType;
import com.hjs.system.config.shiro.common.UserToken;
import com.hjs.system.model.Student;
import com.hjs.system.model.Teacher;
import com.hjs.system.service.StudentService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


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
    private HttpServletRequest request;

    @Autowired
    private StudentService studentServiceImpl;

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    private static final String STUDENT_LOGIN_TYPE = LoginType.STUDENT.toString();
    private static final String TEACHER_LOGIN_TYPE = LoginType.TEACHER.toString();

    //consumes 指定接收的内容的类型
    @RequestMapping(value = "/student/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String studentLogin(Student student) {

        logger.info("接收到web端的登录请求");
//        logger.info(request.getQueryString());
//        Cookie[] cookies = request.getCookies();
//        if (cookies!=null) {
//            for (Cookie cookie : cookies)
//                logger.info(cookie.getName() + ":" + cookie.getValue());
//        }
//
//        Enumeration<String> headerNames = request.getHeaderNames();
//        while (headerNames.hasMoreElements()) {
//            String name = headerNames.nextElement();
//            //通过请求头的名称获取请求头的值
//            String value = request.getHeader(name);
//            System.out.println(name + "----" + value);
//        }

        logger.info("接收到登录请求");
        logger.info("登录Student: " + student);

        // 存在问题：基于session和cookie保持会话的项目，当同一浏览器发生多用户先后登陆，进而产生session覆盖，但之前的登录的用户虽然已经登录态丢失，
        // 却由于同一浏览器属于同一会话，因此凭借保持不变的cookie能够完成一些ajax操作，但如果存在刷新页面的逻辑，则返回的页面会重新渲染为最新的用户，逻辑混乱

        // 无法解决：原想每一次请求登录时都进行判断，判断该请求对应的session是否存在，存在销毁原先用户session，强制登出。重新设置新cookie给最近登陆的用户
        // 但是前一个用户刷新时还是取到了最新的cookie，可见cookie确实是真的整个浏览器域共享...

        // 最新解决方案：用session 存储不同用户信息，在每个url中带上登录名如 fozubaoyou.com?uid=sa，后台用 session[Request["uid"]]来获取用户信息。
//        if (request.getSession(false) != null) { //注意false才会不自动创建session
//            logger.info("将之前用户强制踢下线..."); //销毁之前创建的session，避免无用session过多浪费资源
//            SecurityUtils.getSubject().logout();//包括了getRequest().getSession().invalidate();
//        }


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
            // 登录认证，login成功，没有session就创建session
            // shiro 密码如何验证？ https://www.cnblogs.com/zuochengsi-9/p/10153874.html
            subject.login(userToken);//doGetAuthenticationInfo

//            logger.info("1.Principal: " + subject.getPrincipal());
//            logger.info("2.PreviousPrincipals: " + subject.getPreviousPrincipals());
//            logger.info("3.Principals: "+ subject.getPrincipals());

//            servlet session 和 shiro session? : https://yq.aliyun.com/articles/114167?t=t1
//            subject.getSession().setAttribute("student", (Student)subject.getPrincipal());
            subject.getSession().setAttribute("student", (Student)subject.getPrincipal());
//            HttpSession session = request.getSession();
//            Enumeration enumeration = session.getAttributeNames();
//            while (enumeration.hasMoreElements()) {
//                String name = enumeration.nextElement().toString();
//                System.out.println(name + ": " + session.getAttribute(name));
//            }

            //logger.info(String.valueOf(request.getSession().getMaxInactiveInterval())); //1800s
            //logger.info(String.valueOf(subject.getSession().getTimeout())); //1800 000ms
            //事实证明，即使设置了redis来缓存session，session的过期时间 ≠ redis缓存时间，仍然要手动设置
            subject.getSession().setTimeout(3 * 60 * 60 * 1000);//session有效期设置3小时


            //HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            //System.out.println(request); 重新包装HttpServletRequest为: org.apache.shiro.web.servlet.ShiroHttpServletRequest@7a0ce4d4
            //request.getSession(): org.apache.catalina.session.StandardSessionFacade
            //subject.getSession(): org.apache.shiro.subject.support.DelegatingSubject$StoppingAwareProxiedSession


            //shiro session 是对 spring session 的封装，两者sessionId相同
            logger.info("请求了/student/login: Apache shiro  的sessionId: {}",subject.getSession().getId().toString());
            logger.info("请求了/student/login: Spring servlet的sessionId: {}",request.getSession().getId());

//            logger.info(SecurityUtils.getSubject().getPrincipal().getClass().getName());
//            logger.info(Student.class.getName());


            Map<String, Object> map = new HashMap<>();
            map.put("userId", student.getStudentId());
            map.put("Token", subject.getSession().getId());
            return JSONUtil.returnEntityResult(map);
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
        logger.info("登录Teacher: " + teacher);
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
