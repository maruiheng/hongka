package com.cwtcn.kmlib.api;

import android.content.Context;

import com.cwtcn.kmlib.data.MessagePushData;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.protocol.MessagePushReq;

public class KMPushMamager {

    public Context mContext;

    private static KMPushMamager pushManager;


    private KMPushMamager() {

    }

    public static KMPushMamager getInstance() {
        if (pushManager == null) {
            pushManager = new KMPushMamager();
        }
        return pushManager;
    }

    public void init(Context context) {
        mContext = context;
    }

    public void pushSrore(MessagePushData messagePushData) {
        if (messagePushData != null) {
            NettyClientManager.addOutQ(new MessagePushReq(messagePushData));
        }
    }

    public void clearData() {

    }
}
