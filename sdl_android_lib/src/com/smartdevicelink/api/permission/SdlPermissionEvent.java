package com.smartdevicelink.api.permission;

import android.support.annotation.NonNull;

import java.util.EnumSet;

public class SdlPermissionEvent {

    private final EnumSet<SdlPermission> mAllowedPermissions;
    private final EnumSet<SdlPermission> mUserDisallowedPermissions;
    private final EnumSet<SdlPermission> mDisallowedPermissions;

    public enum PermissionLevel{
        ALL,
        SOME,
        NONE
    }

    SdlPermissionEvent(@NonNull EnumSet<SdlPermission> allowedPermission,
                       @NonNull EnumSet<SdlPermission> userDisallowedPermission,
                       @NonNull EnumSet<SdlPermission> disallowedPermission){
        mAllowedPermissions = allowedPermission;
        mUserDisallowedPermissions = userDisallowedPermission;
        mDisallowedPermissions = disallowedPermission;
    }

    /**
     * Method that returns the collection of {@link SdlPermission} from {@link SdlPermissionFilter}
     * that are available.
     * @return EnumSet of {@link SdlPermission} that are available
     */
    @NonNull
    public EnumSet<SdlPermission> getAllowedPermissions(){
        return mAllowedPermissions;
    }

    @NonNull
    public EnumSet<SdlPermission> getUserDisallowedPermissions(){return mUserDisallowedPermissions;}

    @NonNull
    public EnumSet<SdlPermission> getDisallowedPermissions(){return mDisallowedPermissions;}

    public boolean isPermissionAvailable(@NonNull SdlPermission permission){
        return mAllowedPermissions.contains(permission);
    }

    /**
     * Convenience method that returns whether All, Some or None of the permissions are available from
     * the {@link SdlPermissionFilter}
     * @return {@link com.smartdevicelink.api.permission.SdlPermissionEvent.PermissionLevel} that indicates
     * if all, some or none of the {@link SdlPermission} from the {@link SdlPermissionFilter} are present
     */
    @NonNull
    public PermissionLevel getPermissionLevel(){
        if(!mAllowedPermissions.isEmpty()){
            if( !mUserDisallowedPermissions.isEmpty() || !mDisallowedPermissions.isEmpty() ){
                return PermissionLevel.SOME;
            } else {
                return PermissionLevel.ALL;
            }
        } else {
            return PermissionLevel.NONE;
        }
    }
}