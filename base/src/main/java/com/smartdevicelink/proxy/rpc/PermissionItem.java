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

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;
/**
 * Change in permissions for a given set of RPCs
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th> Req.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>rpcName</td>
 * 			<td>String</td>
 * 			<td>Name of the individual RPC in the policy table.</td>
 *                 <td></td>
 * 			<td>maxlength:100</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>hmiPermissions</td>
 * 			<td>HMIPermissions</td>
 * 			<td>Sets of parameters, which are permitted or prohibited for the given RPC.</td>
 *                 <td></td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *
 * 		<tr>
 * 			<td>parameterPermissions</td>
 * 			<td>ParameterPermissions</td>
 * 			<td>Sets of parameters, which are permitted or prohibited for the given RPC.</td>
 *                 <td></td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 *
 * @since SmartDeviceLink 2.0
 */
public class PermissionItem extends RPCStruct {
	public static final String KEY_RPC_NAME = "rpcName";
	public static final String KEY_HMI_PERMISSIONS = "hmiPermissions";
	public static final String KEY_PARAMETER_PERMISSIONS = "parameterPermissions";
	public static final String KEY_REQUIRE_ENCRYPTION = "requireEncryption";
	/**
	* Constructs a new PermissionItem object
	*/
    public PermissionItem(@NonNull String rpcName, @NonNull HMIPermissions hmiPermissions, @NonNull ParameterPermissions parameterPermissions) {
        this();
        setRpcName(rpcName);
        setHMIPermissions(hmiPermissions);
        setParameterPermissions(parameterPermissions);
    }
    /**
     * Constructs a new PermissionItem object indicated by the Hashtable
     * parameter
     *
     * @param hash The Hashtable to use
     */
    public PermissionItem(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Constructs a new PermissionItem object
     */
    public PermissionItem() { }
    public String getRpcName() {
        return getString(KEY_RPC_NAME);
    }
    public PermissionItem setRpcName(@NonNull String rpcName) {
        setValue(KEY_RPC_NAME, rpcName);
        return this;
    }
    public HMIPermissions getHMIPermissions() {
        return (HMIPermissions) getObject(HMIPermissions.class, KEY_HMI_PERMISSIONS);
    }
    public PermissionItem setHMIPermissions(@NonNull HMIPermissions hmiPermissions) {
        setValue(KEY_HMI_PERMISSIONS, hmiPermissions);
        return this;
    }
    public ParameterPermissions getParameterPermissions() {
    	return (ParameterPermissions) getObject(ParameterPermissions.class, KEY_PARAMETER_PERMISSIONS);
    }
    public PermissionItem setParameterPermissions(@NonNull ParameterPermissions parameterPermissions) {
        setValue(KEY_PARAMETER_PERMISSIONS, parameterPermissions);
        return this;
    }

    /**
     * Gets the encryption requirement for this item
     * @return true is encryption is required, false otherwise
     */
    public Boolean getRequireEncryption() {
        return (Boolean) getValue(KEY_REQUIRE_ENCRYPTION);
    }

    /**
     * Sets the encryption requirement for this item
     * @param isRequired the boolean requirement to be set
     */
    public PermissionItem setRequireEncryption( Boolean isRequired) {
        setValue(KEY_REQUIRE_ENCRYPTION, isRequired);
        return this;
    }
}
