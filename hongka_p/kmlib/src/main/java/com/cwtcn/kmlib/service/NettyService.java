package com.cwtcn.kmlib.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.cwtcn.kmlib.api.KMUserManager;
import com.cwtcn.kmlib.netty.NettyClientManager;
import com.cwtcn.kmlib.netty.NettyMonitor;
import com.cwtcn.kmlib.netty.NettyTask;
import com.cwtcn.kmlib.util.Constants;
import com.cwtcn.kmlib.util.MyLog;
import com.cwtcn.kmlib.util.NetworkUtil;
import com.cwtcn.kmlib.util.PrefsUtil;

/**
 * 启动 Netty
 * Created by Allen on 2016/12/7.
 */

public class NettyService extends Service {

    public static final String TAG = "NettyService";

    private NettyClientManager mNettyManager;

    private NettyTask mTask;

    @Override
    public void onCreate() {
        super.onCreate();
        initTask();
        initReceiver();
        NettyMonitor.registerAlarm(getApplicationContext());
    }

    private void initTask() {
        mTask = new NettyTask(this);
    }

    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver, filter);
    }

    BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.net.conn.CONNECTIVITY_CHANGE")) {
                if (NetworkUtil.hasNetwork(context) && !NettyClientManager.getInstance().isConnected.get()) {
                    boolean isExitState = PrefsUtil.getBooleanSharedPreferences(context, Constants.Prefs.KEY_EXIT_STATE);
                    if (!isExitState && KMUserManager.getInstance().hasUserAndPwd()) {
                        NettyClientManager.getInstance().enableConn.set(true);
                        if (null != mTask && mTask.getStatus() != AsyncTask.Status.RUNNING) {
                            mTask.execute();// 如果异步任务不为空并且没有运行的话，启动
                            MyLog.e(TAG, "task.execute()");
                        }
                    }
                }
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            if (null != mTask && mTask.getStatus() != AsyncTask.Status.RUNNING) {
                mTask.execute();// 如果异步任务不为空并且没有运行的话，启动
                MyLog.e(TAG, "task.execute()");
            }
            // 如果网络状况变化的话
            boolean isRunning = NettyClientManager.getInstance().isRunning.get();
            if (null != intent && intent.hasExtra("noNetwork") && isRunning) {
                if ("n".equals(((String) intent.getStringExtra("noNetwork")))) {
                    NettyClientManager.getInstance().disconnect();
                    if (null != mTask) {
                        mTask.cancel(true);
                        mTask = null;
                    }
                } else if ("y".equals(((String) intent.getStringExtra("noNetwork")))) {
                    if (!NettyClientManager.getInstance().isConnected.get()) {
                        NettyClientManager.getInstance().isRunning.set(true);
                    }
                }
            }
        } catch (Exception e) {
            MyLog.e(TAG, "e==>" + e.toString());
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (null != mTask && mTask.getStatus() == AsyncTask.Status.RUNNING) {
            mTask.cancel(true);
            mTask = null;
        }
        NettyClientManager.getInstance().enableConn.set(false);
        unregisterReceiver(receiver);
        NettyClientManager.getInstance().disconnect();
        super.onDestroy();
    }

}
