package com.cwtcn.kmlib.data;

/**
 * 许愿数据
 *
 * @author Allen
 */
public class WishesData {
    /**
     * 许愿id
     */
    private long id;
    /**
     * 时间戳
     */
    private String datetime;
    /**
     * imei
     */
    private String imei;
    /**
     * 状态
     */
    private String status;
    /**
     * 许愿文本
     */
    private String wishesTxt;
    /**
     * 许愿类型0：语音1文本
     */
    private String wishesType;
    /**
     * 语音地址
     */
    private String wishesUrl;
    /**
     * 是否在播放
     */
    private boolean isPlay = false;

    public String getDatetime() {
        return datetime;
    }

    public String getImei() {
        return imei;
    }

    public String getWishesTxt() {
        return wishesTxt;
    }

    public String getWishesType() {
        return wishesType;
    }

    public String getWishesUrl() {
        return wishesUrl;
    }
}
