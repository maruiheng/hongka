package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.HSTimeData;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/***
 * 新修改位置提醒时间
 * @author Allen
 *
 */
public class HSTimeUpdateReq extends Packet {
    public static final String CMD = "U_LOCATION_TIMES";
    private List<HSTimeData> locAlert;

    public HSTimeUpdateReq() {
        super(CMD);
    }

    public HSTimeUpdateReq(List<HSTimeData> locAlert) {
        super(CMD);
        this.locAlert = locAlert;
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public String encodeArgs() {
        JSONArray para = new JSONArray();
        try {
            if (locAlert != null && locAlert.size() > 0) {
                for (int i = 0; i < locAlert.size(); i++) {
                    JSONObject mItem = new JSONObject();
                    mItem.put("id", locAlert.get(i).getId());
                    mItem.put("name", locAlert.get(i).getName());
                    mItem.put("imei", locAlert.get(i).getImei());
                    mItem.put("wearerId", locAlert.get(i).getWearerId());
                    mItem.put("type", locAlert.get(i).getType());
                    mItem.put("enabled", locAlert.get(i).getEnabled());
                    mItem.put("eachEnabled", locAlert.get(i).getEachEnabled());
                    mItem.put("weekType", locAlert.get(i).getWeekType());
                    mItem.put("startTime", locAlert.get(i).getStartTime());
                    mItem.put("endTime", locAlert.get(i).getEndTime());
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
