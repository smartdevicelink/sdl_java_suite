package com.smartdevicelink.api.menu;

import com.smartdevicelink.api.interfaces.SdlContext;

public abstract class SdlMenuEntry {

    private static volatile int autoId = 1;

    protected String mName;
    protected int mId;

    protected boolean isDisplayed = false;
    protected boolean isChanged = true;
    protected SdlMenu mRootMenu = null;
    protected SdlContext mSdlContext;

    public SdlMenuEntry(SdlContext sdlContext, String name){
        mSdlContext = sdlContext;
        mName = name;
        mId = generateId();
    }

    abstract void update();

    abstract void remove();

    public final void setName(String name){
        mName = name;
        isChanged = true;
    }

    public final String getName(){
        return mName;
    }

    final int getId(){
        if(mRootMenu == null){
            return 0;
        } else {
            return mId;
        }
    }

    final void setRootMenu(SdlMenu menu){
        mRootMenu = menu;
    }

    static synchronized int generateId(){
        return autoId++;
    }

}
