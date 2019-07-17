package com.cwtcn.kmlib.data;

import java.util.List;

/**
 * 家校信息
 * Created by Allen on 2017/3/14.
 */

public class HSData {
    private String imei;
    /** 家校位置信息 */
    private List<HSLocationData> locationInfos;
    /** 家校时间信息 */
    private List<HSTimeData> locationTimes;
    private String pushNewHomeSchool;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public List<HSLocationData> getLocationInfos() {
        return locationInfos;
    }

    public void setLocationInfos(List<HSLocationData> locationInfos) {
        this.locationInfos = locationInfos;
    }

    public List<HSTimeData> getLocationTimes() {
        return locationTimes;
    }

    public void setLocationTimes(List<HSTimeData> locationTimes) {
        this.locationTimes = locationTimes;
    }

    public String getPushNewHomeSchool() {
        return pushNewHomeSchool;
    }

    public void setPushNewHomeSchool(String pushNewHomeSchool) {
        this.pushNewHomeSchool = pushNewHomeSchool;
    }
}
