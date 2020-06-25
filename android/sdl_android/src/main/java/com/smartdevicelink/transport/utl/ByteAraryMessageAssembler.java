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
package com.smartdevicelink.transport.utl;

import com.smartdevicelink.transport.TransportConstants;
import com.smartdevicelink.transport.enums.TransportType;
import com.smartdevicelink.util.DebugTool;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ByteAraryMessageAssembler {
	private static final String TAG = "ByteAraryMsgAssembler";
	ByteArrayOutputStream buffer;
	boolean isFinished;
	TransportType transportType;


	public void init(){
		close();
		this.isFinished = false;
		buffer = new ByteArrayOutputStream();
	}

	public void setTransportType(TransportType transportType){
		this.transportType = transportType;
	}

	public TransportType getTransportType() {
		return transportType;
	}

	public boolean close(){
		if(buffer!=null){
			try {
				buffer.close();
				buffer = null;
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}
	
	public void append(byte[] bytes){
		try {
			buffer.write(bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized boolean handleMessage(int flags, byte[] packet){
			switch(flags){
			case TransportConstants.BYTES_TO_SEND_FLAG_LARGE_PACKET_START: //Fall through to write the bytes after they buffer was init'ed
			case TransportConstants.BYTES_TO_SEND_FLAG_LARGE_PACKET_CONT:	
				append(packet);
				break;
			case TransportConstants.BYTES_TO_SEND_FLAG_LARGE_PACKET_END:
				append(packet);
				this.isFinished = true;
				break;
			default:
				DebugTool.logError("Error handling message");
				return false;
			}
			
			return true;
	}

	public byte[] getBytes(){
		if(buffer == null){
			return null;
		}
		return this.buffer.toByteArray();
	}
	
	public boolean isFinished(){
		return this.isFinished;
	}
}
