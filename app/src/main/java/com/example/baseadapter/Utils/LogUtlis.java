package com.example.baseadapter.Utils;
import android.util.Log;

public class LogUtlis {

    public static boolean isDebug = true; // default debug model true

    public static void d(String className , int line, String msg) {
        if (isDebug) {
            Log.d(className+": " + line, msg);
        }
        return;
    }
    private static int LOG_MAXLENGTH = 2000;
    public static void d(String TAG, String msg) {
        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAXLENGTH;
        for (int i = 0; i < 100; i++) {
            //剩下的文本还是大于规定长度则继续重复截取并输出
            if (strLength > end) {
                LogUtlis.e(TAG + i,22, msg.substring(start, end));
                start = end;
                end = end + LOG_MAXLENGTH;
            } else {
                LogUtlis.e(TAG,26, msg.substring(start, strLength));
                break;
            }
        }
    }

    public static void e(String className , int line, String msg) {
        if (isDebug) {
            Log.e(className+": " + line, msg);
        }
        return;
    }

    public static void v(String className , String line, String msg) {
        if (isDebug) {
            Log.v(className+": " + line, msg);
        }
        return;
    }

    public static void w(String className , String line, String msg) {
        if (isDebug) {
            Log.w(className+": " + line, msg);
        }
        return;
    }

    public static void i(String className , String line, String msg) {
        if (isDebug) {
            Log.i(className+": " + line, msg);
        }
        return;
    }
}
