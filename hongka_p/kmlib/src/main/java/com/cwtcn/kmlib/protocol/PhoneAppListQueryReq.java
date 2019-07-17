package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.protocol.Packet;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 获取手机第三方应用列表,第三方应用使用情况以及设置
 * Created by xhl on 2017/4/25.
 */

public class PhoneAppListQueryReq extends Packet {
    public static final String CMD = "QUERY_THIRD_PARTY_LIST";
    private String imei;

    public PhoneAppListQueryReq() {
        super(CMD);
    }

    public PhoneAppListQueryReq(String imei) {
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
