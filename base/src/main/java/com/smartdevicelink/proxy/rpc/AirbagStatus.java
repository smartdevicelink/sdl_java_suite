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
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;

import java.util.Hashtable;

/**
 * <p>The status of the air bags.</p>
 *
 *
 *
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Version.</th>
 * 		</tr>
 * 		<tr>
 * 			<td>driverAirbagDeployed </td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>Status of driver airbag.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>driverSideAirbagDeployed</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>Status of driver side airbag.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>driverCurtainAirbagDeployed</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>Status of driver curtain airbag.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>passengerAirbagDeployed</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>Status of passenger airbag.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>passengerCurtainAirbagDeployed</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>Status of passenger curtain airbag.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>driverKneeAirbagDeployed</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>Status of driver knee airbag.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>passengerSideAirbagDeployed</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>Status of passenger side airbag.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>passengerKneeAirbagDeployed</td>
 * 			<td>VehicleDataEventStatus</td>
 * 			<td>Status of passenger knee airbag</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *
 *  </table>
 *  
 *  <p><b>Response:</b></p>
 *  If a resultCode of "SUCCESS" is returned, the request was accepted by SDL. By the time the corresponding response is received, the Alert will have completed. 
 *  
 *  <p><b> Non-default Result Codes:</b></p>
 *  <p>REJECTED</p><p>	ABORTED</P>
 *  
 *  
 * @since SmartDeviceLink 1.0
 *  
 * @see Image
 * @see SubscribeVehicleData
 */
public class AirbagStatus extends RPCStruct {
    public static final String KEY_DRIVER_AIRBAG_DEPLOYED = "driverAirbagDeployed";
    public static final String KEY_DRIVER_SIDE_AIRBAG_DEPLOYED = "driverSideAirbagDeployed";
    public static final String KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED = "driverCurtainAirbagDeployed";
    public static final String KEY_DRIVER_KNEE_AIRBAG_DEPLOYED = "driverKneeAirbagDeployed";
    public static final String KEY_PASSENGER_AIRBAG_DEPLOYED = "passengerAirbagDeployed";
    public static final String KEY_PASSENGER_SIDE_AIRBAG_DEPLOYED = "passengerSideAirbagDeployed";
    public static final String KEY_PASSENGER_CURTAIN_AIRBAG_DEPLOYED = "passengerCurtainAirbagDeployed";
    public static final String KEY_PASSENGER_KNEE_AIRBAG_DEPLOYED = "passengerKneeAirbagDeployed";


    public AirbagStatus() { }

    /** Constructs a new AirbagStatus object indicated by the Hashtable
     * parameter
     * @param hash hashtable filled with params to create an instance of this RPC
     * The hash table to use
     */
    public AirbagStatus(Hashtable<String, Object> hash) {
        super(hash);
    }

    public AirbagStatus(@NonNull VehicleDataEventStatus driverAirbagDeployed, @NonNull VehicleDataEventStatus driverSideAirbagDeployed, @NonNull VehicleDataEventStatus driverCurtainAirbagDeployed, @NonNull VehicleDataEventStatus driverKneeAirbagDeployed,
                        @NonNull VehicleDataEventStatus passengerCurtainAirbagDeployed, @NonNull VehicleDataEventStatus passengerAirbagDeployed, @NonNull VehicleDataEventStatus passengerSideAirbagDeployed, @NonNull VehicleDataEventStatus passengerKneeAirbagDeployed) {
        this();
        setDriverAirbagDeployed(driverAirbagDeployed);
        setDriverSideAirbagDeployed(driverSideAirbagDeployed);
        setDriverCurtainAirbagDeployed(driverCurtainAirbagDeployed);
        setDriverKneeAirbagDeployed(driverKneeAirbagDeployed);
        setPassengerAirbagDeployed(passengerAirbagDeployed);
        setPassengerSideAirbagDeployed(passengerSideAirbagDeployed);
        setPassengerCurtainAirbagDeployed(passengerCurtainAirbagDeployed);
        setPassengerKneeAirbagDeployed(passengerKneeAirbagDeployed);
    }

    public void setDriverAirbagDeployed(@NonNull VehicleDataEventStatus driverAirbagDeployed) {
        setValue(KEY_DRIVER_AIRBAG_DEPLOYED, driverAirbagDeployed);
    }
    public VehicleDataEventStatus getDriverAirbagDeployed() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_DRIVER_AIRBAG_DEPLOYED);
    }
    public void setDriverSideAirbagDeployed(@NonNull VehicleDataEventStatus driverSideAirbagDeployed) {
        setValue(KEY_DRIVER_SIDE_AIRBAG_DEPLOYED, driverSideAirbagDeployed);
    }
    public VehicleDataEventStatus getDriverSideAirbagDeployed() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_DRIVER_SIDE_AIRBAG_DEPLOYED);
    }
    public void setDriverCurtainAirbagDeployed(@NonNull VehicleDataEventStatus driverCurtainAirbagDeployed) {
        setValue(KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED, driverCurtainAirbagDeployed);
    }
    public VehicleDataEventStatus getDriverCurtainAirbagDeployed() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED);
    }
    public void setPassengerAirbagDeployed(@NonNull VehicleDataEventStatus passengerAirbagDeployed) {
        setValue(KEY_PASSENGER_AIRBAG_DEPLOYED, passengerAirbagDeployed);
    }
    public VehicleDataEventStatus getPassengerAirbagDeployed() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_PASSENGER_AIRBAG_DEPLOYED);
    }
    public void setPassengerCurtainAirbagDeployed(@NonNull VehicleDataEventStatus passengerCurtainAirbagDeployed) {
        setValue(KEY_PASSENGER_CURTAIN_AIRBAG_DEPLOYED, passengerCurtainAirbagDeployed);
    }
    public VehicleDataEventStatus getPassengerCurtainAirbagDeployed() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_PASSENGER_CURTAIN_AIRBAG_DEPLOYED);
    }
    public void setDriverKneeAirbagDeployed(@NonNull VehicleDataEventStatus driverKneeAirbagDeployed) {
        setValue(KEY_DRIVER_KNEE_AIRBAG_DEPLOYED, driverKneeAirbagDeployed);
    }
    public VehicleDataEventStatus getDriverKneeAirbagDeployed() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_DRIVER_KNEE_AIRBAG_DEPLOYED);
    }
    public void setPassengerSideAirbagDeployed(@NonNull VehicleDataEventStatus passengerSideAirbagDeployed) {
        setValue(KEY_PASSENGER_SIDE_AIRBAG_DEPLOYED, passengerSideAirbagDeployed);
    }
    public VehicleDataEventStatus getPassengerSideAirbagDeployed() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_PASSENGER_SIDE_AIRBAG_DEPLOYED);
    }
    public void setPassengerKneeAirbagDeployed(@NonNull VehicleDataEventStatus passengerKneeAirbagDeployed) {
        setValue(KEY_PASSENGER_KNEE_AIRBAG_DEPLOYED, passengerKneeAirbagDeployed);
    }
    public VehicleDataEventStatus getPassengerKneeAirbagDeployed() {
        return (VehicleDataEventStatus) getObject(VehicleDataEventStatus.class, KEY_PASSENGER_KNEE_AIRBAG_DEPLOYED);
    }
}
