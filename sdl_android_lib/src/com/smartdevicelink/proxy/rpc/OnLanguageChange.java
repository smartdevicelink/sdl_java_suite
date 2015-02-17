package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;

/**
 * Provides information to what language the Sdl HMI language was changed
 * <p>
 * </p>
 * <b>HMI Status Requirements:</b>
 * <ul>
 * HMILevel:
 * <ul>
 * <li>TBD</li>
 * </ul>
 * AudioStreamingState:
 * <ul>
 * <li>TBD</li>
 * </ul>
 * SystemContext:
 * <ul>
 * <li>TBD</li>
 * </ul>
 * </ul>
 * <p>
 * <b>Parameter List:</b>
 * <table border="1" rules="all">
 * <tr>
 * <th>Name</th>
 * <th>Type</th>
 * <th>Description</th>
 * <th>Req</th>
 * <th>Notes</th>
 * <th>SmartDeviceLink Ver Available</th>
 * </tr>
 * <tr>
 * <td>language</td>
 * <td>{@linkplain Language}</td>
 * <td>Current SDL voice engine (VR+TTS) language</td>
 * <td>Y</td>
 * <td></td>
 * <td>SmartDeviceLink 2.0</td>
 * </tr>
 * <tr>
 * <td>hmiDisplayLanguage</td>
 * <td>{@linkplain Language}</td>
 * <td>Current display language</td>
 * <td>Y</td>
 * <td></td>
 * <td>SmartDeviceLink 2.0</td>
 * </tr>
 * </table>
 * </p>
 *
 */
public class OnLanguageChange extends RPCNotification {
	public static final String KEY_LANGUAGE = "language";
	public static final String KEY_HMI_DISPLAY_LANGUAGE = "hmiDisplayLanguage";
	
	private String language, hmiLanguage; // represents Language enum
	
	/**
	*Constructs a newly allocated OnCommand object
	*/    
    public OnLanguageChange() {
        super(FunctionID.ON_LANGUAGE_CHANGE);
    }
    
    /**
     * Creates a OnLanguageChange object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public OnLanguageChange(JSONObject jsonObject){
        super(SdlCommand.ON_LANGUAGE_CHANGE, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.language = JsonUtils.readStringFromJsonObject(jsonObject, KEY_LANGUAGE);
            this.hmiLanguage = JsonUtils.readStringFromJsonObject(jsonObject, KEY_HMI_DISPLAY_LANGUAGE);
            break;
        }
    }
    
    /**
     * <p>Sets language that current SDL voice engine(VR+TTS) use</p>    
     * @param language language that current SDL voice engine(VR+TTS) use
     */  
    public void setLanguage(Language language) {
        this.language = (language == null) ? null : language.getJsonName(sdlVersion);
    }
    
    /**
     * <p>Returns language that current SDL voice engine(VR+TTS) use</p>
     * @return {@linkplain Language} language that current SDL voice engine(VR+TTS) use
     */  
    public Language getLanguage() {
    	return Language.valueForJsonName(this.language, sdlVersion);
    }
    
    /**
     * <p>Sets language that current display use</p>    
     * @param hmiDisplayLanguage language that current SDL voice engine(VR+TTS) use
     */  
    public void setHmiDisplayLanguage(Language hmiDisplayLanguage) {
        this.hmiLanguage = (hmiDisplayLanguage == null) ? null : hmiDisplayLanguage.getJsonName(sdlVersion);
    }
    
    /**
     * <p>Returns language that current  display use</p>
     * @return {@linkplain Language} language that current display use
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
		OnLanguageChange other = (OnLanguageChange) obj;
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
