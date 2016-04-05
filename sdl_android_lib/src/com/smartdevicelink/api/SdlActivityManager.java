package com.smartdevicelink.api;

import android.os.Bundle;

import java.util.Stack;

public class SdlActivityManager implements SdlLifecycleListener {

    SdlActivity mCurrentActivity;
    Stack<SdlActivity> mBackStack;


    public SdlActivityManager(){

    }

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

    public void onSdlAppLaunch(SdlActivity mainActivity){

    }

    public void onSdlAppResume(Bundle savedActivityManager){

    }

    public void startSdlActivity(SdlActivity activity, int flags){

    }


}
