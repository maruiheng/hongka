package com.cwtcn.kmlib.protocol;

import android.text.TextUtils;

import com.cwtcn.kmlib.data.LocationMark;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 3.64查询位置到达返回
 *
 * @author Allen
 */
public class LocMarkQueryRes extends Packet {
    public static final String CMD = "R_Q_LOC_MARK";
    private String cmdId;
    private List<LocationMark> data;

    public LocMarkQueryRes() {
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
            cmdId = sa[2];
            if (status.equals(NettyClientManager.STR_CODE_OK)) {
                msg = "";
                String jsonData = sa[3].toString();
                Gson gson = new Gson();
                data = gson.fromJson(jsonData, new TypeToken<List<LocationMark>>() {}.getType());
            } else {
                msg = sa[offset++];
            }
        } catch (Exception e) {
            e.getCause();
        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        if(!TextUtils.isEmpty(cmdId) && cmdId.contains("HOME")) {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_HOME_MARK_GET, status, msg, data));
        } else {
            EventBus.getDefault().post(new KMEvent(KMEventConst.EVENT_LOC_MARK_GET, status, msg, data));
        }
        return super.respond(sc);
    }
}
