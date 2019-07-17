package com.cwtcn.kmlib.protocol;

import com.cwtcn.kmlib.api.KMWearerManager;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.resp.LoginRespData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * 3.2.2.12响应查询Tracker最新数据 (R_Q_TRACKER_INFO)
 *
 * @author Allen
 */
public class TrackerInfoGetRes extends Packet {
    public static final String CMD = "R_Q_TRACKER_INFO";
    private LoginRespData para;
    private String messageJson;

    public TrackerInfoGetRes() {
        super(CMD);
    }

    @Override
    protected Packet dup() {
        return this;
    }

    @Override
    public void decodeArgs(String[] sa, int offset, int length) {
        Gson gson = new Gson();
        try {
            //R_Q_TRACKER_INFO&0&1.0.2&156127746026635264&DefaultSession153133&150724151931&[{}]
            status = sa[offset++];
            if ((NettyClientManager.STR_CODE_OK).equals(status)) {
                offset++;//时间戳
                messageJson = sa[offset++];//返回的消息体
                TrackerInfoGetRes mData = gson.fromJson(messageJson, new TypeToken<TrackerInfoGetRes>() {
                }.getType());
                if (mData != null) {
                    para = mData.para;
                    KMWearerManager.getInstance().mProductIDMap.put(para.imei, para.productId);
                    KMWearerManager.getInstance().mProductSVerMap.put(para.imei, para.sVer);
                    KMWearerManager.getInstance().mProductFVerMap.put(para.imei, para.fVer);
                }
            } else {
                //error = sa[offset++];
            }
        } catch (Exception e) {

        }
    }

    @Override
    public Packet respond(NettyClientManager sc) {
        return super.respond(sc);
    }
}
