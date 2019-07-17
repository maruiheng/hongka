package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.64删除位置到达返回
 *
 * @author Allen
 */
public class LocMarkDelRes extends Packet {
    public static final String CMD = "R_D_LOC_MARK";

    public LocMarkDelRes() {
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
            if (status.equals(NettyClientManager.STR_CODE_OK)) {
                msg = "";
            } else {
                msg = sa[++offset];
            }
        } catch (Exception e) {
            e.getCause();
        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_LOC_MARK_DEL, status, msg));
        return super.respond(sc);
    }
}
