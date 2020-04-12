package com.hjs.system.config.webSocket;

import org.apache.catalina.session.StandardSessionFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

/**
 * @author 黄继升 16041321
 * @Description: 由于保存用户登录态的HttpSession和WebSocket的Session不是同一个Session，所以需要在这里对HttpSession进行获取后保存
 * @date Created in 2020/4/12 11:56
 * @Modified By:
 */

@Configuration
public class WebSocketConfig extends ServerEndpointConfig.Configurator {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        // 关键操作
        sec.getUserProperties().put(HttpSession.class.getName(), httpSession);
        logger.info("1. webSocket获取到的HttpSessionID：" + httpSession.getId());
    }

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
