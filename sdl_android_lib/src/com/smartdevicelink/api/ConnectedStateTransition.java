package com.smartdevicelink.api;

import android.os.Bundle;

class ConnectedStateTransition extends ActivityStateTransition {

    @Override
    ActivityStateTransition disconnect(SdlActivityManager sam) {
        return super.disconnect(sam);
    }

    @Override
    ActivityStateTransition launchApp(SdlActivityManager sam, Class<? extends SdlActivity> main) {
        return super.launchApp(sam, main);
    }

    @Override
    ActivityStateTransition resumeApp(SdlActivityManager sam, Bundle resumeState) {
        return super.resumeApp(sam, resumeState);
    }
}
