package com.hjs.system.base.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.hjs.system.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/8 18:37
 * @Modified By:
 */
public class JSONUtil {

    private static final Logger logger = LoggerFactory.getLogger(JSONUtil.class);

    public static String returnJSONResult(Integer code, String message, Object entity) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("msg", message);
        map.put("data", entity);
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


    public static String returnJSONResult(Integer code, String message, Integer count, Object entity) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("msg", message);
        map.put("count", count);
        map.put("data", entity);
        logger.info("返回字符串: " + JSONObject.toJSONString(map, SerializerFeature.WriteNullStringAsEmpty));
        return JSON.toJSONString(map);
    }


    // 设置返回失败消息JSON字符串 101
    public static String returnFailResult(String message) {
        return returnJSONResult(101, message, null);
    }


    // 设置返回成功消息JSON字符串 0
    public static String returnSuccessResult(String message) {
        return returnJSONResult(0, message, null);
    }



    // 设置返回实体JSON字符串 0
    public static String returnEntityResult(Object entity) {
        return returnJSONResult(0, "返回数据", entity);
    }



    // 设置返回登录状态失效的JSON字符串 1001
    public static String returnForbiddenResult(String message) { return returnJSONResult(1001, message, null); }


    // 未绑定账号 1111
    public static String returnNoBindingResult(Object entity) {
        return JSONUtil.returnJSONResult(1111, "github账号未绑定本系统账号，请先绑定！", entity);
    }


    //设置返回分页数据JSON字符串
    public static String returnEntityResult(Integer count, String msg, Object entity) {
        return returnJSONResult(0, msg, count, entity);

    }

    //对接收到的github api json数据的特殊处理
    public static String returnGithubApiDataResult(StringBuffer json) {
        StringBuffer result = new StringBuffer("{\"code\":\"200\",\"message\":null,\"entity\":");
        result.append(json);
        return result.toString();
    }


    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();
        list.add(new Student());
        list.add(new Student());
        returnEntityResult(2, "结果如下", list);
        returnEntityResult(new StringBuffer("hjs"));
        returnEntityResult(new Student());
    }

//    public static void test() {
//        JSONArray arr = JSON.parseArray("{id:16041321, key:557523}");
//        System.out.println(arr.size());
//    }
}
