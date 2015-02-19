package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

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
    public static final String KEY_APP_NAME = "appName";
    public static final String KEY_TTS_NAME = "ttsName";
    public static final String KEY_NGN_MEDIA_SCREEN_NAME = "ngnMediaScreenAppName";
    public static final String KEY_VR_SYNONYMS = "vrSynonyms";

	private String language, hmiLanguage; // represents Language enum
	private String appName, ngnAppName;
	private List<TTSChunk> ttsName;
	private List<String> vrSynonyms;
	
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
            this.appName = JsonUtils.readStringFromJsonObject(jsonObject, KEY_APP_NAME);
            this.ngnAppName = JsonUtils.readStringFromJsonObject(jsonObject, KEY_NGN_MEDIA_SCREEN_NAME);
            this.vrSynonyms = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_VR_SYNONYMS);
            
            List<JSONObject> ttsNameObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_TTS_NAME);
            if(ttsNameObjs != null){
                this.ttsName = new ArrayList<TTSChunk>(ttsNameObjs.size());
                for(JSONObject ttsNameObj : ttsNameObjs){
                    this.ttsName.add(new TTSChunk(ttsNameObj));
                }
            }
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

    /**
     * Gets app name
     * 
     * @return The app name
     */
    public String getAppName(){
        return appName;
    }

    /**
     * Sets app name
     * 
     * @param appName App name to set
     */
    public void setAppName(String appName){
        this.appName = appName;
    }

    /**
     * Gets NGN media screen app name
     * 
     * @return The NGN app name
     */
    public String getNgnMediaScreenAppName(){
        return ngnAppName;
    }

    /**
     * Sets NGN media screen app name
     * 
     * @param ngnAppName The NGN app name
     */
    public void setNgnMediaScreenAppName(String ngnAppName){
        this.ngnAppName = ngnAppName;
    }

    /**
     * Gets the TTS name
     * 
     * @return The TTS name
     */
    public List<TTSChunk> getTtsName(){
        return ttsName;
    }

    /**
     * Sets the TTS name
     * 
     * @param ttsName The TTS name to set
     */
    public void setTtsName(List<TTSChunk> ttsName){
        this.ttsName = ttsName;
    }

    /**
     * Gets the List<String> representing the an array of 1-100 elements, each
     * element containing a voice-recognition synonym
     * 
     * @return List<String> -a List value representing the an array of
     *         1-100 elements, each element containing a voice-recognition
     *         synonym
     */
    public List<String> getVrSynonyms(){
        return vrSynonyms;
    }

    /**
     * Sets a vrSynonyms representing the an array of 1-100 elements, each
     * element containing a voice-recognition synonym
     * 
     * @param vrSynonyms
     *            a List<String> value representing the an array of 1-100
     *            elements
     *            <p>
     *            <b>Notes: </b>
     *            <ul>
     *            <li>Each vr synonym is limited to 40 characters, and there can
     *            be 1-100 synonyms in array</li>
     *            <li>May not be the same (by case insensitive comparison) as
     *            the name or any synonym of any currently-registered
     *            application</li>
     *            </ul>
     */
    public void setVrSynonyms(List<String> vrSynonyms){
        this.vrSynonyms = vrSynonyms;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_LANGUAGE, this.language);
            JsonUtils.addToJsonObject(result, KEY_HMI_DISPLAY_LANGUAGE, this.hmiLanguage);
            JsonUtils.addToJsonObject(result, KEY_APP_NAME, this.appName);
            JsonUtils.addToJsonObject(result, KEY_NGN_MEDIA_SCREEN_NAME, this.ngnAppName);
            JsonUtils.addToJsonObject(result, KEY_VR_SYNONYMS, (this.vrSynonyms == null) ? null :
                JsonUtils.createJsonArray(this.vrSynonyms));
            JsonUtils.addToJsonObject(result, KEY_TTS_NAME, (this.ttsName == null) ? null :
                JsonUtils.createJsonArrayOfJsonObjects(this.ttsName, sdlVersion));
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
        result = prime * result + ((appName == null) ? 0 : appName.hashCode());
        result = prime * result + ((ngnAppName == null) ? 0 : ngnAppName.hashCode());
        result = prime * result + ((ttsName == null) ? 0 : ttsName.hashCode());
        result = prime * result + ((vrSynonyms == null) ? 0 : vrSynonyms.hashCode());
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
		} else if (!hmiLanguage.equals(other.hmiLanguage)) { 
			return false;
		}
        if (language == null) {
            if (other.language != null) { 
                return false;
            }
        } else if (!language.equals(other.language)) { 
            return false;
        }
        if (appName == null) {
            if (other.appName != null) { 
                return false;
            }
        } else if (!appName.equals(other.appName)) { 
            return false;
        }
        if (ngnAppName == null) {
            if (other.ngnAppName != null) { 
                return false;
            }
        } else if (!ngnAppName.equals(other.ngnAppName)) { 
            return false;
        }
        if (vrSynonyms == null) {
            if (other.vrSynonyms != null) { 
                return false;
            }
        } else if (!vrSynonyms.equals(other.vrSynonyms)) { 
            return false;
        }
        if (ttsName == null) {
            if (other.ttsName != null) { 
                return false;
            }
        } else if (!ttsName.equals(other.ttsName)) { 
            return false;
        }
		return true;
	}
}
