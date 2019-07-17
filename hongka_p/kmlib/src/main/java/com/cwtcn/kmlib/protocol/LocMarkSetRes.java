package com.cwtcn.kmlib.protocol;

import android.text.TextUtils;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.58创建位置到达返回
 *
 * @author Allen
 */
public class LocMarkSetRes extends Packet {
    public static final String CMD = "R_C_LOC_MARK";
    private String cmdId = "";
    private String data;


    public LocMarkSetRes() {
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
            cmdId = sa[2];
            data = sa[3];
            if (status.equals(NettyClientManager.STR_CODE_OK)) {
                msg = "";
            } else {
                msg = sa[offset++];
            }
        } catch (Exception e) {
            e.getCause();
        }
    }


    @Override
    public Packet respond(NettyClientManager sc) {
        if(!TextUtils.isEmpty(cmdId) && cmdId.contains("HOME")) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_HOME_MARK_SET, status, msg,data));
        } else {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_LOC_MARK_SET, status, msg,data));
        }
        return super.respond(sc);
    }
}
