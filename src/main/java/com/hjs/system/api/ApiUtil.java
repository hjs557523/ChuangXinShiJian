package com.hjs.system.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hjs.system.base.utils.JSONUtil;
import com.hjs.system.vo.CommitStatistic;
import javafx.beans.binding.ObjectExpression;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 黄继升 16041321
 * @Description: 自定义Api请求工具类，并提供github API v3版本 RESTFULL接口请求方法
 * @date Created in 2020/3/12 20:04
 * @Modified By:
 */
public class ApiUtil {

    private static final Logger logger = LoggerFactory.getLogger(ApiUtil.class);

    // github 相关 API
    public static final String GITHUB_HOST = "https://api.github.com";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";


    /* 微信小程序登录相关 */
    public static final String WX_LOGIN_URL = "https://api.weixin.qq.com/sns/jscode2session";
    // appid
    public static final String WX_LOGIN_APPID = "wx55cd29c85957466a";
    // appid_secret
    public static final String WX_LOGIN_SECRET = "55888c4b9a6653ba667c3bab2ebb7dce";
    // 固定参数: 授权类型
    public static final String WX_LOGIN_GRANT_TYPE = "authorization_code";


    /* GitHub 第三方登录 */
    // GitHub注册应用的 CLIENT_ID
    public static final String CLIENT_ID = "fe7d32c06f96eafa8b08";

    // GitHub注册应用的 CLIENT_SECRET
    public static final String CLIENT_SECRET = "d58afc81838581ed16e5276d837beebd4fbcde73";

    // GitHub回调路径
    public static final String CALLBACK = "http://localhost:8080/callback";

    // 获取access_token的URL
    public static final String CODE_URL = "https://github.com/login/oauth/access_token";

    // 获取用户信息的url
    public static final String USER_INFO_URL = "https://api.github.com/user";


    // 获取某个repo中所有的commits列表: https://api.github.com/repos/用户名/仓库名/commits
    public static final String REPO_ALL_COMMIT_URL = "https://api.github.com/repos/%s/%s/commits";


    // 获取某一条commit的详情: https://api.github.com/repos/用户名/仓库名/commits/某一条commit的SHA
    public static final String ONE_COMMIT_DETAIL_URL = "https://api.github.com/repos/%s/%s/commits/%s";


    public static final String REPO_ALL_ISSUES_URL = "https://api.github.com/repos/%s/%s/issues";
    // GET 获取某个repo中的所有issues列表：https://api.github.com/repos/用户名/仓库名/issues
    // POST 追加issue
    /**
     * 追加ISSUE
     * JSON格式：
     * {
     *     "title": "Creating issue from API",
     *     "body": "Posting a issue from Insomia",
     *     "label": ["bug",'',...] 添加标签
     *     "assignee": ['hjs557523', ''] 指定执行人
     *
     * }
     */


    // 获取某一条issues的详情：https://api.github.com/repos/用户名/仓库名/issues/序号。issues都是以1,2,3这样的序列排号的
    public static final String ONE_ISSUE_DETAIL_URL = "https://api.github.com/repos/%s/%s/issues/%s";
    // Update issue
    // PATCH /repos/:owner/:repo/issues/:issue_number参数同上
    // 锁定 issue
    // PUT /repos/:owner/:repo/issues/:issue_number/lock
    // {
    //   "locked": true,
    //   "active_lock_reason": "too heated"  off-topic too heated resolved spam
    // }
    // 解锁：DELETE /repos/:owner/:repo/issues/:issue_number/lock



    // 获取受让人列表：GET /repos/:owner/:repo/assignees
    // 检查用户是否为受让人：GET /repos/:owner/:repo/assignees/:assignee
    // 将受让人添加到问题：POST /repos/:owner/:repo/issues/:issue_number/assignees
    // {
    //     "assignees": [
    //     "hubot",
    //     "other_user"
    //    ]
    //  }
    //
    // 从问题中删除受让人：DELETE /repos/:owner/:repo/issues/:issue_number/assignees
    // {
    //  "assignees": [
    //    "hubot",
    //    "other_user"
    //  ]
    //}
    public static final String ALL_ASSIGNEES_URL = "https://api.github.com/repos/%s/%s/assigness";

