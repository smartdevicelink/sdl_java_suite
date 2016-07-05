package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.Language;

/**
 * If the app recognizes during the app registration that the SDL HMI language
 * (voice/TTS and/or display) does not match the app language, the app will be
 * able (but does not need) to change this registration with changeRegistration
 * prior to app being brought into focus.
 * 
 * <p>Function Group: Base</p>
 * 
 * <p><b>HMILevel can by any</b></p>
 * 
 * <b>Note:</b>
 * 
 * <p>SDL will send the language value confirmed to be supported by HMI via UI.GetCapabilities.</p>
 * <p><b> Parameter List</b></p>
 * 
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th> Req.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>Language</td>
 * 			<td>Language</td>
 * 			<td>Requested SDL voice engine (VR+TTS) language registration.</td>
 *                 <td>Y</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>hmiDisplayLanguage</td>
 * 			<td>Language</td>
 * 			<td>Request display language registration.</td>
 *                 <td>Y</td>
 * 			<td>Minvalue=0; Maxvalue=2000000000</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *
 *            <tr>
 * 			<td>appName</td>
 * 			<td>String</td>
 * 			<td>Request new app name registration</td>
 *                 <td>N</td>
 *                 <td>maxlength:100</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>ttsName</td>
 * 			<td>TTSChunk</td>
 * 			<td>Request new ttsName registration</td>
 *                 <td>N</td>
 *                 <td>minsize:1; maxsize:100</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *            <tr>
 * 			<td>ngnMediaScreenAppName</td>
 * 			<td>String</td>
 * 			<td>Request new app short name registration</td>
 *                 <td>N</td>
 *                 <td>maxlength: 100</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>vrSynonyms</td>
 * 			<td>String</td>
 * 			<td>Request new VR synonyms registration</td>
 *                 <td>N</td>
 *                 <td>maxlength: 40; minsize:1; maxsize:100</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 *  
 * <p><b>Response </b></p>
 * 
 * <p><b>Non-default Result Codes:</b></p>
 * 	<p>SUCCESS</p>
 * 	<p>INVALID_DATA</p>
 * 	<p>OUT_OF_MEMORY</p>
 * <p>	TOO_MANY_PENDING_REQUESTS</p>
 * 	<p>APPLICATION_NOT_REGISTERED</p>
 * 	<p>GENERIC_ERROR</p>
 * 	<p>REJECTED</p>
 *    <p>DISALLOWED</p>
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

	/**
	 * Constructs a new ChangeRegistration object
	 */
    public ChangeRegistration() {
        super(FunctionID.CHANGE_REGISTRATION.toString());
    }

	/**
	 * <p>Constructs a new ChangeRegistration object indicated by the Hashtable
	 * parameter</p>
	 * 
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public ChangeRegistration(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Sets language
	 * 
	 * @param language
	 *            a language value
	 */
    public void setLanguage(Language language) {
        if (language != null) {
            parameters.put(KEY_LANGUAGE, language);
        } else {
        	parameters.remove(KEY_LANGUAGE);
        }
    }

	/**
	 * Gets language
	 * 
	 * @return Language -a Language value
	 */
    public Language getLanguage() {
    	Object obj = parameters.get(KEY_LANGUAGE);
        if (obj instanceof Language) {
            return (Language) obj;
        } else if (obj instanceof String) {
        	return Language.valueForString((String) obj);
        }
        return null;
    }

	/**
	 * Sets HMI display language
	 * 
	 * @param hmiDisplayLanguage
	 *            a Language value
	 */
    public void setHmiDisplayLanguage(Language hmiDisplayLanguage) {
        if (hmiDisplayLanguage != null) {
            parameters.put(KEY_HMI_DISPLAY_LANGUAGE, hmiDisplayLanguage);
        } else {
        	parameters.remove(KEY_HMI_DISPLAY_LANGUAGE);
        }
    }

	/**
	 * Gets HMI display language
	 * 
	 * @return Language -a Language value
	 */
    public Language getHmiDisplayLanguage() {
    	Object obj = parameters.get(KEY_HMI_DISPLAY_LANGUAGE);
        if (obj instanceof Language) {
            return (Language) obj;
        } else if (obj instanceof String) {
        	return Language.valueForString((String) obj);
        }
        return null;
    }
    
    /**
     * Sets app name
     * 
     * @param appName App name to set
     */
    public void setAppName(String appName){
        if(appName != null){
            parameters.put(KEY_APP_NAME, appName);
        }
        else{
            parameters.remove(KEY_APP_NAME);
        }
    }
    
    /**
     * Gets app name
     * 
     * @return The app name
     */
    public String getAppName(){
        return (String) parameters.get(KEY_APP_NAME);
    }
    
    /**
     * Sets NGN media screen app name
     * 
     * @param ngnAppName The NGN app name
     */
    public void setNgnMediaScreenAppName(String ngnAppName){
        if(ngnAppName != null){
            parameters.put(KEY_NGN_MEDIA_SCREEN_NAME, ngnAppName);
        }
        else{
            parameters.remove(KEY_NGN_MEDIA_SCREEN_NAME);
        }
    }
    
    /**
     * Gets NGN media screen app name
     * 
     * @return The NGN app name
     */
    public String getNgnMediaScreenAppName(){
        return (String) parameters.get(KEY_NGN_MEDIA_SCREEN_NAME);
    }
    
    /**
     * Sets the TTS name
     * 
     * @param ttsName The TTS name to set
     */
    public void setTtsName(List<TTSChunk> ttsName){
        if(ttsName != null){
            parameters.put(KEY_TTS_NAME, ttsName);
        }
        else{
            parameters.remove(KEY_TTS_NAME);
        }
    }
    
    /**
     * Gets the TTS name
     * 
     * @return The TTS name
     */
    @SuppressWarnings("unchecked")
    public List<TTSChunk> getTtsName(){
        if (parameters.get(KEY_TTS_NAME) instanceof List<?>) {
            List<?> list = (List<?>)parameters.get(KEY_TTS_NAME);
            if (list != null && list.size() > 0) {
                Object obj = list.get(0);
                if (obj instanceof TTSChunk) {
                    return (List<TTSChunk>) list;
                } else if (obj instanceof Hashtable) {
                    List<TTSChunk> newList = new ArrayList<TTSChunk>();
                    for (Object hashObj : list) {
                        newList.add(new TTSChunk((Hashtable<String, Object>)hashObj));
                    }
                    return newList;
                }
            }
        }
        return null;
    }
    
    /**
     * Gets the List<String> representing the an array of 1-100 elements, each
     * element containing a voice-recognition synonym
     * 
     * @return List<String> -a List value representing the an array of
     *         1-100 elements, each element containing a voice-recognition
     *         synonym
     */    
    @SuppressWarnings("unchecked")
    public List<String> getVrSynonyms() {
        if (parameters.get(KEY_VR_SYNONYMS) instanceof List<?>) {
            List<?> list = (List<?>)parameters.get(KEY_VR_SYNONYMS);
            if (list != null && list.size()>0) {
                Object obj = list.get(0);
                if (obj instanceof String) {
                    return (List<String>) list;
                }
            }
        }
        return null;
    }
    
    /**
     * Sets a vrSynonyms representing the an array of 1-100 elements, each
     * element containing a voice-recognition synonym
     * 
     * @param vrSynonyms
     *            a List<String> value representing the an array of 1-100
     *            elements
     *            <p>
     *            <b>Notes: </b></p>
     *            <ul>
     *            <li>Each vr synonym is limited to 40 characters, and there can
     *            be 1-100 synonyms in array</li>
     *            <li>May not be the same (by case insensitive comparison) as
     *            the name or any synonym of any currently-registered
     *            application</li>
     *            </ul>
     */    
    public void setVrSynonyms(List<String> vrSynonyms) {
        if (vrSynonyms != null) {
            parameters.put(KEY_VR_SYNONYMS, vrSynonyms);
        } else {
            parameters.remove(KEY_VR_SYNONYMS);
        }
    }
}
