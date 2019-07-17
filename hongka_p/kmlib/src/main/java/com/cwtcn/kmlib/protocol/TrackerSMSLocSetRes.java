package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.2.1.30	响应设置Tracker短信上报位置开关
 *
 * @author Allen
 */
public class TrackerSMSLocSetRes extends Packet {
    public static final String CMD = "R_S_SMS_LOC";

    public TrackerSMSLocSetRes() {
        super(CMD);
    }

    @Override
    protected Packet dup() {
        return this;
    }

    //R_S_SMS_LOC&0&180340388&20150804164200	成功
    //R_S_SMS_LOC&W001&20140825001212&860860000000108不在线 失败
    //R_S_SMS_LOC&E500&20140825001212&服务器内部错误 失败
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
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_SMSLOC_SET, status, msg));
        return super.respond(sc);
    }

}
