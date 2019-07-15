package com.cwtcn.kmlib.resp;

import com.cwtcn.kmlib.data.TrackerAlarm;

import java.util.List;

/**
 * Created by Allen on 2017/3/20.
 */

public class TrackerAlarmResp {
    public String imei;
    /** 更新会员 */
    public String userName;
    /** 更新时间 */
    public String lut;
    /** 闹铃时段列表 */
    public List<TrackerAlarm> ars;

    public String userId;
}
