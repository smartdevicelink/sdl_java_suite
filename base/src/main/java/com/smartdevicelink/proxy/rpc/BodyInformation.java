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
import com.smartdevicelink.proxy.rpc.enums.IgnitionStableStatus;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStatus;

import java.util.Hashtable;

/**
 * The body information including power modes.
 *
 * <p><b>Note:</b> The structure defines the information about the park brake and ignition.</p>
 *
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Mandatory</th>
 * 			<th>Description</th>
 * 			<th>Version</th>
 * 		</tr>
 * 		<tr>
 * 			<td>parkBrakeActive</td>
 * 			<td>Boolean</td>
 * 			<td>true</td>
 * 			<td>Describes, if the park break is active. The information about the park brake: - true, if active - false if not.</td>
 *  			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>ignitionStableStatus</td>
 * 			<td>IgnitionStableStatus</td>
 * 			<td>true</td>
 * 			<td>Describes, if the ignition situation is considered stableThe information about stability of the ignition switch. See {@linkplain IgnitionStableStatus}</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>ignitionStatus</td>
 * 			<td>IgnitionStatus</td>
 * 			<td>true</td>
 * 			<td>The information about ignition status. See {@linkplain  IgnitionStatus}</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>parkBrakeActive</td>
 * 			<td>Boolean</td>
 * 			<td>true</td>
 * 			<td>The information about the park brake: - true, if active - false if not.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>driverDoorAjar</td>
 * 			<td>Boolean</td>
 * 			<td>true</td>
 * 			<td>The information about the park brake: - true, if active - false if not.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>passengerDoorAjar</td>
 * 			<td>Boolean</td>
 * 			<td>true</td>
 * 			<td>The information about the park brake: - true, if active - false if not.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>rearLeftDoorAjar</td>
 * 			<td>Boolean</td>
 * 			<td>true</td>
 * 			<td>The information about the park brake: - true, if active - false if not.</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>rearRightDoorAjar</td>
 * 			<td>Boolean</td>
 * 			<td>true</td>
 * 			<td>References signal "DrStatRr_B_Actl".</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *
 *  </table>
 *
 * @see SubscribeVehicleData
 * @see GetVehicleData
 * @see OnVehicleData
 * @since SmartDeviceLink 2.0
 */

public class BodyInformation extends RPCStruct {
    public static final String KEY_PARK_BRAKE_ACTIVE = "parkBrakeActive";
    public static final String KEY_IGNITION_STABLE_STATUS = "ignitionStableStatus";
    public static final String KEY_IGNITION_STATUS = "ignitionStatus";
    public static final String KEY_DRIVER_DOOR_AJAR = "driverDoorAjar";
    public static final String KEY_PASSENGER_DOOR_AJAR = "passengerDoorAjar";
    public static final String KEY_REAR_LEFT_DOOR_AJAR = "rearLeftDoorAjar";
    public static final String KEY_REAR_RIGHT_DOOR_AJAR = "rearRightDoorAjar";

    public BodyInformation() {
    }

    /**
     * Constructs a new BodyInformation object indicated by the Hashtable
     * parameter
     *
     * @param hash hashtable filled with params to create an instance of this RPC
     *             The hash table to use
     */
    public BodyInformation(Hashtable<String, Object> hash) {
        super(hash);
    }

    public BodyInformation(@NonNull Boolean parkBrakeActive, @NonNull IgnitionStableStatus ignitionStableStatus, @NonNull IgnitionStatus ignitionStatus) {
        this();
        setParkBrakeActive(parkBrakeActive);
        setIgnitionStableStatus(ignitionStableStatus);
        setIgnitionStatus(ignitionStatus);
    }

    public BodyInformation setParkBrakeActive(@NonNull Boolean parkBrakeActive) {
        setValue(KEY_PARK_BRAKE_ACTIVE, parkBrakeActive);
        return this;
    }

    public Boolean getParkBrakeActive() {
        return getBoolean(KEY_PARK_BRAKE_ACTIVE);
    }

    public BodyInformation setIgnitionStableStatus(@NonNull IgnitionStableStatus ignitionStableStatus) {
        setValue(KEY_IGNITION_STABLE_STATUS, ignitionStableStatus);
        return this;
    }

    public IgnitionStableStatus getIgnitionStableStatus() {
        return (IgnitionStableStatus) getObject(IgnitionStableStatus.class, KEY_IGNITION_STABLE_STATUS);
    }

    public BodyInformation setIgnitionStatus(@NonNull IgnitionStatus ignitionStatus) {
        setValue(KEY_IGNITION_STATUS, ignitionStatus);
        return this;
    }

    public IgnitionStatus getIgnitionStatus() {
        return (IgnitionStatus) getObject(IgnitionStatus.class, KEY_IGNITION_STATUS);
    }

    public BodyInformation setDriverDoorAjar(Boolean driverDoorAjar) {
        setValue(KEY_DRIVER_DOOR_AJAR, driverDoorAjar);
        return this;
    }

    public Boolean getDriverDoorAjar() {
        return getBoolean(KEY_DRIVER_DOOR_AJAR);
    }


    public BodyInformation setPassengerDoorAjar(Boolean passengerDoorAjar) {
        setValue(KEY_PASSENGER_DOOR_AJAR, passengerDoorAjar);
        return this;
    }

    public Boolean getPassengerDoorAjar() {
        return getBoolean(KEY_PASSENGER_DOOR_AJAR);
    }

    public BodyInformation setRearLeftDoorAjar(Boolean rearLeftDoorAjar) {
        setValue(KEY_REAR_LEFT_DOOR_AJAR, rearLeftDoorAjar);
        return this;
    }

    public Boolean getRearLeftDoorAjar() {
        return getBoolean(KEY_REAR_LEFT_DOOR_AJAR);
    }

    public BodyInformation setRearRightDoorAjar(Boolean rearRightDoorAjar) {
        setValue(KEY_REAR_RIGHT_DOOR_AJAR, rearRightDoorAjar);
        return this;
    }

    public Boolean getRearRightDoorAjar() {
        return getBoolean(KEY_REAR_RIGHT_DOOR_AJAR);
    }

}
