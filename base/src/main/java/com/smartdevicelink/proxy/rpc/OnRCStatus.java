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

public class OnRCStatus extends RPCNotification {
	public static final String KEY_ALLOCATED_MODULES = "allocatedModules";
	public static final String KEY_FREE_MODULES = "freeModules";
	public static final String KEY_ALLOWED = "allowed";

	/**
	 * Constructs a new OnRCStatus object
	 */
	public OnRCStatus() {
		super(FunctionID.ON_RC_STATUS.toString());
	}

	/**
	 * Constructs a new OnRCStatus object indicated by the Hashtable
	 * parameter
	 * @param hash The Hashtable to use
	 */
	public OnRCStatus(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Constructs a newly allocated OnRCStatus object
	 *
	 * @param allocatedModules Contains a list (zero or more) of module types that are allocated to the application.
	 * @param freeModules      Contains a list (zero or more) of module types that are free to access for the application.
	 */
	public OnRCStatus(@NonNull List<ModuleData> allocatedModules, @NonNull List<ModuleData> freeModules) {
		this();
		setAllocatedModules(allocatedModules);
		setFreeModules(freeModules);
	}

	@SuppressWarnings("unchecked")
	public List<ModuleData> getAllocatedModules() {
		return (List<ModuleData>) getObject(ModuleData.class, KEY_ALLOCATED_MODULES);
	}

	public void setAllocatedModules(@NonNull List<ModuleData> allocatedModules) {
		setParameters(KEY_ALLOCATED_MODULES, allocatedModules);
	}

	@SuppressWarnings("unchecked")
	public List<ModuleData> getFreeModules() {
		return (List<ModuleData>) getObject(ModuleData.class, KEY_FREE_MODULES);
	}

	public void setFreeModules(@NonNull List<ModuleData> freeModules) {
		setParameters(KEY_FREE_MODULES, freeModules);
	}

	public Boolean getAllowed() {
		return getBoolean(KEY_ALLOWED);
	}

	public void setAllowed(Boolean allowed) {
		setParameters(KEY_ALLOWED, allowed);
	}
}
