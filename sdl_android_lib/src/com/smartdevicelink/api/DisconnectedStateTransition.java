package com.smartdevicelink.api;

class DisconnectedStateTransition extends ActivityStateTransition {

    @Override
    ActivityStateTransition connect(SdlActivityManager sam) {
        ActivityStateTransition nextState =
                ActivityStateTransitionRegistry.getStateTransition(ConnectedStateTransition.class);
        return nextState.connect(sam);
    }

}
