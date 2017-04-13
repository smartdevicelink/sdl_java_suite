package com.smartdevicelink.api.menu;

class SdlCommandInfo {

    private static volatile int autoId = 1;
    protected String mName;
    protected int mId;


    SdlCommandInfo(String name){
        mName = name;
        mId = generateId();
    }

    final String getName(){
        return mName;
    }

    final int getId(){
        return mId;
    }

    static synchronized int generateId(){
        return autoId++;
    }
}
