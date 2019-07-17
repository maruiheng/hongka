package com.cwtcn.kmlib.event;

import com.cwtcn.kmlib.data.PhoneAppData;

import java.util.List;
import java.util.Map;

/**
 * KM事件
 * Created by Allen on 2016/12/19.
 */

public class KMEvent<T> {
    /** ID */
    private int mEventId = -1;
    /** 返回状态，成功为"0" */
    private String status;
    /** 返回消息， */
    private String msg;
    /** imei */
    private String imei;
    /** 返回数据 */
    private T data;
    /** 返回数据列表 */
    private List<T> list;

    private Map<String, List<T>> map;

    public KMEvent(int eventId, String status, String msg) {
        this.mEventId = eventId;
        this.status = status;
        this.msg = msg;
    }

    public KMEvent(int eventId, String status, String msg, T data) {
        this.mEventId = eventId;
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public KMEvent(int eventId, String status, String msg, Map<String, List<T>> data) {
        this.mEventId = eventId;
        this.status = status;
        this.msg = msg;
        this.map = data;
    }


    public KMEvent(int eventId, String status, String msg, List<T> data) {
        this.mEventId = eventId;
        this.status = status;
        this.msg = msg;
        this.list = data;
    }

    public KMEvent(int eventId, String status, String msg, String imei, T data) {
        this.mEventId = eventId;
        this.status = status;
        this.msg = msg;
        this.imei = imei;
        this.data = data;
    }

    public KMEvent(int eventId, String status, String msg, String imei, List<T> list) {
        this.mEventId = eventId;
        this.status = status;
        this.msg = msg;
        this.imei = imei;
        this.list = list;
    }

    public int getEventId() {
        return mEventId;
    }

    public String getEventStatus() {
        return status;
    }

    public String getEventMsg() {
        return msg;
    }

    public T getEventData() {
        return data;
    }

    public List<T> getEventDataList() {
        return list;
    }

    public Map<String, List<T>> getEventDataMap(){
        return map;
    }

    public String getImei() {
        return imei;
    }
}
