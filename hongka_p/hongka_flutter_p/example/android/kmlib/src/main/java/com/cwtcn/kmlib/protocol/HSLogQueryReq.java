package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/***
 * 3.2.17.25	查询家校提醒提示信息
 * @author Allen
 *
 */
public class HSLogQueryReq extends Packet {
    public static final String CMD = "Q_ALERT_PUSH_HIS";
    private String imei;

    public HSLogQueryReq() {
        super(CMD);
    }

    public HSLogQueryReq(String imei) {
        super(CMD);
        this.imei = imei;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONObject para = new JSONObject();
        try {
            para.put("imei", imei);
            para.put("type", "0");
        } catch (Exception e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(para.toString());
        return para2String();
    }
}
