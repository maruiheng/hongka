package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.2.1.48 设置手表wifi连接返回
 *
 * @author Allen
 */
public class TrackerWifiSetRes extends Packet {
    public static final String CMD = "R_M_SET_WIFI";
    private String msg;

    public TrackerWifiSetRes() {
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
                if (sa.length > offset) {
                    msg = sa[offset];
                } else {
                    msg = "";
                }
            } else {
                msg = sa[offset];
            }
        } catch (Exception e) {
            e.getCause();
        }
    }

    @Override
    public Packet respond(NettyClientManager sav) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WIFI_SET, status, msg));
        return super.respond(sav);
    }
}
