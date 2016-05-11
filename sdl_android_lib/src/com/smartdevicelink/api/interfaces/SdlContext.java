package com.smartdevicelink.api.interfaces;

import android.content.Context;

import com.smartdevicelink.api.SdlActivity;
import com.smartdevicelink.api.choiceset.SdlChoiceSetManager;
import com.smartdevicelink.api.permission.SdlPermissionManager;
import com.smartdevicelink.api.file.SdlFileManager;
import com.smartdevicelink.api.menu.SdlMenu;
import com.smartdevicelink.api.menu.SdlMenuItem;
import com.smartdevicelink.proxy.RPCRequest;

public interface SdlContext {

    void startSdlActivity(Class<? extends SdlActivity> activity, int flags);

    SdlContext getSdlApplicationContext();

    Context getAndroidApplicationContext();

    SdlFileManager getSdlFileManager();

    int registerButtonCallback(SdlButtonListener listener);

    void unregisterButtonCallback(int id);

    void registerMenuCallback(int id, SdlMenuItem.SelectListener listener);

    void unregisterMenuCallback(int id);

    boolean sendRpc(RPCRequest request);

    SdlPermissionManager getSdlPermissionManager();

    SdlChoiceSetManager getSdlChoiceSetManager();

    SdlMenu getTopMenu();

}
