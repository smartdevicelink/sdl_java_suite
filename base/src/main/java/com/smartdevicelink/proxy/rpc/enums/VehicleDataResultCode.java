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
package com.smartdevicelink.proxy.rpc.enums;
/** Enumeration that describes possible result codes of a vehicle data entry request.
 * 
 * @since SmartDeviceLink 2.0 
 *
 *@see com.smartdevicelink.proxy.rpc.DIDResult
 *@see com.smartdevicelink.proxy.rpc.ReadDID
 */

public enum VehicleDataResultCode {
	/**Individual vehicle data item / DTC / DID request or subscription successful
	 * 
	 */

	SUCCESS,
	/**
     *DTC / DID request successful, however, not all active DTCs or full contents of DID location available
     * 
     * @since SmartDeviceLink 4.0
     */
	
	TRUNCATED_DATA,
	/** This vehicle data item is not allowed for this app .The request is not authorized in local policies.
	 * 
	 */

	DISALLOWED,
	/** The user has not granted access to this type of vehicle data item at this time.
	 * 
	 */

	USER_DISALLOWED,
	/** The ECU ID referenced is not a valid ID on the bus / system.
	 * 
	 */

	INVALID_ID,
	/** The requested vehicle data item / DTC / DID is not currently available or responding on the bus / system.
	 * 
	 */

	VEHICLE_DATA_NOT_AVAILABLE,
	/** The vehicle data item is already subscribed.
	 * 
	 */

	DATA_ALREADY_SUBSCRIBED,
	/** The vehicle data item cannot be unsubscribed because it is not currently subscribed.
	 * 
	 */

	DATA_NOT_SUBSCRIBED,
	/** The request for this item is ignored because it is already in progress
 	*/

	IGNORED;

    public static VehicleDataResultCode valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
