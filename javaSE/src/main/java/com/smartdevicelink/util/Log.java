package com.smartdevicelink.util;

public class Log {
    public static int i(String tag, String message) {
        System.out.print("\r\nINFO: " + tag + " - " + message);
        return 4000;
    }

    public static int w(String tag, String message) {
        System.out.print("\r\nWARN: " + tag + " - " + message);
        return 4000;
    }

    public static int e(String tag, String message, Throwable t) {
        if (t != null) {
            System.out.print("\r\nERROR: " + tag + " - " + message + " - " + t.getMessage());
        } else {
            System.out.print("\r\nERROR: " + tag + " - " + message);
        }
        return 4000;
    }
}