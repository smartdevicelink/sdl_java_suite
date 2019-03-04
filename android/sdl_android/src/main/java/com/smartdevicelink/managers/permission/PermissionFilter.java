package com.smartdevicelink.managers.permission;

import android.support.annotation.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * PermissionFilter holds all the required information for a specific OnPermissionChangeListener
 */
class PermissionFilter {
    private final UUID identifier;
    private final List<PermissionElement> permissionElements;
    private final int groupType;
    private final OnPermissionChangeListener listener;

    /**
     * Creates a new instance of PermissionFilter
     * @param identifier
     * @param permissionElements
     * @param groupType
     * @param listener
     * @see com.smartdevicelink.managers.permission.PermissionManager.PermissionGroupType
     */
    PermissionFilter(UUID identifier, @NonNull List<PermissionElement> permissionElements, @NonNull @PermissionManager.PermissionGroupType int groupType, @NonNull OnPermissionChangeListener listener) {
        if (identifier == null) {
            this.identifier = UUID.randomUUID();
        } else {
            this.identifier = identifier;
        }
        this.permissionElements = permissionElements;
        this.groupType = groupType;
        this.listener = listener;
    }

    /**
     * Get the unique id for the listener
     * @return UUID object represents the id for the listener
     */
    protected UUID getIdentifier() {
        return identifier;
    }

    /**
     * Get the permission elements that the developer wants to add a listener for
     * @return List<PermissionElement> represents the RPCs and their parameters that the developer wants to add a listener for
     */
    protected List<PermissionElement> getPermissionElements() {
        return permissionElements;
    }

    /**
     * Get how we want the listener to be called: when any change happens? or when all permissions become allowed?
     * @return PermissionGroupType int value represents whether the developer needs the listener to be called when there is any permissions change or only when all permission become allowed
     * @see com.smartdevicelink.managers.permission.PermissionManager.PermissionGroupType
     */
    protected @PermissionManager.PermissionGroupType int getGroupType() {
        return groupType;
    }

    /**
     * Get the listener object
     * @return OnPermissionChangeListener object represents the listener for that filter
     */
    protected OnPermissionChangeListener getListener() {
        return listener;
    }


}