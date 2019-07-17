package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.PhoneUsageRecord;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 响应获取手机使用情况
 * Created by xhl on 2017/4/25.
 */

public class PhoneUsageDataGetRes extends Packet {
    public static final String CMD = "R_QUERY_X2_DURATION_LIST";
    private String msg;
    private List<PhoneUsageRecord> data;

    public PhoneUsageDataGetRes() {
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
        offset++;
        if (sa.length > offset) {
            msg = sa[offset++];
            Gson gson = new Gson();
            data = gson.fromJson(msg, new TypeToken<List<PhoneUsageRecord>>() {
            }.getType());
        } else {
            msg = "";
        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_PHONE_USAGEDATA, status, msg, data));
        return super.respond(sc);
    }
}
