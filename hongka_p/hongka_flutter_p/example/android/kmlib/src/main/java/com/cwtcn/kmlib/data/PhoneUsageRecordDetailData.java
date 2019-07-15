package com.cwtcn.kmlib.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 手机使用记录详情
 * Created by xhl on 2017/4/25.
 */

public class PhoneUsageRecordDetailData implements Parcelable {
    //{"appName":"电话","createTime":-28800000,"duration":156,"durationCount":0,"durationDate":"2016-10-19 00:00:00.0","id":320775505877934081,"imei":"850850000050073"}
    public String id;

    public String appName;

    public String imei;

    public int duration;

    public int durationCount;

    public String durationDate;

    public String url;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(appName);
        dest.writeString(imei);
        dest.writeInt(duration);
        dest.writeInt(durationCount);
        dest.writeString(durationDate);
        dest.writeString(url);
    }

    public static final Parcelable.Creator<PhoneUsageRecordDetailData> CREATOR = new Creator<PhoneUsageRecordDetailData>() {

        @Override
        public PhoneUsageRecordDetailData createFromParcel(Parcel source) {
            PhoneUsageRecordDetailData data = new PhoneUsageRecordDetailData();
            data.id = source.readString();
            data.appName = source.readString();
            data.imei = source.readString();
            data.duration = source.readInt();
            data.durationCount = source.readInt();
            data.durationDate = source.readString();
            data.url = source.readString();
            return data;
        }

        @Override
        public PhoneUsageRecordDetailData[] newArray(int size) {
            return new PhoneUsageRecordDetailData[size];
        }


    };
}
