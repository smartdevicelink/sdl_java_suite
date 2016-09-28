package com.smartdevicelink.api.menu;

import com.smartdevicelink.api.interfaces.SdlContext;

public abstract class SdlMenuItem {

    private static volatile int autoId = 1;

    protected String mName;
    protected int mId;
    protected int mIndex = -1;

    public SdlMenuItem(String name){
        mName = name;
        mId = generateId();
    }

    public SdlMenuItem(String name, int index){
        mName = name;
        mIndex = index;
        mId = generateId();
    }

    public final String getName(){
        return mName;
    }

    final int getId(){
        return mId;
    }

    static synchronized int generateId(){
        return autoId++;
    }

    abstract void update(SdlContext sdlContext, int subMenuId);

    abstract void remove(SdlContext sdlContext);

    abstract void registerSelectListener(SdlContext sdlContext);

    abstract void unregisterSelectListener(SdlContext sdlContext);

}
