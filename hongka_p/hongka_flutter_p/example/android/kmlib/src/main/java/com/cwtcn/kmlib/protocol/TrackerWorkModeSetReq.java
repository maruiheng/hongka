package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.TrackerWorkMode;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class TrackerWorkModeSetReq extends Packet {
    public final static String CMD = "S_WORK_MODE";
    private TrackerWorkMode data;


    public TrackerWorkModeSetReq(TrackerWorkMode data) {
        super(CMD);
        this.data = data;
    }

    public TrackerWorkModeSetReq() {
        super(CMD);
    }

    @Override
    public String encodeArgs() {
        JSONObject mRequest = new JSONObject();
        try {
            mRequest.put("imei", data.getImei());
            mRequest.put("mode", data.getMode());
            mRequest.put("interval", data.getInterval());
            mRequest.put("intervalEnabled", data.getIntervalRange());
            mRequest.put("sleepStartTime", data.getSleepStartTime());
            mRequest.put("sleepEndTime", data.getSleepEndTime());
            mRequest.put("sleepEnabled", data.isSleepEnabled());
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
