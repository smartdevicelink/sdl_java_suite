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

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/**
 * String containing hexadecimal identifier as well as other common names.
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>statusByte</td>
 * 			<td>String</td>
 * 			<td>Hexadecimal byte string
 * 				 <ul>
 * 					<li>Maxlength = 500</li>
 * 				 </ul>
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 *
 * @since SmartDeviceLink 2.0
 */
public class DTC extends RPCStruct {
    public static final String KEY_IDENTIFIER = "identifier";
    public static final String KEY_STATUS_BYTE = "statusByte";

    /**
     * Constructs a newly allocated DTC object
     */
    public DTC() {
    }

    /**
     * Constructs a newly allocated DTC object indicated by the Hashtable parameter
     *
     * @param hash The Hashtable to use
     */
    public DTC(Hashtable<String, Object> hash) {
        super(hash);
    }

    /**
     * set identifier
     *
     * @param identifier the hexadecimal id of the DTC
     */
    public DTC setIdentifier(String identifier) {
        setValue(KEY_IDENTIFIER, identifier);
        return this;
    }

    /**
     * get identifier
     *
     * @return identifier
     */
    public String getIdentifier() {
        return getString(KEY_IDENTIFIER);
    }

    /**
     * set Hexadecimal byte string
     *
     * @param statusByte Hexadecimal byte string
     */
    public DTC setStatusByte(String statusByte) {
        setValue(KEY_STATUS_BYTE, statusByte);
        return this;
    }

    /**
     * get Hexadecimal byte string
     *
     * @return Hexadecimal byte string
     */
    public String getStatusByte() {
        return getString(KEY_STATUS_BYTE);
    }
}
