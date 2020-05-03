package com.hjs.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.hjs.system.api.ApiUtil;
import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.base.utils.MD5Util;
import com.hjs.system.base.utils.StringUtil;
import com.hjs.system.config.shiro.common.LoginType;
import com.hjs.system.config.shiro.common.UserToken;
import com.hjs.system.mapper.StudentMapper;
import com.hjs.system.model.Student;
import com.hjs.system.model.Teacher;
import com.hjs.system.service.StudentService;
import com.hjs.system.service.TeacherService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
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
import java.util.HashMap;
import java.util.Map;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/10 18:04
 * @Modified By:
 */

@Controller
public class GitHubLoginController {

    private static final Logger logger = LoggerFactory.getLogger(GitHubLoginController.class);
//    private static final String STUDENT_LOGIN_TYPE = LoginType.STUDENT.toString();
//    private static final String TEACHER_LOGIN_TYPE = LoginType.TEACHER.toString();
    private static final String FREE_LOGIN = LoginType.FreeLogin.toString();

    @Autowired
    private StudentService studentServiceImpl;

    @Autowired
    private TeacherService teacherServiceImpl;

    @Autowired
    private HttpServletRequest request;


    /**
     * web端 github第三方授权登录 OAuth2 方式
     * @param code
     * @param state
     * @return
     */
    @RequestMapping("/callback")
    public String githubUserLogin(@RequestParam("code") String code, @RequestParam("state") String state) {
        String userInfo = null;
        String responseStr = null;
        JSONObject githubUser = null;
        Student s = null;
        Teacher t = null;
        logger.info("code = " + code);
        logger.info("sate = " + state);
        if (StringUtil.isEmpty(code) || StringUtil.isEmpty(state)) {
            return JSONUtil.returnFailResult("登录失败");
        }

        Map<String, String> params = new HashMap<>();
        params.put("client_id", ApiUtil.CLIENT_ID);
        params.put("client_secret", ApiUtil.CLIENT_SECRET);
        params.put("code", code);
        params.put("redirect", ApiUtil.CALLBACK);

        while (StringUtil.isEmpty(responseStr)) {
            responseStr = ApiUtil.ApiGetRequest(ApiUtil.CODE_URL, params, null);
        }

        String access_token = ApiUtil.getMapForAccessToken(responseStr).get("access_token");

        logger.info("access_token = " + access_token);

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "token " + access_token);
        header.put("User-Agent", "创新实践课程管理系统");

        Map<String, String> params2 = new HashMap<>();
        params2.put("access_token", access_token);

        // 死循环保证请求到数据？？暂时没想出有其他思路
        while (StringUtil.isEmpty(userInfo)) {
            try {
                userInfo = ApiUtil.ApiGetRequest(ApiUtil.USER_INFO_URL, params2, header);
            } catch (Exception e) {
                logger.info("异常: " + e.getMessage());
            }
        }

        // 获取到github的用户信息
        githubUser = JSONObject.parseObject(userInfo);


        // 接下来执行github用户在本系统的登录操作
        if ((s = studentServiceImpl.findStudentByGitHubName(githubUser.getString("login"))) != null) {

            // 接下来执行登录操作
            String githubName = s.getGithubName();

            // Github免密登录
            UserToken userToken = new UserToken(githubName, s.getPassword(), FREE_LOGIN);
            userToken.setRememberMe(false);
            try {

                //开始验证后台用户数据，完成shiro认证
                Subject subject = SecurityUtils.getSubject();
                subject.login(userToken);
                SecurityUtils.getSubject().getSession().setAttribute(s.getStudentId(),  (Student) subject.getPrincipal());
                SecurityUtils.getSubject().getSession().setTimeout(3 * 60 * 60 * 1000);//3小时

                return "redirect:/index.html?current_user=" + ((Student) subject.getPrincipal()).getStudentId();

            } catch (Exception e) {
                logger.info("登录失败, 该github用户还没有绑定账号");
                return "redirect:/user/binding.html";
            }


        }
        //else if (教师表不为null)

