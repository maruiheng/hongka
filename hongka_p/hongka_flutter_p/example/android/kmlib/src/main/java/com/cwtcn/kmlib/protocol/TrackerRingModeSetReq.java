package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

/**
 * 设置Tracker提示方式(S_RING_MODE)
 *
 * @author Allen
 */
public class TrackerRingModeSetReq extends Packet {
    public static final String CMD = "S_RING_MODE";
    private String imei;
    private int mode;

    public TrackerRingModeSetReq() {
        super(CMD);
    }

    public TrackerRingModeSetReq(String imei, int mode) {
        super(CMD);
        this.imei = imei;
        this.mode = mode;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(imei);
        put(mode);
        return para2String();
    }

}
