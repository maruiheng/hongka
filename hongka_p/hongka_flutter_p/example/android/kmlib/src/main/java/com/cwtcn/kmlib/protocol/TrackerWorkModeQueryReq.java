package com.cwtcn.kmlib.protocol;


import android.text.TextUtils;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

public class TrackerWorkModeQueryReq extends Packet {
    public static final String CMD = "Q_WORK_MODE";
    private String imei;

    @Override
    protected Packet dup() {
        return this;
    }

    public TrackerWorkModeQueryReq() {
        super(CMD);
    }

    public TrackerWorkModeQueryReq(String imei) {
        super(CMD);
        this.imei = imei;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        put(DateUtil.getDalayTimeId());
        if (!TextUtils.isEmpty(imei)) {
            JSONObject mRequest = new JSONObject();
            try {
                mRequest.put("imei", imei);
            } catch (Exception e) {

            }
            put(mRequest.toString());
        }
        return para2String();
    }

}
