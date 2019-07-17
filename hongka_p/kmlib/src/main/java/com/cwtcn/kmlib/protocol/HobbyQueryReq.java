package com.cwtcn.kmlib.protocol;

import android.text.TextUtils;

import com.cwtcn.kmlib.protocol.Packet;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 获取好习惯
 * Created by xhl on 2017/4/25.
 */

public class HobbyQueryReq extends Packet {
    public static final String CMD = "QUERY_X2_GOOD_HABIT";
    private String imei;
    public HobbyQueryReq() {
        super(CMD);
    }

    public HobbyQueryReq(String imei) {
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
        JSONObject para = new JSONObject();
        try {
            para.put("imei", imei);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        put(para.toString());
        return para2String();
    }
}
