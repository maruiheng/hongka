package com.cwtcn.kmlib.protocol;

import android.text.TextUtils;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/***
 * 查询资费请求
 * @author Allen
 *
 */

public class TrackerPhoneFeeQueryReq extends Packet {
    public static final String CMD = "Q_TARIFFS";
    private int type;
    private String imei;
    private String mobile;
    private String code;

    @Override
    protected Packet dup() {
        return this;
    }

    public TrackerPhoneFeeQueryReq() {
        super(CMD);
    }

    public TrackerPhoneFeeQueryReq(String imei, String mobile, int type, String code) {
        super(CMD);
        this.type = type;//0:话费 1:流量
        this.mobile = mobile;
        this.imei = imei;
        this.code = code;
    }

    //Q_TARIFFS&20140825001212&860860000000108&0&18612345678
    @Override
    public String encodeArgs() {
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(imei);
        put(type);
        put(mobile);
        if (!TextUtils.isEmpty(code)) {
            put(code);
        }
        return para2String();
    }
}
