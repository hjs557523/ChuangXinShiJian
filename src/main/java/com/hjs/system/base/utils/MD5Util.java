package com.hjs.system.base.utils;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/9 23:47
 * @Modified By:
 */
public class MD5Util {

    public static String getMd5HashPassword(int hashCount, String saltString, String password) {
        return new Md5Hash(password, saltString, hashCount).toHex();
    }

    public static void main(String[] args) {
        System.out.println(getMd5HashPassword(2,"16041321","557523"));
    }
}
