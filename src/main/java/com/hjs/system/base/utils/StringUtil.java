package com.hjs.system.base.utils;

/**
 * @author 黄继升 16041321
 * @Description: 字符串相关方法的封装
 * @date Created in 2020/3/5 13:31
 * @Modified By:
 */
public class StringUtil {

    /**
     * 将以逗号分隔的字符串转换成字符串数组
     * @param valStr
     * @return
     */
    public static String[] StrList(String valStr) {
        int i = 0;
        int index;
        int length = valStr.length() - valStr.replace(",","").length() + 1;
        String[] returnStr = new String[length];
        valStr = valStr + ",";
        while ((index = valStr.indexOf(',')) > 0) {
            returnStr[i++] = valStr.substring(0, valStr.indexOf(','));
            valStr = valStr.substring(valStr.indexOf(',') + 1, valStr.length());
        }
        //System.out.println(index);
        return returnStr;
    }



    /**
     * 判断目标字符串是否为 null 或 length = 0
     * @param targetString
     * @return
     */
    public static boolean isEmpty(String targetString) {
        if (targetString == null || targetString.equals(""))
            return true;
        return false;
    }

    public static void main(String[] args) {
        String str = new String("Huang,Ji,Sheng");
        for (String s : StringUtil.StrList(str)) {
            System.out.println(s);
        }

    }
}
