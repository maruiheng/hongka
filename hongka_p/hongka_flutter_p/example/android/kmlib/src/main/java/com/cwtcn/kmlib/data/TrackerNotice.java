package com.cwtcn.kmlib.data;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/***
 * 手表的推送消息
 *
 * @author Allen
 */
@Entity
public class TrackerNotice {
    //"content":"您的宝贝[3593]在2016-04-25 10:36:37给您发送了一条亲情语音","functionName":"亲情语音","id":0,"imei":"867327020103593","latitude":38.875653515563563,"longitude":121.541012915887880,"memberId":195989763067478016,"operationTime":1461551797629,"title":"您的宝贝[3593]给您发送了一条亲情语音","type":1
    @Id(autoincrement = true)
    private long id;
    private String title;
    private String content;
    private String functionName;
    private String imei;
    private double latitude;
    private double longitude;
    private String memberId;
    private long operationTime;
    private int type;
    private int unread = 0;


    public TrackerNotice(int id, String imei, String memId, String funName, String title, String content, String opTime, String lat, String lon, int type, int unread) {
        this.id = id;
        this.imei = imei;
        this.memberId = memId;
        this.functionName = funName;
        this.title = title;
        this.content = content;
        this.operationTime = Long.parseLong(opTime);
        this.latitude = Double.parseDouble(lat);
        this.longitude = Double.parseDouble(lon);
        this.type = type;
        this.unread = unread;
    }

    @Generated(hash = 51975052)
    public TrackerNotice(long id, String title, String content, String functionName, String imei, double latitude, double longitude, String memberId, long operationTime, int type, int unread) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.functionName = functionName;
        this.imei = imei;
        this.latitude = latitude;
        this.longitude = longitude;
        this.memberId = memberId;
        this.operationTime = operationTime;
        this.type = type;
        this.unread = unread;
    }

    @Generated(hash = 1727845629)
    public TrackerNotice() {
    }

    @Override
    public boolean equals(Object o) {
        if (this.id == (((TrackerNotice) o).id)) {
            return true;
        } else {
            return false;
        }
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getImei() {
        return this.imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getMemberId() {
        return this.memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public long getOperationTime() {
        return this.operationTime;
    }

    public void setOperationTime(long operationTime) {
        this.operationTime = operationTime;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUnread() {
        return this.unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

}
