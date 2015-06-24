package com.smartdevicelink.exception;


public class SdlException extends Exception {
	
	/**
	 * A serializable class can declare its own serialVersionUID explicitly by
	 * declaring a field named "serialVersionUID" that must be static, final, 
	 * and of type long.
	 * 
	 * @see java.io.Serializable
	 */
	private static final long serialVersionUID = 5922492291870772815L;
	
	/**
	 * The diagnostic details of an exception or error thrown by the 
	 * application.
	 */
	protected Throwable details = null;
	
	/**
	 * The reason for this specific SDL related exception.
	 */
	protected SdlExceptionCause cause = null;
	
	/**
	 * Constructor for an SDL specific exception.
	 * 
	 * @param details The diagnostic details of the exception.
	 */
	public SdlException (Throwable details) {
		super(details.getMessage());
		this.details = details;
	}
	
	/**
	 * Constructor for an SDL specific exception.
	 * 
	 * @param message The information to be stored.
	 * @param cause The reason for the specific SDL related exception.
	 */
	public SdlException (String message, SdlExceptionCause cause) {
		super(message);
		this.cause = cause;
	}
	
	/**
	 * Constructor for an SDL specific exception.
	 * 
	 * @param message The information to be stored.
	 * @param details The diagnostic details of the exception.
	 * @param cause The reason for the specific SDL related exception.
	 */
	public SdlException (String message, Throwable details, SdlExceptionCause cause) {
		super(message + " --- Check inner exception for diagnostic details.");
		this.details = details;
		this.cause = cause;
	}
	
	/**
	 * Provides the cause title of this specific SDL related exception.
	 * 
	 * @return The enum associated with the specific SDL related exception.
	 */
	public SdlExceptionCause getSdlExceptionCause () {
		return cause;
	}
	
	/**
	 * Provides the diagnostic details of this specific SDL related exception.
	 * 
	 * @return The throwable containing diagnostic details of the exception.
	 */
	public Throwable getInnerException () {
		return details;
	}
	
	/**
	 * Provides a formatted string containing detailed exception information.
	 */
	public String toString() {
		StringBuilder build = new StringBuilder("SdlException: ");
		build.append(getMessage());
		
		if(getSdlExceptionCause() != null){
			build.append("\nSdlExceptionCause: ");
			build.append(getSdlExceptionCause().name());
		}
		
		if (details != null) {
			build.append("\nDetails: ");
			build.append(details.toString());
		}
		
		return build.toString();
	}
}