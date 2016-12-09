package com.domob.hot.select.utils;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Utils {

    /**
     * 获取当前的日期
     */
    public static int getDt() {
        Date date = new Date();
        SimpleDateFormat dateFmtDt = new SimpleDateFormat("yyyyMMdd");
        String sDateDt = dateFmtDt.format(date);
        return Integer.parseInt(sDateDt);
    }

    /**
     * 获取当前的小时
     */
    public static int getHr() {
        Date date = new Date();
        SimpleDateFormat dateFmtHr = new SimpleDateFormat("HH");
        String sDateHr = dateFmtHr.format(date);
        return Integer.parseInt(sDateHr);
    }

    /**
     * 获取当前的时间戳，单位是秒
     */
    public static long now() {
        return System.currentTimeMillis() / 1000;
    }

}
