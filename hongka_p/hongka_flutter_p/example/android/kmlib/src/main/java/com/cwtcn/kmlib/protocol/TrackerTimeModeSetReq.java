package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

/**
 * 3.2.1.14设置Tracker时间模式(S_TIME_MODE)
 *
 * @author Allen
 */
public class TrackerTimeModeSetReq extends Packet {
    public static final String CMD = "S_TIME_MODE";
    private String imei;
    private int mode;

    public TrackerTimeModeSetReq() {
        super(CMD);
    }

    public TrackerTimeModeSetReq(String imei, int mode) {
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
