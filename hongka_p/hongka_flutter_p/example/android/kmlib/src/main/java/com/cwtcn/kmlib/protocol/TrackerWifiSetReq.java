package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 3.2.1.48 设置手表wifi连接
 *
 * @author Allen
 */
public class TrackerWifiSetReq extends Packet {
    public static final String CMD = "M_SET_WIFI";
    private String imei;
    private String wifiName;
    private String wifiPassword;

    public TrackerWifiSetReq() {
        super(CMD);
    }

    public TrackerWifiSetReq(String imei, String wifiName, String wifiPassword) {
        super(CMD);
        this.imei = imei;
        this.wifiName = wifiName;
        this.wifiPassword = wifiPassword;
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
            para.put("wifiName", wifiName);
            para.put("wifiPassword", wifiPassword);
        } catch (JSONException e) {
            e.getCause();
        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(para.toString());
        return para2String();
    }
}
