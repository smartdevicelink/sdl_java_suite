package com.smartdevicelink.security;

import android.app.Service;
import android.content.Context;

public abstract class SdlSecurityBase extends BaseSdlSecurityBase{

	protected static Service appService = null;
	protected static Context context;

    @Deprecated
    public static Service getAppService() {
        return appService;
    }

    @Deprecated
    public static void setAppService(Service val) {
        appService = val;
        if (val != null && val.getApplicationContext() != null){
        	setContext(val.getApplicationContext());
        }
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context val) {
        context = val;
    }

}
