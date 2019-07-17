package com.cwtcn.kmlib.api;

import android.content.Context;

import com.cwtcn.kmlib.netty.NettyClientManager;

/**
 * Created by Allen on 2016/12/7.
 */

public class KMManager {

    private static KMManager km;

    public Context mContext;

    public String host = "123.207.163.21";//"tr.abardeen.com";

    public int port = 9997;//8080;

//    public String host = "tr.abardeen.com";//"tr.abardeen.com";
//
//    public int port = 8080;//8080;

    public String mHttpUrl;

    /** 服务器下发的下载地址 */
    public String mDownloadUrl;

    public static boolean IS_FOREIGN_VERSION = false;

    private KMManager() {

    }

    /***
     * 实例化KMManager
     * @return
     */
    public static KMManager getInstance() {
        if(km == null) {
            km = new KMManager();
        }
        return km;
    }

    /***
     * 初始化数据
     * @param context
     */
    public void init(Context context) {
        this.mContext = context;
        NettyClientManager.getInstance().init(context);
        KMUserManager.getInstance().init(context);
        KMWearerManager.getInstance().init(context);
        KMContactManager.getInstance().init(context);
        KMLocationManager.getInstance().init(context);
        KMSettingManager.getInstance().init(context);
        KMDBHelper.getInstance().initDatabase(context);
        KMPushMamager.getInstance().init(context);
    }
    public void clearData() {
        KMUserManager.getInstance().clearData();
        KMWearerManager.getInstance().clearData();
        KMContactManager.getInstance().clearData();
        KMLocationManager.getInstance().clearData();
        KMSettingManager.getInstance().clearData();
        KMPushMamager.getInstance().clearData();
    }

    public void setHostPort(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void setHttpUrl(String url) {
        this.mHttpUrl = url;
    }

    public void setHostPort(String host, int port, String url) {
        this.host = host;
        this.port = port;
        this.mHttpUrl = url;
    }

}
