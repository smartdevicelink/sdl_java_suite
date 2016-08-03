package com.smartdevicelink.api;

import android.os.Bundle;

import com.smartdevicelink.api.interfaces.SdlContext;

import java.util.Stack;

class SdlActivityManager implements SdlApplication.LifecycleListener {

    private ActivityStateTransition mStateTransition;

    private Stack<SdlActivity> mBackStack = new Stack<>();

    SdlActivityManager(){
        mStateTransition = ActivityStateTransitionRegistry.getStateTransition(DisconnectedStateTransition.class);
    }

    public Stack<SdlActivity> getBackStack(){
        return mBackStack;
    }

    public SdlActivity getTopActivity(){
        if(mBackStack.empty()){
            return null;
        } else {
            return mBackStack.peek();
        }
    }

    /**
     * Removes the current activity from the backstack
     */
    void back(){
        mStateTransition = mStateTransition.back(this);
    }

    void finish(){
        mStateTransition = mStateTransition.finish(this);
    }

    @Override
    public void onSdlConnect() {
        mStateTransition = mStateTransition.connect(this);
    }

    @Override
    public void onSdlDisconnect() {
        mStateTransition = mStateTransition.disconnect(this);
    }

    @Override
    public void onBackground() {
        mStateTransition = mStateTransition.background(this);
    }

    @Override
    public void onForeground() {
        mStateTransition = mStateTransition.foreground(this);
    }

    @Override
    public void onExit() {
        mStateTransition = mStateTransition.exit(this);
    }

    /**
     * Creates the default SdlActivity based on the app coming out of HMI_NONE for the first time.
     * @param sdlContext The SdlContext the activity is to be associated with.
     * @param mainActivity Description of the derived SdlActivity class to be instantiated.
     */
    public void onSdlAppLaunch(SdlContext sdlContext, Class<? extends SdlActivity> mainActivity){
        mStateTransition = mStateTransition.launchApp(this, sdlContext, mainActivity);
    }

    /**
     * Restores the SdlActivityManager to a previous point.
     * @param sdlContext The SdlContext the the app is being resumed in.
     * @param savedState serialized information needed to reconstruct the ActivityManager.
     */
    public void onSdlAppResume(SdlContext sdlContext, Bundle savedState){
        mStateTransition = mStateTransition.resumeApp(this, sdlContext, savedState);
    }

    /**
     * Instantiates a new activity to be added to the backstack.
     * @param sdlContext The SdlContext the activity is to be associated with.
     * @param activity Description of the derived SdlActivity class to be instantiated.
     * @param flags Indication from the caller on how the backstack should behave.
     */
    public void startSdlActivity(SdlContext sdlContext, Class<? extends SdlActivity> activity,
                                 Bundle bundle, int flags){
        mStateTransition = mStateTransition.startActivity(this, sdlContext, activity, bundle, flags);
    }

}
