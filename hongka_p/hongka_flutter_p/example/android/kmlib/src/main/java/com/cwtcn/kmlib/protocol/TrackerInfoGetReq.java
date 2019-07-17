package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 3.2.2.11取Tracker信息 (Q_TRACKER_INFO)
 *
 * @author Allen
 */
public class TrackerInfoGetReq extends Packet {
    public static final String CMD = "Q_TRACKER_INFO";
    private String imei;

    public TrackerInfoGetReq() {
        super(CMD);
    }

    public TrackerInfoGetReq(String imei) {
        super(CMD);
        this.imei = imei;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONObject mRequest = new JSONObject();
        try {
            mRequest.put("imei", imei);
        } catch (Exception e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(mRequest.toString());
        return para2String();
    }
}
