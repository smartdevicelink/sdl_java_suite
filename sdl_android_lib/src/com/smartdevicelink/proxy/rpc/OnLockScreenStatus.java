package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;
import com.smartdevicelink.util.JsonUtils;

public class OnLockScreenStatus extends RPCNotification {
	public static final String KEY_DRIVER_DISTRACTION = "driverDistraction";
	public static final String KEY_SHOW_LOCK_SCREEN = "showLockScreen";
	public static final String KEY_USER_SELECTED = "userSelected";

	private Boolean driverDistractionStatus, userSelected;
	private String lockScreenStatus; // represents LockScreenStatus enum
	private String hmiLevel; // represents HMILevel
	
	public OnLockScreenStatus() {
		super(FunctionID.ON_LOCK_SCREEN_STATUS);
	}
	
    /**
     * Creates a OnLockScreenStatus object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public OnLockScreenStatus(JSONObject jsonObject){
        super(jsonObject);
        switch(sdlVersion){
        default:
            this.driverDistractionStatus = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_DRIVER_DISTRACTION);
            this.userSelected = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_USER_SELECTED);
            this.lockScreenStatus = JsonUtils.readStringFromJsonObject(jsonObject, KEY_SHOW_LOCK_SCREEN);
            this.hmiLevel = JsonUtils.readStringFromJsonObject(jsonObject, OnHMIStatus.KEY_HMI_LEVEL);
            
            break;
        }
    }
	
	public Boolean getDriverDistractionStatus() {
		return this.driverDistractionStatus;
	}
	
	public void setDriverDistractionStatus(Boolean driverDistractionStatus) {
		this.driverDistractionStatus = driverDistractionStatus;
	}	
	
	public LockScreenStatus getShowLockScreen() {
		return LockScreenStatus.valueForJsonName(this.lockScreenStatus, sdlVersion);
	}
	
	public void setShowLockScreen(LockScreenStatus showLockScreen) {
		this.lockScreenStatus = (showLockScreen == null) ? null : showLockScreen.getJsonName(sdlVersion);
	}
	
	public Boolean getUserSelected() {
		return this.userSelected;
	}
	
	public void setUserSelected(Boolean userSelected) {
		this.userSelected = userSelected;
	}		
	
	public HMILevel getHMILevel() {
		return HMILevel.valueForJsonName(this.hmiLevel, sdlVersion);
	}
	
	public void setHMILevel(HMILevel setHMILevel) {
		this.hmiLevel = (setHMILevel == null) ? null : setHMILevel.getJsonName(sdlVersion);
	}
	
	@Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_DRIVER_DISTRACTION, this.driverDistractionStatus);
            JsonUtils.addToJsonObject(result, KEY_SHOW_LOCK_SCREEN, this.lockScreenStatus);
            JsonUtils.addToJsonObject(result, KEY_USER_SELECTED, this.userSelected);
            JsonUtils.addToJsonObject(result, OnHMIStatus.KEY_HMI_LEVEL, this.hmiLevel);
            break;
        }
        
        return result;
    }
}
