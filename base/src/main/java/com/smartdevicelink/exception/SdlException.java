package com.smartdevicelink.exception;


public class SdlException extends Exception {
	
	private static final long serialVersionUID = 5922492291870772815L;
	
	protected Throwable detail = null;
	private SdlExceptionCause _sdlExceptionCause = null;
	
	public SdlException(String msg, SdlExceptionCause exceptionCause) {
		super(msg);
		_sdlExceptionCause = exceptionCause;
	}
	
	public SdlException(String msg, Throwable ex, SdlExceptionCause exceptionCause) {
		super(msg + " --- Check inner exception for diagnostic details");
		detail = ex;
		_sdlExceptionCause = exceptionCause;
	}
	
	public SdlException(Throwable ex) {
		super(ex.getMessage());
		detail = ex;
	}
	
	public SdlExceptionCause getSdlExceptionCause() {
		return _sdlExceptionCause;
	}
	
	public Throwable getInnerException() {
		return detail;
	}
	
	public String toString() {
		String ret = this.getClass().getName();
		ret += ": " + this.getMessage();
		if(this.getSdlExceptionCause() != null){
			ret += "\nSdlExceptionCause: " + this.getSdlExceptionCause().name();
		}
		if (detail != null) {
			ret += "\nnested: " + detail.toString();
			detail.printStackTrace();
		}
		return ret;
	}
}
