package com.smartdevicelink.api.permission;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.rpc.enums.HMILevel;

import java.util.Collection;

public class SdlPermissionFilter {

    SdlPermissionSet permissionSet;

    /**
     * Constructs an empty filter for use with {@link SdlPermissionListener} to listen for changes in
     * permissions.
     */
    public SdlPermissionFilter(){
        permissionSet = SdlPermissionSet.obtain();
    }

    /**
     * Adds a single permission to the filter. Using this method will add the given SdlPermission
     * to all HMILevels.
     * @param permission {@link SdlPermission} to add to the filter.
     */
    public void addPermission(SdlPermission permission){
        permissionSet.addPermission(permission);
    }


    /**
     * Adds a set of permission to the filter. Using this method will add the given SdlPermission
     * to all HMILevels.
     * @param permissions Collection of SdlPermissions to be added as a batch. For the best
     *                    performance use an {@link java.util.EnumSet} as the supplied
     *                    collection.
     */
    public void addPermissions(Collection<SdlPermission> permissions){
        permissionSet.addPermissions(permissions);
    }


}