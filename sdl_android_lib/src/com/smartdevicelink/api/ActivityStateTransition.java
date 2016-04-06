package com.smartdevicelink.api;

import android.os.Bundle;

import com.smartdevicelink.api.interfaces.SdlContext;

abstract class ActivityStateTransition {

    ActivityStateTransition(){

    }

    ActivityStateTransition connect(SdlActivityManager sam){
        return this;
    }

    ActivityStateTransition disconnect(SdlActivityManager sam){
        return this;
    }

    ActivityStateTransition launchApp(SdlActivityManager sam, SdlContext sdlContext,
                                      Class<? extends SdlActivity> main){
        return this;
    }

    ActivityStateTransition resumeApp(SdlActivityManager sam, SdlContext sdlContext, Bundle resumeState){
        return this;
    }

    ActivityStateTransition background(SdlActivityManager sam){
        return this;
    }

    ActivityStateTransition foreground(SdlActivityManager sam){
        return this;
    }

    ActivityStateTransition exit(SdlActivityManager sam){
        return this;
    }

    ActivityStateTransition back(SdlActivityManager sam){
        return this;
    }

    ActivityStateTransition startActivity(SdlActivityManager sam, SdlContext SdlContext,
                                          Class<? extends SdlActivity> activity, int flags){
        return this;
    }
}
