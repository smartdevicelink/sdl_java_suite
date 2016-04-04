package com.smartdevicelink.api;

public interface SdlLifecycleListener {

    void onSdlConnect();
    void onBackground();
    void onForeground();
    void onExit();
    void onSdlDisconnect();

}
