package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.data.HabitData;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 响应获取好习惯
 * Created by xhl on 2017/4/25.
 */

public class HobbyQueryRes extends Packet {
    public static final String CMD = "R_QUERY_X2_GOOD_HABIT";
    private String msg;
    private List<HabitData> data;

    public HobbyQueryRes(){
        super(CMD);
    }
    @Override
    protected Packet dup() {
        return this;
    }


    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_QUERY_GOOD_HABIT, status, msg,data));
        return super.respond(sc);
    }

    @Override
    public void decodeArgs(String[] sa, int offset, int length) {
        super.decodeArgs(sa, offset, length);
        try {
            status = sa[1];
            if(status.equals("0")) {
                msg = sa[3];
                Gson gson = new Gson();
                data = gson.fromJson(msg, new TypeToken<List<HabitData>>() {
                }.getType());
            } else {
                msg = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
