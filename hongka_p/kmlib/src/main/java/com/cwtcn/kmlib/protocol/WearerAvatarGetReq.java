package com.cwtcn.kmlib.protocol;


/**
 * 3.45获取佩戴对象图像
 *
 * @author Allen
 */
public class WearerAvatarGetReq extends Packet {
    public static final String CMD = "G_WEARER_AVATAR";
    private String wearID;

    public WearerAvatarGetReq() {
        super(CMD);
    }

    public WearerAvatarGetReq(String wearId) {
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
        put(wearID);//用wearID做为时间戳，便于返回时处理数据
        put(wearID);
        return para2String();
    }
}
