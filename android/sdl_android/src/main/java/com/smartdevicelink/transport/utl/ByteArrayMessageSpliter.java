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

import android.os.Bundle;
import android.os.Message;

import com.smartdevicelink.transport.TransportBroker;
import com.smartdevicelink.transport.TransportConstants;
import com.smartdevicelink.util.DebugTool;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class ByteArrayMessageSpliter {
	private static final String TAG = "ByteArrayMessageSpliter";
	
	//To test set this value to something very small (eg, 50)
	public static final int MAX_BINDER_SIZE = 1000000/4; //~1MB/4 We do this as a safety measure. IPC only allows 1MB for everything. We should never fill more than 25% of the buffer so here we make sure we stay under that

	boolean firstPacket;
	ByteArrayInputStream stream;
	int bytesRead; 
	int what;
	String appId;
	byte[] buffer;
	int orginalSize;
	int priorityCoef;
	int routerServiceVersion = 1;
	TransportRecord transportRecord;
	
	public ByteArrayMessageSpliter(String appId,int what, byte[] bytes, int priorityCoef){
		this.appId = appId;
		this.what = what;
		stream = new ByteArrayInputStream(bytes);
		orginalSize  = stream.available();
		bytesRead = 0; 
		firstPacket = true;
		this.priorityCoef = priorityCoef;
	}
	
	@Deprecated
	public ByteArrayMessageSpliter(Long appId,int what, byte[] bytes, int priorityCoef){
		this.appId = appId+"";
		this.what = what;
		stream = new ByteArrayInputStream(bytes);
		orginalSize  = stream.available();
		bytesRead = 0; 
		firstPacket = true;
		this.priorityCoef = priorityCoef;
	}
	
	public void setRouterServiceVersion(int version){
		this.routerServiceVersion = version;
	}
	
	public void setTransportRecord(TransportRecord transportRecord){
		this.transportRecord = transportRecord;
	}

	public boolean isActive(){
		if(stream!=null){
			return stream.available()>0;
		}
		return false;
	}
	
	public boolean close(){
		if(stream == null){
			return false;
		}
		try {
			stream.close();
			stream = null;
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public Message nextMessage(){
		if(stream == null || stream.available()<=0){
			return null;
		}
		
		Message message = Message.obtain(); //Do we need to always obtain new? or can we just swap bundles?
		message.what = this.what;// TransportConstants.ROUTER_SEND_PACKET;
		Bundle bundle = new Bundle();

		
		if(stream.available()>=MAX_BINDER_SIZE){
			buffer = new byte[MAX_BINDER_SIZE];
			bytesRead = stream.read(buffer, 0, MAX_BINDER_SIZE);
		}else{
			buffer = new byte[stream.available()];
			bytesRead = stream.read(buffer, 0, stream.available());
		}
		
		bundle.putByteArray(TransportConstants.BYTES_TO_SEND_EXTRA_NAME, buffer); //Do we just change this to the args and objs
		bundle.putInt(TransportConstants.BYTES_TO_SEND_EXTRA_OFFSET, 0);
		bundle.putInt(TransportConstants.BYTES_TO_SEND_EXTRA_COUNT, bytesRead);
		if(transportRecord != null){
			bundle.putString(TransportConstants.TRANSPORT_TYPE, transportRecord.getType().name());
			bundle.putString(TransportConstants.TRANSPORT_ADDRESS, transportRecord.getAddress());
		}

		//Determine which flag should be sent for this division of the packet
		if(firstPacket){
			bundle.putInt(TransportConstants.BYTES_TO_SEND_FLAGS, TransportConstants.BYTES_TO_SEND_FLAG_LARGE_PACKET_START);
			bundle.putInt(TransportConstants.PACKET_PRIORITY_COEFFICIENT, this.priorityCoef);
			firstPacket = false;
		}else if(stream.available()<=0){ //We are at the end of the stream so let the flag reflect that
			bundle.putInt(TransportConstants.BYTES_TO_SEND_FLAGS, TransportConstants.BYTES_TO_SEND_FLAG_LARGE_PACKET_END);
		}else{
			bundle.putInt(TransportConstants.BYTES_TO_SEND_FLAGS, TransportConstants.BYTES_TO_SEND_FLAG_LARGE_PACKET_CONT);
		}
		
		if(routerServiceVersion< TransportConstants.RouterServiceVersions.APPID_STRING){
			bundle.putLong(TransportConstants.APP_ID_EXTRA,TransportBroker.convertAppId(appId));
		}
		bundle.putString(TransportConstants.APP_ID_EXTRA_STRING, appId);
		message.setData(bundle);
		DebugTool.logInfo(TAG, ((100 - ((stream.available()*100)/orginalSize) ))+ " percent complete.");
		return message;
	}
}
