package com.cwtcn.kmlib.data;

import android.text.TextUtils;

import java.util.List;

/**
 * 设备最新位置
 * Created by Allen on 2017/3/13.
 */

public class TrackerLeastData {

    private String imei;
    private Act act;
    /** 碰撞 */
    private String hit;
    private LocData lct;
    private List<LocData> lcts;
    /** "150724152030 100 105 01 1 01 0*/
    private String st;
    /** 短信上报位置开关 0关，1开 */
    private String sms_location = "0";
    /** 响铃方式 0:震动 1:响铃 2:震动加响铃3:静音 */
    private String ring_mode = "2";
    /** 连接方式 0:长链接 1:短信链接 2: 长链接+短信链接 */
    private String connect_mode = "0";
    /** 自动应答方式 0:关闭 1:自动接听 2:翻腕接听*/
    private String auto_type = "0";
    /** 自动应答时间*/
    private String auto_time;
    /** 手表白名单亲情号码设置 0关闭  1开启*/
    private int enable_fn_wl = 0;
    /** 是否是24小时制 0关闭  1开启*/
    private int hourTime24 = 0;

    public static class Act {
        /** 上传计步时间 */
        public String i;
        /** 电量 */
        public int p;
        /** 本次计步步数 */
        public int s;
        /** 累积步数 */
        public int ss;
    }

    /**
     * 最新更新时间
     * @return
     */
    public long getUploadTime() {
        if(!TextUtils.isEmpty(st) && st.length() > 12) {
            return Long.parseLong(st.substring(0, 12));
        }
        return 0;
    }

    /**
     * 电量
     * @return
     */
    public int getPower() {
        if(!TextUtils.isEmpty(st) && st.length() > 15) {
            return Integer.parseInt(st.substring(13, 15));
        }
        return 0;
    }

    /**
     * 碰撞开关
     * @return
     */
    public int getCrashSwitch() {
        if(!TextUtils.isEmpty(st) && st.length() > 20) {
            return Integer.parseInt(st.substring(19, 20));
        }
        return 0;
    }

    /***
     * 获取碰撞等级
     * @return
     */
    public int getCrashLevel() {
        if(st != null && st.length() > 21) {
            return Integer.valueOf(st.substring(19, 20));
        }
        return 0;
    }

    /**
     * 脱落开关
     * @return
     */
    public int getFallOffSwitch() {
        if(!TextUtils.isEmpty(st) && st.length() > 24) {
            return Integer.parseInt(st.substring(21, 22));
        }
        return 0;
    }

    /**
     * 脱落状态
     * @return
     */
    public int getFallOffStatus() {
        if(!TextUtils.isEmpty(st) && st.length() > 24) {
            return Integer.parseInt(st.substring(22, 23));
        }
        return 0;
    }

    /**
     * 手表信号
     * @return
     */
    public int getSignal() {
        if(lct != null && !TextUtils.isEmpty(lct.signal)) {
            return Integer.valueOf(lct.signal);
        }
        return 0;
    }

    public String getImei() {
        return imei;
    }

    public Act getAct() {
        return act;
    }

    public String getHit() {
        return hit;
    }

    public LocData getLct() {
        return lct;
    }

    public List<LocData> getLcts() {
        return lcts;
    }

    public String getSmsLocation() {
        return sms_location;
    }

    public String getRingMode() {
        return ring_mode;
    }

    public String getConnectMode() {
        return connect_mode;
    }

    public String getAutoType() {
        return auto_type;
    }

    public String getAutoTime() {
        return auto_time;
    }

    public int getEnableFnWl() {
        return enable_fn_wl;
    }

    public int getHourTime24() {
        return hourTime24;
    }

    public void setSmsocation(String smsLocation) {
        this.sms_location = smsLocation;
    }

    public void setRingMode(String mode) {
        this.ring_mode = mode;
    }

    public void setConnectMode(String mode) {
        this.connect_mode = mode;
    }

    public void setEnableFnWl(int enable_fn_wl) {
        this.enable_fn_wl = enable_fn_wl;
    }

    public void setHourTime24(int hourTime24) {
        this.hourTime24 = hourTime24;
    }
}
