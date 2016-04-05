package com.smartdevicelink.api;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class SdlApplication extends Service implements SdlContext{
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
