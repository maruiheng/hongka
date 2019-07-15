package com.cwtcn.kmlib.protocol;


import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 3.2.15.23手表管理员变更（WEARER_CHANGE_ADMIN）
 *
 * @author Allen
 */
public class WearerAdminChangeReq extends Packet {
    public static final String CMD = "WEARER_CHANGE_ADMIN";
    private String imei;
    private String wearId;

    public WearerAdminChangeReq() {
        super(CMD);
    }

    public WearerAdminChangeReq(String imei, String wearId) {
        super(CMD);
        this.imei = imei;
        this.wearId = wearId;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONObject mObject = new JSONObject();
        try {
            mObject.put("imei", imei);
            mObject.put("wearerId", wearId);
        } catch (Exception e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(mObject.toString());
        return para2String();
    }
}
