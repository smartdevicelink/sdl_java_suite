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
/**
 * 
 */
package com.smartdevicelink.trace;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.telephony.TelephonyManager;

import com.smartdevicelink.util.DebugTool;

import java.util.Iterator;
import java.util.Set;

/**
 * @author vvolkman
 * 
 */
public class TraceDeviceInfo {
	// http://developer.android.com/guide/topics/data/data-storage.html

	private static TelephonyManager m_telephonyManager;
	
	// Constructor
	public TraceDeviceInfo(TelephonyManager telephonyManager) {
		m_telephonyManager = telephonyManager;
	}

	public static void setTelephonyManager(TelephonyManager telephonyManager) {
		m_telephonyManager = telephonyManager;
	}

	public static TelephonyManager getTelephonyManager() {
		return m_telephonyManager;
	}

	// package scoped
	static String getTelephonyHeader() {
		// Telephony manager can tell us a few things...
		String info = "";

		if (m_telephonyManager != null) {
			try { // getDeviceId() requires android.permission.READ_PHONE_STATE
				info = "<deviceid>" + m_telephonyManager.getDeviceId() + "</deviceid>";
			} catch (Exception e1) {
				DebugTool.logError(null, "Failure getting telephony device ID: " + e1.toString(), e1);
			}
	
			info = "<pt>";
			switch (m_telephonyManager.getPhoneType()) {
				case TelephonyManager.PHONE_TYPE_NONE:
					info += "NONE";
					break;
				case TelephonyManager.PHONE_TYPE_GSM:
					info += "GSM";
					break;
				case TelephonyManager.PHONE_TYPE_CDMA:
					info += "CDMA";
					break;
				default:
					info += "UNKNOWN";
			} // end-switch
	
			info += "</pt>" + "<nt>";
	
			switch (m_telephonyManager.getNetworkType()) {
				case TelephonyManager.NETWORK_TYPE_UNKNOWN:
					info += "UKNOWN";
					break;
				case TelephonyManager.NETWORK_TYPE_GPRS:
					info += "GPRS";
					break;
				case TelephonyManager.NETWORK_TYPE_EDGE:
					info += "EDGE";
					break;
				case TelephonyManager.NETWORK_TYPE_UMTS:
					info += "UMTS";
					break;
				case TelephonyManager.NETWORK_TYPE_HSDPA:
					info += "HSDPA";
					break;
				case TelephonyManager.NETWORK_TYPE_HSUPA:
					info += "HSUPA";
					break;
				case TelephonyManager.NETWORK_TYPE_HSPA:
					info += "HSPA";
					break;
				case TelephonyManager.NETWORK_TYPE_CDMA:
					info += "CDMA";
					break;
				case TelephonyManager.NETWORK_TYPE_EVDO_0:
					info += "EVDO_O";
					break;
				case TelephonyManager.NETWORK_TYPE_EVDO_A:
					info += "EVDO_A";
					break;
				case TelephonyManager.NETWORK_TYPE_1xRTT:
					info += "1xRTT";
					break;
				default:
					info += "UNKNOWN";
					break;
			} // end-switch
	
			info += "</nt>";
		} // end-if
		return info;
	} // end-method

	// Package scoped
	static String getLogHeaderBluetoothPairs() {
		Set<BluetoothDevice> btDevices = BluetoothAdapter.getDefaultAdapter()
				.getBondedDevices();

		StringBuilder write = new StringBuilder("<btpairs>");
		Iterator<BluetoothDevice> iter = btDevices.iterator();
		while (iter.hasNext()) {
			write.append(SdlTrace.getBTDeviceInfo(iter.next()));
		}
		write.append("</btpairs>");

		return write.toString();
	} // end-method
} // end-class