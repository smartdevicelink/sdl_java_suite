package com.smartdevicelink.util;

class LogWrapper {

    public static int i(String tag, String message){
        System.out.print("\r\nINFO: " + tag+ " - " + message);
        return 10;


    }
    public static int v(String tag, String message){
        System.out.print("\r\nVERBOSE: " + tag+ " - " + message);
        return 10;


    }
    public static int d(String tag, String message){
        System.out.print("\r\nDEBUG: " + tag+ " - " + message);
        return 10;


    }
    public static int w(String tag, String message){
        System.out.print("\r\nWARN: " + tag+ " - " + message);
        return 10;


    }
    public static int e(String tag, String message){
        System.out.print("\r\nERROR: " + tag+ " - " + message);
        return 10;

    }
    public static int e(String tag, String message, Exception e){
        if(e != null){
            System.out.print("\r\nERROR: " + tag+ " - " + message + " - " + e.getMessage());
        }else{
            System.out.print("\r\nERROR: " + tag+ " - " + message);
        }
        return 10;
    }
    public static int e(String tag, String message, Throwable t){
        if(t != null){
            System.out.print("\r\nERROR: " + tag+ " - " + message + " - " + t.getMessage());
        }else{
            System.out.print("\r\nERROR: " + tag+ " - " + message);
        }
        return 10;
    }

}