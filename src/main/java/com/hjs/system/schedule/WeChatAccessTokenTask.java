package com.hjs.system.schedule;

import com.hjs.system.base.utils.WeChatCommentUtil;
import com.hjs.system.vo.WxAccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author 黄继升 16041321
 * @Description:   微信 accessToken 定时刷新器
 * @date Created in 2020/4/20 0:27
 * @Modified By:
 */

@Component
public class WeChatAccessTokenTask {

    private static final Logger logger = LoggerFactory.getLogger(WeChatAccessTokenTask.class);

    // 目前测试保存在内存中, 后面保存到redis看看
    private WxAccessToken wxAccessToken = null;

    @Autowired
    private WeChatCommentUtil weChatCommentUtil;

    // 第一次延迟1秒执行，当执行完后3600秒再执行
    @Scheduled(initialDelay = 1000, fixedDelay = 3600*1000 )
    public void refreshWeChatAccessToken(){
        try {
            // 刷新token
            synchronized (this) {
                wxAccessToken = weChatCommentUtil.getWxAccessToken();
            }
            logger.info("获取到的微信accessToken为: " + wxAccessToken.getAccess_token());

        } catch (Exception e) {
            logger.error("获取微信adcessToken出错: " +e.getMessage());
            this.refreshWeChatAccessToken();
            // 此处可能陷入死循环
        }

    }

    public synchronized String getWxAccessToken() {
        return this.wxAccessToken.getAccess_token();
    }

}
