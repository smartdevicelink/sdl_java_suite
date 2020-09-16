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

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

/**
 * This RPC allows to request diagnostic module trouble codes from a certain
 * vehicle module.
 * 
 *<p> Function Group: ProprietaryData</p>
 * 
 *<p> <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b></p>
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 * 			<th>Version</th>
 * 		</tr>
 * 		<tr>
 * 			<td>ecuName</td>
 * 			<td>Integer</td>
 * 			<td>Name of ECU.</td>
 *                 <td>Y</td>
 *                 <td>Min Value: 0; Max Value: 65535</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>dtcMask</td>
 * 			<td>Integer</td>
 * 			<td>DTC Mask Byte to be sent in diagnostic request to module.</td>
 *                 <td>N</td>
 *                 <td>Min Value: 0; Max Value: 255</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 *  </table>
 * <p><b>Response</b></p>
 * 
 * <p><b>Non-default Result Codes:</b></p> 
 *<p> SUCCESS</p>
 * <p>INVALID_DATA</p>
 *<p> OUT_OF_MEMORY</p>
 * <p>TOO_MANY_PENDING_REQUESTS</p>
 * <p>APPLICATION_NOT_REGISTERED</p>
 *<p>GENERIC_ERROR</p>  
 * <p>REJECTED</p>  
 * <p>DISALLOWED </p> 
 * <p>USER_DISALLOWED</p>   
 * @since SmartDeviceLink 2.0
 */
public class GetDTCs extends RPCRequest {
	public static final String KEY_DTC_MASK = "dtcMask";
	public static final String KEY_ECU_NAME = "ecuName";

	/**
	 * Constructs a new GetDTCs object
	 */
    public GetDTCs() {
        super(FunctionID.GET_DTCS.toString());
    }

	/**
	 * <p>Constructs a new GetDTCs object indicated by the Hashtable parameter
	 * </p>
	 * 
	 * @param hash The Hashtable to use
	 */
    public GetDTCs(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new GetDTCs object
	 * @param ecuName an Integer value representing a name of the module to receive the DTC form <br>
	 * <b>Notes: </b>Minvalue:0; Maxvalue:65535
	 */
	public GetDTCs(@NonNull Integer ecuName) {
		this();
		setEcuName(ecuName);
	}

	/**
	 * Sets a name of the module to receive the DTC form
	 *
	 * @param ecuName
	 *            an Integer value representing a name of the module to receive
	 *            the DTC form
	 *            <p>
	 *            <b>Notes:</p> </b>Minvalue:0; Maxvalue:65535
	 */
    public GetDTCs setEcuName(@NonNull Integer ecuName) {
        setParameters(KEY_ECU_NAME, ecuName);
        return this;
    }

	/**
	 * Gets a name of the module to receive the DTC form
	 * 
	 * @return Integer -an Integer value representing a name of the module to
	 *         receive the DTC form
	 */
    public Integer getEcuName() {
    	return getInteger(KEY_ECU_NAME);
    }
    public GetDTCs setDtcMask( Integer dtcMask) {
        setParameters(KEY_DTC_MASK, dtcMask);
        return this;
    }
    public Integer getDtcMask() {
    	return getInteger(KEY_DTC_MASK);
    }
}
