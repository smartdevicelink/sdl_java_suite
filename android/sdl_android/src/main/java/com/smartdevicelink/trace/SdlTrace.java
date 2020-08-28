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
package com.smartdevicelink.trace;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.os.Build;
import android.os.Debug;

import java.sql.Timestamp;

/* This class handles the global TraceSettings as requested by the users either through the combination of the following
   1. System defaults
   2. Application XML config
   3. Programmatic requests from application itself

   It is manifested in the <SmartDeviceLink>...</SmartDeviceLink> tags
 */

@SuppressLint("DefaultLocale")
public class SdlTrace extends SdlTraceBase {
	private static String getPid(){
		return String.valueOf(android.os.Process.myPid());
	}

	@SuppressLint("MissingPermission")
	public static String getBTDeviceInfo(BluetoothDevice btDevice) {
		StringBuilder sb = new StringBuilder();
		sb.append("<btp>");
		String btdn = btDevice.getName();
		sb.append("<btn>");
		sb.append(SdlTrace.B64EncodeForXML(btdn));
		sb.append("</btn>");
		sb.append("<bta>").append(btDevice.getAddress()).append("</bta>");
		sb.append("<bts>").append(btDevice.getBondState()).append("</bts>");
		sb.append("</btp>");
		return sb.toString();
	} // end-method

	// Package-scoped
	@SuppressWarnings("deprecation")
	public static String getLogHeader(String dumpReason, int seqNo) {
		final String Sep = "-";
		StringBuilder write = new StringBuilder("<?xml version=\"1.0\"?>" + "<logs>");
		write.append("<info>");
		StringBuilder infoBlock = new StringBuilder();
		String hostInfo = Build.BRAND + Sep + Build.MANUFACTURER + Sep + Build.MODEL + "(" + Build.HOST + ")";
		infoBlock.append("<host>").append(SdlTrace.B64EncodeForXML(hostInfo)).append("</host>");
		String osv = Build.VERSION.RELEASE + " (" + Build.VERSION.CODENAME + ")";
		infoBlock.append("<osv>").append(SdlTrace.B64EncodeForXML(osv)).append("</osv>");
		infoBlock.append(TraceDeviceInfo.getTelephonyHeader());

		long heapSize = Debug.getNativeHeapFreeSize() / 1024;
		long heapAllocated = Debug.getNativeHeapAllocatedSize() / 1024;
		infoBlock.append("<mem><hf>").append(heapSize).append("KB</hf><ha>").append(heapAllocated).append("KB</ha></mem>");
		infoBlock.append("<np>").append(Runtime.getRuntime().availableProcessors()).append("</np>");
		infoBlock.append("<pid>").append(getPid()).append("</pid>");
		infoBlock.append("<tid>").append(Thread.currentThread().getId()).append("</tid>");

		// String dateStamp = (String)
		// DateFormat.format("yy-MM-dd hh:mm:ss SSS", new Timestamp(baseTics));
		Timestamp stamp = new Timestamp(SdlTrace.getBaseTics());
		String GMTtime = stamp.toGMTString().substring(0, 19);
		long fracSec = stamp.getNanos() / 1000000; // divide by a million
		String fracSecStr = String.format("%03d", fracSec);
		infoBlock.append("<utc>").append(GMTtime).append(".").append(fracSecStr).append("</utc>");

		infoBlock.append(TraceDeviceInfo.getLogHeaderBluetoothPairs());
		infoBlock.append(getSmartDeviceLinkTraceRoot(dumpReason, seqNo));

		write.append(infoBlock);

		write.append("</info>" + "<msgs>");
		return write.toString();
	} // end-method

} // end-class