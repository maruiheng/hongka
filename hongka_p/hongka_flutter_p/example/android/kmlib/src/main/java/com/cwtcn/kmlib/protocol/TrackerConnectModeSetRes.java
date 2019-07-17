package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.2.1.27响应设置Tracker链接方式(R_S_CONNECT_MODE)
 *
 * @author Allen
 */
public class TrackerConnectModeSetRes extends Packet {
    public static final String CMD = "R_S_CONNECT_MODE";

    public TrackerConnectModeSetRes() {
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
            offset++;
            if (status.equals(NettyClientManager.STR_CODE_OK)) {
                offset++;
                msg = sa[offset];
            } else {
                msg = sa[offset];
            }
        } catch (Exception e) {

        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_CONNECT_MODE_SET, status, msg));
        return super.respond(sc);
    }
}
