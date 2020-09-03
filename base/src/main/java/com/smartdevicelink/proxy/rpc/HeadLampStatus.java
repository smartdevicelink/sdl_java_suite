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
import com.smartdevicelink.proxy.rpc.enums.AmbientLightStatus;

import java.util.Hashtable;
/** Status of the head lamps.
 * 
 * <p><table border="1" rules="all"></p>
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Mandatory</th>
 * 			<th>Description</th>
 * 		</tr>
 * 		<tr>
 * 			<td>lowBeamsOn</td>
 * 			<td>Boolean</td>
 * 			<td>true</td>
 * 			<td>Status of the low beam lamps. </td>
 * 		</tr>
 * 		<tr>
 * 			<td>highBeamsOn</td>
 * 			<td>Boolean</td>
 * 			<td>true</td>
 * 			<td>Status of the high beam lamps. </td>
 * 		</tr>
 *     <tr>
 * 			<td>ambientLightSensorStatus</td>
 * 			<td>AmbientLightStatus</td>
 * 			<td>true</td>
 * 			<td>Status of the ambient light sensor.</td>
 * 		</tr>
 *
 *
 * </table>
 * @see OnVehicleData
 * @see GetVehicleData
 * @since SmartDeviceLink 1.0
 * 
 */

public class HeadLampStatus extends RPCStruct {
	public static final String KEY_AMBIENT_LIGHT_SENSOR_STATUS = "ambientLightSensorStatus";
	public static final String KEY_HIGH_BEAMS_ON = "highBeamsOn";
    public static final String KEY_LOW_BEAMS_ON = "lowBeamsOn";

    /**
	 * Constructs a new HeadLampStatus object
     */
    public HeadLampStatus() {}
    /**
	 * <p>Constructs a new HeadLampStatus object indicated by the Hashtable
     * parameter</p>
     * @param hash The hash table to use
     */
    public HeadLampStatus(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
	 * Constructs a new HeadLampStatus object
     */
    public HeadLampStatus(@NonNull Boolean lowBeamsOn, @NonNull Boolean highBeamsOn) {
        this();
        setLowBeamsOn(lowBeamsOn);
        setHighBeamsOn(highBeamsOn);
    }
    public HeadLampStatus setAmbientLightStatus( AmbientLightStatus ambientLightSensorStatus) {
        setValue(KEY_AMBIENT_LIGHT_SENSOR_STATUS, ambientLightSensorStatus);
        return this;
    }
    public AmbientLightStatus getAmbientLightStatus() {
        return (AmbientLightStatus) getObject(AmbientLightStatus.class, KEY_AMBIENT_LIGHT_SENSOR_STATUS);
    }
    public HeadLampStatus setHighBeamsOn(@NonNull Boolean highBeamsOn) {
        setValue(KEY_HIGH_BEAMS_ON, highBeamsOn);
        return this;
    }
    public Boolean getHighBeamsOn() {
    	return getBoolean(KEY_HIGH_BEAMS_ON);
    }
    public HeadLampStatus setLowBeamsOn(@NonNull Boolean lowBeamsOn) {
        setValue(KEY_LOW_BEAMS_ON, lowBeamsOn);
        return this;
    }
    public Boolean getLowBeamsOn() {
    	return getBoolean(KEY_LOW_BEAMS_ON);
    }
}
