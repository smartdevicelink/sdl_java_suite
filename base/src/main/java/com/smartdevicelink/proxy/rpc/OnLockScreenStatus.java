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
package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;

import static com.smartdevicelink.proxy.rpc.OnHMIStatus.KEY_HMI_LEVEL;

/**
 * The lockscreen must perform the following:
 *	Limit all application control usability from the mobile device with a full-screen static image overlay or separate view.
 *	For simplicity, the OnLockScreenStatus RPC will be provided via the onOnLockScreenNotification call back. The call back will include the LockScreenStatus enum which indicates if the lockscreen is required, optional or not required.
 *	The call back also includes details regarding the current HMI_Status level, driver distraction status and user selection status of the application. 
 * 
 *
 */
@Deprecated
public class OnLockScreenStatus extends RPCNotification {
	public static final String KEY_DRIVER_DISTRACTION = "driverDistraction";
	public static final String KEY_SHOW_LOCK_SCREEN = "showLockScreen";
	public static final String KEY_USER_SELECTED = "userSelected";

	public OnLockScreenStatus() {
		super(FunctionID.ON_LOCK_SCREEN_STATUS.toString());
	}
    /**
     * <p>Get the current driver distraction status(i.e. whether driver distraction rules are in effect, or not)</p>
     * @return Boolean
     */    

	public Boolean getDriverDistractionStatus() {
		return getBoolean(KEY_DRIVER_DISTRACTION);
	}
	
	public void setDriverDistractionStatus(Boolean driverDistractionStatus) {
		setParameters(KEY_DRIVER_DISTRACTION, driverDistractionStatus);
	}	
    /**
     * <p>Get the {@linkplain LockScreenStatus} enumeration, indicating if the lockscreen should be required, optional or off </p>
     * @return {@linkplain LockScreenStatus} 
     */    	

	public LockScreenStatus getShowLockScreen() {
		return (LockScreenStatus) getParameters(KEY_SHOW_LOCK_SCREEN);
	}
	
	public void setShowLockScreen(LockScreenStatus showLockScreen) {
		setParameters(KEY_SHOW_LOCK_SCREEN, showLockScreen);
	}
    /**
     * <p>Get user selection status for the application (has the app been selected via hmi or voice command)</p>
     * @return Boolean the current user selection status
     */    

	public Boolean getUserSelected() {
		return getBoolean(KEY_USER_SELECTED);
	}
	
	public void setUserSelected(Boolean userSelected) {
		setParameters(KEY_USER_SELECTED, userSelected);
	}		
    /**
     * <p>Get HMILevel in effect for the application</p>
     * @return {@linkplain HMILevel} the current HMI Level in effect for the application
     */    

	public HMILevel getHMILevel() {
		return (HMILevel) getParameters(KEY_HMI_LEVEL);
	}
	
	public void setHMILevel(HMILevel setHMILevel) {
		setParameters(KEY_HMI_LEVEL, setHMILevel);
	}				
}
