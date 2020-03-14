package com.hjs.system.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hjs.system.base.utils.JSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author 黄继升 16041321
 * @Description: 自定义Api请求工具类，并提供github API v3版本 RESTFULL接口请求方法
 * @date Created in 2020/3/12 20:04
 * @Modified By:
 */
public class ApiUtil {

    private static final Logger logger = LoggerFactory.getLogger(ApiUtil.class);
    private final String url;
    public static final String GITHUB_HOST = "https://api.github.com";
    public static final String METHOD_POST = "POST";
    public static final String METHOD_GET = "GET";

    public ApiUtil(String url) {
        this.url = url;
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

}
