package com.smartdevicelink.abstraction.exception;

public class FunctionNotInitializedException extends RuntimeException {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 2810184569708556975L;

	@Override
	public String getLocalizedMessage() {
		return "Function Not Initiaized.  Should be done when Function Loads.";
	}
	
	@Override
	public String getMessage() {
		return "Function Not Initiaized.  Should be done when Function Loads.";
	}

}
