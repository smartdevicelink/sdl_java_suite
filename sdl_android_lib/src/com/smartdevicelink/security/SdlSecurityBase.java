package com.smartdevicelink.security;

public abstract class SdlSecurityBase {
	
	private Byte sessionId = null;
	private String appId = null;
	private String make = null;
	private boolean isInitSuccess = false;
	
    public SdlSecurityBase() {
	}
	
    public void setInitSuccess(boolean val) {
    	isInitSuccess = val;
    }
	
    public boolean getInitSuccess() {
    	return isInitSuccess;
    }
	    
    public Byte getSessionId() {
    	return sessionId;
    }

    public void setSessionId(Byte val) {
    	sessionId = val;
    }
    
    public String getAppId() {
    	return appId;
    }
    
    public void setAppId(String val) {
    	appId = val;
    }

    public String getMake() {
    	return make;
    }
    
    public void setMake(String val) {
    	make = val;
    }

	public abstract void initialize();
    	
    public abstract Integer runHandshake(byte[] inputData,byte[] outputData);
    
	public abstract Integer encryptData(byte[] inputData,byte[] outputData);
	
    public abstract Integer decryptData(byte[] inputData,byte[] outputData);
    
    public abstract void shutDown();
}
