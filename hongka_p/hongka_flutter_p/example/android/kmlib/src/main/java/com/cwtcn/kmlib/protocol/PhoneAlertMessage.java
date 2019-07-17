package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 推送手机最新消息
 * Created by xhl on 2017/4/25.
 */

public class PhoneAlertMessage extends Packet {
    public static final String CMD = "P_X2_ALERT_MESSAGE";

    private String msg;

    public PhoneAlertMessage() {
        super(CMD);
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public void decodeArgs(String[] sa, int offset, int length) {
        super.decodeArgs(sa, offset, length);
        try {
            msg = sa[offset];
        } catch (Exception e) {
        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_NEW_INFO_PUSH, status, msg));
        return super.respond(sc);
    }
}
