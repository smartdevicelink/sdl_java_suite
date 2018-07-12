package com.smartdevicelink.api.PermissionManager;

import java.util.List;
import java.util.UUID;

class PermissionFilter {
    private UUID identifier;
    private List<PermissionElement> permissionElements;
    private int groupType;
    private PermissionManager.OnPermissionChangeListener listener;

    PermissionFilter(UUID identifier, List<PermissionElement> permissionElements, int groupType, PermissionManager.OnPermissionChangeListener listener) {
        this.identifier = identifier;
        if (this.identifier == null) {
            this.identifier = UUID.randomUUID();
        }
        this.permissionElements = permissionElements;
        this.groupType = groupType;
        this.listener = listener;
    }

    UUID getIdentifier() {
        return identifier;
    }

    List<PermissionElement> getPermissionElements() {
        return permissionElements;
    }

    int getGroupType() {
        return groupType;
    }

    PermissionManager.OnPermissionChangeListener getListener() {
        return listener;
    }


}