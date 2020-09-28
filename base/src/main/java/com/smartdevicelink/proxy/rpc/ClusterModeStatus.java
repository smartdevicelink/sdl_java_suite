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
import com.smartdevicelink.proxy.rpc.enums.CarModeStatus;
import com.smartdevicelink.proxy.rpc.enums.PowerModeQualificationStatus;
import com.smartdevicelink.proxy.rpc.enums.PowerModeStatus;

import java.util.Hashtable;

/**
 * <p>The status modes of the instrument panel cluster.</p>
 *
 *
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Mandatory</th>
 * 			<th>Description</th>
 * 		</tr>
 * 		<tr>
 * 			<td>powerModeActive</td>
 * 			<td>Boolean</td>
 * 			<td></td>
 * 			<td>References signal "PowerMode_UB".</td>
 * 		</tr>
 * 		<tr>
 * 			<td>powerModeQualificationStatus</td>
 * 			<td>PowerModeQualificationStatus</td>
 * 			<td></td>
 * 			<td>References signal "PowerModeQF".</td>
 * 		</tr>
 * 		<tr>
 * 			<td>carModeStatus</td>
 * 			<td>CarModeStatus</td>
 * 			<td></td>
 * 			<td>Describes the car mode the vehicle is in.</td>
 *           </tr>
 * 		<tr>
 * 			<td>powerModeStatus</td>
 * 			<td>PowerModeStatus</td>
 * 			<td>true</td>
 * 			<td>Describes the different power modes</td>
 * 		</tr>
 *
 *  </table>
 *
 * @see SubscribeVehicleData
 * @see SubscribeVehicleData
 * @see Image
 * @since SmartDeviceLink 1.0
 */

public class ClusterModeStatus extends RPCStruct {
    public static final String KEY_POWER_MODE_ACTIVE = "powerModeActive";
    public static final String KEY_POWER_MODE_QUALIFICATION_STATUS = "powerModeQualificationStatus";
    public static final String KEY_CAR_MODE_STATUS = "carModeStatus";
    public static final String KEY_POWER_MODE_STATUS = "powerModeStatus";

    public ClusterModeStatus() {
    }

    /**
     * <p>Constructs a new ClusterModeStatus object indicated by the Hashtable
     * parameter</p>
     *
     * @param hash The hash table to use to create an instance of this RPC
     */
    public ClusterModeStatus(Hashtable<String, Object> hash) {
        super(hash);
    }

    public ClusterModeStatus(@NonNull Boolean powerModeActive, @NonNull PowerModeQualificationStatus powerModeQualificationStatus, @NonNull CarModeStatus carModeStatus, @NonNull PowerModeStatus powerModeStatus) {
        this();
        setPowerModeActive(powerModeActive);
        setPowerModeQualificationStatus(powerModeQualificationStatus);
        setCarModeStatus(carModeStatus);
        setPowerModeStatus(powerModeStatus);
    }

    public ClusterModeStatus setPowerModeActive(@NonNull Boolean powerModeActive) {
        setValue(KEY_POWER_MODE_ACTIVE, powerModeActive);
        return this;
    }

    public Boolean getPowerModeActive() {
        return getBoolean(KEY_POWER_MODE_ACTIVE);
    }

    public ClusterModeStatus setPowerModeQualificationStatus(@NonNull PowerModeQualificationStatus powerModeQualificationStatus) {
        setValue(KEY_POWER_MODE_QUALIFICATION_STATUS, powerModeQualificationStatus);
        return this;
    }

    public PowerModeQualificationStatus getPowerModeQualificationStatus() {
        return (PowerModeQualificationStatus) getObject(PowerModeQualificationStatus.class, KEY_POWER_MODE_QUALIFICATION_STATUS);
    }

    public ClusterModeStatus setCarModeStatus(@NonNull CarModeStatus carModeStatus) {
        setValue(KEY_CAR_MODE_STATUS, carModeStatus);
        return this;
    }

    public CarModeStatus getCarModeStatus() {
        return (CarModeStatus) getObject(CarModeStatus.class, KEY_CAR_MODE_STATUS);
    }

    public ClusterModeStatus setPowerModeStatus(@NonNull PowerModeStatus powerModeStatus) {
        setValue(KEY_POWER_MODE_STATUS, powerModeStatus);
        return this;
    }

    public PowerModeStatus getPowerModeStatus() {
        return (PowerModeStatus) getObject(PowerModeStatus.class, KEY_POWER_MODE_STATUS);
    }
}
