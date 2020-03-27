package com.hjs.system.controller;

import com.hjs.system.base.utils.JSONUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/15 16:17
 * @Modified By:
 */

@Controller
public class LogoutController {

    private static final Logger logger = LoggerFactory.getLogger(LogoutController.class);

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    @ResponseBody
    public String logout() {
        try {
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            logger.info("执行了logout(), logout成功!");
            return JSONUtil.returnSuccessResult("退出成功!");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("执行了logout(), logout异常!");
            return JSONUtil.returnFailResult("退出失败");
        }
    }

}
