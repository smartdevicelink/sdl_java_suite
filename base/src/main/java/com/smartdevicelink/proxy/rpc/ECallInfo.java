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
import com.smartdevicelink.proxy.rpc.enums.ECallConfirmationStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataNotificationStatus;

import java.util.Hashtable;

/** Emergency Call notification and confirmation data.
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th> Req.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>eCallNotificationStatus</td>
 * 			<td>VehicleDataNotificationStatus</td>
 * 			<td>References signal "eCallNotification_4A".</td>
 *                 <td></td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>auxECallNotificationStatus</td>
 * 			<td>VehicleDataNotificationStatus</td>
 * 			<td>References signal "eCallNotification". This is an alternative signal available on some carlines replacing the eCallNotificationStatus, but showing the same values.</td>
 *                 <td></td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>eCallConfirmationStatus</td>
 * 			<td>ECallConfirmationStatus</td>
 * 			<td>References signal "eCallConfirmation"</td>
 *                 <td></td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 2.0
 * @see SubscribeVehicleData
 * 
 *
 *
 */

public class ECallInfo extends RPCStruct {
    public static final String KEY_E_CALL_NOTIFICATION_STATUS = "eCallNotificationStatus";
    public static final String KEY_AUX_E_CALL_NOTIFICATION_STATUS = "auxECallNotificationStatus";
    public static final String KEY_E_CALL_CONFIRMATION_STATUS = "eCallConfirmationStatus";
    /** Constructs a new ECallInfo object
	 */
    public ECallInfo() { }
    /** Constructs a new ECallInfo object indicated by the Hashtable
     * parameter
     * @param hash <p>The hash table to use</p>
     */
    public ECallInfo(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
	 * Constructs a new ECallInfo object
     */
    public ECallInfo(@NonNull VehicleDataNotificationStatus eCallNotificationStatus, @NonNull VehicleDataNotificationStatus auxECallNotificationStatus, @NonNull ECallConfirmationStatus eCallConfirmationStatus) {
        this();
        setECallNotificationStatus(eCallNotificationStatus);
        setAuxECallNotificationStatus(auxECallNotificationStatus);
        setECallConfirmationStatus(eCallConfirmationStatus);
    }

    public ECallInfo setECallNotificationStatus(@NonNull VehicleDataNotificationStatus eCallNotificationStatus) {
        setValue(KEY_E_CALL_NOTIFICATION_STATUS, eCallNotificationStatus);
        return this;
    }
    public VehicleDataNotificationStatus getECallNotificationStatus() {
        return (VehicleDataNotificationStatus) getObject(VehicleDataNotificationStatus.class, KEY_E_CALL_NOTIFICATION_STATUS);
    }
    public ECallInfo setAuxECallNotificationStatus(@NonNull VehicleDataNotificationStatus auxECallNotificationStatus) {
        setValue(KEY_AUX_E_CALL_NOTIFICATION_STATUS, auxECallNotificationStatus);
        return this;
    }
    public VehicleDataNotificationStatus getAuxECallNotificationStatus() {
        return (VehicleDataNotificationStatus) getObject(VehicleDataNotificationStatus.class, KEY_AUX_E_CALL_NOTIFICATION_STATUS);
    }
    public ECallInfo setECallConfirmationStatus(@NonNull ECallConfirmationStatus eCallConfirmationStatus) {
        setValue(KEY_E_CALL_CONFIRMATION_STATUS, eCallConfirmationStatus);
        return this;
    }
    public ECallConfirmationStatus getECallConfirmationStatus() {
        return (ECallConfirmationStatus) getObject(ECallConfirmationStatus.class, KEY_E_CALL_CONFIRMATION_STATUS);
    }
}
