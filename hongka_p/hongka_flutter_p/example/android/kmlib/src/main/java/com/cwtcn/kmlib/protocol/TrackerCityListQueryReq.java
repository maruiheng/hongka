package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取城市列表
 * @author Allen
 */
public class TrackerCityListQueryReq extends Packet {

    public static final String CMD = "Q_TRACKER_CITY_LIST";
    private String imei;
    private String city;

    public TrackerCityListQueryReq() {
        super(CMD);
    }

    public TrackerCityListQueryReq(String imei, String city) {
        super(CMD);
        this.imei = imei;
        this.city = city;
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
            para.put("city", city);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(para.toString());
        return para2String();
    }
}
