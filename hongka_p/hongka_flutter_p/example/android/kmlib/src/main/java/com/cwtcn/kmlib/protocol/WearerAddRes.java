package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMLocationManager;
import com.cwtcn.kmlib.api.KMWearerManager;
import com.cwtcn.kmlib.data.Wearer;
import com.cwtcn.kmlib.event.KMEvent;
import com.cwtcn.kmlib.event.KMEventConst;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 3.36添加佩戴对象返回应答
 *
 * @author Allen
 */
public class WearerAddRes extends Packet {
    public static final String CMD = "R_C_WEARER";
    private String orderId;
    private String jsonStr;
    private Wearer mWearer;

    public WearerAddRes() {
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
            status = sa[offset++];
            if (status.equals(NettyClientManager.STR_CODE_OK)) {// 成功
                orderId = sa[offset++];
                jsonStr = sa[offset++];
                Gson gson = new Gson();
                mWearer = gson.fromJson(jsonStr, Wearer.class);
                if (KMWearerManager.getInstance().getWearers() != null) {
                    KMWearerManager.getInstance().getWearers().add(mWearer);
                } else {
                    CopyOnWriteArrayList<Wearer> wearers = new CopyOnWriteArrayList<Wearer>();
                    wearers.add(mWearer);
                    KMWearerManager.getInstance().setWearers(wearers);
                }
                KMWearerManager.getInstance().mProductIDMap.put(mWearer.getImei(), mWearer.getProductId());
                KMWearerManager.getInstance().trackerInfoGet(mWearer.getImei());
                KMLocationManager.getInstance().trackerLDGet(mWearer.getImei());
                if (mWearer.areas != null && mWearer.areas.size() > 0) {
                    KMLocationManager.getInstance().alertAreaGet(mWearer.areas.get(0).getId());
                }
            } else {
                offset++;
                msg = sa[offset++];
            }
        } catch (Exception e) {

        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        EventBus.getDefault().post(new KMEvent<Wearer>(KMEventConst.EVENT_WEARER_ADD, status, msg, mWearer));
        return super.respond(sc);
    }
}
