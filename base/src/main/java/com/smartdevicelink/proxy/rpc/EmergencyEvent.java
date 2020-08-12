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
import com.smartdevicelink.proxy.rpc.enums.EmergencyEventType;
import com.smartdevicelink.proxy.rpc.enums.FuelCutoffStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;

import java.util.Hashtable;

/** Information related to an emergency event (and if it occurred).
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Mandatory</th>
 * 			<th>Description</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>emergencyEventType</td>
 * 			<td>EmergencyEventType</td>
 * 			<td></td>
 * 			<td>References signal "VedsEvntType_D_Ltchd". See{@linkplain EmergencyEventType}</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>fuelCutoffStatus</td>
 * 			<td>FuelCutoffStatus</td>
 * 			<td></td>
 * 			<td>References signal "RCM_FuelCutoff". See{@linkplain FuelCutoffStatus}</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>rolloverEvent</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td></td>
 * 			<td>References signal "VedsEvntRoll_D_Ltchd". See{@linkplain VehicleDataEventStatus}</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>maximumChangeVelocity</td>
 * 			<td>Integer</td>
 * 			<td></td>
 * 			<td>References signal "VedsMaxDeltaV_D_Ltchd".</td>
 * 			<td>minvalue=0; maxvalue=255;<p> Additional reserved values:</p> <p>0x00 No event; 0xFE Not supported; 0xFF Fault</p> </td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *
 *
 * 		<tr>
 * 			<td>multipleEvents</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td></td>
 * 			<td>References signal "VedsMultiEvnt_D_Ltchd". See{@linkplain VehicleDataEventStatus}</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 * 
 * @see Image
 * @see SubscribeVehicleData
 * @since SmartDeviceLink 2.0
 *
 */

public class EmergencyEvent extends RPCStruct {
    public static final String KEY_EMERGENCY_EVENT_TYPE = "emergencyEventType";
    public static final String KEY_FUEL_CUTOFF_STATUS = "fuelCutoffStatus";
    public static final String KEY_ROLLOVER_EVENT = "rolloverEvent";
    public static final String KEY_MAXIMUM_CHANGE_VELOCITY = "maximumChangeVelocity";
    public static final String KEY_MULTIPLE_EVENTS = "multipleEvents";

    /** Constructs a new EmergencyEvent object
     *
     */
    public EmergencyEvent() { }
    /** Constructs a new EmergencyEvent object indicated by the Hashtable
     * parameter
     * @param hash <p>The hash table to use</p>
     */
    public EmergencyEvent(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
	 * Constructs a new EmergencyEvent object
     */
    public EmergencyEvent(@NonNull EmergencyEventType emergencyEventType, @NonNull FuelCutoffStatus fuelCutoffStatus, @NonNull VehicleDataEventStatus rolloverEvent, @NonNull Integer maximumChangeVelocity, @NonNull VehicleDataEventStatus multipleEvents) {
        this();
        setEmergencyEventType(emergencyEventType);
        setFuelCutoffStatus(fuelCutoffStatus);
        setRolloverEvent(rolloverEvent);
        setMaximumChangeVelocity(maximumChangeVelocity);
        setMultipleEvents(multipleEvents);
    }

    public void setEmergencyEventType(@NonNull EmergencyEventType emergencyEventType) {
        setValue(KEY_EMERGENCY_EVENT_TYPE, emergencyEventType);
    }
    public EmergencyEventType getEmergencyEventType() {
        return (EmergencyEventType) getObject(EmergencyEventType.class, KEY_EMERGENCY_EVENT_TYPE);
    }
    public void setFuelCutoffStatus(@NonNull FuelCutoffStatus fuelCutoffStatus) {
        setValue(KEY_FUEL_CUTOFF_STATUS, fuelCutoffStatus);
    }
    public FuelCutoffStatus getFuelCutoffStatus() {
        return (FuelCutoffStatus) getObject(FuelCutoffStatus.class, KEY_FUEL_CUTOFF_STATUS);
    }
    public void setRolloverEvent(@NonNull VehicleDataEventStatus rolloverEvent) {
        setValue(KEY_ROLLOVER_EVENT, rolloverEvent);
    }
    public VehicleDataEventStatus getRolloverEvent() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_ROLLOVER_EVENT);
    }
    public void setMaximumChangeVelocity(@NonNull Integer maximumChangeVelocity) {
        setValue(KEY_MAXIMUM_CHANGE_VELOCITY, maximumChangeVelocity);
    }
    public Integer getMaximumChangeVelocity() {
    	return getInteger(KEY_MAXIMUM_CHANGE_VELOCITY);
    }
    public void setMultipleEvents(@NonNull VehicleDataEventStatus multipleEvents) {
        setValue(KEY_MULTIPLE_EVENTS, multipleEvents);
    }
    public VehicleDataEventStatus getMultipleEvents() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_MULTIPLE_EVENTS);
    }
}
