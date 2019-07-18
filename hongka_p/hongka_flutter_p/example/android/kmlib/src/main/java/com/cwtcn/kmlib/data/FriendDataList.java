package com.cwtcn.kmlib.data;

import java.util.List;

public class FriendDataList {
    /** imei */
    private String imei;
    /** 小伙伴 */
    private List<FriendData> list;

    public FriendDataList(String imei, List<FriendData> list) {
        this.imei = imei;
        this.list = list;
    }

    public String getImei() {
        return imei;
    }

    public List<FriendData> getList() {
        return list;
    }
}
