package com.smartdevicelink.api.interfaces;

import android.content.Context;

import com.smartdevicelink.api.SdlActivity;
import com.smartdevicelink.api.file.SdlFileManager;

public interface SdlContext {

    void startSdlActivity(Class<? extends SdlActivity> activity, int flags);

    SdlContext getSdlApplicationContext();

    Context getAndroidApplicationContext();

    SdlFileManager getSdlFileManager();
}
