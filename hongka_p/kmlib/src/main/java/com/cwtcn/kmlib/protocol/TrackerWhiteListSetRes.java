package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;

/**
 * 3.2.1.2响应设置Tracker开启/关闭亲情号码白名单 (R_E_WL)
 *
 * @author Allen
 */
public class TrackerWhiteListSetRes extends Packet {
    public static final String CMD = "R_E_WL";
    private String msg;

    public TrackerWhiteListSetRes() {
        super(CMD);
    }

    @Override
    protected Packet dup() {
        return this;
    }

    //R_E_WL &0& 20140825001212& {"on":0 }& 操作成功！手表网络良好时会自动同步
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
                } else {
                    msg = "";
                }
            } else {
                msg = sa[offset];
            }
        } catch (Exception e) {

        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_EWL_SET, status, msg));
        return super.respond(sc);
    }
}
