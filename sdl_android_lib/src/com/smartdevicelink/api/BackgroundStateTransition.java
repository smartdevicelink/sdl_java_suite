package com.smartdevicelink.api;

import android.os.Bundle;

import com.smartdevicelink.api.interfaces.SdlContext;

class BackgroundStateTransition extends ActivityStateTransition {

    @Override
    ActivityStateTransition disconnect(SdlActivityManager sam) {
        clearBackStack(sam.getBackStack());
        ActivityStateTransition nextState =
                ActivityStateTransitionRegistry.getStateTransition(DisconnectedStateTransition.class);
        return nextState.disconnect(sam);
    }

    @Override
    ActivityStateTransition foreground(SdlActivityManager sam) {
        foregroundTopActivity(sam.getBackStack());
        ActivityStateTransition nextState =
                ActivityStateTransitionRegistry.getStateTransition(ForegroundStateTransition.class);
        return nextState.foreground(sam);
    }

    @Override
    ActivityStateTransition exit(SdlActivityManager sam) {
        clearBackStack(sam.getBackStack());
        ActivityStateTransition nextState =
                ActivityStateTransitionRegistry.getStateTransition(ConnectedStateTransition.class);
        return nextState.exit(sam);
    }

    @Override
    ActivityStateTransition back(SdlActivityManager sam) {
        navigateBack(sam.getBackStack());
        return this;
    }

    @Override
    ActivityStateTransition startActivity(SdlActivityManager sam, SdlContext sdlContext,
                                          Class<? extends SdlActivity> activity, Bundle bundle, int flags) {
        instantiateActivity(sam, sdlContext, activity, bundle, flags);
        return this;
    }

    @Override
    ActivityStateTransition finish(SdlActivityManager sam) {
        finishActivity(sam.getBackStack());
        return this;
    }
}
