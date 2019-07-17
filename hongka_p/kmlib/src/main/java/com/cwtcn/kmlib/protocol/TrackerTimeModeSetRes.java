package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.2.1.15响应设置Tracker时间模式(R_S_TIMEI_MODE)
 *
 * @author Allen
 */
public class TrackerTimeModeSetRes extends Packet {
    public static final String CMD = "R_SET_TIME_MODE";
    private String msg;

    public TrackerTimeModeSetRes() {
        super(CMD);
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public void decodeArgs(String[] sa, int offset, int length) {
        super.decodeArgs(sa, offset, length);
        status = sa[offset++];
        offset++;
        if (NettyClientManager.STR_CODE_OK.equals(status)) {
            if (sa.length > offset) {
                msg = sa[offset++];
            } else {
                msg = "";
            }
        } else {
            msg = sa[offset];
        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_TIMEMODE_SET, status, msg));
        return super.respond(sc);
    }
}
