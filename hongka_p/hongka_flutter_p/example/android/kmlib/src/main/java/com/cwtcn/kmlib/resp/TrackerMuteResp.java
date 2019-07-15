package com.cwtcn.kmlib.resp;

import com.cwtcn.kmlib.data.TrackerMuteData;

import java.util.List;

/**
 * 静音时段
 * Created by Allen on 2017/3/20.
 */

public class TrackerMuteResp {

    public String imei;

    public String userId;

    public String userName;

    public String lut;

    public List<TrackerMuteData> mrs;
}
