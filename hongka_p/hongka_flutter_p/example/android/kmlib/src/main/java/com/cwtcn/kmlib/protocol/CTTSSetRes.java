package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.2.1.24响应设置手表联系人(R_MS_CTTS)
 *
 * @author Allen
 */
public class CTTSSetRes extends Packet {
    public static final String CMD = "R_MS_CTTS";


    public CTTSSetRes() {
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
                }
            } else {
                msg = sa[offset];
            }
        } catch (Exception e) {

        }
    }

    @Override
    public Packet respond(NettyClientManager cm) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WEARER_CONTACT_SET, status, msg));
        return super.respond(cm);
    }
}
