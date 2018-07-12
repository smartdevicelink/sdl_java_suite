package com.smartdevicelink.api.PermissionManager;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.UUID;

class PermissionFilter {
    private UUID identifier;
    private List<PermissionElement> permissionElements;
    private int groupType;
    private OnPermissionChangeListener listener;

    PermissionFilter(UUID identifier, @NonNull List<PermissionElement> permissionElements, @NonNull @PermissionManager.PermissionGroupType int groupType, @NonNull OnPermissionChangeListener listener) {
        this.identifier = identifier;
        if (this.identifier == null) {
            this.identifier = UUID.randomUUID();
        }
        this.permissionElements = permissionElements;
        this.groupType = groupType;
        this.listener = listener;
    }

    protected UUID getIdentifier() {
        return identifier;
    }

    protected List<PermissionElement> getPermissionElements() {
        return permissionElements;
    }

    protected int getGroupType() {
        return groupType;
    }

    protected OnPermissionChangeListener getListener() {
        return listener;
    }


}