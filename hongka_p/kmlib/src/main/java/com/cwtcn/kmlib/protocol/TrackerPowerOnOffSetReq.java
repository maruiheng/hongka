package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.TrackerPowerOnOff;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 3.2.1.41	设置手表自动开关机时间 (S_AUTO_POWER) (T1506用)
 *
 * @author Allen
 */
public class TrackerPowerOnOffSetReq extends Packet {
    public final static String CMD = "S_AUTO_POWER";
    private TrackerPowerOnOff data;

    public TrackerPowerOnOffSetReq() {
        super(CMD);
    }

    public TrackerPowerOnOffSetReq(TrackerPowerOnOff data) {
        super(CMD);
        this.data = data;
    }

    @Override
    public String encodeArgs() {
        //S_AUTO_POWER&123456&{"imei":"867327020018825","enable":true,"bootTime":"0700","shutdownTime":"2100","weekType":"1111111"}
        JSONObject mRequest = new JSONObject();
        try {
            mRequest.put("imei", data.getImei());
            mRequest.put("enable", data.getEnable());
            mRequest.put("bootTime", data.getBootTime());
            mRequest.put("shutdownTime", data.getShutdownTime());
            mRequest.put("weekType", data.getWeek());
        } catch (JSONException e) {

        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(mRequest.toString());
        return para2String();
    }

    @Override
    protected Packet dup() {
        return this;
    }

}
