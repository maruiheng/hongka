package com.cwtcn.kmlib.netty;

import android.os.AsyncTask;

import com.cwtcn.kmlib.service.NettyService;

/**
 * 启动Netty的Task
 * Created by Allen on 2016/12/8.
 */

public class NettyTask extends AsyncTask<Void, Void, Void> {

    private final NettyService mNettyService;

    public NettyTask(NettyService s) {
        mNettyService = s;
    }

    @Override
    protected Void doInBackground(Void... params) {
        NettyClientManager.getInstance().loop();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mNettyService.stopSelf();
        super.onPostExecute(aVoid);
    }
}
