package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

/**
 * 3.79获取所有手表最新位置
 *
 * @author Allen
 */
public class TrackerLDGetAllReq extends Packet {
    public static final String CMD = "Q_TLL";

    public TrackerLDGetAllReq() {
        super(CMD);
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        put(DateUtil.getDalayTimeId());
        return para2String();
    }
}
