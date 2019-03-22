package com.smartdevicelink.managers.audio;

import android.support.annotation.NonNull;

import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.proxy.interfaces.ISdl;

abstract class BaseAudioStreamManager extends BaseSubManager {
    BaseAudioStreamManager(@NonNull ISdl internalInterface) {
        super(internalInterface);
    }
}
