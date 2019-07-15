package com.cwtcn.kmlib.protocol;

import android.text.TextUtils;
import android.util.Log;

import com.cwtcn.kmlib.data.PhoneSettingData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

/**
 * 推送X2设置 (P_X2_SETTING)
 * Created by xhl on 2017/4/25.
 */

public class PhoneSettingPush extends Packet {
    public static final String CMD = "P_X2_SETTING";

    public String imei;
    private String mResponse;
    private PhoneSettingData data;

    public PhoneSettingPush() {
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
            mResponse = sa[offset++];
            Gson gson = new Gson();
            data = gson.fromJson(mResponse, PhoneSettingData.class);
            status = "0";
        } catch(Exception ex) {
            Log.e(CMD, "exception");
        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_PHONE_SET_PUSH, status, msg,data));
        return super.respond(sc);
    }
}
