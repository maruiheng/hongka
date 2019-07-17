package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

/**
 * 3.2.1.3推送Tracker开启/关闭亲情号码白名单(P_E_WL)
 *
 * @author Allen
 */
public class TrackerWhiteListPush extends Packet {
    public static final String CMD = "P_E_WL";
    private String imei;
    private int mode;

    public TrackerWhiteListPush() {
        super(CMD);
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public void decodeArgs(String[] sa, int offset, int length) {
        super.decodeArgs(sa, offset, length);
        //{"imei":"860860000030075","on":0}
        try {
            String mResponse = sa[offset++];
            status = "0";
            JSONObject jsonObject = new JSONObject(mResponse);
            if (jsonObject != null && jsonObject.has("imei") && jsonObject.has("on")) {
                imei = jsonObject.optString("imei");
                mode = jsonObject.optInt("on");
            }
        } catch (Exception ex) {
            ex.getCause();
        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent<Integer>(KMEventConst.EVENT_EWL_GET, status, msg, imei, mode));
        return super.respond(sc);
    }
}
