package com.cwtcn.kmlib.protocol;


import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.91WIFI位置绑定返回
 */
public class WifiLocUpdateRes extends Packet {
    public static final String CMD = "R_U_WIFI_LOCATION";
    public String msg;

    public WifiLocUpdateRes() {
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
                msg = sa[offset++];
            } else {
                msg = sa[offset++];
            }
        } catch (Exception e) {
            e.getCause();
        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WIFI_LOC_UPDATE, status, msg));
        return super.respond(sc);
    }
}
