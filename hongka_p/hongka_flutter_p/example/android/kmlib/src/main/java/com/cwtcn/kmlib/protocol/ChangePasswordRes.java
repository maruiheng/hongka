package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

public class ChangePasswordRes extends Packet {

    public static final String CMD = "R_U_PASSWORD";

    @Override
    protected Packet dup() {
        return this;
    }

    public ChangePasswordRes() {
        super(CMD);
    }

    @Override
    public void decodeArgs(String[] sa, int offset, int length) {
        super.decodeArgs(sa, offset, length);
        status = sa[offset++];
        offset++;
        if (status.equals(NettyClientManager.STR_CODE_OK)) {
            msg = "";
        } else {
            msg = sa[offset++];
        }
    }

    @Override
    public Packet respond(NettyClientManager sav) {// 处理界面信息
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_MODIFY_PWD, status, msg));
        return super.respond(sav);
    }
}
