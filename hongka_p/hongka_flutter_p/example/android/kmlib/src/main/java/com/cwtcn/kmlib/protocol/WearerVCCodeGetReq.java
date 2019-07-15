package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 3.33获取手表动态验证码,验证码需要在手表上查看
 *
 * @author Allen
 */
public class WearerVCCodeGetReq extends Packet {
    public static final String CMD = "G_TRACKER_VALIDATION_CODE";
    private String imei;

    public WearerVCCodeGetReq() {
        super(CMD);
    }

    public WearerVCCodeGetReq(String imei) {
        super(CMD);
        this.imei = imei;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONObject mRequest = new JSONObject();
        try {
            mRequest.put("imei", imei);
        } catch (Exception e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(mRequest.toString());
        return para2String();
    }
}
