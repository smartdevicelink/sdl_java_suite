/*
 * Copyright (c) 2019 Livio, Inc.
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
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
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

import java.util.Hashtable;
import java.util.List;


public class WeatherAlert extends RPCStruct {

	public static final String KEY_TITLE = "title";
	public static final String KEY_SUMMARY = "summary";
	public static final String KEY_EXPIRES = "expires";
	public static final String KEY_REGIONS = "regions";
	public static final String KEY_SEVERITY = "severity";
	public static final String KEY_TIME_ISSUED = "timeIssued";

	// Constructors

	public WeatherAlert() { }

	public WeatherAlert(Hashtable<String, Object> hash) {
		super(hash);
	}

	public WeatherAlert(@NonNull List<String> regions) {
		this();
		setRegions(regions);
	}

	// Setters and Getters

	/**
	 * @param title -
	 */
	public void setTitle(String title) {
		setValue(KEY_TITLE, title);
	}

	/**
	 * @return title
	 */
	public String getTitle() {
		return getString(KEY_TITLE);
	}

	/**
	 * @param summary -
	 */
	public void setSummary(String summary) {
		setValue(KEY_SUMMARY, summary);
	}

	/**
	 * @return summary
	 */
	public String getSummary() {
		return getString(KEY_SUMMARY);
	}

	/**
	 * @param severity -
	 */
	public void setSeverity(String severity) {
		setValue(KEY_SEVERITY, severity);
	}

	/**
	 * @return severity
	 */
	public String getSeverity() {
		return getString(KEY_SEVERITY);
	}

	/**
	 * Min Size: 1, Max Size: 99
	 * @param regions -
	 */
	public void setRegions(@NonNull List<String> regions) {
		setValue(KEY_REGIONS, regions);
	}

	/**
	 * @return regions list
	 */
	@SuppressWarnings("unchecked")
	public List<String> getRegions(){
		return (List<String>) getObject(String.class,KEY_REGIONS);
	}

	/**
	 * @param expires -
	 */
	public void setExpires(DateTime expires) {
		setValue(KEY_EXPIRES, expires);
	}

	/**
	 * @return expires
	 */
	public DateTime getExpires() {
		return (DateTime) getObject(DateTime.class,KEY_EXPIRES);
	}

	/**
	 * @param timeIssued -
	 */
	public void setTimeIssued(DateTime timeIssued) {
		setValue(KEY_TIME_ISSUED, timeIssued);
	}

	/**
	 * @return timeIssued
	 */
	public DateTime getTimeIssued() {
		return (DateTime) getObject(DateTime.class,KEY_TIME_ISSUED);
	}

}
