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
 * 推送好习惯
 * Created by xhl on 2017/4/25.
 */

public class HobbyPush extends Packet {
    public static final String CMD = "P_X2_APP_GOOD_HABIT";
    private String msg;
    private List<HabitData> data;

    public HobbyPush() {
        super( CMD);
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public void decodeArgs(String[] sa, int offset, int length) {
        super.decodeArgs(sa, offset, length);
        try {
            msg = sa[1];
            Gson gson = new Gson();
            data = gson.fromJson(msg, new TypeToken<List<HabitData>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_SET_GOOD_HABIT_PUSH, status, msg,data));
        return super.respond(sc);
    }
}
