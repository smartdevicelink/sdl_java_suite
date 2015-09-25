package com.smartdevicelink.proxy.rpc.listeners;

import com.smartdevicelink.proxy.RPCMessage;

public abstract class OnPutFileUpdateListener extends OnRPCUpdateListener{

	long totalSize = 0;

	public OnPutFileUpdateListener(){
		setListenerType(UPDATE_LISTENER_TYPE_PUT_FILE);
	}
	
	@Override
	public final void onStart(int correlationId) {
		super.onStart(correlationId);
		onStart(correlationId, totalSize); //We do this so we can send back the total size
	}

	public void onStart(int correlationId, long totalSize){
		
	}
	
	@Override
	public final void onFinish(int correlationId, RPCMessage message) {
		onFinish(correlationId, message, totalSize); //Calling our special abstract method
	}
	/**
	 * Called when the putfile request is responded to.
	 * @param correlationId
	 * @param message
	 * @param totalSize
	 */
	public abstract void onFinish(int correlationId, RPCMessage message, long totalSize);
	
	/**
	 * onUpdate is called during a putfile stream request
	 * @param correlationId of the original request
	 * @param bytesWritten
	 * @param totalSize
	 */
	public void onUpdate(int correlationId, long bytesWritten, long totalSize){
		
	};
	
	public final void setTotalSize(long totalSize){
		this.totalSize = totalSize;
	}
}
