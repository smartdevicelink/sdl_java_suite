package com.smartdevicelink.util;

public class Log {
    public static void i(String tag, String message) {
        System.out.print("\r\nINFO: " + tag + " - " + message);
    }

    public static void w(String tag, String message) {
        System.out.print("\r\nWARN: " + tag + " - " + message);
    }

    public static void e(String tag, String message, Throwable t) {
        if (t != null) {
            System.out.print("\r\nERROR: " + tag + " - " + message + " - " + t.getMessage());
        } else {
            System.out.print("\r\nERROR: " + tag + " - " + message);
        }
    }
}