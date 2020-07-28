package com.smartdevicelink.util;

public class Log {
    public static void i(String tag, String message) {
        android.util.Log.i(tag, message);
    }

    public static void w(String tag, String message) {
        android.util.Log.w(tag, message);
    }

    public static void e(String tag, String message, Throwable t) {
        if (t != null) {
            android.util.Log.e(tag, message, t);
        } else {
            android.util.Log.e(tag, message);
        }
    }
}