package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/***
 * 3.2.1.29	设置Tracker短信上报位置开关
 * @author Allen
 *
 */
public class TrackerSMSLocSetReq extends Packet {
    public static final String CMD = "S_SMS_LOC";
    private String imei;
    private String smsLocation;

    public TrackerSMSLocSetReq() {
        super(CMD);
    }

    public TrackerSMSLocSetReq(String imei, String smsLocation) {
        super(CMD);
        this.imei = imei;
        this.smsLocation = smsLocation;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONObject mRequest = new JSONObject();
        try {
            mRequest.put("sms_location", smsLocation);
            mRequest.put("imei", imei);
        } catch (Exception e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(mRequest.toString());
        return para2String();
    }
}
