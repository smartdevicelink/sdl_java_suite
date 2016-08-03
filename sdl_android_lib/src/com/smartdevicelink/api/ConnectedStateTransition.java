package com.smartdevicelink.api;

import android.os.Bundle;

import com.smartdevicelink.api.interfaces.SdlContext;

class ConnectedStateTransition extends ActivityStateTransition {

    private static final String TAG = ConnectedStateTransition.class.getSimpleName();

    @Override
    ActivityStateTransition disconnect(SdlActivityManager sam) {
        ActivityStateTransition nextState =
                ActivityStateTransitionRegistry.getStateTransition(DisconnectedStateTransition.class);
        return nextState.disconnect(sam);
    }

    @Override
    ActivityStateTransition launchApp(SdlActivityManager sam, SdlContext sdlContext, Class<? extends SdlActivity> main) {
        boolean activityStarted = instantiateActivity(sam, sdlContext, main, null, SdlActivity.FLAG_DEFAULT);

        if(activityStarted){
            ActivityStateTransition nextState =
                    ActivityStateTransitionRegistry.getStateTransition(BackgroundStateTransition.class);
            return nextState.launchApp(sam, sdlContext, main);
        } else {
            return super.launchApp(sam, sdlContext, main);
        }
    }

    @Override
    ActivityStateTransition resumeApp(SdlActivityManager sam, SdlContext sdlContext, Bundle resumeState) {
        // TODO: Implement along with app resumption
        return super.resumeApp(sam, sdlContext, resumeState);
    }
}
