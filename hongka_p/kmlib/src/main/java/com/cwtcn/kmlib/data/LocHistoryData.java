package com.cwtcn.kmlib.data;

/**
 * 历史位置数据
 * Created by Allen on 2017/3/13.
 */

public class LocHistoryData {
    //{"imei":"860860000031035","lat":38.861380000000000,"lon":121.522981000000000,"mapType":"4","power":100,"t":"2","time":"170209173842"}
    private String imei;
    private double lat;
    private double lon;
    /** 地图类型 */
    private String mapType;
    private int power;
    /** 定位类型 0:gps, 1:gprs, 2:wifi */
    private String t;
    private String time;

    public String getImei() {
        return imei;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getMapType() {
        return mapType;
    }

    public int getPower() {
        return power;
    }

    public String getT() {
        return t;
    }

    public String getTime() {
        return time;
    }
}
