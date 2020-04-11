package com.hjs.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.hjs.system.api.ApiUtil;
import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.base.utils.StringUtil;
import com.hjs.system.config.shiro.common.LoginType;
import com.hjs.system.config.shiro.common.ShiroLoginFilter;
import com.hjs.system.config.shiro.common.UserToken;
import com.hjs.system.model.Student;
import com.hjs.system.model.Teacher;
import com.hjs.system.service.StudentService;
import com.hjs.system.service.TeacherService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 黄继升 16041321
 * @Description: 微信登录接口
 * @date Created in 2020/4/1 17:18
 * @Modified By:
 */

@Controller
public class WxLoginController {

    private static final String STUDENT_LOGIN_TYPE = LoginType.STUDENT.toString();
    private static final String TEACHER_LOGIN_TYPE = LoginType.TEACHER.toString();
    private static final Logger logger = LoggerFactory.getLogger(WxLoginController.class);

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private StudentService studentServiceImpl;

    @Autowired
    private TeacherService teacherServiceImpl;

    @RequestMapping(value = "/wx/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String wxUserLogin(@RequestParam("code") String code, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("userType") Integer userType) {

//        if (ShiroLoginFilter.isAjaxRequest(request))
//            logger.info("该微信请求是ajax请求");
//        else
//            logger.info("该微信请求不是ajax请求");

        logger.info("接收到微信端的登录请求");


        // 参数都不能为空
        if (StringUtil.isEmpty(code) || StringUtil.isEmpty(username) || StringUtil.isEmpty(password) || StringUtil.isEmpty(userType.toString()))
            return JSONUtil.returnFailResult("验证信息不完整, 请重新填写登录!");

        Map<String, String> params = new HashMap<>();
        params.put("appid", ApiUtil.WX_LOGIN_APPID);
        params.put("secret", ApiUtil.WX_LOGIN_SECRET);
        params.put("js_code", code);
        params.put("grant_type", ApiUtil.WX_LOGIN_GRANT_TYPE);

        // 发送请求
        String wxResult = ApiUtil.ApiGetRequest(ApiUtil.WX_LOGIN_URL, params, null);
        if (!StringUtil.isEmpty(wxResult)) {
            JSONObject jsonObject = JSONObject.parseObject(wxResult);

            // 获取返回参数
            String session_key = jsonObject.get("session_key").toString();
            String open_id = jsonObject.get("openid").toString();

            //开始验证后台用户数据，完成shiro认证
            Subject subject = SecurityUtils.getSubject();

            UserToken userToken = null;

            if (userType == 0) {
                userToken = new UserToken(username, password, STUDENT_LOGIN_TYPE);
            }
            else {
                userToken = new UserToken(username, password, TEACHER_LOGIN_TYPE);
            }

            userToken.setRememberMe(false);

            try {
                subject.login(userToken);
                if (userType == 0) {
                    try {
                        Student authenticationInfo = (Student) SecurityUtils.getSubject().getPrincipal();
                        if (StringUtil.isEmpty(studentServiceImpl.findStudentByStudentId(username).getOpenId())) {
                            Student student = new Student();
                            student.setOpenId(open_id);
                            student.setSid(authenticationInfo.getSid());
                            if (studentServiceImpl.updateStudent(student) > 0) {
                                //更新当前用户的角色信息 AuthenticationInfo
                                authenticationInfo.setOpenId(open_id);
                                logger.info("为student: {} 添加微信openId: {} 成功", authenticationInfo.getStudentId(), authenticationInfo.getOpenId());
                            } else {
                                logger.info("为student: {} 添加微信openId: {} 失败", authenticationInfo.getStudentId(), authenticationInfo.getOpenId());
                            }
                        }
                        //更新session
                        SecurityUtils.getSubject().getSession().setAttribute("student", authenticationInfo);
                        SecurityUtils.getSubject().getSession().setTimeout(3 * 60 * 60 * 1000);//3小时
                    } catch (Exception e) {
                        logger.info("添加openId失败!");
                        return JSONUtil.returnFailResult("添加openId失败!");
                    }
                }
//                else {
//                    try {
//                        Teacher authenticationInfo = (Teacher) SecurityUtils.getSubject().getPrincipal();
//                        if (StringUtil.isEmpty(teacherServiceImpl.findTeacherByTeacherId(username).getOpenId())) {
//                            Student student = new Student();
//                            student.setOpenId(open_id);
//                            if (studentServiceImpl.updateStudent(student) > 0) {
//                                //更新当前用户的角色信息 AuthenticationInfo
//                                authenticationInfo.setOpenId(open_id);
//                                logger.info("为student: {} 添加微信openId: {} 成功", authenticationInfo.getStudentId(), authenticationInfo.getOpenId());
//                            } else {
//                                logger.info("为student: {} 添加微信openId: {} 失败", authenticationInfo.getStudentId(), authenticationInfo.getOpenId());
//                            }
//                        }
//                        //更新session
//                        SecurityUtils.getSubject().getSession().setAttribute("student", authenticationInfo);
//                    } catch (Exception e) {
//                        logger.info("添加openId失败!");
//                        return JSONUtil.returnFailResult("添加openId失败!");
//                    }
//                }
//                    subject.getSession().setAttribute("teacher", (Teacher)subject.getPrincipal());
                return JSONUtil.returnEntityResult("JSESSIONID=" + subject.getSession().getId());
            } catch (AuthenticationException e) {
                logger.info("认证失败");
                return JSONUtil.returnFailResult("账号或密码错误");
            }

        }
        else {
            return JSONUtil.returnFailResult("验证失败! 请检查账号和密码是否正确, 或是否同意应用授权");
        }

    }


}
