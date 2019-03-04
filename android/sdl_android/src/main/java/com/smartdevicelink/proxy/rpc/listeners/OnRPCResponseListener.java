package com.smartdevicelink.proxy.rpc.listeners;

import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

public abstract class OnRPCResponseListener extends OnRPCListener {


	public OnRPCResponseListener(){
		setListenerType(OnRPCListener.UPDATE_LISTENER_TYPE_BASE_RPC);
	}

	public final void onReceived(final RPCMessage message){
		if (message != null && message instanceof RPCResponse){
			onResponse(((RPCResponse) message).getCorrelationID(), (RPCResponse)message);
		}
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
