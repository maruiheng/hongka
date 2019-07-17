package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.TrackerWorkMode;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class TrackerWorkModeQueryRes extends Packet {
    public final static String CMD = "R_Q_WORK_MODE";
    private List<TrackerWorkMode> data;

    public TrackerWorkModeQueryRes() {
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
            offset++;
            if (NettyClientManager.STR_CODE_OK.equals(status)) {
                String json = sa[offset++];
                Gson gson = new Gson();
                data = gson.fromJson(json, new TypeToken<List<TrackerWorkMode>>() {}.getType());
            } else {
                msg = sa[offset++];
            }
        } catch (Exception e) {

        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_WORK_MODE_GET, status, msg, (data != null && data.size() > 0) ? data.get(0) : null));
        return super.respond(sc);
    }
}
