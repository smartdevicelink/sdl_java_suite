package com.smartdevicelink.lifecycle;

import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;

public interface SdlLifecycleListener {

    void onSdlConnected();

    void onLockScreenStatusChanged(LockScreenStatus lockScreenStatus);

    void onHmiStatusChanged(HMILevel level);

    void onSdlDisconnected();

}
