package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.17设置静音时段返回
 *
 * @author Allen
 */
public class TrackerMuteRangeSetRes extends Packet {
    public static final String CMD = "R_S_MUTE_RANGE";
    private String msg;

    public TrackerMuteRangeSetRes() {
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

        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_CLASS_MODE_SET, status, msg));
        return super.respond(sc);
    }
}
