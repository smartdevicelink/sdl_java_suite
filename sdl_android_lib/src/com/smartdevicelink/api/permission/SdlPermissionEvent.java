package com.smartdevicelink.api.permission;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.rpc.enums.HMILevel;

import java.util.EnumSet;

public class SdlPermissionEvent {
    final SdlPermissionSet mPermissionSet;

    public SdlPermissionEvent(SdlPermissionSet permission){
        this.mPermissionSet = permission;
    }

    /**
     * This method returns the set of permissions allowed in the given HMILevel.
     * @param hmiLevel The {@link HMILevel} of the desired permission set.
     * @return {@link EnumSet} of {@link SdlPermission} items representing available permissions.
     */
    @NonNull
    public EnumSet<SdlPermission> getPermissions(@NonNull HMILevel hmiLevel){
        return mPermissionSet.permissions.get(hmiLevel.ordinal());
    }

    /**
     * This method returns the set of permissions representing all permissions that are available
     * regardless of the HMI levels that they are available in.
     * @return {@link EnumSet} of {@link SdlPermission} items representing available permissions.
     */
    @NonNull
    public EnumSet<SdlPermission> getPermissions(){
        EnumSet<SdlPermission> permissions = EnumSet.noneOf(SdlPermission.class);

        for(EnumSet<SdlPermission> set: mPermissionSet.permissions){
            permissions.addAll(set);
        }

        return permissions;
    }

}