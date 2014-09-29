package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;

public class OnLockScreenStatus extends RPCNotification {
	public OnLockScreenStatus() {
		super("OnLockScreenStatus");
	}
	
	public String getDriverDistractionStatus() {
		return (String)parameters.get(Names.driverDistraction);
	}
	
	public void setDriverDistractionStatus(Boolean driverDistractionStatus) {
		
		if (driverDistractionStatus == null){
			parameters.put(Names.driverDistraction, Names.notSet);
		}
		else{
			parameters.put(Names.driverDistraction, driverDistractionStatus.toString());
		}
	}	
	
	public LockScreenStatus getShowLockScreen() {
		return (LockScreenStatus)parameters.get(Names.showLockScreen);
	}
	
	public void setShowLockScreen(LockScreenStatus showLockScreen) {
		if (showLockScreen != null) {
			parameters.put(Names.showLockScreen, showLockScreen );
        } else if (parameters.contains(Names.showLockScreen)) {
        	parameters.remove(Names.showLockScreen);
        }
	}
	
	public Boolean getUserSelected() {
		return (Boolean)parameters.get(Names.userSelected);
	}
	
	public void setUserSelected(Boolean userSelected) {
		if (userSelected != null) {
			parameters.put(Names.userSelected, userSelected );
        } else if (parameters.contains(Names.userSelected)) {
        	parameters.remove(Names.userSelected);
        }
	}		
	
	public HMILevel getHMILevel() {
		return (HMILevel)parameters.get(Names.hmiLevel);
	}
	
	public void setHMILevel(HMILevel setHMILevel) {
		if (setHMILevel != null) {
			parameters.put(Names.hmiLevel, setHMILevel );
        } else if (parameters.contains(Names.hmiLevel)) {
        	parameters.remove(Names.hmiLevel);
        }
	}				
}