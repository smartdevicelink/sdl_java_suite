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

import java.util.Hashtable;

/**
 * Individual requested DID result and data.
 *
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Mandatory</th>
 *                 <th> Additional</th>
 * 			<th>Description</th>
 * 		</tr>
 * 		<tr>
 * 			<td>resultCode</td>
 * 			<td>VehicleDataResultCode</td>
 * 			<td>true</td>
 *                 <td></td>
 * 			<td>Individual DID result code </td>
 * 		</tr>
 * 		<tr>
 * 			<td>didLocation</td>
 * 			<td>Integer</td>
 * 			<td>true</td>
 *                 <td>minvalue = 0  maxvalue = 65535 </td>
 * 			<td>The address of DID location from the ReadDID request.</td>
 * 		</tr>
 *
 * 		<tr>
 * 			<td>data</td>
 * 			<td>String</td>
 * 			<td>false</td>
 *                 <td>maxlength = 5000 </td>
 * 			<td>The DID data which is the hex byte string of however many bytes are stored at that location</td>
 * 		</tr>
 *  </table>
 *
 * @see VehicleDataResultCode
 * @see ReadDID
 * @since SmartDeviceLink 1.0
 */

public class DIDResult extends RPCStruct {
    public static final String KEY_RESULT_CODE = "resultCode";
    public static final String KEY_DATA = "data";
    public static final String KEY_DID_LOCATION = "didLocation";

    public DIDResult() {
    }

    /**
     * <p>Constructs a new DIDResult object indicated by the Hashtable
     * parameter</p>
     *
     * @param hash The hash table to use to create an instance of this RPC
     */
    public DIDResult(Hashtable<String, Object> hash) {
        super(hash);
    }

    public DIDResult(@NonNull VehicleDataResultCode resultCode, @NonNull Integer didLocation) {
        this();
        setResultCode(resultCode);
        setDidLocation(didLocation);
    }

    public DIDResult setResultCode(@NonNull VehicleDataResultCode resultCode) {
        setValue(KEY_RESULT_CODE, resultCode);
        return this;
    }

    public VehicleDataResultCode getResultCode() {
        return (VehicleDataResultCode) getObject(VehicleDataResultCode.class, KEY_RESULT_CODE);
    }

    public DIDResult setDidLocation(@NonNull Integer didLocation) {
        setValue(KEY_DID_LOCATION, didLocation);
        return this;
    }

    public Integer getDidLocation() {
        return getInteger(KEY_DID_LOCATION);
    }

    public DIDResult setData(String data) {
        setValue(KEY_DATA, data);
        return this;
    }

    public String getData() {
        return getString(KEY_DATA);
    }
}
