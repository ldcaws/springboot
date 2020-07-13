package com.ldc.sftpdemo.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @description:
 * @author: ss
 * @time: 2020/7/6 22:56
 */
public class DateUtil {

    public static String DATE_FROMMAT_YMD = "yyyyMMdd";
    public static String DATE_CONNECT_FROMMAT_YMD = "yyyy-MM-dd";
    public static String DATE_FROMMAT_YMDHMS = "yyyyMMddHHmmss";
    public static String DATE_CONNECT_FROMMAT_YMD_YMDHMS = "yyyy-MM-dd HH:mm:ss";
    public static String DATE_CONNECT_FROMMAT_YMD_YMDHMS_EXT = "yyyyMMdd HH:mm:ss";
    public static String DATE_FROMMAT_YMDHMSS = "yyyyMMddHHmmssSSS";

    private static SimpleDateFormat getFormat(String pattern){
        return new SimpleDateFormat(pattern);
    }

    /**
     * 根据模板获取给定日期的格式结果
     *
     * @param date 日期类型
     * @param pattern 模板
     * @return
     */
    public static String formatDateByPattern(Date date, String pattern){
        return getFormat(pattern).format(date);
    }

    /**
     * 根据模板获取给定日期的格式结果
     *
     * @param date 时间戳
     * @param pattern 模板
     * @return
     */
    public static String formateDateByPattern(long date,String pattern){
        return getFormat(pattern).format(new Date(date));
    }

    /**
     * 根据模板获取当前时间的格式结果
     *
     * @param pattern 模板
     * @return
     */
    public static String formateNowByPattern(String pattern){
        return getFormat(pattern).format(new Date());
    }

    /**
     * 将给定时间字符串转换日志格式
     *
     * @param date 日期(字符串)
     * @param pattern 模板
     * @return
     */
    public static Date formateStrToDate(String date,String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(date);
        } catch (ParseException e) {
            throw new RuntimeException("字符串格式化成日期");
        }
    }

    /**
     * 获取当前的最后时间点
     *
     * @param date 日期(2018-04-09)
     * @return
     */
    public static Date formateStartTime(String date) {
        return formateStrToDate(date+" 00:00:00",DATE_CONNECT_FROMMAT_YMD_YMDHMS_EXT);
    }

    /**
     * 获取当前的起始时间点
     *
     * @param date 日期(2018-04-09)
     * @return
     */
    public static Date formateEndTime(String date) {
        return formateStrToDate(date+" 23:59:59",DATE_CONNECT_FROMMAT_YMD_YMDHMS_EXT);
    }
}
