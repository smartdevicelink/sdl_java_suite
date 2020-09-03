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
 * Dials a phone number and switches to phone application.
 *
 * @since SmartDeviceLink 4.0
 */
public class DialNumber extends RPCRequest {
	public static final String KEY_NUMBER = "number";


	public DialNumber(){
        super(FunctionID.DIAL_NUMBER.toString());
	}
	
	public DialNumber(Hashtable<String, Object> hash) {
		super(hash);
	}

	public DialNumber(@NonNull String number){
		this();
		setNumber(number);
	}
	/**
	 * Sets a number to dial
	 * 
	 * @param number
	 *             a phone number is a string, which can be up to 40 chars.
	 *            <p>
	 *            <b>Notes: </b>Maxlength=40</p>
	 *             All characters shall be stripped from string except digits 0-9 and * # , ; +
	 */
    public DialNumber setNumber(@NonNull String number) {
        if (number != null) {
        	number = number.replaceAll("[^0-9*#,;+]", ""); //This will sanitize the input
        }
		setParameters(KEY_NUMBER, number);
        return this;
    }

	/**
	 * Gets a number to dial
	 * 
	 * @return String - a String value representing a number to dial
	 */
    public String getNumber() {
        return getString(KEY_NUMBER);
    }
}
