package com.smartdevicelink.api.permission;

import android.support.annotation.NonNull;

public interface SdlPermissionListener {

    /**
     * This method is called when a change in permissions specified by the {@link SdlPermissionSet}
     * supplied with this listener occurs.
     * @param event This {@link SdlPermissionEvent} can only contain permissions that are specified
     *              in the {@link SdlPermissionSet} supplied with the listener.
     */
    void onPermissionChanged(@NonNull SdlPermissionEvent event);

}
