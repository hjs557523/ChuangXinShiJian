package com.hjs.system.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/29 13:31
 * @Modified By:
 */
public class TokenVerifyUtil {
    private static final Logger logger = LoggerFactory.getLogger(TokenVerifyUtil.class);

    public static boolean verifyLayUiAdminToken() {
        //取header，取cookie，取body，都没有access_token, 直接return false
        return true;
    }
}
