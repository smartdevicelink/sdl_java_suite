package com.smartdevicelink.api.menu;

import com.smartdevicelink.api.interfaces.SdlContext;

public class SdlLocalMenuManager {

    private SdlMenu mRootMenu;
    private SdlMenu mGlobalMenuClone;
    private SdlContext mSdlContext;

    public SdlLocalMenuManager(SdlContext sdlContext, SdlMenu globalMenuClone){
        mRootMenu = globalMenuClone;
        mGlobalMenuClone = globalMenuClone;
        mSdlContext = sdlContext;
    }

    void populateLocalMenuOptions(SdlContext sdlContext){

    }

    void removeLocalMenuOptions(SdlContext sdlContext){

    }

}
