package com.hjs.system.config.shiro.common;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author 黄继升 16041321
 * @Description: UsernamePasswordToken就是用户传过来的用户名和密码
 * @date Created in 2020/3/9 15:04
 * @Modified By:
 */
public class UserToken extends UsernamePasswordToken {

    //登录类型，判断是学生登录，教师登录还是管理员登录
    private String loginType;

    public UserToken(final String username, final String password, String loginType) {
        super(username, password);
        this.loginType = loginType;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }



}
