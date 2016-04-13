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
     * to all HMILevels. This should be sufficient for most use cases. When using this method do not
     * use {@link com.smartdevicelink.api.permission.SdlPermissionManager.ListenerMode#MATCH_EXACT} as
     * the listener mode.
     * @param permission {@link SdlPermission} to add to the filter.
     */
    public void addPermission(SdlPermission permission){
        permissionSet.addPermission(permission);
    }

    /**
     * Adds a single permission to the filter with the specified HMILevel.
     * @param permission {@link SdlPermission} to add to the filter.
     * @param hmiLevel HMILevel that the permission should available in to trigger the
     * {@link SdlPermissionListener} callback.
     */
    public void addPermission(SdlPermission permission, @NonNull HMILevel hmiLevel){
        permissionSet.addPermission(permission, hmiLevel);
    }

    /**
     * Adds a set of permission tos the filter. Using this method will add the given SdlPermission
     * to all HMILevels. This should be sufficient for most use cases. When using this method do not
     * use {@link com.smartdevicelink.api.permission.SdlPermissionManager.ListenerMode#MATCH_EXACT} as
     * the listener more.
     * @param permissions Collection of SdlPermissions to be added as a batch. For the best
     *                    performance use an {@link java.util.EnumSet} as the supplied
     *                    collection.
     */
    public void addPermissions(Collection<SdlPermission> permissions){
        permissionSet.addPermissions(permissions);
    }

    /**
     * Adds a set of permission tos the filter. Using this method will add the given SdlPermission
     * to all HMILevels. This should be used when fine control over what HMILevels trigger the
     * SdlPermissionListener callback.
     * @param permissions Collection of SdlPermissions to be added as a batch. For the best
     *                    performance use an {@link java.util.EnumSet} as the supplied
     *                    collection.
     * @param hmiLevel HMILevel that the permission should available in to trigger the
     * {@link SdlPermissionListener} callback.
     */
    public void addPermissions(Collection<SdlPermission> permissions, @NonNull HMILevel hmiLevel){
        permissionSet.addPermissions(permissions, hmiLevel);
    }

}