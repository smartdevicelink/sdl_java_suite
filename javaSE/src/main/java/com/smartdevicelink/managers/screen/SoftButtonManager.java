package com.smartdevicelink.managers.screen;

import android.support.annotation.NonNull;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.proxy.interfaces.ISdl;

/**
 * <strong>SoftButtonManager</strong> <br>
 * SoftButtonManager gives the developer the ability to control how soft buttons are displayed on the head unit.<br>
 * Note: This class must be accessed through the SdlManager->ScreenManager. Do not instantiate it by itself.<br>
 */
class SoftButtonManager extends BaseSoftButtonManager {

    /**
     * Creates a new instance of the SoftButtonManager
     *
     * @param internalInterface
     * @param fileManager
     */
    SoftButtonManager(@NonNull ISdl internalInterface, @NonNull FileManager fileManager) {
        super(internalInterface, fileManager);
    }
}
