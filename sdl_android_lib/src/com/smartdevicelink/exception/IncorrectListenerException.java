package com.smartdevicelink.exception;

import com.smartdevicelink.abstraction.listener.NotificationListener;
import com.smartdevicelink.proxy.RPCRequest;

public class IncorrectListenerException extends RuntimeException {
	
	private static final long serialVersionUID = -5586325409713642860L;
	
	private RPCRequest request;
	private NotificationListener listener;
	
	public IncorrectListenerException(RPCRequest rpc, NotificationListener listener) {
		request = rpc;
		this.listener = listener;
	}
	
	@Override
	public String getLocalizedMessage() {
		if(listener == null) return "Null Listener";
		if(request == null) return "Null Listener";
		
		return "Wrong listener ("+listener.getClass().getName()+") called with that Notification "+request.getClass().getName()+").";
	}
	
	@Override
	public String getMessage() {
		if(listener == null) return "Null Listener";
		if(request == null) return "Null Listener";
		return "Wrong listener ("+listener.getClass().getName()+") called with that Notification "+request.getClass().getName()+").";
	}

}
