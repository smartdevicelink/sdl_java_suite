package com.smartdevicelink.api.permission;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.rpc.enums.HMILevel;

import java.util.Collection;

public class SdlPermissionFilter {

    //TODO: Refactor to an EnumSet so that the user is not hanging onto SdlPermissionSets when creating listeners?
    SdlPermissionSet permissionSet;

    /**
     * Constructs an empty filter for use with {@link SdlPermissionListener} to listen for changes in
     * permissions.
     */
    public SdlPermissionFilter(){
        permissionSet = SdlPermissionSet.obtain();
    }

    /**
     * Adds a single permission to the filter.
     * @param permission {@link SdlPermission} to add to the filter.
     */
    public void addPermission(@NonNull SdlPermission permission){
        permissionSet.addPermission(permission);
    }


    /**
     * Adds a set of permission to the filter.
     * @param permissions Collection of SdlPermissions to be added as a batch. For the best
     *                    performance use an {@link java.util.EnumSet} as the supplied
     *                    collection.
     */
    public void addPermissions(@NonNull Collection<SdlPermission> permissions){
        permissionSet.addPermissions(permissions);
    }


}