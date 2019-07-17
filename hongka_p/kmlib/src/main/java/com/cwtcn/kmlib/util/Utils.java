package com.cwtcn.kmlib.util;

import android.content.Context;
import android.content.pm.PackageManager;

import java.util.Locale;

/**
 * Created by Allen on 2016/12/8.
 */

public class Utils {

    /**
     * 获取系统语言
     * @param context
     * @return
     */
    public static String getLocale(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        return locale.getLanguage();
    }

    /**
     * 获取APP版本
     * @param context
     * @return
     */
    public static String getAppVersion(Context context) {
        String versionName;
        try {
            versionName = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
            versionName = "4.0";
        }
        return versionName;
    }

    public static String getString(Context context, int resId) {
        return context.getString(resId);
    }


}
