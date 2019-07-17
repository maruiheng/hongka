package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.TrackerAlarm;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 3.2.1.18设置手表闹铃(S_ALARM_RING)
 *
 * @author Allen
 */
public class TrackerAlarmSetReq extends Packet {
    public static final String CMD = "S_ALARM_RING";
    private String imei;
    private TrackerAlarm mAlarmRings;

    public TrackerAlarmSetReq() {
        super(CMD);
    }

    public TrackerAlarmSetReq(String imei, TrackerAlarm mAlarms) {
        super(CMD);
        this.imei = imei;
        mAlarmRings = mAlarms;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(getSetQuiteMessageForLongsocket(imei, mAlarmRings));
        return para2String();
    }

    private String getSetQuiteMessageForLongsocket(String imei, TrackerAlarm alarm) {
        JSONObject ob = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            ob.put("imei", imei);
            JSONObject jsonFam = new JSONObject();
            jsonFam.put("time", alarm.getTime());
            jsonFam.put("enabled", alarm.isEnabled());
            jsonFam.put("ringType", alarm.getRingType());
            jsonFam.put("week", alarm.getWeek());
            array.put(jsonFam);
            ob.put("ars", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ob.toString();
    }
}
