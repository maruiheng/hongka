
package com.cwtcn.kmlib.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义LOG，根据LOGLEVEL决定是否控制台输出
 */
public class MyLog {

    private static final int NOTHING = -1;
    private static final int ERR = 1;
    private static final int INFO = 2;
    private static final int DEBUG = 3;

    public static final int LOGLEVEL = DEBUG;
    public static final boolean ISPRINT = false;
    public static final String LABEL = "kmlib";

    public static void w(String tag, String msg) {
        if (LOGLEVEL >= DEBUG) {
            Log.w(tag, LABEL + msg);
            if(ISPRINT) {
                saveCrashInfo2File(msg);
            }
        }
    }

    public static void d(String tag, String msg) {
        if (LOGLEVEL >= DEBUG) {
            Log.d(tag, LABEL + msg);
            if(ISPRINT) {
            	saveCrashInfo2File(msg);
            }
        }
    }

    public static void i(String tag, String msg) {
        if (LOGLEVEL >= INFO) {
            Log.i(tag, LABEL + msg);
            if (ISPRINT) {
                saveCrashInfo2File(msg);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (LOGLEVEL >= ERR) {
            Log.e(tag, LABEL + msg);
            if (ISPRINT) {
                saveCrashInfo2File(msg);
            }
        }
    }

    private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static String saveCrashInfo2File(String ex) {
        StringBuffer sb = new StringBuffer();
        String time = format.format(new Date());
        sb.append(time + "==" + ex + "/\n");
        // 保存文件
        String fileName = LABEL + "-log.log";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                File dir = new File(Environment.getExternalStorageDirectory() + "/crash/");
                Log.i("CrashHandler", dir.toString());
                if (!dir.exists()) dir.mkdir();
                FileOutputStream fos = new FileOutputStream(dir + "/" + fileName, true);
                fos.write(sb.toString().getBytes());
                fos.close();
                return fileName;
            } catch (FileNotFoundException e) {
                //e.printStackTrace();
            } catch (IOException e) {
                //e.printStackTrace();
            }
        }
        return null;
    }
}
