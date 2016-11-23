package com.smartdevicelink.api.menu;

import com.smartdevicelink.api.interfaces.SdlContext;

public abstract class SdlMenuItem {

    protected int mIndex = -1;
    private SdlCommandInfo mInfo;

    public SdlMenuItem(String name){
        mInfo = new SdlCommandInfo(name);
    }

    public SdlMenuItem(String name, int index){
        this(name);
        mIndex = index;
    }

    public final String getName(){
        return mInfo.getName();
    }

    final int getId(){
        return mInfo.getId();
    }

    abstract void update(SdlContext sdlContext, int subMenuId);

    abstract void remove(SdlContext sdlContext);

    abstract void registerSelectListener(SdlContext sdlContext);

    abstract void unregisterSelectListener(SdlContext sdlContext);

}
