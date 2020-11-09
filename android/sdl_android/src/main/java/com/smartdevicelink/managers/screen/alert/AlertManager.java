package com.smartdevicelink.managers.screen.alert;

import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import com.smartdevicelink.managers.ISdl;

/**
 * <strong>AlertManager</strong> <br>
 * <p>
 * Note: This class must be accessed through the SdlManager. Do not instantiate it by itself. <br>
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public class AlertManager extends BaseAlertManager {

    public AlertManager(@NonNull ISdl internalInterface) {
        super(internalInterface);
    }
}
