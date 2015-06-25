package com.smartdevicelink.proxy.rpc.listeners;

import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.enums.Result;

public abstract class OnRPCUpdateListener {
	public final static int UPDATE_LISTENER_TYPE_BASE_RPC 		= 0;
	public final static int UPDATE_LISTENER_TYPE_PUT_FILE 		= 1;

	int listenerType;
	
	/**
	 * This is the base listener for all RPCs.
	 */
	public OnRPCUpdateListener(){
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
	 * onStart, onUpdated, and onError.
	 * @param correlationId
	 * @param message This will be the response message received from the core side. It should be cast into a corresponding RPC Response type. ie, if setting this
	 * for a PutFile request, the message parameter should be cast to a PutFileResponse class.
	 */
	public abstract void onFinish(int correlationId, final RPCMessage message);
	
	public void onError(int correlationId, Result resultCode, String info){
		
	};
	

	
}
