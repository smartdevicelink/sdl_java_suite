package com.smartdevicelink.test;



public class Logger{

    private Logger(){}
    
    public static void log(String tag, String msg){
        if(Config.DEBUG){
            System.out.print(tag + ": ");
            System.out.println(msg);
        }
    }
    
}
