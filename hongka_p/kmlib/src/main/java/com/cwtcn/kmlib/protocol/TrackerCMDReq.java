package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMConstants;

/**
 * APP发送设置指令
 *
 * @author Allen
 */
public class TrackerCMDReq extends Packet {
    protected static final String CMD = "CMD";
    private String imei;
    private String cmdKey;
    private String cmd;

    public TrackerCMDReq(String imei) {
        super(CMD);
    }

    public TrackerCMDReq(String cmdKey, String imei, String cmd) {
        super(CMD);
        this.cmdKey = cmdKey;
        this.imei = imei;
        this.cmd = cmd;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        put(cmdKey + imei);
        put(imei);
        put(cmd);
        return para2String();
    }
}
