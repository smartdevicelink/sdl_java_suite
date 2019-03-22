package com.smartdevicelink.managers.video;

import android.support.annotation.NonNull;

import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.proxy.interfaces.ISdl;

abstract class BaseVideoStreamManager extends BaseSubManager {
    BaseVideoStreamManager(@NonNull ISdl internalInterface) {
        super(internalInterface);
    }
}
