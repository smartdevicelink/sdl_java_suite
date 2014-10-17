package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;

public class OnLockScreenStatus extends RPCNotification {
	public static final String driverDistraction = "driverDistraction";
	public static final String showLockScreen = "showLockScreen";
	public static final String userSelected = "userSelected";
	public static final String notSet = "notSet";

	public OnLockScreenStatus() {
		super("OnLockScreenStatus");
	}
	
	public String getDriverDistractionStatus() {
		return (String)parameters.get(OnLockScreenStatus.driverDistraction);
	}
	
	public void setDriverDistractionStatus(Boolean driverDistractionStatus) {
		
		if (driverDistractionStatus == null){
			parameters.put(OnLockScreenStatus.driverDistraction, OnLockScreenStatus.notSet);
		}
		else{
			parameters.put(OnLockScreenStatus.driverDistraction, driverDistractionStatus.toString());
		}
	}	
	
	public LockScreenStatus getShowLockScreen() {
		return (LockScreenStatus)parameters.get(OnLockScreenStatus.showLockScreen);
	}
	
	public void setShowLockScreen(LockScreenStatus showLockScreen) {
		if (showLockScreen != null) {
			parameters.put(OnLockScreenStatus.showLockScreen, showLockScreen );
        } else if (parameters.contains(OnLockScreenStatus.showLockScreen)) {
        	parameters.remove(OnLockScreenStatus.showLockScreen);
        }
	}
	
	public Boolean getUserSelected() {
		return (Boolean)parameters.get(OnLockScreenStatus.userSelected);
	}
	
	public void setUserSelected(Boolean userSelected) {
		if (userSelected != null) {
			parameters.put(OnLockScreenStatus.userSelected, userSelected );
        } else if (parameters.contains(OnLockScreenStatus.userSelected)) {
        	parameters.remove(OnLockScreenStatus.userSelected);
        }
	}		
	
	public HMILevel getHMILevel() {
		return (HMILevel)parameters.get(OnHMIStatus.hmiLevel);
	}
	
	public void setHMILevel(HMILevel setHMILevel) {
		if (setHMILevel != null) {
			parameters.put(OnHMIStatus.hmiLevel, setHMILevel );
        } else if (parameters.contains(OnHMIStatus.hmiLevel)) {
        	parameters.remove(OnHMIStatus.hmiLevel);
        }
	}				
}
