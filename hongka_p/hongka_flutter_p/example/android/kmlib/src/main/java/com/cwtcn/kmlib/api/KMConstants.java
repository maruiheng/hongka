package com.cwtcn.kmlib.api;

/**
 * 接口常量
 * Created by Allen on 2017/3/9.
 */

public interface KMConstants {

    //String HTTP_API = "http://192.168.1.16:8086/sportlive/api/";
//    String HTTP_API = "http://dev.cwtcn.com/sportlive/api/";
    String HTTP_API = "http://123.207.162.46:8701/sportlive/api/";
    String CODE_OK = "0";
    String CODE_ERROR = "-1";

    /**
     * 位置标记类型
     */
    public interface LocMarkType {
        /** 通用位置标记 */
        int TYPE_LOC_MARK_COMMON = 1;
        /** 家的位置标记 */
        int TYPE_LOC_MARK_HOME = 3;
    }

    /**
     * 定位模式
     */
    public enum LocMode {
        /** 精确定位模式 */
        MODE_EXACT,
        /** 正常定位模式 */
        MODE_NORMAL,
        /** 省电定位模式 */
        MODE_POWER_SAVE
    }

    /**
     * Tracker指令
     */
    public interface TrackerCMD {
        String CMD_TITLE = "KT*";
        String CMD_SEPARATOR = "_";
        String CMD_OFF_ALARM = "kt*bdjc*";
        String CMD_ACCIDENT_ALARM = "kt*pzjc*";
        String CMD_ACCIDENT_ALARM_LEVEL = "kt*pzjc*dj*";
        String CMD_LOC_ONCE = "kt*cxwz*";
    }

    /***
     * 错误提示语
     */
    public interface KMTips {
        String ERROR_NOT_SUPPORT = "This feature is not supported for your watch";
        String ERROR_FILE_NOT_EXIST = "The file is not exist";
    }

    /***
     * 用户登录类型
     */
    public interface UserType {
        String TYPE_DF = "1";
        String TYPE_WX = "2";
        String TYPE_QQ = "3";
        String TYPE_WB = "4";
        /** 手机号注册 */
        String TYPE_MOBILE = "6";
    }

}
