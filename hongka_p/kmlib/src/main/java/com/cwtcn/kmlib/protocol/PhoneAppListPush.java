package com.cwtcn.kmlib.protocol;

import android.text.TextUtils;
import android.util.Log;

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
 * 推送X2应用设置时间,是否卸载(P_X2_APP_SETTING)
 * Created by xhl on 2017/4/25.
 */

public class PhoneAppListPush extends Packet {
    public static final String CMD = "P_X2_APP_SETTING";

    public String imei;

    private String mResponse;

    private PhoneAppListData data;

    private Map<String, List<PhoneAppData>> mPhoneAppMap = new HashMap<>();

    public PhoneAppListPush() {
        super(CMD);
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public void decodeArgs(String[] sa, int offset, int length) {
        super.decodeArgs(sa, offset, length);
        try {
            mResponse = sa[offset++];
            Gson gson = new Gson();
            PhoneAppListData data = gson.fromJson(mResponse, PhoneAppListData.class);
            if(data != null && data.tpaList != null && !TextUtils.isEmpty(data.tpaList.get(0).imei)){
                List<PhoneAppData> mList = new ArrayList<PhoneAppData>();
                for(PhoneAppData data1 : data.tpaList) {
                    for(PhoneAppListData.XDD xdd : data.xddList) {
                        if(data1.name.equals(xdd.appName)) {
                            data1.duration = xdd.duration;
                            break;
                        }
                    }
                    mList.add(data1);
                }
                mPhoneAppMap.put(data.tpaList.get(0).imei, mList);
            }
            status = "0";
        } catch(Exception ex) {
            Log.e(CMD, "exception");
        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_APP_USETIMEORUNINSTALLPUSH, status, msg,mPhoneAppMap));
        return super.respond(sc);
    }
}
