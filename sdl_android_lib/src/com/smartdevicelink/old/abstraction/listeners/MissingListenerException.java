package com.smartdevicelink.old.abstraction.listeners;

import com.smartdevicelink.proxy.RPCRequest;

public class MissingListenerException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9046907383014253528L;
	
	private RPCRequest mRequest;
	
	public MissingListenerException(RPCRequest request) {
		mRequest = request;
	}
	
	@Override
	public String getLocalizedMessage() {
		return "Your "+mRequest.getClass().getName()+" needs a listener.  You have to include a listener inside the RPC using the <RPC>WithListener class";
	}
	
	@Override
	public String getMessage() {
		return "Your "+mRequest.getClass().getName()+" needs a listener.  You have to include a listener inside the RPC using the <RPC>WithListener class";
	}

	
}
