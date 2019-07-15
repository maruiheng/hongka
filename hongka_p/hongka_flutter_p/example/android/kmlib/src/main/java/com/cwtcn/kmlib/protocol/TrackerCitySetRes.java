package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 响应手表城市设置
 * Created by Allen
 */
public class TrackerCitySetRes extends Packet {
    public static final String CMD = "R_M_SET_CITY";
    private String msg;

    public TrackerCitySetRes() {
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
            status = sa[1];
            if (status.equals(NettyClientManager.STR_CODE_OK)) {
                msg = sa[3];
            } else {
                msg = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Packet respond(NettyClientManager sav) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_CITY_SET, status, msg));
        return super.respond(sav);
    }
}
