package com.hjs.system.runnable;

import com.alibaba.fastjson.JSON;
import com.hjs.system.api.ApiUtil;
import com.hjs.system.vo.WxMssVo;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/5/3 1:32
 * @Modified By:
 */
public class SendMessage implements Runnable {

    private WxMssVo wxMssVo;

    private String accessToken;

    public SendMessage(WxMssVo wxMssVo, String accessToken) {
        this.wxMssVo = wxMssVo;
        this.accessToken = accessToken;
    }

    @Override
    public void run() {
        sendNewProcessMessage();
    }


    public String sendNewProcessMessage() {
        // 每次access_token 都从内存里取
        String url = ApiUtil.WX_SUBSCRIBE_MESSAGE_URL + "?access_token=" + accessToken;
        String json = JSON.toJSONString(wxMssVo);
        String result = ApiUtil.ApiPostRequest(url, json, null);
        return result;
    }
}
