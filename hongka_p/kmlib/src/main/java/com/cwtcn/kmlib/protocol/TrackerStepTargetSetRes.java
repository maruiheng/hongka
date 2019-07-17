package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.52设置活动目标返回
 *
 * @author Allen
 */
public class TrackerStepTargetSetRes extends Packet {
    public static final String CMD = "R_S_ACT_CFG";

    public TrackerStepTargetSetRes() {
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
        try {
            if (NettyClientManager.STR_CODE_OK.equals(status)) {
                msg = sa[offset];
            } else {
                msg = sa[offset];
            }
        } catch (Exception e) {
            e.getCause();
        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_STEP_TARGET_SET, status, msg));
        return super.respond(sc);
    }
}
