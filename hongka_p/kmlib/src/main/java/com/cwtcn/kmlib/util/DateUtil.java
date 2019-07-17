package com.cwtcn.kmlib.util;

import android.annotation.SuppressLint;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * DateUtil
 * Created by Allen on 2016/12/19.
 */

public class DateUtil {

    @SuppressLint("SimpleDateFormat")
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
    private static final SimpleDateFormat sdfDalaytime = new SimpleDateFormat("HHmmssSSS");
    private static final SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static String getDalayTimeId() {
        return sdfDalaytime.format(new Date());
    }

    public static String getCurrentTime() {
        return sdf.format(new Date());
    }

    public static String getTime(Date date) {
        return sdf1.format(date);
    }
}
