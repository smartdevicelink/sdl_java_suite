package com.smartdevicelink.api;

import android.app.Service;

import com.smartdevicelink.api.interfaces.SdlContext;

public abstract class SdlService extends Service implements SdlApplication.LifecycleListener, SdlContext {
}
