package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.util.DebugTool;

/**
 * If the app recognizes during the app registration that the SDL HMI language
 * (voice/TTS and/or display) does not match the app language, the app will be
 * able (but does not need) to change this registration with changeRegistration
 * prior to app being brought into focus.
 * <p>
 * Function Group: Base
 * <p>
 * <b>HMILevel can by any</b>
 * <p>
 * <b>Note:</b><br>
 * <p>
  *  SDL will send the language value confirmed to be supported by HMI via UI.GetCapabilities.<br>
  * <p><b> Parameter List</b>
 * <table border="1" rules="all">
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
 * 			<td>Minvalue=0 <br>Maxvalue=2000000000</td>
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
 *                 <td>minsize:1<br> maxsize:100</td>
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
 *                 <td>maxlength: 40<br>minsize:1<br>maxsize:100</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 * <p>  
 * <b>Response </b><br>
 * <p>
 * <b>Non-default Result Codes:</b><br>
 * ¥	SUCCESS<br>
 * ¥	INVALID_DATA<br>
 * ¥	OUT_OF_MEMORY<br>
 * ¥	TOO_MANY_PENDING_REQUESTS<br>
 * ¥	APPLICATION_NOT_REGISTERED<br>
 * ¥	GENERIC_ERROR<br>
 * ¥	REJECTED<br>
 * ¥   DISALLOWED<br>
 
 * 
 * @since SmartDeviceLink 2.0
 * @see RegisterAppInterface
 */
public class ChangeRegistration extends RPCRequest {
	public static final String KEY_LANGUAGE = "language";
	public static final String KEY_HMI_DISPLAY_LANGUAGE = "hmiDisplayLanguage";

	/**
	 * Constructs a new ChangeRegistration object
	 */
    public ChangeRegistration() {
        super(FunctionID.CHANGE_REGISTRATION);
    }

	/**
	 * Constructs a new ChangeRegistration object indicated by the Hashtable
	 * parameter
	 * <p>
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
        	Language theCode = null;
            try {
                theCode = Language.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_LANGUAGE, e);
            }
            return theCode;
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
        	Language theCode = null;
            try {
                theCode = Language.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_HMI_DISPLAY_LANGUAGE, e);
            }
            return theCode;
        }
        return null;
    }
}
