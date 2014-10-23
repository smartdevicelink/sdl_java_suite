package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.util.DebugTool;

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
	/**
	*Constructs a newly allocated OnCommand object
	*/    
    public OnLanguageChange() {
        super("OnLanguageChange");
    }
    /**
     *<p>Constructs a newly allocated OnLanguageChange object indicated by the Hashtable parameter</p>
     *@param hash The Hashtable to use
     */
    public OnLanguageChange(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * <p>Sets language that current SDL voice engine(VR+TTS) use</p>    
     * @param language language that current SDL voice engine(VR+TTS) use
     */  
    public void setLanguage(Language language) {
        if (language != null) {
            parameters.put(Names.language, language);
        } else {
        	parameters.remove(Names.language);
        }
    }
    /**
     * <p>Returns language that current SDL voice engine(VR+TTS) use</p>
     * @return {@linkplain Language} language that current SDL voice engine(VR+TTS) use
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
     * <p>Sets language that current display use</p>    
     * @param hmiDisplayLanguage language that current SDL voice engine(VR+TTS) use
     */  
    public void setHmiDisplayLanguage(Language hmiDisplayLanguage) {
        if (hmiDisplayLanguage != null) {
            parameters.put(Names.hmiDisplayLanguage, hmiDisplayLanguage);
        } else {
        	parameters.remove(Names.hmiDisplayLanguage);
        }
    }
    /**
     * <p>Returns language that current  display use</p>
     * @return {@linkplain Language} language that current display use
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
