package com.smartdevicelink.util;

public class Log {
    public static void i(String tag, String message) {
        System.out.print("\r\nI: " + tag + " - " + message);
    }

    public static void w(String tag, String message) {
        System.out.print("\r\nW: " + tag + " - " + message);
    }

    public static void e(String tag, String message, Throwable t) {
        if (t != null) {
            System.err.print("\r\nE: " + tag + " - " + message + " - " + t.getMessage());
        } else {
            System.err.print("\r\nE: " + tag + " - " + message);
        }
    }
}