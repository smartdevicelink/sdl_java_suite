package com.smartdevicelink.api.lockscreen;

public class LockScreenConfig {
    final Class<? extends LockScreenActivity> lockScreen;
    final boolean isShownInOptional;

    public LockScreenConfig(Class<? extends LockScreenActivity> lockScreen,
                            boolean isShownInOptional){
        this.lockScreen = lockScreen;
        this.isShownInOptional = isShownInOptional;
    }

}
