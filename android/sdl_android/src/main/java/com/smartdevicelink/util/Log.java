package com.smartdevicelink.util;

public class Log {
    public static int i(String tag, String message) {
        return android.util.Log.i(tag, message);
    }

    public static int w(String tag, String message) {
        return android.util.Log.w(tag, message);
    }

    public static int e(String tag, String message, Throwable t) {
        if (t != null) {
            return android.util.Log.e(tag, message, t);
        } else {
            return android.util.Log.e(tag, message);
        }
    }
}