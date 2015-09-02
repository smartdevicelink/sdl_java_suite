package com.smartdevicelink.abstraction.exception;

public class WrongThreadException extends RuntimeException {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 3714448138639061480L;

	@Override
	public String getLocalizedMessage() {
		return "Application functions cannot be called on the main thread";
	}
	
	@Override
	public String getMessage() {
		return "Application functions cannot be called on the main thread";
	}

}
