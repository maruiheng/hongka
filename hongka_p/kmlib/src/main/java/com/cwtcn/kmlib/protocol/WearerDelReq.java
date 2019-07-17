package com.cwtcn.kmlib.protocol;


import com.cwtcn.kmlib.util.DateUtil;

/**
 * 3.39删除佩戴对象
 *
 * @author Allen
 */
public class WearerDelReq extends Packet {
    public static final String CMD = "D_WEARER";
    private String wearID;

    public WearerDelReq() {
        super(CMD);
    }

    public WearerDelReq(String wearId) {
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
