package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.TrackerCityData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

/**
 * 响应获取手表城市列表
 * @author Allen
 */
public class TrackerCityQueryRes extends Packet {
    public static final String CMD = "R_Q_TRACKER_CITY";
    private TrackerCityData data;

    public TrackerCityQueryRes(){
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
            status = sa[1];
            if(status.equals(NettyClientManager.STR_CODE_OK)) {
                String json = sa[3];
                Gson gson = new Gson();
                data = gson.fromJson(json, new TypeToken<TrackerCityData>(){}.getType());
            } else {
                msg = sa[3];
            }
        } catch (Exception e) {
            e.getCause();
        }
    }

    @Override
    public Packet respond(NettyClientManager sav) {
        EventBus.getDefault().post(new KMEvent<TrackerCityData>(KMEventConst.EVENT_CITY_GET, status, msg, data));
        return super.respond(sav);
    }
}
