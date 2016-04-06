package com.smartdevicelink.api;

import com.smartdevicelink.api.interfaces.SdlContext;

class BackgroundStateTransition extends ActivityStateTransition {

    @Override
    ActivityStateTransition disconnect(SdlActivityManager sam) {
        return super.disconnect(sam);
    }

    @Override
    ActivityStateTransition foreground(SdlActivityManager sam) {
        return super.foreground(sam);
    }

    @Override
    ActivityStateTransition exit(SdlActivityManager sam) {
        return super.exit(sam);
    }

    @Override
    ActivityStateTransition back(SdlActivityManager sam) {
        return super.back(sam);
    }

    @Override
    ActivityStateTransition startActivity(SdlActivityManager sam, SdlContext SdlContext, Class<? extends SdlActivity> activity, int flags) {
        return super.startActivity(sam, SdlContext, activity, flags);
    }

}
