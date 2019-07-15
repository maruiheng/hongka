package com.cwtcn.kmlib.protocol;

import android.text.TextUtils;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * KT04查询手表亲情号码
 *
 * @author Allen
 */
public class FamilyNumbersEXQueryReq extends Packet {
    public static final String CMD = "Q_FN_EX";
    private String imei;

    public FamilyNumbersEXQueryReq() {
        super(CMD);
    }

    public FamilyNumbersEXQueryReq(String imei) {
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
