package com.cwtcn.kmlib.protocol;

import android.text.TextUtils;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 3.2.1.25查询手表联系人 (MG_CTTS)
 *
 * @author Allen
 */
public class CTTSQueryReq extends Packet {
    public static final String CMD = "MG_ALL_CTTS";
    private String imei;

    public CTTSQueryReq() {
        super(CMD);
    }

    public CTTSQueryReq(String imei) {
        super(CMD);
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
            JSONObject mObject = new JSONObject();
            try {
                mObject.put("imei", imei);
            } catch (Exception e) {

            }
            put(mObject.toString());
        }
        return para2String();
    }
}
