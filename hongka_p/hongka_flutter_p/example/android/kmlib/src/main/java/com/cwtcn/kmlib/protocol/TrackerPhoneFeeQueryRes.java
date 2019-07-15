package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/***
 * 查询资费请求返回
 * @author Allen
 *
 */
public class TrackerPhoneFeeQueryRes extends Packet {
    public final static String CMD = "R_Q_TARIFFS";
    private String errorMsg;

    public TrackerPhoneFeeQueryRes() {
        super(CMD);
    }

    @Override
    protected Packet dup() {
        return this;
    }


    @Override
    public void decodeArgs(String[] sa, int offset, int length) {
        super.decodeArgs(sa, offset, length);
        //R_Q_TARIFFS&W001&20140825001212&860860000000108不在线
        try {
            status = sa[offset++];
            if (status.equals(NettyClientManager.STR_CODE_OK)) {
                offset++;//移动指针
                errorMsg = "";
            } else {
                offset++;//移动指针
                errorMsg = sa[offset++];
            }
        } catch (Exception e) {
            e.getCause();
        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_CHECK_FEE, status, msg));
        return super.respond(sc);
    }
}
