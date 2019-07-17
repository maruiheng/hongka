package com.cwtcn.kmlib.data;

/**
 * 响铃模式实体
 *
 * @author Allen
 */
public class TrackerRingMode {
    private long id;
    private String imei;
    /** 提醒方式，0:震动 1:响铃 2:震动加响铃3:静音 */
    private int ringing;
    private String lastUpdatedTime;
    private String lastUpdatedBy;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getRinging() {
        return ringing;
    }

    public void setRinging(int ringing) {
        this.ringing = ringing;
    }
}
