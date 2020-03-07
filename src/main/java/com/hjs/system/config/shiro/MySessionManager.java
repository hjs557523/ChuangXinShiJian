package com.hjs.system.config.shiro;

import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;

/**
 * @author 黄继升 16041321
 * @Description: 传统结构项目中，shiro从cookie中读取sessionId以此来维持会话，在前后端分离的项目中（也可在移动APP项目使用），
 * 我们选择在ajax的请求头中传递sessionId，因此需要重写shiro获取sessionId的方式。
 * 自定义MySessionManager类继承DefaultWebSessionManager类，重写getSessionId方法
 * @date Created in 2020/3/7 13:00
 * @Modified By:
 */
public class MySessionManager extends DefaultWebSessionManager {
}
