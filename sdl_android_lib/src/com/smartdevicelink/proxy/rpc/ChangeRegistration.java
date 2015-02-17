package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

/**
 * If the app recognizes during the app registration that the Sdl HMI language
 * (voice/TTS and/or display) does not match the app language, the app will be
 * able (but does not need) to change this registration with changeRegistration
 * prior to app being brought into focus
 * <p>
 * Function Group: Base
 * <p>
 * <b>HMILevel can by any</b>
 * <p>
 * 
 * @since SmartDeviceLink 2.0
 * @see RegisterAppInterface
 */
public class ChangeRegistration extends RPCRequest {
	public static final String KEY_LANGUAGE = "language";
	public static final String KEY_HMI_DISPLAY_LANGUAGE = "hmiDisplayLanguage";

	private String language, hmiLanguage; // represents Language enum
	
	/**
	 * Constructs a new ChangeRegistration object
	 */
    public ChangeRegistration() {
        super(FunctionID.CHANGE_REGISTRATION);
    }

    /**
     * Creates a ChangeRegistration object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public ChangeRegistration(JSONObject jsonObject) {
        super(SdlCommand.CHANGE_REGISTRATION, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.language = JsonUtils.readStringFromJsonObject(jsonObject, KEY_LANGUAGE);
            this.hmiLanguage = JsonUtils.readStringFromJsonObject(jsonObject, KEY_HMI_DISPLAY_LANGUAGE);
            break;
        }
    }

	/**
	 * Sets language
	 * 
	 * @param language
	 *            a language value
	 */
    public void setLanguage(Language language) {
        this.language = (language == null) ? null : language.getJsonName(sdlVersion);
    }

	/**
	 * Gets language
	 * 
	 * @return Language -a Language value
	 */
    public Language getLanguage() {
    	return Language.valueForJsonName(this.language, sdlVersion);
    }

	/**
	 * Sets HMI display language
	 * 
	 * @param hmiDisplayLanguage
	 *            a Language value
	 */
    public void setHmiDisplayLanguage(Language hmiDisplayLanguage) {
        this.hmiLanguage = (hmiDisplayLanguage == null) ? null : hmiDisplayLanguage.getJsonName(sdlVersion);
    }

	/**
	 * Gets HMI display language
	 * 
	 * @return Language -a Language value
	 */
    public Language getHmiDisplayLanguage() {
    	return Language.valueForJsonName(this.hmiLanguage, sdlVersion);
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_LANGUAGE, this.language);
            JsonUtils.addToJsonObject(result, KEY_HMI_DISPLAY_LANGUAGE, this.hmiLanguage);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hmiLanguage == null) ? 0 : hmiLanguage.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
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
		ChangeRegistration other = (ChangeRegistration) obj;
		if (hmiLanguage == null) {
			if (other.hmiLanguage != null) { 
				return false;
			}
		} 
		else if (!hmiLanguage.equals(other.hmiLanguage)) { 
			return false;
		}
		if (language == null) {
			if (other.language != null) { 
				return false;
			}
		} 
		else if (!language.equals(other.language)) { 
			return false;
		}
		return true;
	}
}
