package com.cwtcn.kmlib.util;

import android.os.Environment;

/**
 * Created by Allen on 2016/12/6.
 */

public interface Constants {

    /** 手机的系统 */
    public static final String OS = "Android " + android.os.Build.VERSION.RELEASE;
    /** 协议版本 */
    public static final String PROTOCAL_VERSION = "1.0.2";

    /** 定义SD卡根目录 */
    public static final String SD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String PATH_ROOT = SD_ROOT + "/kidmate/";
    public static final String IMAGE_SAVE = PATH_ROOT + "images/";
    public static final String HEAD_PATH = IMAGE_SAVE + "head/";

    // 心跳周期
    long HEARBEAT = -1;
    // 10秒钟重新连接socket周期
    long RECONNECT_PERIOD = 10 * 1000L;
    // 5分钟重新连接
    long RECONNECT_PERIOD1 = 5 * 60 * 1000L;
    //socket 超时时间
    int TIME_OUT = 20 * 1000;

    /** 数据包头开始标志 CWT */
    byte[] PACKAGE_HEADER_FLAG = { 'C', 'W', 'T' };
    byte[] HEART_BEAT_RETURN = { 0, 0, 0, 0 };
    int LENGTH_BITS = 4;
    byte DELIMITER_AND = '&';

    interface Prefs {
        String SHARED_PREFERENCES = "com.cwtcn.kmlib";
        String KEY_USER = "key_name";
        String KEY_PWD = "key_pwd";
        /** 用户UUID唯一标识 */
        String KEY_UUID = "key_uuid";
        /** 退出应用标识 */
        String KEY_EXIT_STATE = "key_exit_state";
    }
}
