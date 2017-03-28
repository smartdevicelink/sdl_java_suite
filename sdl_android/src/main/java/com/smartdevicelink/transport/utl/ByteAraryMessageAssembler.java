package com.smartdevicelink.transport.utl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.util.Log;

import com.smartdevicelink.transport.TransportConstants;

public class ByteAraryMessageAssembler {
	private static final String TAG = "ByteAraryMessageAssembler";
	ByteArrayOutputStream buffer;
	boolean isFinished;
	
	public void init(){
		close();
		this.isFinished = false;
		buffer = new ByteArrayOutputStream();
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
				Log.e(TAG, "Error handling message");
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
