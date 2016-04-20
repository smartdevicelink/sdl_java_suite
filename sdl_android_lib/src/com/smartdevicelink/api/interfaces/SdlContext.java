package com.smartdevicelink.api.interfaces;

import android.content.Context;

import com.smartdevicelink.api.SdlActivity;
import com.smartdevicelink.proxy.RPCRequest;

public interface SdlContext {

    void startSdlActivity(Class<? extends SdlActivity> activity, int flags);

    SdlContext getSdlApplicationContext();

    Context getAndroidApplicationContext();

    int registerButtonCallback(SdlButtonListener listener);

    void unregisterButtonCallback(int id);

    boolean sendRpc(RPCRequest request);
}
