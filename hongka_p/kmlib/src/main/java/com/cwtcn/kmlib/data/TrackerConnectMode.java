package com.cwtcn.kmlib.data;

/**
 * 连接模式实体
 *
 * @author Allen
 */
public class TrackerConnectMode {
    private long id;
    private String imei;
    /** 连接方式 0:长链接 1:短信链接 2: 长链接+短信链接 */
    private int connect_mode;

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

    public int getConnectMode() {
        return connect_mode;
    }

    public void setConnectMode(int connect_mode) {
        this.connect_mode = connect_mode;
    }
}
