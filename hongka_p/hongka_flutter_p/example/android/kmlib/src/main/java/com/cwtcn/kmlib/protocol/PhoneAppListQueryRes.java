package com.cwtcn.kmlib.protocol;

import android.text.TextUtils;

import com.cwtcn.kmlib.data.PhoneAppData;
import com.cwtcn.kmlib.data.PhoneAppListData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 响应获取手机第三方应用列表(R_QUERY_THIRD_PARTY_LIST)
 * Created by xhl on 2017/4/25.
 */

public class PhoneAppListQueryRes extends Packet {
    public static final String CMD = "R_QUERY_THIRD_PARTY_LIST";
    private Map<String, List<PhoneAppData>> mPhoneAppMap = new HashMap<>();
    private String msg;

    public PhoneAppListQueryRes() {
        super(CMD);
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public void decodeArgs(String[] sa, int offset, int length) {
        super.decodeArgs(sa, offset, length);
        status = sa[offset++];
        offset++;
        try {
            if (status.equals("0")) {
                String jsonData = sa[offset++];
                msg = jsonData;
                Gson gson = new Gson();
                PhoneAppListData data = gson.fromJson(jsonData, PhoneAppListData.class);
                if (data != null && data.tpaList .size()>0 && !TextUtils.isEmpty(data.tpaList.get(0).imei)) {
                    List<PhoneAppData> mList = new ArrayList<PhoneAppData>();
                    for (PhoneAppData data1 : data.tpaList) {
                        for (PhoneAppListData.XDD xdd : data.xddList) {
                            if (data1.name.equals(xdd.appName)) {
                                data1.duration = xdd.duration;
                                break;
                            }
                        }
                        mList.add(data1);
                    }
                    mPhoneAppMap.put(data.tpaList.get(0).imei, mList);
                }
            } else {
                msg = sa[offset++];
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_PHONE_3RDAPP_LIST, status, msg, mPhoneAppMap));
        return super.respond(sc);
    }
}
