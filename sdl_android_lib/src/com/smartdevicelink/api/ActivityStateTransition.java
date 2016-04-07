package com.smartdevicelink.api;

import android.os.Bundle;
import android.util.Log;

import com.smartdevicelink.api.interfaces.SdlContext;

import java.util.Stack;

abstract class ActivityStateTransition {

    private static final String TAG = ActivityStateTransition.class.getSimpleName();

    ActivityStateTransition(){

    }

    ActivityStateTransition connect(SdlActivityManager sam){
        return this;
    }

    ActivityStateTransition disconnect(SdlActivityManager sam){
        return this;
    }

    ActivityStateTransition launchApp(SdlActivityManager sam, SdlContext sdlContext,
                                      Class<? extends SdlActivity> main){
        return this;
    }

    ActivityStateTransition resumeApp(SdlActivityManager sam, SdlContext sdlContext, Bundle resumeState){
        return this;
    }

    ActivityStateTransition background(SdlActivityManager sam){
        return this;
    }

    ActivityStateTransition foreground(SdlActivityManager sam){
        return this;
    }

    ActivityStateTransition exit(SdlActivityManager sam){
        return this;
    }

    ActivityStateTransition back(SdlActivityManager sam){
        return this;
    }

    ActivityStateTransition startActivity(SdlActivityManager sam, SdlContext SdlContext,
                                          Class<? extends SdlActivity> activity, int flags){
        return this;
    }

    protected SdlActivity instantiateActivity(Class<? extends SdlActivity> main, SdlContext sdlContext){

        // TODO: make sure class is not already instantiated in sam

        SdlActivity newActivity = null;

        try {
            newActivity = main.newInstance();
            newActivity.initialize(sdlContext);
        } catch (InstantiationException e) {
            Log.e(TAG, "Unable to instantiate " + main.getSimpleName(), e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Unable to access constructor for " + main.getSimpleName(), e);
        }

        return newActivity;
    }

    protected void putNewActivityOnStack(SdlActivityManager sam, SdlActivity newActivity) {
        Stack<SdlActivity> backStack = sam.getBackStack();
        backStack.push(newActivity);
        newActivity.performCreate();
        newActivity.performStart();
    }

    protected void stopTopActivity(SdlActivityManager sam){
        Stack<SdlActivity> backStack = sam.getBackStack();
        if(backStack.empty()) return;

        SdlActivity activity = backStack.peek();

        if(activity.getActivityState() == SdlActivity.SdlActivityState.FOREGROUND){
            activity.performBackground();
        }
        activity.performStop();
    }

}
