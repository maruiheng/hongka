package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.HSLocationData;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 3.2.17.12	创建新版家校提醒时间信息
 *
 * @author Allen
 */
public class HSLocationSetReq extends Packet {
    public static final String CMD = "C_LOCATION_INFO";
    private HSLocationData data;

    public HSLocationSetReq() {
        super(CMD);
    }

    public HSLocationSetReq(HSLocationData data) {
        super(CMD);
        this.data = data;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONArray para = new JSONArray();
        try {
            JSONObject mItem = new JSONObject();
            mItem.put("name", data.getName());
            mItem.put("imei", data.getImei());
            mItem.put("wearerId", data.getWearerId());
            mItem.put("type", data.getType());
            mItem.put("latitude", data.getLatitude());
            mItem.put("longitude", data.getLongitude());
            mItem.put("coordinateType", data.getCoordinateType());
            mItem.put("address", data.getAddress());
            mItem.put("mac", data.getMac());
            mItem.put("ssid", data.getSsid());
            para.put(mItem);
        } catch (JSONException e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(para.toString());
        return para2String();
    }
}
