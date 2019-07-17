package com.cwtcn.kmlib.data;

import org.json.JSONObject;

/**
 * Created by xhl on 2017/4/25.
 */

public class PhoneSettingData implements Cloneable {

    public String imei;
    /** 应用下载控制开关 1是开启，开启后不能下载应用*/
    public int enabled_down_app = 0;
    /** 应用使用时长控制开关 1是开启，开启后限制应用使用时间 */
    public int enabled_duration_app = 0;
    /** 手机使用时长控制开关 1是开启，开启后限制使用时间 */
    public int enabled_duration_phone = 0;
    /** 手机可用开始时间 */
    public String phone_enabled_stime;
    /** 手机可用结束时间 */
    public String phone_enabled_etime;
    /** 手机每次连续使用时长，分钟 */
    public int phone_once_time = 60;
    /** 手机连续使用后休息时长，分钟 */
    public int phone_rest_time = 30;
    /** 距离提醒开关 */
    public int enabled_dis_warn = 1;
    /** 坐姿提醒开关 */
    public int enabled_sit_posture = 1;
    /** 走路禁用开关 */
    public int enabled_walk = 0;
    /** 开关机控制开关 */
    public int enabled_on_off = 0;
    /** 开关机控制-开机时间 */
    public String phone_on_time = "0600";
    /** 开关机控制-关机时间 */
    public String phone_off_time = "2200";
    /** 通话时长设置 */
    public int phone_call_time = -1;
    /** 模板类型设置*/
    public int template_type = 0;

    @Override
    public Object clone() throws CloneNotSupportedException {
        PhoneSettingData sc = null;
        try
        {
            sc = (PhoneSettingData) super.clone();
        } catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
        return sc;
    }

    public String getJSONData() {
        JSONObject mObject = new JSONObject();
        try {
            mObject.put("imei", imei);
            mObject.put("enabled_down_app", enabled_down_app);
            mObject.put("enabled_duration_app", enabled_duration_app);
            mObject.put("enabled_duration_phone", enabled_duration_phone);
            //mObject.put("phone_enabled_stime", phone_enabled_stime);
            //mObject.put("phone_enabled_etime", phone_enabled_etime);
            mObject.put("phone_once_time", phone_once_time);
            mObject.put("phone_rest_time", phone_rest_time);
            mObject.put("enabled_dis_warn", enabled_dis_warn);
            mObject.put("enabled_sit_posture", enabled_sit_posture);
            mObject.put("enabled_walk", enabled_walk);
            mObject.put("enabled_on_off", enabled_on_off);
            mObject.put("phone_on_time", phone_on_time);
            mObject.put("phone_off_time", phone_off_time);
            mObject.put("phone_call_time", phone_call_time);
            mObject.put("template_type", template_type);
        } catch (Exception e) {
        }
        return  mObject.toString();
    }
}
