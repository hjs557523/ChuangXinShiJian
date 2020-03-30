package com.hjs.system.controller;

import com.hjs.system.base.utils.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/30 21:05
 * @Modified By:
 */

@Controller
public class TestImgController {

    private static final Logger logger = LoggerFactory.getLogger(TestImgController.class);

    @RequestMapping(value = "/img/post", method = RequestMethod.POST)
    @ResponseBody
    public String test() {
        logger.info("执行");
        return JSONUtil.returnSuccessResult("success");
    }


    @RequestMapping(value = "/img/get", method = RequestMethod.GET)
    @ResponseBody
    public String test2() {
        logger.info("执行2");
        return JSONUtil.returnSuccessResult("success");
    }
}
