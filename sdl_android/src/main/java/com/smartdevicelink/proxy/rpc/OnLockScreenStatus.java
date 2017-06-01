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
		setParameter(KEY_DRIVER_DISTRACTION, driverDistractionStatus);
	}	
    /**
     * <p>Get the {@linkplain LockScreenStatus} enumeration, indicating if the lockscreen should be required, optional or off </p>
     * @return {@linkplain LockScreenStatus} 
     */    	

	public LockScreenStatus getShowLockScreen() {
		return (LockScreenStatus) getParameter(KEY_SHOW_LOCK_SCREEN);
	}
	
	public void setShowLockScreen(LockScreenStatus showLockScreen) {
		setParameter(KEY_SHOW_LOCK_SCREEN, showLockScreen);
	}
    /**
     * <p>Get user selection status for the application (has the app been selected via hmi or voice command)</p>
     * @return Boolean the current user selection status
     */    

	public Boolean getUserSelected() {
		return getBoolean(KEY_USER_SELECTED);
	}
	
	public void setUserSelected(Boolean userSelected) {
		setParameter(KEY_USER_SELECTED, userSelected);
	}		
    /**
     * <p>Get HMILevel in effect for the application</p>
     * @return {@linkplain HMILevel} the current HMI Level in effect for the application
     */    

	public HMILevel getHMILevel() {
		return (HMILevel) getParameter(KEY_HMI_LEVEL);
	}
	
	public void setHMILevel(HMILevel setHMILevel) {
		setParameter(KEY_HMI_LEVEL, setHMILevel);
	}				
}
