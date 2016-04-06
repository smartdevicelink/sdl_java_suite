package com.smartdevicelink.api;

import android.os.Bundle;

import com.smartdevicelink.api.interfaces.SdlContext;

class ConnectedStateTransition extends ActivityStateTransition {

    @Override
    ActivityStateTransition disconnect(SdlActivityManager sam) {
        return super.disconnect(sam);
    }

    @Override
    ActivityStateTransition launchApp(SdlActivityManager sam, SdlContext sdlContext, Class<? extends SdlActivity> main) {
        return super.launchApp(sam, sdlContext, main);
    }

    @Override
    ActivityStateTransition resumeApp(SdlActivityManager sam, SdlContext sdlContext, Bundle resumeState) {
        return super.resumeApp(sam, sdlContext, resumeState);
    }
}
