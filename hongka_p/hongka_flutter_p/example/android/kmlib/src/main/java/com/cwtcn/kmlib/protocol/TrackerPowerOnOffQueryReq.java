package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

/***
 * 3.2.1.44	查询手表自动开关机时间(MQ_AUTO_POWER) (T1506用)
 * @author Allen
 *
 */

public class TrackerPowerOnOffQueryReq extends Packet {
    public static final String CMD = "MQ_AUTO_POWER";
    private String imei;

    @Override
    protected Packet dup() {
        return this;
    }

    public TrackerPowerOnOffQueryReq() {
        super(CMD);
    }

    public TrackerPowerOnOffQueryReq(String imei) {
        super(CMD);
        this.imei = imei;
    }

    @Override
    public String encodeArgs() {
        JSONObject mRequest = new JSONObject();
        try {
            mRequest.put("imei", imei);
        } catch (JSONException e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(mRequest.toString());
        return para2String();
    }

}
