package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
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
        super(SdlCommand.ON_LOCK_SCREEN_STATUS, jsonObject);
        jsonObject = getParameters(jsonObject);
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((driverDistractionStatus == null) ? 0
						: driverDistractionStatus.hashCode());
		result = prime * result
				+ ((hmiLevel == null) ? 0 : hmiLevel.hashCode());
		result = prime
				* result
				+ ((lockScreenStatus == null) ? 0 : lockScreenStatus.hashCode());
		result = prime * result
				+ ((userSelected == null) ? 0 : userSelected.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { 
			return true;
		}
		if (obj == null) { 
			return false;
		}
		if (getClass() != obj.getClass()) { 
			return false;
		}
		OnLockScreenStatus other = (OnLockScreenStatus) obj;
		if (driverDistractionStatus == null) {
			if (other.driverDistractionStatus != null) { 
				return false;
			}
		} 
		else if (!driverDistractionStatus.equals(other.driverDistractionStatus)) { 
			return false;
		}
		if (hmiLevel == null) {
			if (other.hmiLevel != null) { 
				return false;
			}
		} 
		else if (!hmiLevel.equals(other.hmiLevel)) { 
			return false;
		}
		if (lockScreenStatus == null) {
			if (other.lockScreenStatus != null) { 
				return false;
			}
		} 
		else if (!lockScreenStatus.equals(other.lockScreenStatus)) { 
			return false;
		}
		if (userSelected == null) {
			if (other.userSelected != null) { 
				return false;
			}
		} 
		else if (!userSelected.equals(other.userSelected)) { 
			return false;
		}
		return true;
	}
}
