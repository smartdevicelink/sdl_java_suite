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
package com.smartdevicelink.protocol.enums;

import com.smartdevicelink.util.ByteEnumer;

import java.util.Vector;

public class FrameData extends ByteEnumer {

	private static Vector<FrameData> theList = new Vector<FrameData>();
	public static Vector<FrameData> getList() { return theList; } 
	
	byte i = 0x00;
	
	protected FrameData(byte value, String name) {
		super(value, name);
	}
	
	public final static FrameData StartSession = new FrameData((byte)0x01, "StartSession");
	public final static FrameData StartSessionACK = new FrameData((byte)0x02, "StartSessionACK");
	public final static FrameData StartSessionNACK = new FrameData((byte)0x03, "StartSessionNACK");
	public final static FrameData EndSession = new FrameData((byte)0x04, "EndSession");
	
	public final static FrameData SingleFrame = new FrameData((byte)0x00, "SingleFrame");
	public final static FrameData FirstFrame = new FrameData((byte)0x00, "FirstFrame");
	public final static FrameData ConsecutiveFrame = new FrameData((byte)0x00, "ConsecutiveFrame");
	public final static byte LastFrame = (byte)0x00;
	
	static {
		theList.addElement(StartSession);
		theList.addElement(StartSessionACK);
		theList.addElement(StartSessionNACK);
		theList.addElement(EndSession);	
	}
	
	public static FrameData valueOf(String passedButton) {
		return (FrameData) get(theList, passedButton);
	}
	
	public static FrameData[] values() {
		return theList.toArray(new FrameData[theList.size()]);
	}
}
