package com.smartdevicelink.proxy.rpc.listeners;

import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

public abstract class OnRPCResponseListener {
	/**
	 * Generic listener type that will work for most RPCs
	 */
	public final static int UPDATE_LISTENER_TYPE_BASE_RPC 		= 0;
	/**
	 * Listener type specific to putfile
	 */
	public final static int UPDATE_LISTENER_TYPE_PUT_FILE 		= 1;

	/**
	 * Stores what type of listener this instance is. This prevents of from having to use reflection
	 */
	int listenerType;
	
	/**
	 * This is the base listener for all RPCs.
	 */
	public OnRPCResponseListener(){
		setListenerType(UPDATE_LISTENER_TYPE_BASE_RPC);
	}
	
	protected final void setListenerType(int type){
		this.listenerType = type;
	}
	/**
	 * This is used to see what type of listener this instance is. It is needed
	 * because some RPCs require additional callbacks. Types are  constants located in this class
	 * @return the type of listener this is 
	 */
	public int getListenerType(){
		return this.listenerType;
	}
	
	/* *****************************************************************
	 ************************* Event Callbacks *************************
	 *******************************************************************/
	
	/**
	 * This method will be called once the packet has been entered into the queue of messages to send
	 * @param correlationId
	 */
	public void onStart(int correlationId){
		
	};

	/**
	 * This is the only method that must be extended. Other methods that are able to be extended are 
	 * onStart and onError.
	 * @param correlationId
	 * @param response This will be the response message received from the core side. It should be cast into a corresponding RPC Response type. ie, if setting this
	 * for a PutFile request, the message parameter should be cast to a PutFileResponse class.
	 */
	public abstract void onResponse(int correlationId, final RPCResponse response);
	
	/**
	 * Called when there was some sort of error during the original request.
	 * @param correlationId
	 * @param resultCode
	 * @param info
	 */
	public void onError(int correlationId, Result resultCode, String info){
		
	};
	

	
}
