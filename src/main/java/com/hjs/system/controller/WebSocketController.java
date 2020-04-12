package com.hjs.system.controller;

import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.service.WebSocketServer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/12 15:27
 * @Modified By:
 */

@RestController
public class WebSocketController {

    @RequestMapping("/push/{toUserId}")
    public String pushToWeb(String message, @PathVariable String toUserId) throws IOException {
        WebSocketServer.sendInfo(message,toUserId);
        return JSONUtil.returnSuccessResult("发送成功!");
    }
}
