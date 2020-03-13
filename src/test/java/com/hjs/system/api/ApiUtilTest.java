package com.hjs.system.api;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author 黄继升 16041321
 * @Description: Api测试类
 * @date Created in 2020/3/13 11:07
 * @Modified By:
 */
class ApiUtilTest {

    private static final Logger logger = LoggerFactory.getLogger(ApiUtilTest.class);
    private static final String OWNER = "hjs557523";
    private static final String REPO = "cxzhsj";
    private static final String ACCESS_TOKEN = "2b2623b8f657bbde8f667a8ad4f016d60084979c";

    @Test
    void getUserUrl() {
        logger.info("github api: {}", ApiUtil.getUserUrl(OWNER, REPO));
    }

    @Test
    void requestGithubApi() {
        String result = ApiUtil.requestGithubApi(ApiUtil.getUserUrl(OWNER, REPO), ApiUtil.METHOD_GET, ACCESS_TOKEN, null);
        logger.info("github api请求结果: {}", result);

    }
}