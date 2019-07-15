package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

/**
 * 3.49获取报警区域
 *
 * @author Allen
 */
public class AlertAreaGetReq extends Packet {
    public static final String CMD = "G_ALERT_AREA";
    private String areaID;

    public AlertAreaGetReq() {
        super(CMD);
    }

    public AlertAreaGetReq(String areaID) {
        super(CMD);
        this.areaID = areaID;
    }

    @Override
    protected Packet dup() {
        return this;
    }


    @Override
    public String encodeArgs() {
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(areaID);
        return para2String();
    }

}
