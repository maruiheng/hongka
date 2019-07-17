package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取城市列表
 * @author Allen
 */
public class TrackerCityQueryReq extends Packet {

    public static final String CMD = "Q_TRACKER_CITY";
    private String imei;

    public TrackerCityQueryReq() {
        super(CMD);
    }

    public TrackerCityQueryReq(String imei) {
        super(CMD);
        this.imei = imei;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONObject para = new JSONObject();
        try {
            para.put("imei", imei);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(para.toString());
        return para2String();
    }
}
