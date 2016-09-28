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
                                          Class<? extends SdlActivity> activity, Bundle bundle, int flags){
        return this;
    }

    ActivityStateTransition finish(SdlActivityManager sam){
        return this;
    }

    protected boolean instantiateActivity(SdlActivityManager sam, SdlContext sdlContext,
                                          Class<? extends SdlActivity> activity, Bundle bundle, int flags){

        Stack<SdlActivity> backStack = sam.getBackStack();

        switch (flags){
            case SdlActivity.FLAG_CLEAR_HISTORY:
                clearHistory(backStack);
                if(!backStack.empty() && backStack.peek().getClass() == activity){
                    startTopActivity(backStack, "");
                    return true;
                }
                break;
            case SdlActivity.FLAG_CLEAR_TOP:
                SdlActivity newTop = getInstanceFromStack(backStack, activity);
                if(newTop != null){
                    String currentTemplate = clearTop(backStack, newTop);
                    startTopActivity(backStack, currentTemplate);
                    return true;
                }
                break;
            case SdlActivity.FLAG_PULL_TO_TOP:
                SdlActivity instanceFromStack = getInstanceFromStack(backStack, activity);
                if(instanceFromStack != null){
                    if(instanceFromStack != backStack.peek()) {
                        String currentTemplate = stopTopActivity(backStack);
                        backStack.remove(instanceFromStack);
                        backStack.push(instanceFromStack);
                        startTopActivity(backStack, currentTemplate);
                    }
                    return true;
                }
                break;
            default:
                break;
        }

        try {
            String currentTemplate = "";
            SdlActivity newActivity = activity.newInstance();
            newActivity.initialize(sdlContext);

            if(!backStack.empty()) {
                currentTemplate = stopTopActivity(backStack);
            }

            backStack.push(newActivity);
            createTopActivity(backStack, bundle);
            startTopActivity(backStack, currentTemplate);
            return true;
        } catch (InstantiationException e) {
            Log.e(TAG, "Unable to instantiate " + activity.getSimpleName(), e);
            return false;
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Unable to access constructor for " + activity.getSimpleName(), e);
            return false;
        }
    }

    protected SdlActivity getInstanceFromStack(Stack<SdlActivity> backStack, Class<? extends SdlActivity> activity){
        if(!backStack.empty()) {
            for (SdlActivity act : backStack) {
                if (act.getClass() == activity) return act;
            }
        }
        return null;
    }

    protected String clearTop(Stack<SdlActivity> backStack, SdlActivity activity){
        String currentTemplate = "";
        while(!backStack.empty() && backStack.peek() != activity){
            currentTemplate = destroyTopActivity(backStack);
        }
        return currentTemplate;
    }

    protected void clearHistory(Stack<SdlActivity> backStack){
        while(backStack.size() > 1){
            destroyTopActivity(backStack);
        }
    }

    protected void createTopActivity(Stack<SdlActivity> backStack, Bundle bundle) {
        if (backStack.empty()) return;

        SdlActivity activity = backStack.peek();
        if (activity != null) {
            SdlActivity.SdlActivityState activityState = activity.getActivityState();
            if (activityState == SdlActivity.SdlActivityState.PRE_CREATE) {
                activity.performCreate(bundle);
            }
        }
    }

    protected void startTopActivity(Stack<SdlActivity> backStack, String currentTemplate){
        if(backStack.empty()) return;

        SdlActivity activity = backStack.peek();
        if(activity != null){
            SdlActivity.SdlActivityState activityState = activity.getActivityState();
            if(activityState == SdlActivity.SdlActivityState.STOPPED){
                activity.performRestart();
            }

            activityState = activity.getActivityState();
            if(activityState == SdlActivity.SdlActivityState.POST_CREATE){
                activity.performStart(currentTemplate);
            }
        }
    }

    protected void foregroundTopActivity(Stack<SdlActivity> backStack){
        if(backStack.empty()) return;

        SdlActivity activity = backStack.peek();

        if(activity != null && activity.getActivityState() == SdlActivity.SdlActivityState.BACKGROUND) {
            activity.performForeground();
        }
    }

    protected void backgroundTopActivity(Stack<SdlActivity> backStack){
        if(backStack.empty()) return;

        SdlActivity activity = backStack.peek();
        if(activity != null && activity.getActivityState() == SdlActivity.SdlActivityState.FOREGROUND) {
            activity.performBackground();
        }
    }

    protected String stopTopActivity(Stack<SdlActivity> backStack){
        String currentTemplate = "";
        if(backStack.empty()) return currentTemplate;

        backgroundTopActivity(backStack);

        SdlActivity activity = backStack.peek();
        if(activity != null && activity.getActivityState() == SdlActivity.SdlActivityState.BACKGROUND) {
            currentTemplate = activity.performStop();
        }
        return currentTemplate;
    }

    protected String destroyTopActivity(Stack<SdlActivity> backStack){
        String currentTemplate = "";
        if(backStack.empty()) return currentTemplate;

        backStack.peek().setIsFinishing(true);

        currentTemplate = stopTopActivity(backStack);

        SdlActivity activity = backStack.pop();
        if(activity != null) {
            activity.performDestroy();
        }
        return currentTemplate;
    }

    protected String clearBackStack(Stack<SdlActivity> backStack){
        String currentTemplate = "";
        clearHistory(backStack);
        if(!backStack.empty()) {
            currentTemplate = destroyTopActivity(backStack);
        }
        return currentTemplate;
    }

    protected void navigateBack(Stack<SdlActivity> backStack){
        Log.d(TAG, "Navigating back with stack size: " + backStack.size());
        if(backStack.size() > 1){
            SdlActivity topActivity = backStack.peek();
            boolean isBackHandled = topActivity.performBackNavigation();
            if(!isBackHandled){
                String currentTemplate = destroyTopActivity(backStack);
                startTopActivity(backStack, currentTemplate);
            }
        }
    }

    protected void finishActivity(Stack<SdlActivity> backStack){
        if(backStack.empty()) return;

        SdlActivity topActivity = backStack.peek();
        if(!topActivity.isFinishing()){
            SdlActivity.SdlActivityState targetState = topActivity.getActivityState();
            Log.d(TAG, "State was: " + targetState.name());
            String currentTemplate = destroyTopActivity(backStack);
            if(targetState == SdlActivity.SdlActivityState.FOREGROUND ||
                    targetState == SdlActivity.SdlActivityState.BACKGROUND){
                startTopActivity(backStack, currentTemplate);
            }
            if(targetState == SdlActivity.SdlActivityState.FOREGROUND){
                foregroundTopActivity(backStack);
            }
        } else {
            Log.w(TAG, "Finish called on SdlActivity that is already finishing.\n" +
                    "SdlActivity class: " + topActivity.getClass().getCanonicalName());
        }
    }

}
