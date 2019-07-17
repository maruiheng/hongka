package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

/***
 * 查询蓝牙交友请求
 * @author Allen
 *
 */
public class WearerFriendQueryReq extends Packet {
    public static final String CMD = "Q_FRI";
    private String imei;

    @Override
    protected Packet dup() {
        return this;
    }

    public WearerFriendQueryReq() {
        super(CMD);
    }

    public WearerFriendQueryReq(String imei) {
        super(CMD);
        this.imei = imei;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        put(DateUtil.getDalayTimeId());
        return para2String();
    }

}
