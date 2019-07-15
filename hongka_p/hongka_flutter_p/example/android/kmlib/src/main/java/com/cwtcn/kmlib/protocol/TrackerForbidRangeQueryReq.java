package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 3.2.1.6查询Tracker静默时段 (Q_FORBID_RANGE)
 *
 * @author Allen
 */
public class TrackerForbidRangeQueryReq extends Packet {
    public static final String CMD = "Q_FORBID_RANGE";
    private String imei;

    public TrackerForbidRangeQueryReq() {
        super(CMD);
    }

    public TrackerForbidRangeQueryReq(String imei) {
        super(CMD);
        this.imei = imei;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONObject request = new JSONObject();
        try {
            request.put("imei", imei);
        } catch (Exception e) {
            e.getCause();
        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(request.toString());
        return para2String();
    }
}
