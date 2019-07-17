package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

/**
 * 3.53获取活动目标
 *
 * @author Allen
 */
public class TrackerStepTargetGetReq extends Packet {
    public static final String CMD = "G_ACT_CFG";
    private String wearID;

    public TrackerStepTargetGetReq() {
        super(CMD);
    }

    public TrackerStepTargetGetReq(String wearId) {
        super(CMD);
        this.wearID = wearId;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(wearID);
        return para2String();
    }
}
