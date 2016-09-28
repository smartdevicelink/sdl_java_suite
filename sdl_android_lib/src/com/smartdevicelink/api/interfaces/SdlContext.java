package com.smartdevicelink.api.interfaces;

import android.content.Context;
import android.os.Bundle;
import android.os.Looper;

import com.smartdevicelink.api.SdlActivity;
import com.smartdevicelink.api.file.SdlFileManager;
import com.smartdevicelink.api.menu.SdlMenuManager;
import com.smartdevicelink.api.menu.SdlMenuOption;
import com.smartdevicelink.api.menu.SdlMenuTransaction;
import com.smartdevicelink.api.permission.SdlPermissionManager;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.HMICapabilities;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.VehicleType;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;

public interface SdlContext {

    void startSdlActivity(Class<? extends SdlActivity> activity, Bundle bundle, int flags);

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

    Looper getSdlExecutionLooper();

    SdlMenuTransaction beginGlobalMenuTransaction();

    void registerRpcNotificationListener(FunctionID functionID, OnRPCNotificationListener rpcNotificationListener);

    void unregisterRpcNotificationListener(FunctionID functionID, OnRPCNotificationListener rpcNotificationListener);

    HMICapabilities getHmiCapabilities();

    DisplayCapabilities getDisplayCapabilities();

    VehicleType getVehicleType();

    SdlMsgVersion getSdlMessageVersion();

    Language getConnectedLanguage();

}
