package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.resp.TrackerTimeModeResp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 3.2.1.16 推送Tracker设置24小时(P_TIMEI_MODE){"imei":"860860000000108","hourTime24":1}
 *
 * @author Allen
 */
public class TrackerTimeModePush extends Packet {
    public static final String CMD = "P_TM";
    private List<TrackerTimeModeResp> data;

    public TrackerTimeModePush() {
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
            String mResponse = sa[offset++];
            Gson gson = new Gson();
            data = gson.fromJson(mResponse, new TypeToken<List<TrackerTimeModeResp>>() {}.getType());
            status = "0";
        } catch (Exception ex) {
            ex.getCause();
        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        if(data != null && data.size() > 0) {
            EventBus.getDefault().post(new KMEvent<Integer>(KMEventConst.EVENT_TIMEMODE_GET, status, msg, data.get(0).imei, data.get(0).hourTime24));
        }
        return super.respond(sc);
    }
}
