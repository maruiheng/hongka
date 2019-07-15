package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 3.2.1.21查询Tracker闹铃(Q_ALARM_RING)
 *
 * @author Allen
 */
public class TrackerAlarmQueryReq extends Packet {
    public static final String CMD = "Q_ALARM_RING";
    private String imei;

    public TrackerAlarmQueryReq() {
        super(CMD);
    }

    public TrackerAlarmQueryReq(String imei) {
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
        JSONObject para = new JSONObject();
        try {
            para.put("imei", imei);
        } catch (Exception e) {

        }
        put(para.toString());
        return para2String();
    }
}
