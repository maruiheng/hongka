package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;


public class TrackerResetReq extends Packet {
    public static final String CMD = "RESET_TRACKER";
    private String imei;

    @Override
    protected Packet dup() {
        return this;
    }

    public TrackerResetReq() {
        super(CMD);
    }

    public TrackerResetReq(String imei) {
        super(CMD);
        this.imei = imei;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(imei);
        return para2String();
    }
}