package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.WiFiData;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 3.91WIFI位置绑定
 */
public class WifiLocUpdateReq extends Packet {
    public static final String CMD = "U_WIFI_LOCATION";
    private String imei;
    private Double lat;
    private Double lon;
    private int mapType = 4;
    private List<WiFiData> wifis;

    public WifiLocUpdateReq() {
        super(CMD);
    }

    public WifiLocUpdateReq(String imei, Double lat, Double lon, List<WiFiData> mWifiList) {
        super(CMD);
        this.imei = imei;
        this.lat = lat;
        this.lon = lon;
        this.wifis = mWifiList;
    }

    @Override
    protected Packet dup() {
        return this;
    }


    @Override
    public String encodeArgs() {
        JSONObject req = new JSONObject();
        JSONArray mWifis = new JSONArray();
        try {
            req.put("imei", imei);
            req.put("latitude", lat);
            req.put("longitude", lon);
            req.put("mapType", mapType);
            for (int i = 0; i < wifis.size(); i++) {
                JSONObject para = new JSONObject();
                para.put("mac", wifis.get(i).m);
                para.put("ssid", wifis.get(i).s);
                mWifis.put(para);
            }
            req.put("wifis", mWifis.toString());
        } catch (JSONException e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(req.toString());
        return para2String();
    }
}
