package com.smartdevicelink.transport.utl;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import com.smartdevicelink.transport.TransportConstants;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

public class ByteArrayMessageSpliter {
	private static final String TAG = "ByteArrayMessageSpliter";
	
	//To test set this value to something very small (eg, 50)
	public static final int MAX_BINDER_SIZE = 1000000/4; //~1MB/4 We do this as a safety measure. IPC only allows 1MB for everything. We should never fill more than 25% of the buffer so here we make sure we stay under that

	boolean firstPacket;
	ByteArrayInputStream stream;
	int bytesRead; 
	int what;
	Long appId;
	byte[] buffer;
	int orginalSize;
	int priorityCoef;
	
	public ByteArrayMessageSpliter(String appId,int what, byte[] bytes, int priorityCoef){
		this.appId = Long.valueOf(appId);
		this.what = what;
		stream = new ByteArrayInputStream(bytes);
		orginalSize  = stream.available();
		bytesRead = 0; 
		firstPacket = true;
		this.priorityCoef = priorityCoef;
	}
	
	public ByteArrayMessageSpliter(Long appId,int what, byte[] bytes, int priorityCoef){
		this.appId = appId;
		this.what = what;
		stream = new ByteArrayInputStream(bytes);
		orginalSize  = stream.available();
		bytesRead = 0; 
		firstPacket = true;
		this.priorityCoef = priorityCoef;
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
		
		bundle.putLong(TransportConstants.APP_ID_EXTRA, appId);
		message.setData(bundle);
		Log.i(TAG, ((100 - ((stream.available()*100)/orginalSize) ))+ " percent complete.");
		return message;
	}
}
