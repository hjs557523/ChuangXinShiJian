package com.hjs.system.base.utils;

import com.alibaba.fastjson.JSONObject;
import com.hjs.system.api.ApiUtil;
import com.hjs.system.vo.WxAccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 黄继升 16041321
 * @Description: 微信共用和常用的操作类
 * @date Created in 2020/5/2 20:30
 * @Modified By:
 */
@Component
public class WeChatCommentUtil {

    private static final Logger logger = LoggerFactory.getLogger(WeChatCommentUtil.class);

    public WxAccessToken getWxAccessToken() {
        WxAccessToken wxAccessToken = null;
        // 访问微信服务器接口
        Map<String, String> params = new HashMap<>();
        params.put("grant_type", ApiUtil.WX_ACCESS_TOKEN_GRANT_TYPE);
        params.put("appid", ApiUtil.WX_LOGIN_APPID);
        params.put("secret", ApiUtil.WX_LOGIN_SECRET);

        String result = ApiUtil.ApiGetRequest(ApiUtil.WX_ACCESS_TOKEN_URL, params, null);
        if (!StringUtil.isEmpty(result)) {
            JSONObject jsonObject = JSONObject.parseObject(result);
            try {
                String accessToken = jsonObject.getString("access_token");
                Integer expiresIn = jsonObject.getInteger("expires_in");
                wxAccessToken = new WxAccessToken();
                wxAccessToken.setAccess_token(accessToken);
                wxAccessToken.setExpires_in(expiresIn);
            } catch (Exception e) {
                logger.info("解析异常: " + e.getMessage());
            }
        } else {
            logger.info("获取token失败");
        }

        return wxAccessToken;
    }
}
