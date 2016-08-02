package com.smartdevicelink.api.lockscreen;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.CallSuper;

public abstract class LockScreenActivity extends Activity {

    @Override
    @CallSuper
    protected final void onDestroy() {
        LockScreenManager.setLockScreenInstance(null);
        super.onDestroy();
    }

    @Override
    @CallSuper
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LockScreenManager.setLockScreenInstance(this);
    }

    @Override
    public final void onBackPressed(){

    }
}
