/*
 * Copyright (c) 2019, Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.managers.permission;

import androidx.annotation.NonNull;

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
