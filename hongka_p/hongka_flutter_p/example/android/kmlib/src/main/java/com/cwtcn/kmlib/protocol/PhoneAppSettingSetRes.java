package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by xhl on 2017/4/25.
 */

public class PhoneAppSettingSetRes extends Packet {
    public static final String CMD = "R_X2_APP_SETTING";
    private String msg;

    public PhoneAppSettingSetRes() {
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
        if(sa.length > offset) {
            msg = sa[offset++];
        }else{
            msg = "";
        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_APP_USETIMEORUNINSTALL, status, msg));
        return super.respond(sc);
    }
}
