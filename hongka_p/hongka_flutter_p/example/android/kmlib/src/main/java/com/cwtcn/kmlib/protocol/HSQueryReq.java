package com.cwtcn.kmlib.protocol;

import android.text.TextUtils;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/***
 * 3.2.17.16	查询新版家校提醒信息
 * @author Allen
 *
 */
public class HSQueryReq extends Packet {
    public static final String CMD = "Q_LOCATION_DATA";
    private long wearerId;
    private String imei;

    public HSQueryReq() {
        super(CMD);
    }

    public HSQueryReq(String imei, long wearerId) {
        super(CMD);
        this.wearerId = wearerId;
        this.imei = imei;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        put(DateUtil.getDalayTimeId());
        if (!TextUtils.isEmpty(imei)) {
            JSONObject data = new JSONObject();
            try {
                data.put("imei", imei);
                data.put("wearerId", wearerId);
            } catch (Exception e) {

            }
            put(data.toString());
        }
        return para2String();
    }
}
