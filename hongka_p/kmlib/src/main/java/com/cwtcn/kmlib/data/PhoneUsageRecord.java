package com.cwtcn.kmlib.data;

/**
 * 手机的整体使用记录
 * Created by xhl on 2017/4/25.
 */

public class PhoneUsageRecord {
    public String id = "";

    public int duration = 0;

    public String durationDate;

    public PhoneUsageRecord(String date) {
        durationDate = date;
    }
}