        else {
            return "redirect:/user/binding.html";//跳转到绑定系统账号和github账号
            //然后临时保存github 用户信息到session里，再定义一个Controller接收绑定参数（根据userType在表中进行插入。。草！）
        }
    }




    /**
     * 微信小程序端 —— 微信 + github账号密码 双重认证方式登录（个人类型小程序不支持第三方webView跳转授权） 因此这里用Basic认证方式登录
     * @param base64Token
     * @param userType
     * @return
     */
    @RequestMapping(value = "/wx/githubLogin",  method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String githubUserLogin(@RequestParam("base64Token") String base64Token, @RequestParam("userType") Integer userType, @RequestParam("code") String code) {

        if (StringUtil.isEmpty(code) || StringUtil.isEmpty(base64Token))
            return JSONUtil.returnFailResult("验证信息不完整, 请重新填写登录!");

        // 先进行微信登录验证
        Map<String, String> params = new HashMap<>();
        params.put("appid", ApiUtil.WX_LOGIN_APPID);
        params.put("secret", ApiUtil.WX_LOGIN_SECRET);
        params.put("js_code", code);
        params.put("grant_type", ApiUtil.WX_LOGIN_GRANT_TYPE);

        // 发送请求
        String wxResult = ApiUtil.ApiGetRequest(ApiUtil.WX_LOGIN_URL, params, null);
        if (!StringUtil.isEmpty(wxResult)) {
            JSONObject jsonObject = JSONObject.parseObject(wxResult);
            // 获取返回微信用户的openId
            // String session_key = jsonObject.get("session_key").toString();
            String open_id = jsonObject.getString("openid");

            String githubUsername = null;
            String githubAvatarUrl = null;
            String userInfo = null;
            Student s = null;
            Teacher t = null;
            JSONObject githubUser = null;
            if (StringUtil.isEmpty(base64Token) || StringUtil.isEmpty(userType.toString())) {
                return JSONUtil.returnFailResult("token和userType不能为空!");
            }
            logger.info("Basic " + base64Token);

            Map<String, String> header = new HashMap<>();
            header.put("Authorization", "Basic " + base64Token);
            header.put("Content-Type", "application/json; charset=utf-8");
            header.put("Accept","application/json");
            header.put("Connection", "keep-Alive");
            header.put("User-Agent", "创新实践课程管理系统");


            // 请求github验证用户是否具有权限
            try {
                userInfo = ApiUtil.ApiGetRequest(ApiUtil.USER_INFO_URL, null, header);
                // 获取到github的用户信息
                githubUser = JSONObject.parseObject(userInfo);
                githubUsername = githubUser.getString("login");
                githubAvatarUrl = githubUser.getString("avatar_url");
            } catch (Exception e) {
                logger.info("异常: " + e.getMessage());
                return JSONUtil.returnFailResult("账号或密码错误");
            }

            if (githubUsername == null)
                return JSONUtil.returnFailResult("登录失败，Github没有当前用户!");

            // 接下来执行github用户在本系统的登录操作
            if (userType == 0) { //学生
                if ((s = studentServiceImpl.findStudentByGitHubName(githubUsername)) != null) { // 如果该github在本系统有账号

                    // 接下来执行登录操作, github用户免密登录
                    UserToken userToken = new UserToken(githubUsername, s.getPassword(), FREE_LOGIN);
                    userToken.setRememberMe(false);
                    try {
                        //开始验证后台用户数据，完成shiro认证
                        Subject subject = SecurityUtils.getSubject();
                        subject.login(userToken);
                        SecurityUtils.getSubject().getSession().setAttribute("Student",  (Student) subject.getPrincipal());
                        SecurityUtils.getSubject().getSession().setAttribute("token", base64Token);
                        SecurityUtils.getSubject().getSession().setTimeout(6 * 60 * 60 * 1000);//6小时

                        Map<String, Object> map = new HashMap<>();
                        map.put("cookie", "JSESSIONID=" + subject.getSession().getId());
                        map.put("userId", s.getSid());
                        return JSONUtil.returnEntityResult(map);// 返回sessionId给小程序前台缓存cookie

                    } catch (Exception e) {
                        logger.info("github学生登录失败，未知错误");
                        return JSONUtil.returnFailResult("登录异常, 请联系开发者处理");
                    }
                } else { //该github在本系统没有账号，需要进行绑定（插入），但是先缓存用户登录态信息
                    request.getSession().setAttribute("githubUserName", githubUsername);
                    request.getSession().setAttribute("githubAvatarUrl", githubAvatarUrl);
                    request.getSession().setAttribute("token", base64Token); //之后再进行github操作需要用到
                    request.getSession().setAttribute("userType", userType);
                    request.getSession().setAttribute("openId", open_id);
                    return JSONUtil.returnNoBindingResult("JSESSIONID=" + request.getSession().getId());
                }
            }

            else if (userType == 1) { //教师
                if ((t = teacherServiceImpl.findTeacherByGitHubName(githubUsername)) != null) {
                    UserToken userToken = new UserToken(githubUsername, t.getPassword(), FREE_LOGIN);
                    userToken.setRememberMe(false);
                    try {
                        Subject subject = SecurityUtils.getSubject();
                        subject.login(userToken);
                        SecurityUtils.getSubject().getSession().setAttribute("Teacher", (Teacher) subject.getPrincipal());
                        SecurityUtils.getSubject().getSession().setAttribute("token", base64Token);
                        SecurityUtils.getSubject().getSession().setTimeout(3 * 60 * 60 * 1000);

                        Map<String, Object> map = new HashMap<>();
                        map.put("cookie", "JSESSIONID=" + subject.getSession().getId());
                        map.put("userId", t.getTid());
                        return JSONUtil.returnEntityResult(map);

                    } catch (Exception e) {
                        logger.info("github教师登录失败, 未知错误");
                        return JSONUtil.returnFailResult("登录失败，服务器连接异常，请稍后再试");
                    }
                }

                else {
                    request.getSession().setAttribute("githubUserName", githubUsername);
                    request.getSession().setAttribute("token", base64Token);
                    request.getSession().setAttribute("userType", userType);
                    return JSONUtil.returnNoBindingResult("JSESSIONID=" + request.getSession().getId());
                }
            }

            else {
                return JSONUtil.returnFailResult("用户类型登录类型错误");
            }
        }
        else {
            return JSONUtil.returnFailResult("请检查code是否正确");
        }




    }


}
