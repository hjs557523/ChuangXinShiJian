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
    private static final String REPO = "cxsj3";
    private static final String ACCESS_TOKEN = "";

    @Test
    void getUserUrl() {
        logger.info("github api: {}", ApiUtil.getUserRepoUrl(OWNER, REPO));
    }

    @Test
    void requestGithubApi() {
        while (true) {
            String result = ApiUtil.requestGithubApi(ApiUtil.getUserRepoUrl(OWNER, REPO), ApiUtil.METHOD_GET, ACCESS_TOKEN, null);
            logger.info("github api请求结果: {}", result);
        }

    }
}