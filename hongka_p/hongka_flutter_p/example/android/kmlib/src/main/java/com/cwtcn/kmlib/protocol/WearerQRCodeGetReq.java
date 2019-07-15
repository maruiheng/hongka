package com.cwtcn.kmlib.protocol;

import android.text.TextUtils;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 3.31获取手表二维码的值
 *
 * @author Allen
 */
public class WearerQRCodeGetReq extends Packet {
    public static final String CMD = "G_BINDING_VALIDATION_CODE";
    private String imei;

    public WearerQRCodeGetReq() {
        super(CMD);
    }

    public WearerQRCodeGetReq(String imei) {
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
        if (!TextUtils.isEmpty(imei)) {
            JSONObject mRequest = new JSONObject();
            try {
                mRequest.put("imei", imei);
            } catch (Exception e) {

            }
            put(mRequest.toString());
        }
        return para2String();
    }
}
