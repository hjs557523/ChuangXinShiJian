package com.hjs.system.vo;

import java.io.Serializable;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/5/2 21:01
 * @Modified By:
 */
public class WxAccessToken implements Serializable {

    private static final long serialVersionUID = 1L;

    private String access_token;

    private Integer expires_in;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    @Override
    public String toString() {
        return "WxAccessToken{" +
                "access_token='" + access_token + '\'' +
                ", expires_in=" + expires_in +
                '}';
    }
}
