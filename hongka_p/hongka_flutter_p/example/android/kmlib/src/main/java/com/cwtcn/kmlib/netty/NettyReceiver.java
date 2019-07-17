package com.cwtcn.kmlib.netty;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.cwtcn.kmlib.service.NettyService;

/**
 * Created by Allen on 2016/12/7.
 */

public class NettyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(!NettyMonitor.isNettyServiceRuning(context)) {
            NettyMonitor.onReceive(context, intent);
        }
    }
}
