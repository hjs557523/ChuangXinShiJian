package com.hjs.system.controller;

import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.service.WebSocketServer;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/12 15:27
 * @Modified By:
 */

@RestController
public class WebSocketController {

    @RequestMapping(value = "/push/{userType}/{toUserId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public String pushToWeb(@RequestParam("message") String message, @PathVariable("userType") String userType, @PathVariable("toUserId") String toUserId) throws IOException {
        WebSocketServer.sendInfo(message, userType, toUserId);
        return JSONUtil.returnSuccessResult("发送成功!");
    }
}
