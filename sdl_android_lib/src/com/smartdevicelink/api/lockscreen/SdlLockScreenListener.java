package com.smartdevicelink.api.lockscreen;

import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;

public interface SdlLockScreenListener {

    void onLockScreenStatus(LockScreenStatus lockScreenStatus);

}
