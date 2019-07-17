package com.cwtcn.kmlib.data;

/**
 * 自动关机设置
 *
 * @author Allen
 */
public class TrackerPowerOnOff {

    private String imei;
    private int enable;
    private String bootTime;
    private String shutdownTime;
    private String week;

    public TrackerPowerOnOff(String imei, int enable, String bootTime, String shutdownTime, String week) {
        this.imei = imei;
        this.enable = enable;
        this.bootTime = bootTime;
        this.shutdownTime = shutdownTime;
        this.week = week;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    public String getBootTime() {
        return bootTime;
    }

    public void setBootTime(String bootTime) {
        this.bootTime = bootTime;
    }

    public String getShutdownTime() {
        return shutdownTime;
    }

    public void setShutdownTime(String shutdownTime) {
        this.shutdownTime = shutdownTime;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
