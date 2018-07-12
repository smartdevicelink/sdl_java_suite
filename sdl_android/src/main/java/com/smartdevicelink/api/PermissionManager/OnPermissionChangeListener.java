package com.smartdevicelink.api.PermissionManager;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;

import java.util.Map;

public interface OnPermissionChangeListener {
    void onPermissionsChange(@NonNull Map<FunctionID, PermissionStatus> allowedPermissions, @NonNull @PermissionManager.PermissionGroupStatus int permissionGroupStatus);
}
