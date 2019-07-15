package com.cwtcn.kmlib.protocol;

import android.text.TextUtils;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 3.13 查询手表亲情号码
 *
 * @author Allen
 */
public class FamilyNumbersQueryReq extends Packet {
    public static final String CMD = "Q_FN";
    private String imei;

    public FamilyNumbersQueryReq() {
        super(CMD);
    }

    public FamilyNumbersQueryReq(String imei) {
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
            JSONObject para = new JSONObject();
            try {
                para.put("imei", imei);
            } catch (Exception e) {

            }
            put(para.toString());
        }
        return para2String();
    }
}
