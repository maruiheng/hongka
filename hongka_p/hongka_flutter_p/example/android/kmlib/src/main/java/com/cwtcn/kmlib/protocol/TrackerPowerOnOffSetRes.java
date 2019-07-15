package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.2.1.42	响应设置手表自动开关机时间 (R_S_AUTO_POWER) (T1506用)
 *
 * @author Allen
 */

public class TrackerPowerOnOffSetRes extends Packet {
    public final static String CMD = "R_S_AUTO_POWER";

    public TrackerPowerOnOffSetRes() {
        super(CMD);
    }

    @Override
    protected Packet dup() {
        return this;
    }

    //R_S_AUTO_POWER&0&123456&20160412173611&操作成功！手表网络良好时会自动同步
    @Override
    public void decodeArgs(String[] sa, int offset, int length) {
        super.decodeArgs(sa, offset, length);
        status = sa[offset++];
        offset++;
        try {
            if (NettyClientManager.STR_CODE_OK.equals(status)) {
                offset++;
                if (sa.length > offset) {
                    msg = sa[offset];
                } else {
                    msg = "";
                }
            } else {
                msg = sa[offset++];
            }
        } catch (Exception e) {

        }
    }

    @Override
    public Packet respond(NettyClientManager sav) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_POWER_SET, status, msg));
        return super.respond(sav);
    }
}
