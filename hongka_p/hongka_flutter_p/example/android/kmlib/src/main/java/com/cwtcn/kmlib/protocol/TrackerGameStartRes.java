package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.98 设置手表游戏返回
 *
 * @author Allen
 */
public class TrackerGameStartRes extends Packet {
    public static final String CMD = "R_SET_GAME";

    public TrackerGameStartRes() {
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
        if (status.equals(NettyClientManager.STR_CODE_OK)) {
            msg = sa[offset];
        } else {
            msg = sa[offset++];
        }
    }


    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_GAME_START, status, msg));
        return super.respond(sc);
    }
}
