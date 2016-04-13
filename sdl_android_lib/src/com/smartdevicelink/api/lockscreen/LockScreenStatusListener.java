package com.smartdevicelink.api.lockscreen;

import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;

public interface LockScreenStatusListener {

    void onLockScreenStatus(String appId, LockScreenStatus status);

}
