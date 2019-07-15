package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

public class MessagePushRes extends Packet {
    public static final String CMD = "R_M_DOWN_RESOURCE";
    private String cmdId = "";

    public MessagePushRes() {
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
            status = sa[offset++];
            cmdId = sa[2];
            if (status.equals(NettyClientManager.STR_CODE_OK)) {
                msg = "";
            } else {
                msg = sa[offset++];
            }
        } catch (Exception e) {
            e.getCause();
        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_MESSAGE_PUSH, status, msg));
        return super.respond(sc);
    }
}
