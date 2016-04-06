package com.smartdevicelink.api;

import android.os.Bundle;

import com.smartdevicelink.api.interfaces.SdlContext;

import java.util.Stack;

public class SdlActivityManager implements SdlApplication.LifecycleListener {

    private SdlContext mSdlApplicationContext;

    SdlActivity mCurrentActivity;
    Stack<SdlActivity> mBackStack = new Stack<>();

    public SdlActivityManager(SdlContext sdlApplicationcontext){
        mSdlApplicationContext = sdlApplicationcontext;
    }

    /**
     * Removes the current activity from the backstack
     */
    public void back(){

    }

    public void back(String name){

    }


    @Override
    public void onSdlConnect() {

    }

    @Override
    public void onSdlDisconnect() {

    }

    @Override
    public void onBackground() {

    }

    @Override
    public void onForeground() {

    }

    @Override
    public void onExit() {

    }

    /**
     * Creates the default SdlActivity based on the app coming into HMI FULL for the first time
     * @param mainActivity Description of the derived SdlActivity class to be instantiated
     */
    public void onSdlAppLaunch(Class<? extends SdlActivity> mainActivity){

    }

    /**
     * Restores the SdlActivityManager to a previous point
     * @param savedActivityManager serialized information needed to reconstruct the ActivityManager
     */
    public void onSdlAppResume(Bundle savedActivityManager){

    }

    /**
     * Instantiates a new activity to be added to the backstack
     * @param activity Description of the derived SdlActivity class to be instantiated
     * @param flags Indication from the caller on how the backstack should behave
     */
    public void startSdlActivity(SdlApplication sdlApplicationContext, Class<? extends SdlActivity> activity, int flags){

    }


}
