package com.cwtcn.kmlib.protocol;

import android.text.TextUtils;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 获取手机设置信息
 * Created by xhl on 2017/4/25.
 */

public class PhoneSettingQueryReq extends Packet {
    public static final String CMD = "QUERY_X2_SETTING";
    private String imei;

    public PhoneSettingQueryReq() {
        super(CMD);
    }

    public PhoneSettingQueryReq(String imei) {
        super(CMD);
        this.imei = imei;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        put(DateUtil.getDalayTimeId());
        JSONObject object = new JSONObject();
        try {
            object.put("imei", imei);
        } catch (Exception e) {
        }
        put(object.toString());
        return para2String();
    }
}