    // 获取repo所有issue下的comment
    public static final String REPO_ALL_COMMENT_URL = "https://api.github.com/repos/%s/%s/issues/comments";


    // 获取某repo某issue中的comments列表。https://api.github.com/repos/用户名/仓库名/issues/序号/comments
    public static final String ISSUE_ALL_COMMENT_URL = "https://api.github.com/repos/%s/%s/issues/%s/comments";
    // 创建comment：
    // POST /repos/:owner/:repo/issues/:issue_number/comments
    // {
    //  "body": "Me too"
    // }
    //
    // 修改comment
    // PATCH /repos/:owner/:repo/issues/comments/:comment_id
    //
    // 删除comment
    // DELETE /repos/:owner/:repo/issues/comments/:comment_id

    // 获取某一条comment详情。https://api.github.com/repos/用户名/仓库名/issues/comments/评论详情的ID。其中评论ID是从issues列表中获得的
    public static final String ONE_COMMENT_DETAIL_URL = "https://api.github.com/repos/%s/%s/issues/comments/%s";


    // 列出仓库团队的所有协作者
    public static final String ALL_COLLABORATORS_URL = "https://api.github.com/repos/%s/%s/collaborators";


    public static final String IS_COLLABORATOR_URL = "https://api.github.com/repos/%s/%s/collaborators/%s";
    // 判断用户是否为协作者:
    // GET /repos/:owner/:repo/collaborators/:username
    // 响应：
    // 合作者：Status: 204 No Content
    // 非合作者：Status: 404 Not Found

    // 添加用户为协作者：
    // PUT /repos/:owner/:repo/collaborators/:username
    // 参数：permission 类型：string 值: pull push admin maintain triage，默认push

    // 删除协作者
    // DELETE /repos/:owner/:repo/collaborators/:username
    // 响应：
    // Status: 204 No Content

    public static final String REPO_ALL_LABELS_URL = "https://api.github.com/repos/%s/%s/labels";


    // 统计添加，删除，和提交的贡献者列表
    public static final String REPO_ALL_COMMIT_STATS = "https://api.github.com/repos/%s/%s/stats/contributors";




    /* gitLab */




    public static Map<String, String> getMapForAccessToken(String response) {
        Map<String, String> map = new HashMap<>();
        // 以&来解析字符串
        String[] result = response.split("\\&");

        for (String str : result) {
            // 以=来解析字符串
            String[] split = str.split("=");
            // 将字符串存入map中
            if (split.length == 1)
                map.put(split[0], null);
            else
                map.put(split[0], split[1]);

        }
        return map;
    }



