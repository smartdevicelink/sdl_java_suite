/*
 * Copyright (c) 2019 Livio, Inc.
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
package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.ServiceUpdateReason;

import java.util.Hashtable;

public class AppServiceCapability extends RPCStruct {

	public static final String KEY_UPDATE_REASON = "updateReason";
	public static final String KEY_UPDATED_APP_SERVICE_RECORD = "updatedAppServiceRecord";

	// Constructors

	public AppServiceCapability(){}

	/**
	 * @param hash of parameters
	 */
	public AppServiceCapability(Hashtable<String, Object> hash) {
		super(hash);
	}


	/**
	 * @param updatedAppServiceRecord -
	 */
	public AppServiceCapability(@NonNull AppServiceRecord updatedAppServiceRecord){
		this();
		setUpdatedAppServiceRecord(updatedAppServiceRecord);
	}

	// Setters and Getters

	/**
	 * Only included in OnSystemCapabilityUpdated. Update reason for this service record.
	 * @param updateReason -
	 */
	public void setUpdateReason(ServiceUpdateReason updateReason){
		setValue(KEY_UPDATE_REASON, updateReason);
	}

	/**
	 * Only included in OnSystemCapabilityUpdated. Update reason for this service record.
	 * @return updateReason - The updateReason
	 */
	public ServiceUpdateReason getUpdateReason(){
		return (ServiceUpdateReason) getObject(ServiceUpdateReason.class, KEY_UPDATE_REASON);
	}

	/**
	 * Only included in OnSystemCapabilityUpdated. Update reason for this service record.
	 * @param updatedAppServiceRecord -
	 */
	public void setUpdatedAppServiceRecord(AppServiceRecord updatedAppServiceRecord){
		setValue(KEY_UPDATED_APP_SERVICE_RECORD, updatedAppServiceRecord);
	}

	/**
	 * Only included in OnSystemCapabilityUpdated. Update reason for this service record.
	 * @return updateReason - The updateReason
	 */
	public AppServiceRecord getUpdatedAppServiceRecord(){
		return (AppServiceRecord) getObject(AppServiceRecord.class, KEY_UPDATED_APP_SERVICE_RECORD);
	}

	/**
	 * Helper method to compare an AppServiceCapability to this instance.
	 * @param capability the AppServiceCapability to compare to this one
	 * @return if both AppServiceCapability objects refer to the same service
	 */
	public boolean matchesAppService(AppServiceCapability capability){
		if(capability != null){
			AppServiceRecord appServiceRecord = getUpdatedAppServiceRecord();
			AppServiceRecord otherASR = capability.getUpdatedAppServiceRecord();

			if(appServiceRecord != null && otherASR != null) {
				// If both service IDs exists we can compare them. If either is null we can't use
				// only this check.
				if(appServiceRecord.getServiceID() != null && otherASR.getServiceID() != null){
					//return whether the app service IDs are equal or not
					return appServiceRecord.getServiceID().equalsIgnoreCase(otherASR.getServiceID());
				}else{
					AppServiceManifest manifest = appServiceRecord.getServiceManifest();
					AppServiceManifest otherManifest = otherASR.getServiceManifest();
					if(manifest != null && otherManifest != null){
						//Check the service names, if they are the same it can be assumed they are the same service
						return (manifest.getServiceName() != null && manifest.getServiceName().equalsIgnoreCase(otherManifest.getServiceName()));
					}
				}
			}
		}
		// If it got to this point it was not the same
		return false;
	}

}
