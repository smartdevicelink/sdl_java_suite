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

    /**
     * SdlPermissionEvent constructor
     * @param permission Current set of permissions available
     * @param permissionLevel Convenience enum for the
     */
    SdlPermissionEvent(EnumSet<SdlPermission> permission, PermissionLevel permissionLevel){
        mPermissionLevel = permissionLevel;
        mPermissions = permission;
    }

    @NonNull
    public final EnumSet<SdlPermission> getPermissions(){
        return mPermissions;
    }

    @NonNull
    public final PermissionLevel getPermissionLevel(){
        return  mPermissionLevel;
    }



}