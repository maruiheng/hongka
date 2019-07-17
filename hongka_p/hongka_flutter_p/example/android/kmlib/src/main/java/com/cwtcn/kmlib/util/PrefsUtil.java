package com.cwtcn.kmlib.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreference工具类
 * Created by Allen on 2016/12/7.
 */

public class PrefsUtil {

    /**
     * 获取SharedPreference bool类型的返回值
     * @param context
     * @param key
     * @return
     */
    public static boolean getBooleanSharedPreferences(Context context, String key) {
        SharedPreferences sp = null;
        sp = context.getSharedPreferences(Constants.Prefs.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

    /**
     * 获取SharedPreference int类型的返回值
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getIntSharedPreferences(Context context, String key, int defaultValue) {
        SharedPreferences sp = null;
        sp = context.getSharedPreferences(Constants.Prefs.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }

    /**
     * 获取SharedPreference long类型的返回值
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static long getLongSharedPreferences(Context context, String key, long defaultValue) {
        SharedPreferences sp = null;
        sp = context.getSharedPreferences(Constants.Prefs.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sp.getLong(key, defaultValue);
    }

    /**
     * 获取SharedPreference String类型的返回值
     * @param context
     * @param key
     * @return
     */
    public static String getStringSharedPreferences(Context context, String key) {
        return getStringSharedPreferences(context, key, "");
    }

    /**
     * 获取SharedPreference String类型的返回值
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static String getStringSharedPreferences(Context context, String key, String defaultValue) {
        SharedPreferences sp = null;
        sp = context.getSharedPreferences(Constants.Prefs.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    /**
     * 保存到SharedPreference
     * @param context
     * @param accept
     * @param key
     */
    public static void setSharedPreferencesAll(Context context, Object accept, String key) {
        SharedPreferences sp = null;
        sp = context.getSharedPreferences(Constants.Prefs.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        if (accept instanceof Boolean) {
            e.putBoolean(key, (Boolean) accept);
        } else if (accept instanceof String) {
            e.putString(key, (String) accept);
        } else if (accept instanceof Integer) {
            e.putInt(key, (Integer) accept);
        } else if (accept instanceof Long) {
            e.putLong(key, (Long) accept);
        }
        e.commit();
    }

    /**
     * 保存到SharedPreference
     * @param context
     * @param accept
     * @param key
     */
    public static void setSharedPreferencesAll(Context context, String[] accept, String[] key) {
        SharedPreferences sp = null;
        sp = context.getSharedPreferences(Constants.Prefs.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        int length = accept.length;
        for (int i = 0; i < length; i++) {
            e.putString(key[i], accept[i]);
        }
        e.commit();
    }

    /**
     * 保存到SharedPreference
     * @param context
     * @param accept
     * @param key
     */
    public static void setSharedPreferencesAll(Context context, int[] accept, String[] key) {
        SharedPreferences sp = null;
        sp = context.getSharedPreferences(Constants.Prefs.SHARED_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        int length = accept.length;
        for (int i = 0; i < length; i++) {
            e.putInt(key[i], accept[i]);
        }
        e.commit();
    }
}
