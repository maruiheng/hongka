package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 获取手机某一天应用使用情况
 * Created by xhl on 2017/4/25.
 */

public class PhoneUsageDetailDataGetReq extends Packet {
    public static final String CMD = "QUERY_X2_DURATION_DETAIL_LIST";
    private String imei;
    private String durationDate;

    public PhoneUsageDetailDataGetReq() {
        super(CMD);
    }

    public PhoneUsageDetailDataGetReq(String imei,String durationDate) {
        super( CMD);
        this.imei = imei;
        this.durationDate = durationDate;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        put(DateUtil.getDalayTimeId());
        JSONObject object = new JSONObject();
        try {
            object.put("imei", imei);
            object.put("durationDate", durationDate);
        } catch (Exception e) {

        }
        put(object.toString());
        return para2String();
    }
}
