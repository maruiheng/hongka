package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.TrackerMuteData;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 3.2.1.4设置Tracker静默时段(S_FORBID_RANGE)
 *
 * @author Allen
 */
public class TrackerForbidRangeSetReq extends Packet {
    public static final String CMD = "S_FORBID_RANGE";
    private String imei;
    private List<TrackerMuteData> mQuietTimes;

    public TrackerForbidRangeSetReq() {
        super(CMD);
    }

    public TrackerForbidRangeSetReq(String imei, List<TrackerMuteData> mQts) {
        super(CMD);
        this.imei = imei;
        mQuietTimes = mQts;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(getSetQuiteMessageForLongsocket(imei, mQuietTimes));
        return para2String();
    }

    private String getSetQuiteMessageForLongsocket(String imei, List<TrackerMuteData> numberList) {
        JSONObject ob = new JSONObject();
        JSONArray array = new JSONArray();
        try {
            ob.put("imei", imei);
            for (TrackerMuteData fam : numberList) {
                JSONObject jsonFam = new JSONObject();
                jsonFam.put("no", fam.getNo() + 1);
                jsonFam.put("enabled", fam.isEnabled());
                jsonFam.put("range", fam.getRange());
                jsonFam.put("week", fam.week);
                array.put(jsonFam);
            }
            ob.put("mrs", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ob.toString();
    }
}
