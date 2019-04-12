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
/** This enumeration reflects the status of the wipers.
 * 
 * @since SmartDeviceLink 2.0
 * 
 * @see com.smartdevicelink.proxy.rpc.GetVehicleData
 * @see com.smartdevicelink.proxy.rpc.OnVehicleData
 */

public enum WiperStatus {
	/** The wipers are off.
	 * 
	 */

	OFF,
	/** The wipers are automatically off after detecting the wipers do not need to be engaged (rain stopped, etc.).
	 * 
	 */

	AUTO_OFF,
	/** Means that though set to off, somehow the wipers have been engaged (physically moved enough to engage a wiping motion).
	 * 
	 */

	OFF_MOVING,
	/** The wipers are manually off after having been working.
	 * 
	 */

	MAN_INT_OFF,
	/** The wipers are manually on.
	 * 
	 */

	MAN_INT_ON,
	/** The wipers are manually set to low speed.
	 * 
	 */

	MAN_LOW,
	/** The wipers are manually set to high speed.
	 * 
	 */

	MAN_HIGH,
	/** The wipers are manually set for doing a flick.
	 * 
	 */

	MAN_FLICK,
	/** The wipers are set to use the water from vehicle washer bottle for cleaning the windscreen.
	 * 
	 */

	WASH,
	/** The wipers are automatically set to low speed.
	 * 
	 */

	AUTO_LOW,
	/** The wipers are automatically set to high speed.
	 * 
	 */

	AUTO_HIGH,
	/** This is for when a user has just initiated a WASH and several seconds later a secondary wipe is automatically initiated to clear remaining fluid
	 */

	COURTESYWIPE,
	/** This is set as the user moves between possible automatic wiper speeds.
	 * 
	 */

	AUTO_ADJUST,
	/** The wiper is stalled to its place. There may be an obstruction.
	 * 
	 */

	STALLED,
	/** The sensor / module cannot provide any information for wiper.
	 * 
	 */

	NO_DATA_EXISTS;
	/**
	 * Convert String to WiperStatus
	 * @param value String
	 * @return WiperStatus
	 */

    public static WiperStatus valueForString(String value) {
        try{
            return valueOf(value);
        }catch(Exception e){
            return null;
        }
    }
}
