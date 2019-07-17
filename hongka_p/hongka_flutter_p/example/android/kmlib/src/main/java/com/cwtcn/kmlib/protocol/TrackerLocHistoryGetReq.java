package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 3.81获取手表历史位置
 *
 * @author Allen
 */
public class TrackerLocHistoryGetReq extends Packet {
    public static final String CMD = "Q_LOCATION";
    private String imei;
    private String date;
    private int page;
    private int pageSize;

    public TrackerLocHistoryGetReq() {
        super(CMD);
    }

    public TrackerLocHistoryGetReq(String imei, String date) {
        super(CMD);
        this.imei = imei;
        this.date = date;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        String toTime = date.substring(0, "yyyymmdd".length()) + "235959";
        JSONObject para = new JSONObject();
        try {
            para.put("imei", imei);
            para.put("fromTime", date);
            para.put("toTime", toTime);
            if (page != 0 && pageSize != 0) {
                para.put("page", page);
                para.put("pageSize", pageSize);
            }
        } catch (Exception e) {
            e.getCause();
        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(para.toString());
        return para2String();
    }
}
