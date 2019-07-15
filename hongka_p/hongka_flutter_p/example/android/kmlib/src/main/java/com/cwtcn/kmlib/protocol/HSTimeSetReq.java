package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.HSTimeData;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 新版家校时间设置
 *
 * @author Allen
 */
public class HSTimeSetReq extends Packet {
    public static final String CMD = "C_LOCATION_TIMES";
    private List<HSTimeData> data;

    public HSTimeSetReq() {
        super(CMD);
    }

    public HSTimeSetReq(List<HSTimeData> data) {
        super(CMD);
        this.data = data;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONArray para = new JSONArray();
        try {
            if (data != null && data.size() > 0) {
                for (int i = 0; i < data.size(); i++) {
                    JSONObject mItem = new JSONObject();
                    mItem.put("name", data.get(i).getName());
                    mItem.put("imei", data.get(i).getImei());
                    mItem.put("wearerId", data.get(i).getWearerId());
                    mItem.put("type", data.get(i).getType());
                    mItem.put("enabled", data.get(i).getEnabled());
                    mItem.put("eachEnabled", data.get(i).getEachEnabled());
                    mItem.put("weekType", data.get(i).getWeekType());
                    mItem.put("startTime", data.get(i).getStartTime());
                    mItem.put("endTime", data.get(i).getEndTime());
                    para.put(mItem);
                }
            }
        } catch (JSONException e) {
            e.getCause();
        }
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(para.toString());
        return para2String();
    }
}