    public static String ApiGetRequest(String url, Map<String, String> params, Map<String, String> header) {
        //创建HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String resultString = "";
        CloseableHttpResponse response = null;

        try {
            //创建URI
            URIBuilder builder = new URIBuilder(url);
            if (params != null) {
                for (String key : params.keySet()) {
                    builder.addParameter(key, params.get(key));
                }
            }
            URI uri = builder.build();

            //创建http GET请求
            HttpGet httpGet = new HttpGet(uri);

            if (header!=null) {
                for (String key : header.keySet()) {
                    httpGet.addHeader(key, header.get(key));
                }
            }


            //执行请求
            response = httpClient.execute(httpGet);//这里可能会抛出异常

            //判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {//200
                resultString = EntityUtils.toString(response.getEntity(), "UTF-8");

            }

        } catch (Exception e) {
            logger.info("get请求接口出现错误: " + e.getMessage());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                logger.info("关闭response出现错误: " + e.getMessage());
            }
        }
        return resultString;
    }


    public static String ApiGetRequest(String url) {
        return ApiGetRequest(url, null, null);
    }


    public static String ApiPostRequest(String url, Map<String, String> params, Map<String, String> header) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            HttpPost httpPost = new HttpPost(url);

            if (params != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : params.keySet()) {
                    paramList.add(new BasicNameValuePair(key, params.get(key)));
                }
                //模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }


            if (header!=null) {
                for (String key : header.keySet()) {
                    httpPost.addHeader(key, header.get(key));
                }
            }

            //执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");

        } catch (Exception e) {
            logger.info("post请求接口出现错误: " + e.getMessage());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                logger.info("关闭response出现错误: " + e.getMessage());
            }
        }

        return resultString;
    }




    public static String ApiPostRequest(String url, String json, Map<String, String> header) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            entity.setContentEncoding("UTF-8");
            httpPost.setEntity(entity);

            if (header!=null) {
                for (String key : header.keySet()) {
                    httpPost.addHeader(key, header.get(key));
                }
            }

            //执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");

        } catch (Exception e) {
            logger.info("post请求接口出现错误: " + e.getMessage());
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                httpClient.close();
            } catch (IOException e) {
                logger.info("关闭response出现错误: " + e.getMessage());
            }
        }

        return resultString;
    }



    public static String wxApiPostRequestJSON(String url, String json) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            HttpPost httpPost = new HttpPost(url);
            StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            logger.info("post请求微信接口出现错误: " + e.getMessage());
        } finally {
            try {
                if (response != null)
                    response.close();
                httpClient.close();
            } catch (IOException e) {
                logger.info("关闭response出现错误: " + e.getMessage());
            }
        }

        return resultString;
    }


    //获取用户user的某个仓库信息
    public static String getUserRepoUrl(String owner, String repo) {
        return GITHUB_HOST + String.format("/repos/%s/%s", owner, repo);
    }


    /**
     * 对Github请求方式进行封装, 并返回响应结果, 返回结果重新封装, 以JSON格式。这是一个阻塞方法
     * @param url    请求接口路径
     * @param method 请求方式: GET/POST
     * @param data   上传数据: JSON/null
     * @return       响应结果以JSONUtil工具类进行重新封装成指定JSON格式的字符串
     *               使用时需根据实际情况强制类型转换
     */
    public static String requestGithubApi(String url, String method, String access_token, String data) {
        HttpURLConnection conn = null;
        BufferedReader in = null;

        try {
            URL urlObject = new URL(url);
            conn = (HttpURLConnection) urlObject.openConnection();
            conn.setConnectTimeout(120000);
            conn.setReadTimeout(120000);
            conn.setDoInput(true);//是否需要从接口接收数据，默认为true
            conn.setDoOutput(true);//是否需要往接口传输数据，默认为false
            conn.setUseCaches(false);//注意如果是POST请求，是不允许使用缓存的。这里无论什么方式直接就false
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("Authorization", "token " + access_token);
            conn.setRequestProperty("User-Agent", "Hjs's Graduation Project");//所有API请求都必须包含有效的User-Agent标头, 否则将会被拒绝
            try {
                if (method.equalsIgnoreCase(ApiUtil.METHOD_POST)) {
                    conn.setRequestMethod(ApiUtil.METHOD_POST);
                    conn.connect();
                    // post方法有待完成
                    // 400 发送了无效JSON、错误类型JSON值
                    // 422 无效字段
                    // in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    return null;
                } else if (method.equalsIgnoreCase(ApiUtil.METHOD_GET)) {
                    conn.setRequestMethod(ApiUtil.METHOD_GET);
                    try {
                        conn.connect();//只建立tcp连接，并没有发送http请求
                    } catch (IOException e) {
                        e.printStackTrace();
                        return JSONUtil.returnFailResult("connect连接失败, 可能被墙了");
                    }
                    int code = conn.getResponseCode();
                    if ((code == HttpURLConnection.HTTP_OK) || (conn.getResponseCode() == HttpURLConnection.HTTP_CREATED) || (conn.getResponseCode() == HttpURLConnection.HTTP_ACCEPTED)) {
                        // 当返回不是HttpURLConnection.HTTP_OK， HttpURLConnection.HTTP_CREATED， HttpURLConnection.HTTP_ACCEPTED 时，
                        // 不能用getInputStream()，而是应该用getErrorStream()
                        in = new BufferedReader(new InputStreamReader(conn.getInputStream()));//网上说这里才实际发送请求???
                        String line;
                        StringBuffer result = new StringBuffer();//使用StringBuffer在同一个对象上拼接字符串，防止创建过多String对象
                        while ((line = in.readLine()) != null) {
                            result.append(line);
                        }
                        if (result.length() <= 0) {
                            return JSONUtil.returnSuccessResult("没有数据记录");
                        }
                        in.close();
                        return JSONUtil.returnGithubApiDataResult(result);

                    } else if (code == HttpURLConnection.HTTP_UNAUTHORIZED) { //401
                        in.close();
                        return JSONUtil.returnFailResult("github api请求未经授权! 请检查access_token");
                    } else if (code == HttpURLConnection.HTTP_FORBIDDEN) {    //403
                        in.close();
                        return JSONUtil.returnFailResult("github api请求已超过最大登录尝试次数, 请稍后再试! 或请检查access_token");
                    } else if (code == HttpURLConnection.HTTP_NOT_FOUND) {    //404
                        in.close();
                        return JSONUtil.returnFailResult("没有查找到github api对应的资源，或者您没有权限访问该存储私有库!");
                    } else {
                        in.close();
                        return JSONUtil.returnFailResult("请求失败1!");
                    }

                } else {
                    return JSONUtil.returnFailResult("请求方式有误, 请使用GET/POST传递数据");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return JSONUtil.returnFailResult("请求失败2");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return JSONUtil.returnFailResult("请求失败3!");
        } finally {
        }

    }


    public static List getRepoAllLabels(String githubName, String repo, String accessToken) {
        List<String> list = new ArrayList<>();
        String url = String.format(ApiUtil.REPO_ALL_LABELS_URL, new Object[]{githubName, repo});

        Map<String, String> header = new HashMap<>();

        header.put("Authorization", "Basic " + accessToken);
        header.put("Content-Type", "application/json; charset=utf-8");
        header.put("Connection", "keep-Alive");
        header.put("User-Agent", githubName);

        String result = ApiGetRequest(url, null, header);
        try {
            JSONArray jsonArray = JSONArray.parseArray(result);
            for (Object item : jsonArray) {
                list.add(((JSONObject)item).getString("name"));
            }
        } catch (Exception e) {
            return null;
        }

        return list;

    }


    /**
     * 代码统计 + commit统计
     * @param ownerGitHub
     * @param repo
     * @param accessToken
     * @param myGitHub
     * @return
     */
    public static List getMemberCommit(String ownerGitHub, String repo, String accessToken, String myGitHub) {
        List<CommitStatistic> list = new ArrayList<>();
        String url = String.format(ApiUtil.REPO_ALL_COMMIT_STATS, new Object[]{ownerGitHub, repo});
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Basic " + accessToken);
        header.put("Content-Type", "application/json; charset=utf-8");
        header.put("Connection", "keep-Alive");
        header.put("User-Agent", myGitHub);

        String result = ApiGetRequest(url, null, header);
        try {
            JSONArray jsonArray = JSONArray.parseArray(result);
            for (Object item : jsonArray) {
                JSONObject jsonObject = (JSONObject) item;
                CommitStatistic commitStatistic = new CommitStatistic();
                commitStatistic.setTotalCodeNum(0);
                List<Integer> weekCodeNum = new ArrayList<>();
                Integer total = jsonObject.getInteger("total");
                JSONArray weekArray = jsonObject.getJSONArray("weeks");
                for (Object week : weekArray) {
                    JSONObject weekObject = (JSONObject) week;
                    Integer add = weekObject.getInteger("a");
                    Integer del = weekObject.getInteger("d");
                    commitStatistic.setTotalCodeNum(commitStatistic.getTotalCodeNum() + add - del);
                    weekCodeNum.add(add - del);
                }
                commitStatistic.setWeekCodeNum(weekCodeNum);
                JSONObject author = jsonObject.getJSONObject("author");
                String githubName = author.getString("login");
                commitStatistic.setTotalCommit(total);
                commitStatistic.setGithubName(githubName);
                list.add(commitStatistic);
            }

            return list;

        } catch (Exception e) {
            logger.info("产生异常: " + e.getMessage());
            return null;
        }

    }

}
