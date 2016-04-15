package com.smartdevicelink.api.permission;

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.rpc.enums.HMILevel;

import java.util.EnumSet;

public class SdlPermissionEvent {

    final EnumSet<SdlPermission> mPermissions;
    final PermissionLevel mPermissionLevel;

    public enum PermissionLevel{
        ALL,
        SOME,
        NONE
    }

    SdlPermissionEvent(@NonNull EnumSet<SdlPermission> permission, @NonNull PermissionLevel permissionLevel){
        mPermissionLevel = permissionLevel;
        mPermissions = permission;
    }

    /**
     * Method that returns the collection of {@link SdlPermission} from {@link SdlPermissionFilter}
     * that are available.
     * @return EnumSet of {@link SdlPermission} that are available
     */
    @NonNull
    public final EnumSet<SdlPermission> getPermissions(){
        return mPermissions;
    }

    /**
     * Convenience method that returns whether All, Some or None of the permissions are available from
     * the {@link SdlPermissionFilter}
     * @return {@link com.smartdevicelink.api.permission.SdlPermissionEvent.PermissionLevel} that indicates
     * if all, some or none of the {@link SdlPermission} from the {@link SdlPermissionFilter} are present
     */
    @NonNull
    public final PermissionLevel getPermissionLevel(){
        return  mPermissionLevel;
    }



}