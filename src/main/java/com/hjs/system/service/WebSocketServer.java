package com.hjs.system.service;

import com.alibaba.fastjson.JSONObject;
import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.base.utils.StringUtil;
import com.hjs.system.config.webSocket.WebSocketConfig;
import com.hjs.system.model.Subject;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/12 12:28
 * @Modified By:
 */

/* websocket是原型模式（用于创建重复的对象）,每次建立双向通信的时候都会创建一个实例,区别于spring的单例模式。虽然@Component默认是单例模式的，
   但springboot还是会为每个websocket连接初始化一个bean（@ServerEndPoint），所以可以用一个静态set保存起来。configurator必不可少，否则无法获取HttpSession*/
@ServerEndpoint(value = "/websocket/{userType}/{userId}", configurator = WebSocketConfig.class)
@Component
public class WebSocketServer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    // 统计在线人数。仅测试，这里应该设计为线程安全变量最好，但synchronized方法保证了同步性就ok了
    private static int onlineCount = 0;

    // concurrent包的线程安全Set，用来存放每个 客户端(同cookie)/用户(同cookie不同id) 对应的MyWebSocket对象。这里以用户作为区分对象，不以客户端
    // 存放教师实体
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap = new ConcurrentHashMap<>();

    // 存放学生实体
    private static ConcurrentHashMap<String, WebSocketServer> webSocketMap2 = new ConcurrentHashMap<>();

    // 将未读信息保存到内存里(后面有时间会采用消息中间件来缓存) 教师
    private static ConcurrentHashMap<String, List<String>> unReadMap = new ConcurrentHashMap<>();

    // 将未读信息保存到内存里(后面有时间会采用消息中间件来缓存) 学生
    private static ConcurrentHashMap<String, List<String>> unReadMap2 = new ConcurrentHashMap<>();

    // 与某个客户端的连接会话，需要通过它来给客户端发送数据。这个session是WebSocket创建的session，从0开始递增。不是HttpSession
    private Session session;

    // 接收userId
    private String userId = "";


    private String usertype = "";


    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }



    /**
     *  给用户类型为userType 的 userId 发送自定义消息
     * @param message
     * @param userId
     * @throws IOException
     */
    public static void sendInfo(String message, String usertype, String userId) throws IOException{
        logger.info("发送消息给: " + userId + "，报文: " + message);
        if (usertype.equals("teacher")) {
            // 如果接收消息的用户在线
            if (webSocketMap.containsKey(userId)) {
                webSocketMap.get(userId).sendMessage(message);

            } else if (!StringUtil.isEmpty(userId)){
                logger.info("用户 "+userId+" 不在线！");

                // 保存到消息队列中
                synchronized (unReadMap) {
                    List<String> messageList = unReadMap.get(userId);
                    if (messageList == null) {
                        messageList = new ArrayList<>();
                        unReadMap.put(userId, messageList);
                    }
                    messageList.add(message);
                }
            } else {
                logger.error("userId为空");
            }
        }
        else {
            // 如果接收消息的用户在线
            if (webSocketMap2.containsKey(userId)) {
                webSocketMap2.get(userId).sendMessage(message);

            } else if (!StringUtil.isEmpty(userId)){
                logger.info("用户 "+userId+" 不在线！");

                // 保存到消息队列中
                synchronized (unReadMap2) {
                    List<String> messageList = unReadMap2.get(userId);
                    if (messageList == null) {
                        messageList = new ArrayList<>();
                        unReadMap2.put(userId, messageList);
                    }
                    messageList.add(message);
                }
            } else {
                logger.error("userId为空");
            }
        }


    }


    /**
     * 服务器主动给当前用户推送消息
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);//同步方式发送消息
    }


    /**
     * 连接建立成功调用的方法
     * @param session
     * @param config
     * @param userId
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config, @PathParam("userType") String usertype, @PathParam("userId") String userId) {
        logger.info(usertype);
        logger.info(userId);

        this.session = session;
        this.userId = userId;
        this.usertype = usertype;
        logger.info("webSocket创建的sessionId是: " + session.getId());

        /* 两种方式可获得HttpSession */
        // 1、以当前webSocket session会话获取HttpSession
        //HttpSession httpSession = (HttpSession) session.getUserProperties().get(HttpSession.class.getName());

        // 2、通过方法参数config来获取HttpSession
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        logger.info("2. 获取到的HttpSessionId：" + httpSession.getId());

        if (usertype.equals("teacher")) {
            if (webSocketMap.containsKey(userId)) {
                webSocketMap.remove(userId); //防止重复连接
                webSocketMap.put(userId, this);
            } else {

                // 加入用户Map中:
                webSocketMap.put(userId, this);

                // 在线数+1：
                addOnlineCount();

            }

            logger.info(userId);
            logger.info("用户连接：" + userId + ", 当前在线人数：" + getOnlineCount());

            // 多线程并发冲突解决
            synchronized (unReadMap) {
                List<String> messageList = unReadMap.get(userId);
                if (messageList != null && !messageList.isEmpty()) { //查找未读消息
                    Iterator<String> iterator = messageList.iterator();
                    try {
                        while (iterator.hasNext()) {
                            sendMessage(iterator.next());
                            iterator.remove();
                            Thread.sleep(2000);
                        }
                    } catch (Exception e) {
                        logger.error("未读消息发送失败" + e.getMessage());
                    }
                }
                else {
                    logger.info("messageList为null");
                }
            }
        } else {
            if (webSocketMap2.containsKey(userId)) {
                webSocketMap2.remove(userId); //防止重复连接
                webSocketMap2.put(userId, this);
            } else {

                // 加入用户Map中:
                webSocketMap2.put(userId, this);

                // 在线数+1：
                addOnlineCount();

            }
            logger.info("用户连接：" + userId + ", 当前在线人数：" + getOnlineCount());

            // 多线程并发冲突解决
            synchronized (unReadMap2) {
                List<String> messageList = unReadMap2.get(userId);
                if (messageList != null && !messageList.isEmpty()) { //查找未读消息
                    Iterator<String> iterator = messageList.iterator();
                    try {
                        while (iterator.hasNext()) {
                            sendMessage(iterator.next());
                            iterator.remove();
                            Thread.sleep(2000);// 毎2s弹一次消息
                        }
                    } catch (Exception e) {
                        logger.error("未读消息发送失败" + e.getMessage());
                    }
                }
                else {
                    logger.info("messageList为null");
                }
            }
        }

    }



    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        if (this.usertype.equals("teacher")) {
            if (webSocketMap.containsKey(userId)) {
                webSocketMap.remove(userId);
                subOnlineCount();
            }
            logger.info("用户退出：" + userId + "，当前在线人数为：" + getOnlineCount());
        }
        else {
            if (webSocketMap2.containsKey(userId)) {
                webSocketMap2.remove(userId);
                subOnlineCount();
            }
            logger.info("用户退出：" + userId + "，当前在线人数为：" + getOnlineCount());
        }
    }


    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送的信息
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        logger.info("用户消息来自：类型:" + usertype + " id:" + userId + ", 报文：" + message);
        // 可以群发消息
        // 消息保存到数据库，redis
        logger.info("webSocket的sessionId是: " + session.getId());
        if (!StringUtil.isEmpty(message)) {
            try {
                JSONObject jsonObject = JSONObject.parseObject(message);
                // 追加发送人(防止串改)
                jsonObject.put("fromUserId", this.userId);
                String toUserId = jsonObject.getString("toUserId");
                String userType = jsonObject.getString("userType");
                // 传送给对应toUserId用户的websocket
                if (userType.equals("teacher")) {
                    if (webSocketMap.containsKey(toUserId)) {
                        // 向对方发送信息
                        webSocketMap.get(toUserId).sendMessage(jsonObject.toJSONString());

                    } else if (!StringUtil.isEmpty(toUserId)){
                        logger.info("请求的userId:"+toUserId+"暂时不在服务器上, 消息中间件执行缓存消息操作");


                        // 多线程并发 优化
                        synchronized (unReadMap) {
                            List<String> messageList = unReadMap.get(toUserId);
                            if (messageList == null) {
                                messageList = new ArrayList<String>();
                                unReadMap.put(toUserId, messageList);
                            }
                            messageList.add(message);
                        }

                    } else {

                        logger.error("没有指定userId");
                    }
                } else {
                    if (webSocketMap2.containsKey(toUserId)) {
                        // 向对方发送信息
                        webSocketMap2.get(toUserId).sendMessage(jsonObject.toJSONString());

                    } else if (!StringUtil.isEmpty(toUserId)){
                        logger.info("请求的userId:"+toUserId+"暂时不在服务器上, 开始执行缓存消息操作");


                        // 多线程并发 优化
                        synchronized (unReadMap2) {
                            List<String> messageList = unReadMap2.get(toUserId);
                            if (messageList == null) {
                                messageList = new ArrayList<String>();
                                unReadMap2.put(toUserId, messageList);
                            }
                            messageList.add(message);
                        }

                    } else {
                        logger.error("没有指定userId");
                    }
                }

            } catch (IOException e) {

                logger.info("网络异常: " + e.getMessage());
            }
        }
    }


    @OnError
    public void onError(Session session, Throwable error) {
        logger.error("用户错误: 类型=" + usertype + " id=" + this.userId + ", 原因:"+error.getMessage());
        error.printStackTrace();
    }
}
