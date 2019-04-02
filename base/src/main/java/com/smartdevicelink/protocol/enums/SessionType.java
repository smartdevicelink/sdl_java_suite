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


public class SessionType extends ByteEnumer {

	private static Vector<SessionType> theList = new Vector<SessionType>();
	public static Vector<SessionType> getList() { return theList; } 
	
	byte i = 0x00;
	
	protected SessionType(byte value, String name) {super(value, name);}
	public final static SessionType CONTROL = new SessionType((byte) 0, "CONTROL");
	public final static SessionType RPC = new SessionType((byte)0x07, "RPC");
	public final static SessionType PCM = new SessionType((byte)0x0A, "PCM");
	public final static SessionType NAV = new SessionType((byte)0x0B, "NAV");
	public final static SessionType BULK_DATA = new SessionType((byte)0xF, "BULK_DATA");

	static {
		theList.addElement(RPC);
		theList.addElement(PCM);
		theList.addElement(NAV);
		theList.addElement(BULK_DATA);
		theList.addElement(CONTROL);
	}
	
	public static SessionType valueOf(byte passedButton) {
		return (SessionType) get(theList, passedButton);
	}
	
	public static SessionType[] values() {
		return theList.toArray(new SessionType[theList.size()]);
	}
}
