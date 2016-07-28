package com.smartdevicelink.api.interfaces;

import android.content.Context;
import android.os.Handler;

import com.smartdevicelink.api.SdlActivity;
import com.smartdevicelink.api.file.SdlFileManager;
import com.smartdevicelink.api.menu.SdlMenuManager;
import com.smartdevicelink.api.menu.SdlMenuOption;
import com.smartdevicelink.api.menu.SdlMenuTransaction;
import com.smartdevicelink.api.permission.SdlPermissionManager;
import com.smartdevicelink.proxy.RPCRequest;

public interface SdlContext {

    void startSdlActivity(Class<? extends SdlActivity> activity, int flags);

    SdlContext getSdlApplicationContext();

    Context getAndroidApplicationContext();

    SdlFileManager getSdlFileManager();

    SdlMenuManager getSdlMenuManager();

    int registerButtonCallback(SdlButtonListener listener);

    void unregisterButtonCallback(int id);

    void registerMenuCallback(int id, SdlMenuOption.SelectListener listener);

    void unregisterMenuCallback(int id);

    boolean sendRpc(RPCRequest request);

    SdlPermissionManager getSdlPermissionManager();

    SdlMenuTransaction beginGlobalMenuTransaction();

    Handler getExecutionHandler();

}
