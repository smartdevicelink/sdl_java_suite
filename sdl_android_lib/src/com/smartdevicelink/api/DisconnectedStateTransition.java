package com.smartdevicelink.api;

class DisconnectedStateTransition extends ActivityStateTransition {

    @Override
    ActivityStateTransition connect(SdlActivityManager sam) {
        return transitionToState(sam, ConnectedStateTransition.class);
    }

}
