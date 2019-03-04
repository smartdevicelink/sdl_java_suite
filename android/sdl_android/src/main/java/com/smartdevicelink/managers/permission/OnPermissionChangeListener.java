package com.smartdevicelink.managers.permission;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;

import java.util.Map;

/**
 * OnPermissionChangeListener is a listener which includes a callback method that can be called when there are permission changes
 */
public interface OnPermissionChangeListener {
    /**
     * Call back method that PermissionManager will call to inform the developer about permission changes
     * @param allowedPermissions an overall view about the status of the permissions
     * @param permissionGroupStatus a detailed view about which permissions are allowed and which ones are not
     * @see com.smartdevicelink.managers.permission.PermissionManager.PermissionGroupStatus
     */
    void onPermissionsChange(@NonNull Map<FunctionID, PermissionStatus> allowedPermissions, @NonNull @PermissionManager.PermissionGroupStatus int permissionGroupStatus);
}
