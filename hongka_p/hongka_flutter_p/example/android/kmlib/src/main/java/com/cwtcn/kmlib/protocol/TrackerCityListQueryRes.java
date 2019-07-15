package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.TrackerCityData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 响应获取手表城市列表
 * @author Allen
 */
public class TrackerCityListQueryRes extends Packet {
    public static final String CMD = "R_Q_TRACKER_CITY_LIST";
    private List<TrackerCityData> data;

    public TrackerCityListQueryRes(){
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
                String jsonData = sa[3];
                Gson gson = new Gson();
                data = gson.fromJson(jsonData, new TypeToken<List<TrackerCityData>>(){}.getType());
            } else {
                msg = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Packet respond(NettyClientManager sav) {
        EventBus.getDefault().post(new KMEvent<TrackerCityData>(KMEventConst.EVENT_CITYLIST_SEARCH, status, msg, data));
        return super.respond(sav);
    }
}
