package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.TrackerCityData;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 手表城市设置
 * Created by Allen
 */
public class TrackerCitySetReq extends Packet {
    public static final String CMD = "M_SET_CITY";
    private String imei;
    private TrackerCityData data;

    public TrackerCitySetReq() {
        super(CMD);
    }

    public TrackerCitySetReq(String imei, TrackerCityData data) {
        super(CMD);
        this.imei = imei;
        this.data = data;
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
            para.put("cityId", data.getCityId());
            para.put("city", data.getCity());
            para.put("cnty", data.getCnty());
            para.put("lat", data.getLat());
            para.put("lon", data.getLon());
        } catch (JSONException e) {
            e.getCause();
        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(para.toString());
        return para2String();
    }
}
