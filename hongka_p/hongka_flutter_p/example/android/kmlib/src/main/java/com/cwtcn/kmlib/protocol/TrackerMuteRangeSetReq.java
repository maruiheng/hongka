package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.TrackerMuteData;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 3.16 设置静音时段
 *
 * @author Allen
 */
public class TrackerMuteRangeSetReq extends Packet {
    public static final String CMD = "S_MUTE_RANGE";
    private String imei;
    private List<TrackerMuteData> mQuietTimes;

    public TrackerMuteRangeSetReq() {
        super(CMD);
    }

    public TrackerMuteRangeSetReq(String imei, List<TrackerMuteData> mQts) {
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
                jsonFam.put("enable", fam.isEnabled());
                jsonFam.put("range", fam.getRange());
                //jsonFam.put("week", fam.week);
                array.put(jsonFam);
            }
            ob.put("mrs", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ob.toString();
    }
}
