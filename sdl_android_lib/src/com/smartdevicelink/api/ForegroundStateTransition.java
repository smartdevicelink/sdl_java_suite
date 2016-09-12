package com.smartdevicelink.api;

import android.os.Bundle;

import com.smartdevicelink.api.interfaces.SdlContext;

class ForegroundStateTransition extends ActivityStateTransition {

    @Override
    ActivityStateTransition disconnect(SdlActivityManager sam) {
        backgroundTopActivity(sam.getBackStack());
        ActivityStateTransition nextState =
                ActivityStateTransitionRegistry.getStateTransition(BackgroundStateTransition.class);
        return nextState.disconnect(sam);
    }

    @Override
    ActivityStateTransition background(SdlActivityManager sam) {
        backgroundTopActivity(sam.getBackStack());
        ActivityStateTransition nextState =
                ActivityStateTransitionRegistry.getStateTransition(BackgroundStateTransition.class);
        return nextState.background(sam);
    }

    @Override
    ActivityStateTransition exit(SdlActivityManager sam) {
        backgroundTopActivity(sam.getBackStack());
        ActivityStateTransition nextState =
                ActivityStateTransitionRegistry.getStateTransition(BackgroundStateTransition.class);
        return nextState.exit(sam);
    }

    @Override
    ActivityStateTransition back(SdlActivityManager sam) {
        navigateBack(sam.getBackStack());
        foregroundTopActivity(sam.getBackStack());
        return this;
    }

    @Override
    ActivityStateTransition startActivity(SdlActivityManager sam, SdlContext SdlContext,
                                          Class<? extends SdlActivity> activity, Bundle bundle, int flags) {
        boolean activityStarted = instantiateActivity(sam, SdlContext, activity, bundle, flags);
        if(activityStarted){
            foregroundTopActivity(sam.getBackStack());
        }
        return this;
    }

    @Override
    ActivityStateTransition finish(SdlActivityManager sam) {
        finishActivity(sam.getBackStack());
        foregroundTopActivity(sam.getBackStack());
        return this;
    }

}
