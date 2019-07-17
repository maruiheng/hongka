package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.LocHistoryData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 3.80获取所有手表最新位置返回
 *
 * @author Allen
 */
public class TrackerLDGetAllRes extends Packet {
    public static final String CMD = "R_Q_TLL";
    public List<LocHistoryData> data;

    public TrackerLDGetAllRes() {
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
        if (status.equals(NettyClientManager.STR_CODE_OK)) {
            Gson gson = new Gson();
            String jsonData = sa[++offset].trim();
            data = gson.fromJson(jsonData, new TypeToken<List<LocHistoryData>>() {}.getType());
        } else {
            msg = sa[offset++];
        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_TLL_GET, status, msg, data));
        return super.respond(sc);
    }
}
