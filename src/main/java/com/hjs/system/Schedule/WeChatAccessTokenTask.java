package com.hjs.system.Schedule;

import org.springframework.stereotype.Component;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/4/20 0:27
 * @Modified By:
 */

//@Component
//public class WeChatAccessTokenTask {
//
//    @Autowired
//    private WeixinCommenUtil weixinCommenUtil;
//
//    // 第一次延迟1秒执行，当执行完后7100秒再执行
//    @Scheduled(initialDelay = 1000, fixedDelay = 7000*1000 )
//    public void getWeixinAccessToken(){
//
//        try {
//
//            String token = weixinCommenUtil.getToken(WeixinConstants.APPID, WeixinConstants.APPSECRET).getAccess_token();
//            WeixinAccessToken.setToken(token);
//            logger.info("获取到的微信accessToken为"+token);
//
//        } catch (Exception e) {
//            logger.error("获取微信adcessToken出错，信息如下");
//            e.printStackTrace();
//            this.getWeixinAccessToken();
//            // 此处可能陷入死循环
//        }
//
//    }
//
//}
