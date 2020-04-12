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
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @Autowired
    private StudentService studentServiceImpl;

    private static final Logger logger = LoggerFactory.getLogger(GitHubLoginController.class);

    private static final String STUDENT_LOGIN_TYPE = LoginType.STUDENT.toString();
    private static final String TEACHER_LOGIN_TYPE = LoginType.TEACHER.toString();
    private static final String FREE_LOGIN = LoginType.FreeLogin.toString();

    // web端 github授权登录
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
}
