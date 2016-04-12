package com.smartdevicelink.api.lockscreen;

import android.app.Application;

import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;

public class LockScreenActivityManager {

    private static LockScreenActivity LOCK_SCREEN_INSTANCE = null;

    private static LockScreenActivityManager mInstance;

    static LockScreenActivityManager getInstance(){
        if(mInstance == null){

        }
        return null;
    }

    private LockScreenActivityManager(Application androidApplication, Class<? extends LockScreenActivity> lockScreen){

    }

    public void updateLockScreenStatus(String appId, LockScreenStatus status){

    }

    private void registerActivityLifecycle(Application application){

    }

    static void setLockScreenInstance(LockScreenActivity lockScreenInstance){
        LOCK_SCREEN_INSTANCE = lockScreenInstance;
    }
}
