package com.cwtcn.kmlib.data;

import android.text.TextUtils;

import org.json.JSONObject;

/**
 * Created by xhl on 2017/4/25.
 */

public class HabitData {
    /**
     * create_time : 1477622549000
     * enabled : 1
     * habit : 55
     * id : 323535020331782144
     * imei : 850850000050082
     * remind_time : 0800
     * update_time : 1477636978000
     * week_type : 1111111
     */

    private long create_time;
    private int enabled;
    private String habit;
    private String id;
    private String imei;
    private String remind_time;
    private long update_time;
    private String week_type;
    private String type;

    public HabitData(String imei, String type, String habit, int enabled, String remind_time, String week_type, String id) {
        this.imei = imei;
        this.type = type;
        this.habit = habit;
        this.enabled = enabled;
        this.remind_time = remind_time;
        this.week_type = week_type;
        this.id = id;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getRemind_time() {
        return remind_time;
    }

    public void setRemind_time(String remind_time) {
        this.remind_time = remind_time;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public String getWeek_type() {
        return week_type;
    }

    public void setWeek_type(String week_type) {
        this.week_type = week_type;
    }

    public String getJSONData() {
        JSONObject object = new JSONObject();
        try {
            object.put("imei", imei);
            object.put("habit", habit);
            object.put("enabled", enabled);
            object.put("remind_time", remind_time);
            object.put("week_type", week_type);
            object.put("type", type);
            if(!TextUtils.isEmpty(id)) {
                object.put("id", id);
            }
        } catch (Exception e) {

        }
        return  object.toString();
    }
}
