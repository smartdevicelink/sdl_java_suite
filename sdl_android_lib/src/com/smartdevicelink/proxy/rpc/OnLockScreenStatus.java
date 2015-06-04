package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RpcNotification;
import com.smartdevicelink.proxy.rpc.enums.HmiLevel;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;

public class OnLockScreenStatus extends RpcNotification {
	public static final String KEY_DRIVER_DISTRACTION = "driverDistraction";
	public static final String KEY_SHOW_LOCK_SCREEN = "showLockScreen";
	public static final String KEY_USER_SELECTED = "userSelected";

	public OnLockScreenStatus() {
		super(FunctionId.ON_LOCK_SCREEN_STATUS.toString());
	}
	
	public Boolean getDriverDistractionStatus() {
		return (Boolean)parameters.get(KEY_DRIVER_DISTRACTION);
	}
	
	public void setDriverDistractionStatus(Boolean driverDistractionStatus) {
		
		if (driverDistractionStatus != null){
			parameters.put(KEY_DRIVER_DISTRACTION, driverDistractionStatus);
		} else {
	        parameters.remove(KEY_DRIVER_DISTRACTION);
	    }
	}	
	
	public LockScreenStatus getShowLockScreen() {
		return (LockScreenStatus)parameters.get(KEY_SHOW_LOCK_SCREEN);
	}
	
	public void setShowLockScreen(LockScreenStatus showLockScreen) {
		if (showLockScreen != null) {
			parameters.put(KEY_SHOW_LOCK_SCREEN, showLockScreen );
        } else {
        	parameters.remove(KEY_SHOW_LOCK_SCREEN);
        }
	}
	
	public Boolean getUserSelected() {
		return (Boolean)parameters.get(KEY_USER_SELECTED);
	}
	
	public void setUserSelected(Boolean userSelected) {
		if (userSelected != null) {
			parameters.put(KEY_USER_SELECTED, userSelected );
        } else {
        	parameters.remove(KEY_USER_SELECTED);
        }
	}		
	
	public HmiLevel getHmiLevel() {
		return (HmiLevel)parameters.get(OnHMIStatus.KEY_HMI_LEVEL);
	}
	
	public void setHmiLevel(HmiLevel setHmiLevel) {
		if (setHmiLevel != null) {
			parameters.put(OnHMIStatus.KEY_HMI_LEVEL, setHmiLevel );
        } else {
        	parameters.remove(OnHMIStatus.KEY_HMI_LEVEL);
        }
	}				
}
