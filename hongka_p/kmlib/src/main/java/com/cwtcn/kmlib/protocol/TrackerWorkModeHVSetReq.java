package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.TrackerWorkMode;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 3.2.1.9新版设置定位模式(S_WORK_MODE_HV)
 *
 * @author Allen
 */
public class TrackerWorkModeHVSetReq extends Packet {
    public final static String CMD = "S_WORK_MODE_HV";
    private TrackerWorkMode data;

    public TrackerWorkModeHVSetReq() {
        super(CMD);
    }

    public TrackerWorkModeHVSetReq(TrackerWorkMode mode) {
        super(CMD);
        this.data = mode;
    }

    @Override
    public String encodeArgs() {
        JSONObject mRequest = new JSONObject();
        try {
            mRequest.put("imei", data.getImei());
            mRequest.put("mode", data.getMode());
            mRequest.put("interval", data.getInterval());
            mRequest.put("intervalEnabled", data.isIntervalEnabled());
            mRequest.put("sleepStartTime", data.getSleepStartTime());
            mRequest.put("sleepEndTime", data.getSleepEndTime());
            mRequest.put("sleepEnabled", data.isSleepEnabled());

            JSONArray array = new JSONArray();
            JSONObject range = new JSONObject();
            if(data.getIntervalRange() != null && data.getIntervalRange().size() > 0) {
                range.put("enable", data.getIntervalRange().get(0).isEnabled());
                range.put("no", data.getIntervalRange().get(0).getNo());
                range.put("range", data.getIntervalRange().get(0).getRange());
                range.put("week", data.getIntervalRange().get(0).getWeek());
            }
            array.put(range);

            mRequest.put("intervalRange", array);

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
