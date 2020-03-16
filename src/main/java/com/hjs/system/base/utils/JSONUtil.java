package com.hjs.system.base.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hjs.system.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/8 18:37
 * @Modified By:
 */
public class JSONUtil {

    private static final Logger logger = LoggerFactory.getLogger(JSONUtil.class);

    public static String returnJSONResult(String code, String message, Object entity) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("message", message);
        map.put("entity", entity);
        logger.info("返回字符串: " + JSONObject.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty));

//        //alibaba 的fastjson默认会把null值滤去
//        logger.info("返回字符串: " + JSONObject.toJSONString(map));
//        logger.info("返回字符串: " + JSON.toJSONString(map));
//        //WriteNullStringAsEmpty和WriteMapNullValue均可以将为null的属性显示出来，而不是默认被忽略掉
//        logger.info("返回字符串: " + JSON.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty));
//        logger.info("返回字符串: " + JSON.toJSONString(map, SerializerFeature.WriteMapNullValue));

          //net.sf.json 对JSON的处理
//        logger.info("返回字符串: " + net.sf.json.JSONObject.fromObject(map).toString());
        return JSON.toJSONString(map);
    }


    //设置返回失败消息JSON字符串
    public static String returnFailResult(String message) {
        return returnJSONResult("0", message, null);
    }

    //设置返回成功消息JSON字符串
    public static String returnSuccessResult(String message) {
        return returnJSONResult("200", message, null);
    }

    //设置返回403权限不足JSON字符串
    public static String returnForbiddenResult() {
        return returnJSONResult("403", "权限不足", null);
    }

    //设置返回实体JSON字符串
    public static String returnEntityResult(Object entity) {
        return returnJSONResult("200", "返回数据", entity);

    }

    //对接收到的github api json数据的特殊处理
    public static String returnGithubApiDataResult(StringBuffer json) {
        StringBuffer result = new StringBuffer("{\"code\":\"200\",\"message\":null,\"entity\":");
        result.append(json);
        return result.toString();
    }

    public static void main(String[] args) {
        returnEntityResult(new Integer(1));
        returnEntityResult(new StringBuffer("hjs"));
        returnEntityResult(new Student());
    }

//    public static void test() {
//        JSONArray arr = JSON.parseArray("{id:16041321, key:557523}");
//        System.out.println(arr.size());
//    }
}
