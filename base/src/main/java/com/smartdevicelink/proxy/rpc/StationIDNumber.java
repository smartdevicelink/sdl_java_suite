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

public class StationIDNumber extends RPCStruct {
	public static final String KEY_COUNTRY_CODE = "countryCode";
	public static final String KEY_FCC_FACILITY_ID = "fccFacilityId";

	public StationIDNumber() {
	}

	public StationIDNumber(Hashtable<String, Object> hash) {
		super(hash);
	}

	/**
	 * Sets the countryCode portion of the StationIDNumber class
	 *
	 * @param countryCode Binary Representation of ITU Country Code. USA Code is 001.
	 */
	public void setCountryCode(Integer countryCode) {
		setValue(KEY_COUNTRY_CODE, countryCode);
	}

	/**
	 * Gets the countryCode portion of the StationIDNumber class
	 *
	 * @return Integer - Binary Representation of ITU Country Code. USA Code is 001.
	 */
	public Integer getCountryCode() {
		return getInteger(KEY_COUNTRY_CODE);
	}

	/**
	 * Sets the fccFacilityId portion of the StationIDNumber class
	 *
	 * @param fccFacilityId Binary representation  of unique facility ID assigned by the FCC; FCC controlled for U.S. territory.
	 */
	public void setFccFacilityId(Integer fccFacilityId) {
		setValue(KEY_FCC_FACILITY_ID, fccFacilityId);
	}

	/**
	 * Gets the fccFacilityId portion of the StationIDNumber class
	 *
	 * @return Integer - Binary representation  of unique facility ID assigned by the FCC; FCC controlled for U.S. territory.
	 */
	public Integer getFccFacilityId() {
		return getInteger(KEY_FCC_FACILITY_ID);
	}

}
