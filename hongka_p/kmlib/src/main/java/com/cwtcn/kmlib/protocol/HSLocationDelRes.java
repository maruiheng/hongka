package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.2.17.19	响应删除新版家校提醒时间信息
 *
 * @author Allen
 */
public class HSLocationDelRes extends Packet {
    public static final String CMD = "R_D_LOCATION_INFO";

    public HSLocationDelRes() {
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
                offset++;//移动指针
                msg = "";
            } else {
                offset++;//移动指针
                msg = sa[offset++];
            }
        } catch (Exception e)   {
            e.getCause();
        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_HS_LOCATION_DEL, status, msg));
        return super.respond(sc);
    }
}
