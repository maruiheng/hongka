package com.cwtcn.kmlib.netty;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.SystemClock;

import com.cwtcn.kmlib.service.NettyService;
import com.cwtcn.kmlib.util.MyLog;
import com.cwtcn.kmlib.util.NetworkUtil;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 监控Netty Service
 * Created by Allen on 2016/12/7.
 */

public class NettyMonitor {

    private static String TAG = "NettyMonitor";

    private static final String ALARM_ACTION = "com.cwtcn.kmlib.monitor";
    private static final AtomicBoolean isRegisterAlarm = new AtomicBoolean(false);

    /***
     * 启动 Netty Service
     * @param context
     */
    public static void startNettyService(Context context) {
        /*if(!KMManager.getInstance().hasUserAndPwd()) {
            return;
        }*/
        Intent i = new Intent(context, NettyService.class);
        if (!NetworkUtil.hasNetwork(context)) {
            i.putExtra("noNetwork", "n");
        } else {
            i.putExtra("noNetwork", "y");
        }
        MyLog.i(TAG, "startSavService >" + !NetworkUtil.hasNetwork(context));
        context.startService(i);
        //registerAlarm(context);
    }

    /***
     * 停止 Netty Service
     * @param context
     */
    public static void stopNettyService(Context context) {
        MyLog.i(TAG, "stopSavService");
        try {
            Intent i = new Intent(context, NettyService.class);
            context.stopService(i);
        } catch (Exception e) {
        }
    }


    /**
     * 检测Service服务是否运行
     * @param context
     * @return
     */
    public static boolean isNettyServiceRuning(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> infos = am.getRunningServices(100); // 30是最大值
        for (ActivityManager.RunningServiceInfo info : infos) {
            if (info.service.getClassName().equals("com.cwtcn.kmlib.service.NettyService")) {// 就是说之前已经连上了
                return true;
            }
        }
        return false;
    }

    /**
     * 注册监控Netty Service的闹钟
     * @param context
     */
    public static void registerAlarm(Context context) {
        if (isRegisterAlarm.get()) {
            return;
        }
        isRegisterAlarm.set(true);
        final Intent intent = new Intent(context, NettyReceiver.class);
        intent.setAction(ALARM_ACTION);
        final PendingIntent pending = PendingIntent.getBroadcast(context, 0, intent, 0);
        final AlarmManager alarm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarm.cancel(pending);
        alarm.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 3 * 60 * 1000, pending);
    }

    public static void onReceive(Context context, Intent intent) {
        if (null != intent && ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            startNettyService(context);
        } else {
            MyLog.i(TAG, "registerAlarm");
            registerAlarm(context);
        }
    }
}
