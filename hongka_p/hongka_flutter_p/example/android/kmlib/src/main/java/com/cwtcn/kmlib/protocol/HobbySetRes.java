package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 响应设置好习惯
 * Created by xhl on 2017/4/25.
 */

public class HobbySetRes extends Packet {
    public static final String CMD = "R_SET_X2_GOOD_HABIT";
    private String msg;

    public HobbySetRes(){
        super(CMD);
    }
    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_SET_GOOD_HABIT, status, msg));
        return super.respond(sc);
    }

    @Override
    public void decodeArgs(String[] sa, int offset, int length) {
        super.decodeArgs(sa, offset, length);
        try {
            status = sa[offset++];
            offset++;
            if(status.equals("0")) {
                offset++;
                if(sa.length > offset) {
                    msg = sa[offset];
                }else{
                    msg = "";
                }
            } else {
                msg = sa[offset];
            }
        } catch (Exception e) {

        }
    }
}
