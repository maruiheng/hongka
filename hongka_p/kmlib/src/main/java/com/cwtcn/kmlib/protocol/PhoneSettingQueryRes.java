package com.cwtcn.kmlib.protocol;

import android.text.TextUtils;

import com.cwtcn.kmlib.data.PhoneSettingData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

/**
 * 响应获取手机设置信息
 * Created by xhl on 2017/4/25.
 */

public class PhoneSettingQueryRes extends Packet{
    public static final String CMD = "R_QUERY_X2_SETTING";
    private PhoneSettingData data;
    private String msg;

    public PhoneSettingQueryRes() {
        super( CMD);
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
        try {
            if(status.equals("0")) {
                String jsonData = sa[offset++];
                msg = jsonData;
                Gson gson = new Gson();
                data = gson.fromJson(jsonData, PhoneSettingData.class);
            }else {
                msg = sa[offset++];
            }
        } catch (Exception e) {

        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_QUERY_PHONE_SET, status, msg,data));
        return super.respond(sc);
    }
}
