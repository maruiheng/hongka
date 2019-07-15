package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.PhoneUsageRecordDetailData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 响应获取手机某一天应用使用情况
 * Created by xhl on 2017/4/25.
 */

public class PhoneUsageDetailDataGetRes extends Packet {
    public static final String CMD = "R_QUERY_X2_DURATION_DETAIL_LIST";
    private String msg;
    private List<PhoneUsageRecordDetailData> data;

    public PhoneUsageDetailDataGetRes() {
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
        if(sa.length > offset) {
            msg = sa[offset++];
            Gson gson = new Gson();
            try {
                data = gson.fromJson(msg, new TypeToken<List<PhoneUsageRecordDetailData>>() {
                }.getType());
            } catch (Exception e) {

            }
        }else{
            msg = "";
        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_PHONE_USEAPPINFOBYDAY, status, msg, data));
        return super.respond(sc);
    }
}
