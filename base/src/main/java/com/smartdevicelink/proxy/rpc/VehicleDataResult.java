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
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataType;

import java.util.Hashtable;
/**
 * 
 * Individual published data request result.
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
 * 			<td>dataType</td>
 * 			<td>VehicleDataType</td>
 * 			<td>Defined published data element type.</td>
 *                 <td></td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>resultCode</td>
 * 			<td>VehicleDataResultCode</td>
 * 			<td>Published data result code.</td>
 *                 <td></td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *  </table>
 *
 */
public class VehicleDataResult extends RPCStruct {
	public static final String KEY_DATA_TYPE = "dataType";
	public static final String KEY_RESULT_CODE = "resultCode";
	public static final String KEY_OEM_CUSTOM_DATA_TYPE = "oemCustomDataType";

	public VehicleDataResult() { }
	  /**
		* <p>
		* Constructs a new VehicleDataResult object indicated by the Hashtable
		* parameter
		* </p>
		* 
		* @param hash the Hashtable to use
		*/
	public VehicleDataResult(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Individual published data request result.
	 * @param dataType Defined published data element type.
	 * @param resultCode Published data result code.
	 */
	public VehicleDataResult(@NonNull VehicleDataType dataType, @NonNull VehicleDataResultCode resultCode){
		this();
		setDataType(dataType);
		setResultCode(resultCode);
	}
	
	public void setResultCode(@NonNull VehicleDataResultCode resultCode) {
		setValue(KEY_RESULT_CODE, resultCode);
	}
	
	public VehicleDataResultCode getResultCode() {
		return (VehicleDataResultCode) getObject(VehicleDataResultCode.class, KEY_RESULT_CODE);
	}	

	public void setDataType(@NonNull VehicleDataType dataType) {
		setValue(KEY_DATA_TYPE, dataType);
	}

	public VehicleDataType getDataType() {
		return (VehicleDataType) getObject(VehicleDataType.class, KEY_DATA_TYPE);
	}
	
	public void setOEMCustomVehicleDataType(String oemCustomDataType) {
		setValue(KEY_OEM_CUSTOM_DATA_TYPE, oemCustomDataType);
	}

	public String getOEMCustomVehicleDataType() {
		return (String) getObject(String.class, KEY_OEM_CUSTOM_DATA_TYPE);
	}
}
