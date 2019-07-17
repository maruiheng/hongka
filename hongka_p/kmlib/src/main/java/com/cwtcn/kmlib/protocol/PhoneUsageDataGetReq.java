package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 获取手机使用情况
 * Created by xhl on 2017/4/25.
 */

public class PhoneUsageDataGetReq extends Packet{
    public static final String CMD = "QUERY_X2_DURATION_LIST";
    private String dateFrom;
    private String dateTo;
    private String imei;

    public PhoneUsageDataGetReq() {
        super(CMD);
    }

    public PhoneUsageDataGetReq( String imei,String dateFrom,String dateTo) {
        super( CMD);
        this.imei = imei;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
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
            object.put("imei",imei);
            object.put("durationDateSt", dateFrom);
            object.put("durationDateEn", dateTo);
        } catch (Exception e) {

        }
        put(object.toString());
        return para2String();
    }
}
