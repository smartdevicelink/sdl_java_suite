package com.smartdevicelink.api.permission;

import android.support.annotation.NonNull;

import java.util.Collection;

public class SdlPermissionFilter {

    final SdlPermissionSet permissionSet = SdlPermissionSet.obtain();

    /**
     * Constructs a single permission to the filter on.
     * @param permission {@link SdlPermission} to add to the filter.
     */
    public SdlPermissionFilter(@NonNull SdlPermission permission){
        permissionSet.addPermission(permission);
    }

    /**
     * Constructs a set of permissions to filter on.
     * @param permissions Collection of SdlPermissions to be added as a batch. For the best
     *                    performance use an {@link java.util.EnumSet} as the supplied
     *                    collection.
     */
    public SdlPermissionFilter(@NonNull Collection<SdlPermission> permissions){
        permissionSet.addPermissions(permissions);
    }


}