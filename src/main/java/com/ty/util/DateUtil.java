package com.ty.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期工具类
 *
 * @Author Tommy
 * @Date 2025/12/3
 */
public class DateUtil {

    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

    public static String now() {
        Date date = new Date();
        return DATE_FORMAT.format(date);
    }
}
