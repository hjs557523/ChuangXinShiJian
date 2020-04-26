package com.hjs.system.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.mountcloud.graphql.GraphqlClient;
import org.mountcloud.graphql.request.query.DefaultGraphqlQuery;
import org.mountcloud.graphql.request.query.GraphqlQuery;
import org.mountcloud.graphql.response.GraphqlResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

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

    @Test
    void githubGraphQlApiTest() {

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type","application/json");
        header.put("Authorization", "token " + GraphQLApiUtils.MY_ACCESS_TOKEN);
        header.put("connection", "Keep-Alive");
        header.put("User-Agent", "hjs557523");

        GraphqlClient graphqlClient = GraphqlClient.buildGraphqlClient(GraphQLApiUtils.GITHUB_GRAPHQL_URL);
        graphqlClient.setHttpHeaders(header);
        GraphqlQuery query = new DefaultGraphqlQuery("viewer");
        query.addResultAttributes("login");
        try {
            GraphqlResponse response = graphqlClient.doQuery(query);
            Map result = response.getData();
            logger.info(result.toString());
        } catch (Exception e) {
            logger.info("error");
        }

    }


    @Test
    void githubApiTest() {

        Map<String, String> header = new HashMap<>();

        header.put("Authorization", "token " + GraphQLApiUtils.MY_ACCESS_TOKEN);
        header.put("Content-Type", "application/json; charset=utf-8");
        header.put("Connection", "keep-Alive");
        header.put("User-Agent", "创新实践课程管理系统");
        String realUrl = String.format(ApiUtil.ONE_COMMIT_DETAIL_URL, new Object[]{"hjs557523","GraduationProject-MiniProgram","03bd3ea32a591394a444cf452c47941537b7be33"});
        logger.info(realUrl);
        try {
            String result = ApiUtil.ApiGetRequest(realUrl, null, header);
//            JSONArray jsonArray = JSONObject.parseArray(result);
//            for (Object jsonObject: jsonArray) {
//                logger.info(jsonObject.toString());
//            }
            logger.info(result);

        } catch (Exception e) {
            logger.info("error");
        }

    }


    @Test
    void test() {
        logger.info(ApiUtil.getRepoAllLabels("hjs557523", "GraduationProject", "aGpzNTU3NTIzOmpJU0hFTkcxOTk3MTAzMQ==").toString());
    }


}