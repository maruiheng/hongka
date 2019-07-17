package com.cwtcn.kmlib.data;

/**
 * Created by xhl on 2017/4/21.
 */

public class HSOkData {
    /**
     * deviation : 200
     * id : 387037845569032192
     * imei : 860860000030249
     * lastUpdateTime : 20170421161957
     * wearerId : 350070052898623488
     * result;1
     */

    private int deviation;
    private long id;
    private String imei;
    private String lastUpdateTime;
    private long wearerId;
    private int result;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public int getDeviation() {
        return deviation;
    }

    public void setDeviation(int deviation) {
        this.deviation = deviation;
    }

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

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public long getWearerId() {
        return wearerId;
    }

    public void setWearerId(long wearerId) {
        this.wearerId = wearerId;
    }
}
