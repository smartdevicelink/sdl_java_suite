package com.smartdevicelink.util;

public class Log {
    public static int i(String tag, String message) {
        android.util.Log.i(tag, message);
        return 10;
    }

    public static int w(String tag, String message) {
        android.util.Log.w(tag, message);
        return 10;
    }

    public static int e(String tag, String message, Throwable t) {
        if (t != null) {
            android.util.Log.e(tag, message, t);
        } else {
            android.util.Log.e(tag, message);
        }
        return 10;
    }
}