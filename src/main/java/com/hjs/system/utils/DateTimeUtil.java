package com.hjs.system.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2020/3/5 13:31
 * @Modified By:
 */
public class DateTimeUtil {
    public static final String DATETIMEFORMAT1 = "yyyyMMddHHmmssSSS";
    public static final String DATETIMEFORMAT2 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATETIMEFORMAT3 = "yyyy-MM-dd";

    public static final String getDateTimeStr_yyyyMMddHHmmssSSS() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATETIMEFORMAT1));
    }
}
