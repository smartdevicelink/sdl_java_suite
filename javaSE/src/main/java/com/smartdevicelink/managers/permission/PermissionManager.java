package com.smartdevicelink.managers.permission;

import android.support.annotation.NonNull;
import com.smartdevicelink.proxy.interfaces.ISdl;

/**
 PermissionManager gives the developer information about what permissions are permitted in specific HMI level
 and helps developers setup listeners to be called when specific permissions become allowed.<br>

 This should be used through the {@link com.smartdevicelink.managers.SdlManager} and not be instantiated by itself
**/

 public class PermissionManager extends BasePermissionManager{


    /**
     * Creates a new instance of the PermissionManager
     *
     * @param internalInterface
     */
    public PermissionManager(@NonNull ISdl internalInterface) {
        super(internalInterface);
    }
}
