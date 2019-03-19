package com.smartdevicelink.proxy.rpc.enums;

/**
 * Enumeration listing possible Service update reasons.
 */
public enum ServiceUpdateReason {

	/**
	 * The service has just been published with the module and once
	 * activated to the primary service of its type, it will be ready for possible consumption.
	 */
	PUBLISHED,

	/**
	 * The service has just been unpublished with the module and is no longer accessible
	 */
	REMOVED,

	/**
	 * The service is activated as the primary service of this type. All requests dealing with
	 * this service type will be handled by this service.
	 */
	ACTIVATED,

	/**
	 * The service has been deactivated as the primary service of its type
	 */
	DEACTIVATED,

	/**
	 * The service has updated its manifest. This could imply updated capabilities
	 */
	MANIFEST_UPDATE,

	;

	/**
	 * Convert String to ServiceUpdateReason
	 * @param value String
	 * @return ServiceUpdateReason
	 */
	public static ServiceUpdateReason valueForString(String value) {
		try{
			return valueOf(value);
		}catch(Exception e){
			return null;
		}
	}
}