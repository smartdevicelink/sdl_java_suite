package com.smartdevicelink.api.menu;

import com.smartdevicelink.api.interfaces.SdlContext;

public abstract class SdlMenuItem {

    private static volatile int autoId = 1;

    protected String mName;
    protected int mId;

    public SdlMenuItem(String name){
        mName = name;
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

    abstract void update(SdlContext sdlContext, int subMenuId, int index);

    abstract void remove(SdlContext sdlContext);

    abstract void registerSelectListener(SdlContext sdlContext);

    abstract void unregisterSelectListener(SdlContext sdlContext);

}
