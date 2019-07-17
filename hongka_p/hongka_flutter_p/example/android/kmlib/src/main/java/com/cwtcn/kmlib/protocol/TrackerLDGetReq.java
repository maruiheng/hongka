package com.cwtcn.kmlib.protocol;

import android.text.TextUtils;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 3.77获取手表最新数据
 *
 * @author Allen
 */
public class TrackerLDGetReq extends Packet {
    public static final String CMD = "Q_TLD";
    private String imei;

    public TrackerLDGetReq() {
        super(CMD);
    }

    public TrackerLDGetReq(String imei) {
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
            String.format("&%s&{\"imei\":\"%s\"}", DateUtil.getDalayTimeId(), imei);// 查询单个
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
