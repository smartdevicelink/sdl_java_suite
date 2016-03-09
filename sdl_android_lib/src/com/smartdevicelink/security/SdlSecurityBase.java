package com.smartdevicelink.security;

import java.util.ArrayList;
import java.util.List;
import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.enums.SessionType;

public abstract class SdlSecurityBase {
	
	private SdlSession session = null;
	private String appId = null;
	private String make = null;
	private boolean isInitSuccess = false;
	private List<SessionType> startServiceList = new ArrayList<SessionType>();	
	
    public SdlSecurityBase() {
	}
	
	public abstract void initialize();
	
    public abstract Integer runHandshake(byte[] inputData,byte[] outputData);
    
	public abstract Integer encryptData(byte[] inputData,byte[] outputData);
	
    public abstract Integer decryptData(byte[] inputData,byte[] outputData);
    
    public abstract void shutDown();
    
    public void resetParams() {
    	session = null;
    	appId = null;
    	isInitSuccess = false;
    	startServiceList.clear();
    }
    
    public List<SessionType> getServiceList() {
    	return startServiceList;
    }
    
    public void handleInitResult(boolean val) {
    	setInitSuccess(val);
    	session.onSecurityInitialized();
    }
	
    private void setInitSuccess(boolean val) {
    	isInitSuccess = val;
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
}
