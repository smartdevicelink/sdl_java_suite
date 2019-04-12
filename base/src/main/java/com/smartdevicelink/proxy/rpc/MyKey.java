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

import android.support.annotation.NonNull;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataStatus;

import java.util.Hashtable;
/** Information related to the MyKey feature.
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Mandatory</th>
 * 			<th>Description</th>
 * 		</tr>
 * 		<tr>
 * 			<td>e911Override</td>
 * 			<td>VehicleDataStatus</td>
 * 			<td>true</td>
 *			<td>Indicates whether e911 override is on.</td>
 * 		</tr>
 *  </table>
 *
 *@since SmartDeviceLink 2.0
 *
 *@see GetVehicleData
 *@see OnVehicleData
 *@see VehicleDataStatus
 * 
 *
 */

public class MyKey extends RPCStruct {
    public static final String KEY_E_911_OVERRIDE = "e911Override";
	/**
	* Constructs a new MyKey object indicated
	*/
    public MyKey() { }
    /**
     * <p>Constructs a new MyKey object indicated by the Hashtable
     * parameter</p>
     * @param hash The Hashtable to use
     */
    public MyKey(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Constructs a new MyKey object indicated
     */
    public MyKey(@NonNull VehicleDataStatus e911Override) {
        this();
        setE911Override(e911Override);
    }
    public void setE911Override(@NonNull VehicleDataStatus e911Override) {
        setValue(KEY_E_911_OVERRIDE, e911Override);
    }
    public VehicleDataStatus getE911Override() {
        return (VehicleDataStatus) getObject(VehicleDataStatus.class, KEY_E_911_OVERRIDE);
    }
}
