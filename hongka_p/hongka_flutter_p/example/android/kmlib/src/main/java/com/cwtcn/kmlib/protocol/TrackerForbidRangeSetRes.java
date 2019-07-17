package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 响应设置Tracker静默时段(R_S_ FORBID_RANGE)
 *
 * @author Allen
 */
public class TrackerForbidRangeSetRes extends Packet {
    public static final String CMD = "R_S_FORBID_RANGE";

    public TrackerForbidRangeSetRes() {
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
