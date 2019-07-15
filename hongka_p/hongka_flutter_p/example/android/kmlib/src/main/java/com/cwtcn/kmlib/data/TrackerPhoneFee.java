package com.cwtcn.kmlib.data;

/**
 * 手表资费
 */
public class TrackerPhoneFee {

    //imei":"860860000000108","selType":"0","result":"结果
    private String imei;
    private String selType;//查询类型
    private String result;//结果
    private String currentTime;//更新时间
    private String cwtSerialCode;//短信长度

    public String getImei() {
        return imei;
    }

    public String getSelType() {
        return selType;
    }

    public String getResult() {
        return result;
    }
}
