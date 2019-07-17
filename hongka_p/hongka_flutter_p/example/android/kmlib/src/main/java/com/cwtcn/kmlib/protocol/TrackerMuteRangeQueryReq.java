package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 3.18 查询静音时段
 *
 * @author Allen
 */
public class TrackerMuteRangeQueryReq extends Packet {
    public static final String CMD = "Q_MUTE_RANGE";
    private String imei;

    public TrackerMuteRangeQueryReq() {
        super(CMD);
    }

    public TrackerMuteRangeQueryReq(String imei) {
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
            e.getCause();
        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(mRequest.toString());
        return para2String();
    }
}
