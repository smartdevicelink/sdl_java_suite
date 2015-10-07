package com.smartdevicelink.lifecycle;

import android.os.Binder;

public abstract class SdlLifecycleBinder extends Binder{

    abstract ISdlLifecycleService getSdlPersistentService();

}
