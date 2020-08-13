/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.security;

import com.smartdevicelink.SdlConnection.SdlSession;
import com.smartdevicelink.protocol.enums.SessionType;

import java.util.ArrayList;
import java.util.List;

abstract class AbstractSdlSecurityBase {
	
	protected SdlSession session = null;	
	protected String appId = null;
	protected List<String> makeList = null;
	protected boolean isInitSuccess = false;
	protected byte sessionId = 0;
	protected List<SessionType> startServiceList = new ArrayList<SessionType>();	
	
    public AbstractSdlSecurityBase() {
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
        if (session == null) return;

        setInitSuccess(val);
        session.onSecurityInitialized();
    }
    
    public void handleSdlSession(SdlSession sdlSession) {
    	if (sdlSession == null) return;
    	
    	setSessionId((byte)sdlSession.getSessionId());
    	setSdlSession(sdlSession);
    }
	
    private void setInitSuccess(boolean val) {
    	isInitSuccess = val;
    }
    
    public boolean getInitSuccess() {
    	return isInitSuccess;
    }
    
    private void setSessionId(byte val) {
    	sessionId = val;
    }
    
    public byte getSessionId() {
    	return sessionId;
    }

    private void setSdlSession(SdlSession val) {
    	session = val;
    }
    
    public String getAppId() {
    	return appId;
    }
    
    public void setAppId(String val) {
    	appId = val;
    }

    public List<String> getMakeList() {
    	return makeList;
    }
    
    public void setMakeList(List<String> val) {
    	makeList = val;
    }
}
