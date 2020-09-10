/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
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
package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;

import java.util.Hashtable;
import java.util.List;

/**
 * Provides update to app of which sets of functions are available
 * <p>
 * </p>
 * <b>HMI Status Requirements:</b>
 * <ul>
 * HMILevel:
 * <ul>
 * <li>Any</li>
 * </ul>
 * AudioStreamingState:
 * <ul>
 * <li>TBD</li>
 * </ul>
 * SystemContext:
 * <ul>
 * <li>TBD</li>
 * </ul>
 * </ul>
 * <p>
 * <b>Parameter List:</b>
 * <table border="1" rules="all">
 * <tr>
 * <th>Name</th>
 * <th>Type</th>
 * <th>Description</th>
 * <th>Req</th>
 * <th>Notes</th>
 * <th>SmartDeviceLink Ver Available</th>
 * </tr>
 * <tr>
 * <td>permissionItem</td>
 * <td>PermissionItem[]</td>
 * <td>Change in permissions for a given set of RPCs</td>
 * <td>Y</td>
 * <td>Minsize=1 Maxsize=100</td>
 * <td>SmartDeviceLink 2.0</td>
 * </tr>
 * </table>
 * </p>
 */
public class OnPermissionsChange extends RPCNotification {
	public static final String KEY_PERMISSION_ITEM = "permissionItem";
	public static final String KEY_REQUIRE_ENCRYPTION = "requireEncryption";
	/**
	*Constructs a newly allocated OnCommand object
	*/    
	public OnPermissionsChange() {
		super(FunctionID.ON_PERMISSIONS_CHANGE.toString());
	}
	/**
     *<p>Constructs a newly allocated OnPermissionsChange object indicated by the Hashtable parameter</p>
     *@param hash The Hashtable to use
     */
	public OnPermissionsChange(Hashtable<String, Object> hash) {
		super(hash);
	}
	/**
	 *Constructs a newly allocated OnCommand object
	 * @param permissionItem an List of  PermissionItem describing change in permissions for a given set of RPCs
	 */
	public OnPermissionsChange(@NonNull List<PermissionItem> permissionItem) {
		this();
		setPermissionItem(permissionItem);
	}
	/**
     * <p>Returns List<PermissionItem> object describing change in permissions for a given set of RPCs</p>
     * @return List<{@linkplain PermissionItem}> an object describing describing change in permissions for a given set of RPCs
     */   
    @SuppressWarnings("unchecked")
	public List<PermissionItem> getPermissionItem() {
		return (List<PermissionItem>) getObject(PermissionItem.class, KEY_PERMISSION_ITEM);
	}
    /**
     * <p>Sets PermissionItems describing change in permissions for a given set of RPCs</p>
     * @param permissionItem an List of  PermissionItem describing change in permissions for a given set of RPCs
     */
	public OnPermissionsChange setPermissionItem(@NonNull List<PermissionItem> permissionItem) {
        setParameters(KEY_PERMISSION_ITEM, permissionItem);
        return this;
    }

	/**
	 * Returns the encryption requirement for this permission change
	 * @return true if encryption is required, false otherwise
	 */
	public Boolean getRequireEncryption() {
		return getBoolean(KEY_REQUIRE_ENCRYPTION);
	}

	/**
	 * Sets the encryption requirement for this permission change
	 * @param isRequired the boolean requirement to be set
	 */
	public OnPermissionsChange setRequireEncryption( Boolean isRequired) {
        setParameters(KEY_REQUIRE_ENCRYPTION, isRequired);
        return this;
    }
}
