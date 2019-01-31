package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

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

}
