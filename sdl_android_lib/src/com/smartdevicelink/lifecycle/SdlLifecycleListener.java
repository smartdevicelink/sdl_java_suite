package com.smartdevicelink.lifecycle;

import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;

public interface SdlLifecycleListener {

    void onSdlConnected();

    void onHmiStatusChanged(HMILevel level);

    void onLockScreenStatus(LockScreenStatus lockScreenStatus);

    void onSdlDisconnected();

}
