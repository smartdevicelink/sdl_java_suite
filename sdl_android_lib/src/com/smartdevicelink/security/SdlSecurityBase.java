package com.smartdevicelink.security;

import java.util.HashMap;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.enums.SessionType;
public abstract class SdlSecurityBase {
	
	private SdlSession session = null;
	private String appId = null;
	private String make = null;
	private boolean isInitSuccess = false;
	public HashMap<SessionType, Boolean> serviceTypeList = new HashMap<SessionType, Boolean>();
	
	
    public SdlSecurityBase() {
	}
	
    public void setInitSuccess(boolean val) {
    	isInitSuccess = val;
    	session.onSecurityInitialized();
    }
	
    public boolean getInitSuccess() {
    	return isInitSuccess;
    }
	    
    public Byte getSessionId() {
    	if (session != null)
    		return session.getSessionId();
    	
    	return null;
    }

    public void setSdlSession(SdlSession val) {
    	session = val;
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
	
	public abstract void hs();
    	
    public abstract Integer runHandshake(byte[] inputData,byte[] outputData);
    
	public abstract Integer encryptData(byte[] inputData,byte[] outputData);
	
    public abstract Integer decryptData(byte[] inputData,byte[] outputData);
    
    public abstract void shutDown();
}
