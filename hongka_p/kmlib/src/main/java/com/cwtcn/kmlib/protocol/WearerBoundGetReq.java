package com.cwtcn.kmlib.protocol;


import com.cwtcn.kmlib.util.DateUtil;

/**
 * 3.2.15.21手表管理员获取该表所有绑定人（WEARER_GET_ORDER_BINDINGS）
 *
 * @author Allen
 */
public class WearerBoundGetReq extends Packet {
    public static final String CMD = "WEARER_GET_ALL_BINDINGS";
    private String imei;

    public WearerBoundGetReq() {
        super(CMD);
    }

    public WearerBoundGetReq(String imei) {
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
        put(imei);
        return para2String();
    }
}
