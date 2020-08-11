package com.smartdevicelink.managers.screen;

import androidx.annotation.NonNull;
import com.smartdevicelink.proxy.interfaces.ISdl;

/**
 * <strong>SubscribeButtonManager</strong> <br>
 *
 * Note: This class must be accessed through the SdlManager. Do not instantiate it by itself. <br>
 *
 */
class SubscribeButtonManager extends BaseSubscribeButtonManager {

    SubscribeButtonManager(@NonNull ISdl internalInterface) {
        super(internalInterface);
    }
}
