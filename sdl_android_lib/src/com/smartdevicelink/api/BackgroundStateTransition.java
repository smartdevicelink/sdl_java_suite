package com.smartdevicelink.api;

import com.smartdevicelink.api.interfaces.SdlContext;

import java.util.Stack;

class BackgroundStateTransition extends ActivityStateTransition {

    @Override
    ActivityStateTransition disconnect(SdlActivityManager sam) {
        destroyBackStack(sam);

        ActivityStateTransition nextState =
                ActivityStateTransitionRegistry.getStateTransition(DisconnectedStateTransition.class);
        return nextState.disconnect(sam);
    }

    @Override
    ActivityStateTransition foreground(SdlActivityManager sam) {
        SdlActivity topActivity = sam.getTopActivity();
        if(topActivity != null){
            topActivity.performForeground();
        }

        ActivityStateTransition nextState =
                ActivityStateTransitionRegistry.getStateTransition(ForegroundStateTransition.class);
        return nextState.foreground(sam);
    }

    @Override
    ActivityStateTransition exit(SdlActivityManager sam) {
        destroyBackStack(sam);
        ActivityStateTransition nextState =
                ActivityStateTransitionRegistry.getStateTransition(ConnectedStateTransition.class);
        return nextState.exit(sam);
    }

    @Override
    ActivityStateTransition back(SdlActivityManager sam) {
        Stack<SdlActivity> backStack = sam.getBackStack();
        if(backStack.size() > 1){
            SdlActivity topActivity = backStack.pop();
            topActivity.performStop();
            topActivity.performDestroy();

            topActivity = backStack.peek();
            topActivity.performRestart();
            topActivity.performStart();
        }
        return this;
    }

    @Override
    ActivityStateTransition startActivity(SdlActivityManager sam, SdlContext SdlContext, Class<? extends SdlActivity> activity, int flags) {
        stopTopActivity(sam);
        SdlActivity newActivity = instantiateActivity(sam, SdlContext, activity);
        putNewActivityOnStack(sam, newActivity);
        return this;
    }

    private void destroyBackStack(SdlActivityManager sam) {
        SdlActivity topActivity = sam.getTopActivity();
        if(topActivity != null){
            topActivity.performStop();
        }

        Stack<SdlActivity> backStack = sam.getBackStack();
        while(!backStack.empty()){
            SdlActivity activity = backStack.pop();
            activity.performDestroy();
        }
    }

}
