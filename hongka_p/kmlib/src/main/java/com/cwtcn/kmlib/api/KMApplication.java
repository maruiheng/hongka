package com.cwtcn.kmlib.api;

import android.support.multidex.MultiDexApplication;

/**
 * KM的Application
 * Created by Allen on 2017/4/10.
 */

public class KMApplication extends MultiDexApplication {

    public static KMApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        KMManager.getInstance().init(getApplicationContext());
    }

    public static KMApplication getInstance() {
        return instance;
    }
}
