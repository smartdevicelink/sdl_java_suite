package com.smartdevicelink.security;

public abstract class SdlSecurityBase {
	
	private Byte sessionId = null;
	private String appId = null;
	
    public SdlSecurityBase() {
	}
    
    public void setSessionId(Byte val) {
    	sessionId = val;
    }

    public void setAppId(String val) {
    	appId = val;
    }

    public Byte getSessionId() {
    	return sessionId;
    }

    public String getAppId() {
    	return appId;
    }

	public abstract Boolean initialize();
    
	public abstract Boolean initializeWithPfx(byte[] pfx);
    
    public abstract Boolean initializeWithPem(byte[] cert, byte[] key);
	
    public abstract Integer runHandshake(byte[] inputData,byte[] outputData);
    
	public abstract Integer encryptData(byte[] inputData,byte[] outputData);
	
    public abstract Integer decryptData(byte[] inputData,byte[] outputData);
    
    public abstract void closeSession();
}
