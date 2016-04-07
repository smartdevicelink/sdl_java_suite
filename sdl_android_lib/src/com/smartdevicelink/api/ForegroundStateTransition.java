package com.smartdevicelink.api;

import com.smartdevicelink.api.interfaces.SdlContext;

import java.util.Stack;

class ForegroundStateTransition extends ActivityStateTransition {

    @Override
    ActivityStateTransition disconnect(SdlActivityManager sam) {
        backgroundTopActivity(sam);
        ActivityStateTransition nextState =
                ActivityStateTransitionRegistry.getStateTransition(BackgroundStateTransition.class);
        return nextState.disconnect(sam);
    }

    @Override
    ActivityStateTransition background(SdlActivityManager sam) {
        backgroundTopActivity(sam);
        ActivityStateTransition nextState =
                ActivityStateTransitionRegistry.getStateTransition(BackgroundStateTransition.class);
        return nextState.background(sam);
    }

    @Override
    ActivityStateTransition exit(SdlActivityManager sam) {
        backgroundTopActivity(sam);
        ActivityStateTransition nextState =
                ActivityStateTransitionRegistry.getStateTransition(BackgroundStateTransition.class);
        return nextState.exit(sam);
    }

    @Override
    ActivityStateTransition back(SdlActivityManager sam) {
        Stack<SdlActivity> backStack = sam.getBackStack();
        if(backStack.size() > 1){
            SdlActivity topActivity = backStack.pop();
            topActivity.performBackground();
            topActivity.performStop();
            topActivity.performDestroy();

            topActivity = backStack.peek();
            topActivity.performRestart();
            topActivity.performStart();
            topActivity.performForeground();
        }
        return this;
    }

    @Override
    ActivityStateTransition startActivity(SdlActivityManager sam, SdlContext SdlContext, Class<? extends SdlActivity> activity, int flags) {
        stopTopActivity(sam);
        SdlActivity newActivity = instantiateActivity(SdlContext, activity);
        putNewActivityOnStack(sam, newActivity);
        newActivity.performForeground();
        return this;
    }

    private void backgroundTopActivity(SdlActivityManager sam) {
        SdlActivity topActivity = sam.getTopActivity();
        if(topActivity != null){
            topActivity.performBackground();
        }
    }


}
