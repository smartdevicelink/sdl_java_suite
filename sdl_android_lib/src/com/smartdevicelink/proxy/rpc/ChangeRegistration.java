package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.util.DebugTool;

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

	/**
	 * Constructs a new ChangeRegistration object
	 */
    public ChangeRegistration() {
        super("ChangeRegistration");
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
            parameters.put(Names.language, language);
        } else {
        	parameters.remove(Names.language);
        }
    }

	/**
	 * Gets language
	 * 
	 * @return Language -a Language value
	 */
    public Language getLanguage() {
    	Object obj = parameters.get(Names.language);
        if (obj instanceof Language) {
            return (Language) obj;
        } else if (obj instanceof String) {
        	Language theCode = null;
            try {
                theCode = Language.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.language, e);
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
            parameters.put(Names.hmiDisplayLanguage, hmiDisplayLanguage);
        } else {
        	parameters.remove(Names.hmiDisplayLanguage);
        }
    }

	/**
	 * Gets HMI display language
	 * 
	 * @return Language -a Language value
	 */
    public Language getHmiDisplayLanguage() {
    	Object obj = parameters.get(Names.hmiDisplayLanguage);
        if (obj instanceof Language) {
            return (Language) obj;
        } else if (obj instanceof String) {
        	Language theCode = null;
            try {
                theCode = Language.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.hmiDisplayLanguage, e);
            }
            return theCode;
        }
        return null;
    }
}
