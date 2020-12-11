package com.haozi.ttqk.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

    /**
     * 按指定模式转换时间
    * @Description
    * @author shisong
    * @date 16:58 2018/1/16
    * @modifyNote
    * @param
    * @return
    */
    public static String dateToString(Date date,String format){
        if(null != date){
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }else{
            return "";
        }
    }

    /**
     * 按指定模式转换时间
     * @Description
     * @author shisong
     * @date 16:58 2018/1/16
     * @modifyNote
     * @param
     * @return
     */
    public static String dateToFormatString(Date date){
        String pattern = "yyyy-MM-dd HH:mm:ss";
        return dateToString(date,pattern);
    }
    /**
     * 按指定模式转换时间，不含秒
     * @Description
     * @author shisong
     * @date 16:58 2018/1/16
     * @modifyNote
     * @param
     * @return
     */
    public static String dateToFormatStringNotSecond(Date date){
        String pattern = "yyyy-MM-dd HH:mm";
        return dateToString(date,pattern);
    }

    /**
     * @param strDate
     * @return java.util.Date
     */
    public static Date parseDateSecondFormat(String strDate) {
        String pattern = "yyyy-MM-dd HH:mm:ss";
        return parseDateFormat(strDate, pattern);
    }

    /**
     * @param strDate
     * @param pattern
     * @return java.util.Date
     */
    public static Date parseDateFormat(String strDate, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        Date date = null;
        sdf.applyPattern(pattern);
        try {
            date = sdf.parse(strDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;

    }
}
