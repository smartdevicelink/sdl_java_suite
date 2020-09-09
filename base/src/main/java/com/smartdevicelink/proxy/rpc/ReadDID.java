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
import java.util.List;

/**
 * <p>Non periodic vehicle data read request. This is an RPC to get diagnostics
 * data from certain vehicle modules. DIDs of a certain module might differ from
 * vehicle type to vehicle type</p>
 * 
 * <p>Function Group: ProprietaryData</p>
 * 
 * <p><b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b></p>
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
 * 			<td>ecuName</td>
 * 			<td>Integer</td>
 * 			<td>Name of ECU.</td>
 *                 <td>Y</td>
 * 			<td>Minvalue: 0; Maxvalue: 65535</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *
 * 		<tr>
 * 			<td>didLocation</td>
 * 			<td>Integer</td>
 * 			<td>Get raw data from vehicle data DID location(s).</td>
 *                 <td>Y</td>
 * 			<td>Minvalue: 0; Maxvalue: 65535</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 			<td>appID</td>
 * 			<td>Integer</td>
 * 			<td>ID of the application that requested this RPC.</td>
 *                 <td>Y</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 *  
 *  <p><b>Response</b></p>
 *   
 *  <p><b>Non-default Result Codes:</b></p>
 *  <p>SUCCESS</p>
 *  <p>INVALID_DATA</p>
 *  <p>OUT_OF_MEMORY</p>
 *  <p>TOO_MANY_PENDING_REQUESTS</p>
 *  <p>APPLICATION_NOT_REGISTERED</p>
 *  <p>GENERIC_ERROR</p>
 *  <p>REJECTED</p>
 * <p> DISALLOWED</p>
 *  <p>USER_DISALLOWED </p>
 *  <p>TRUNCATED_DATA</p>
 * 
 * @since SmartDeviceLink 2.0
 */
public class ReadDID extends RPCRequest {
	public static final String KEY_ECU_NAME = "ecuName";
	public static final String KEY_DID_LOCATION = "didLocation";

	/**
	 * Constructs a new ReadDID object
	 */
    public ReadDID() {
        super(FunctionID.READ_DID.toString());
    }

	/**
	 * Constructs a new ReadDID object indicated by the Hashtable parameter
	 * 
	 * @param hash The Hashtable to use
	 */
    public ReadDID(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new ReadDID object
	 * @param ecuName an Integer value representing the ID of the vehicle module
	 * <b>Notes: </b>Minvalue:0; Maxvalue:65535
	 * @param didLocation a List<Integer> value representing raw data from vehicle data DID location(s) <br>
	 * <b>Notes: </b>
	 * <ul>
	 * 		<li>Minvalue:0; Maxvalue:65535</li>
	 * 		<li>ArrayMin:0; ArrayMax:1000</li>
	 * </ul>
	 */
	public ReadDID(@NonNull Integer ecuName, @NonNull List<Integer> didLocation) {
		this();
		setEcuName(ecuName);
		setDidLocation(didLocation);
	}

	/**
	 * Sets an ID of the vehicle module
	 *
	 * @param ecuName
	 *            an Integer value representing the ID of the vehicle module
	 *            <p></p>
	 *            <b>Notes: </b>Minvalue:0; Maxvalue:65535
	 */
    public ReadDID setEcuName(@NonNull Integer ecuName) {
        setParameters(KEY_ECU_NAME, ecuName);
        return this;
    }

	/**
	 * Gets the ID of the vehicle module
	 * 
	 * @return Integer -an Integer value representing the ID of the vehicle
	 *         module
	 */
    public Integer getEcuName() {
    	return getInteger(KEY_ECU_NAME);
    }

	/**
	 * Sets raw data from vehicle data DID location(s)
	 *
	 * @param didLocation
	 *            a List<Integer> value representing raw data from vehicle
	 *            data DID location(s)
	 *            <p></p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>Minvalue:0; Maxvalue:65535</li>
	 *            <li>ArrayMin:0; ArrayMax:1000</li>
	 *            </ul>
	 */
    public ReadDID setDidLocation(@NonNull List<Integer> didLocation) {
        setParameters(KEY_DID_LOCATION, didLocation);
        return this;
    }

	/**
	 * Gets raw data from vehicle data DID location(s)
	 * 
	 * @return List<Integer> -a List<Integer> value representing raw data
	 *         from vehicle data DID location(s)
	 */
    @SuppressWarnings("unchecked")
    public List<Integer> getDidLocation() {
		return (List<Integer>) getObject(Integer.class, KEY_DID_LOCATION);
    }
}
