package com.cwtcn.kmlib.protocol;

import android.text.TextUtils;

import com.cwtcn.kmlib.data.HabitData;
import com.cwtcn.kmlib.util.DateUtil;

import org.json.JSONObject;

/**
 * 设置好习惯
 * Created by xhl on 2017/4/25.
 */

public class HobbySetReq extends Packet {
    public static final String CMD = "SET_X2_GOOD_HABIT";
    private String imei;
    /** 0保存 1更新 2删除*/
    private String type;
    private String habit;
    /**默认开关*/
    private int enabled;
    /**提醒时间段*/
    private String remind_time;
    /**循环周期*/
    private String week_type;
    private String id;
    private HabitData data;

    public HobbySetReq() {
        super(CMD);
    }

    public HobbySetReq(HabitData data) {
        super(CMD);
        this.data = data;
    }
    @Override
    protected Packet dup() {
        return this;
    }
    @Override
    public String encodeArgs() {
        put(CMD);
        put(DateUtil.getDalayTimeId());
        put(data.getJSONData());
        return para2String();
    }
}
