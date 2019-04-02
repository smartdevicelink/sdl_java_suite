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

public class FrameType extends ByteEnumer {

	private static Vector<FrameType> theList = new Vector<FrameType>();
	public static Vector<FrameType> getList() { return theList; } 
	
	byte i = 0x00;
	
	protected FrameType(byte value, String name) {
		super(value, name);
	}
	
	public final static FrameType Control = new FrameType((byte)0x00, "Control");
	public final static FrameType Single = new FrameType((byte)0x01, "Single");
	public final static FrameType First = new FrameType((byte)0x02, "First");
	public final static FrameType Consecutive = new FrameType((byte)0x03, "Consecutive");
	
	static {
		theList.addElement(Control);
		theList.addElement(Single);
		theList.addElement(First);
		theList.addElement(Consecutive);
	}
	
	public static FrameType valueOf(byte passed) {
		return (FrameType) get(theList, passed);
	}
	
	public static FrameType[] values() {
		return theList.toArray(new FrameType[theList.size()]);
	}
}
