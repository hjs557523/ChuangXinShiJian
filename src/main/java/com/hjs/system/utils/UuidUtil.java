package com.hjs.system.utils;

import java.util.UUID;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/5 14:30
 * @Modified By:
 */
public class UuidUtil {

    public static String get32UUID() {
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-","");
        return uuid;
    }

    public static void main(String[] args) {
        System.out.println(get32UUID());
    }
}
