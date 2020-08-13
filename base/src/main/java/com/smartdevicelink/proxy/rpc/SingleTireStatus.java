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
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.proxy.rpc.enums.TPMS;

import java.util.Hashtable;

/**
 * Tire pressure status of a single tire.
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>status</td>
 * 			<td>ComponentVolumeStatus</td>
 * 			<td>Describes the volume status of a single tire
 * 					See {@linkplain ComponentVolumeStatus}
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>tpms</td>
 * 			<td>TPMS</td>
 * 			<td>The status of TPMS according to the particular tire.
 * 					See {@linkplain com.smartdevicelink.proxy.rpc.enums.TPMS}
 * 			</td>
 * 			<td>SmartDeviceLink 5.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>pressure</td>
 * 			<td>Float</td>
 * 			<td>The pressure value of the particular tire in kilo pascal.</td>
 * 			<td>SmartDeviceLink 5.0</td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 2.0
 */
public class SingleTireStatus extends RPCStruct {
	public static final String KEY_STATUS = "status";
	public static final String KEY_TPMS = "tpms";
	public static final String KEY_PRESSURE = "pressure";

	/**
	 * Constructs a newly allocated SingleTireStatus object
	 */
    public SingleTireStatus() { }
    
    /**
     * Constructs a newly allocated SingleTireStatus object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public SingleTireStatus(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a newly allocated SingleTireStatus object
	 * @param status Describes the volume status of a single tire
	 */
	public SingleTireStatus(@NonNull ComponentVolumeStatus status){
		this();
		setStatus(status);
	}
    
    /**
     * set the volume status of a single tire
     * @param status the volume status of a single tire
     */
    public void setStatus(@NonNull ComponentVolumeStatus status) {
    	setValue(KEY_STATUS, status);
    }
    
    /**
     * get the volume status of a single tire
     * @return the volume status of a single tire
     */
    public ComponentVolumeStatus getStatus() {
        return (ComponentVolumeStatus) getObject(ComponentVolumeStatus.class, KEY_STATUS);
    }

	/**
	 * Set the status of TPMS according to the particular tire.
	 * @param tpms The status of TPMS
	 */
	public void setTPMS(@NonNull TPMS tpms) { setValue(KEY_TPMS, tpms); }

	/**
	 * Get the status of TPMS according to the particular tire.
	 * @return the TPMS status
	 */
	public TPMS getTPMS() {
		return (TPMS) getObject(TPMS.class, KEY_TPMS);
	}

	/**
	 * @param pressure The pressure value of the particular tire in kilo pascal.
	 */
	public void setPressure(@NonNull Float pressure) { setValue(KEY_PRESSURE, pressure); }

	/**
	 * @return the pressure value of the particular tire in kilo pascal.
	 */
	public Float getPressure() {
		return getFloat(KEY_PRESSURE);
	}
}
