package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.constants.Names;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.util.DebugTool;
/**
 * Registers the application's interface with SDL&reg;, declaring properties of
 * the registration, including the messaging interface version, the app name,
 * etc. The mobile application must establish its interface registration with
 * SDL&reg; before any other interaction with SDL&reg; can take place. The
 * registration lasts until it is terminated either by the application calling
 * the <i> {@linkplain UnregisterAppInterface}</i> method, or by SDL&reg;
 * sending an <i> {@linkplain OnAppInterfaceUnregistered}</i> notification, or
 * by loss of the underlying transport connection, or closing of the underlying
 * message transmission protocol RPC session
 * <p>
 * Until the application receives its first <i>{@linkplain OnHMIStatus}</i>
 * Notification, its HMI Status is assumed to be: <i>
 * {@linkplain com.smartdevicelink.proxy.rpc.enums.HMILevel}</i>=NONE, <i>
 * {@linkplain com.smartdevicelink.proxy.rpc.enums.AudioStreamingState}
 * </i>=NOT_AUDIBLE, <i>
 * {@linkplain com.smartdevicelink.proxy.rpc.enums.SystemContext}</i>=MAIN
 * <p>
 * All SDL&reg; resources which the application creates or uses (e.g. Choice
 * Sets, Command Menu, etc.) are associated with the application's interface
 * registration. Therefore, when the interface registration ends, the SDL&reg;
 * resources associated with the application are disposed of. As a result, even
 * though the application itself may continue to run on its host platform (e.g.
 * mobile device) after the interface registration terminates, the application
 * will not be able to use the SDL&reg; HMI without first establishing a new
 * interface registration and re-creating its required SDL&reg; resources. That
 * is, SDL&reg; resources created by (or on behalf of) an application do not
 * persist beyond the life-span of the interface registration
 * <p>
 * Resources and settings whose lifespan is tied to the duration of an
 * application's interface registration:<br/>
 * <ul>
 * <li>Choice Sets</li>
 * <li>Command Menus (built by successive calls to <i>{@linkplain AddCommand}
 * </i>)</li>
 * <li>Media clock timer display value</li>
 * <li>Media clock timer display value</li>
 * <li>Media clock timer display value</li>
 * </ul>
 * <p>
 * The autoActivateID is used to grant an application the HMILevel and
 * AudioStreamingState it had when it last disconnected
 * <p>
 * <b>Notes: </b>The autoActivateID parameter, and associated behavior, is
 * currently ignored by SDL&reg;
 * <p>
 * When first calling this method (i.e. first time within life cycle of mobile
 * app), an autoActivateID should not be included. After successfully
 * registering an interface, an autoActivateID is returned to the mobile
 * application for it to use in subsequent connections. If the connection
 * between SDL&reg; and the mobile application is lost, such as the vehicle is
 * turned off while the application is running, the autoActivateID can then be
 * passed in another call to RegisterAppInterface to re-acquire <i>
 * {@linkplain com.smartdevicelink.proxy.rpc.enums.HMILevel}</i>=FULL
 * <p>
 * If the application intends to stream audio it is important to indicate so via
 * the isMediaApp parameter. When set to true, audio will reliably stream
 * without any configuration required by the user. When not set, audio may
 * stream, depending on what the user might have manually configured as a media
 * source on SDL&reg;
 * <p>
 * There is no time limit for how long the autoActivateID is "valid" (i.e. would
 * confer focus and opt-in)
 * <p>
 * <b>HMILevel is not defined before registering</b><br/>
 * </p>
 * 
 * @since SmartDeviceLink 1.0
 * @see UnregisterAppInterface
 * @see OnAppInterfaceUnregistered
 */
public class RegisterAppInterface extends RPCRequest {
	/**
	 * Constructs a new RegisterAppInterface object
	 */
    public RegisterAppInterface() {
        super("RegisterAppInterface");
    }
	/**
	 * Constructs a new RegisterAppInterface object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */    
    public RegisterAppInterface(Hashtable<String, Object> hash) {
        super(hash);
    }
	/**
	 * Gets the version of the SDL&reg; SmartDeviceLink interface
	 * 
	 * @return SdlMsgVersion -a SdlMsgVersion object representing version of
	 *         the SDL&reg; SmartDeviceLink interface
	 */    
    @SuppressWarnings("unchecked")
    public SdlMsgVersion getSdlMsgVersion() {
        Object obj = parameters.get(Names.sdlMsgVersion);
        if (obj instanceof SdlMsgVersion) {
        	return (SdlMsgVersion) obj;
        } else if (obj instanceof Hashtable) {
        	return new SdlMsgVersion((Hashtable<String, Object>) obj);
        }
        return null;
    }
	/**
	 * Sets the version of the SDL&reg; SmartDeviceLink interface
	 * 
	 * @param sdlMsgVersion
	 *            a SdlMsgVersion object representing version of the SDL&reg;
	 *            SmartDeviceLink interface
	 *            <p>
	 *            <b>Notes: </b>To be compatible, app msg major version number
	 *            must be less than or equal to SDL&reg; major version number.
	 *            If msg versions are incompatible, app has 20 seconds to
	 *            attempt successful RegisterAppInterface (w.r.t. msg version)
	 *            on underlying protocol session, else will be terminated. Major
	 *            version number is a compatibility declaration. Minor version
	 *            number indicates minor functional variations (e.g. features,
	 *            capabilities, bug fixes) when sent from SDL&reg; to app (in
	 *            RegisterAppInterface response). However, the minor version
	 *            number sent from the app to SDL&reg; (in RegisterAppInterface
	 *            request) is ignored by SDL&reg;
	 */    
    public void setSdlMsgVersion(SdlMsgVersion sdlMsgVersion) {
        if (sdlMsgVersion != null) {
            parameters.put(Names.sdlMsgVersion, sdlMsgVersion);
        } else {
        	parameters.remove(Names.sdlMsgVersion);
        }
    }
    
    @SuppressWarnings("unchecked")
    public DeviceInfo getDeviceInfo() {
        Object obj = parameters.get(Names.deviceInfo);
        if (obj instanceof DeviceInfo) {
        	return (DeviceInfo) obj;
        } else if (obj instanceof Hashtable) {
        	return new DeviceInfo((Hashtable<String, Object>) obj);
        }
        return null;
    }    
    
    public void setDeviceInfo(DeviceInfo deviceInfo) {
        if (deviceInfo != null) {
            parameters.put(Names.deviceInfo, deviceInfo);
        } else {
        	parameters.remove(Names.deviceInfo);
        }
    }    
	/**
	 * Gets Mobile Application's Name
	 * 
	 * @return String -a String representing the Mobile Application's Name
	 */    
    public String getAppName() {
        return (String) parameters.get(Names.appName);
    }
	/**
	 * Sets Mobile Application's Name, This name is displayed in the SDL&reg;
	 * Mobile Applications menu. It also serves as the unique identifier of the
	 * application for SmartDeviceLink
	 * 
	 * @param appName
	 *            a String value representing the Mobile Application's Name
	 *            <p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>Must be 1-100 characters in length</li>
	 *            <li>May not be the same (by case insensitive comparison) as
	 *            the name or any synonym of any currently-registered
	 *            application</li>
	 *            </ul>
	 */    
    public void setAppName(String appName) {
        if (appName != null) {
            parameters.put(Names.appName, appName);
        } else {
        	parameters.remove(Names.appName);
        }
    }

	/**
	 * Gets TTS string for VR recognition of the mobile application name
	 * 
	 * @return Vector<TTSChunk> -Vector value representing the TTS string
	 * @since SmartDeviceLink 2.0
	 */
    @SuppressWarnings("unchecked")
    public Vector<TTSChunk> getTtsName() {
        if (parameters.get(Names.ttsName) instanceof Vector<?>) {
	    	Vector<?> list = (Vector<?>)parameters.get(Names.ttsName);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TTSChunk) {
	                return (Vector<TTSChunk>) list;
	            } else if (obj instanceof Hashtable) {
	                Vector<TTSChunk> newList = new Vector<TTSChunk>();
	                for (Object hashObj : list) {
	                    newList.add(new TTSChunk((Hashtable<String, Object>) hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }

	/**
	 * 
	 * @param ttsName
	 *            a Vector<TTSChunk> value represeting the TTS Name
	 *            <p>
	 *            <b>Notes: </b><br/>
	 *            <ul>
	 *            <li>Size must be 1-100</li>
	 *            <li>Needs to be unique over all applications</li>
	 *            <li>May not be empty</li>
	 *            <li>May not start with a new line character</li>
	 *            <li>May not interfere with any name or synonym of previously
	 *            registered applications and the following list of words</li>
	 *            <li>Needs to be unique over all applications. Applications
	 *            with the same name will be rejected</li>
	 *            </ul>
	 * @since SmartDeviceLink 2.0
	 */
    public void setTtsName(Vector<TTSChunk> ttsName) {
        if (ttsName != null) {
            parameters.put(Names.ttsName, ttsName);
        } else {
        	parameters.remove(Names.ttsName);
        }
    }
	/**
	 * Gets a String representing an abbreviated version of the mobile
	 * applincation's name (if necessary) that will be displayed on the NGN
	 * media screen
	 * 
	 * @return String -a String value representing an abbreviated version of the
	 *         mobile applincation's name
	 */    
    public String getNgnMediaScreenAppName() {
        return (String) parameters.get(Names.ngnMediaScreenAppName);
    }
	/**
	 * Sets a String representing an abbreviated version of the mobile
	 * applincation's name (if necessary) that will be displayed on the NGN
	 * media screen
	 * 
	 * @param ngnMediaScreenAppName
	 *            a String value representing an abbreviated version of the
	 *            mobile applincation's name
	 *            <p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>Must be 1-5 characters</li>
	 *            <li>If not provided, value will be derived from appName
	 *            truncated to 5 characters</li>
	 *            </ul>
	 */    
    public void setNgnMediaScreenAppName(String ngnMediaScreenAppName) {
        if (ngnMediaScreenAppName != null) {
            parameters.put(Names.ngnMediaScreenAppName, ngnMediaScreenAppName);
        } else {
        	parameters.remove(Names.ngnMediaScreenAppName);
        }
    }
	/**
	 * Gets the Vector<String> representing the an array of 1-100 elements, each
	 * element containing a voice-recognition synonym
	 * 
	 * @return Vector<String> -a Vector value representing the an array of
	 *         1-100 elements, each element containing a voice-recognition
	 *         synonym
	 */    
    @SuppressWarnings("unchecked")
    public Vector<String> getVrSynonyms() {
    	if (parameters.get(Names.vrSynonyms) instanceof Vector<?>) {
    		Vector<?> list = (Vector<?>)parameters.get(Names.vrSynonyms);
    		if (list != null && list.size()>0) {
    			Object obj = list.get(0);
    			if (obj instanceof String) {
    				return (Vector<String>) list;
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
	 *            a Vector<String> value representing the an array of 1-100
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
    public void setVrSynonyms(Vector<String> vrSynonyms) {
        if (vrSynonyms != null) {
            parameters.put(Names.vrSynonyms, vrSynonyms);
        } else {
        	parameters.remove(Names.vrSynonyms);
        }
    }
	/**
	 * Gets a Boolean representing MediaApplication
	 * 
	 * @return Boolean -a Boolean value representing a mobile application that is
	 *         a media application or not
	 */    
    public Boolean getIsMediaApplication() {
        return (Boolean) parameters.get(Names.isMediaApplication);
    }
	/**
	 * Sets a Boolean to indicate a mobile application that is a media
	 * application or not
	 * 
	 * @param isMediaApplication
	 *            a Boolean value
	 */    
    public void setIsMediaApplication(Boolean isMediaApplication) {
        if (isMediaApplication != null) {
            parameters.put(Names.isMediaApplication, isMediaApplication);
        } else {
        	parameters.remove(Names.isMediaApplication);
        }
    }
	/**
	 * Gets a Language enumeration indicating what language the application
	 * intends to use for user interaction (Display, TTS and VR)
	 * 
	 * @return Enumeration -a language enumeration
	 */    
    public Language getLanguageDesired() {
        Object obj = parameters.get(Names.languageDesired);
        if (obj instanceof Language) {
            return (Language) obj;
        } else if (obj instanceof String) {
            Language theCode = null;
            try {
                theCode = Language.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.languageDesired, e);
            }
            return theCode;
        }
        return null;
    }
	/**
	 * Sets an enumeration indicating what language the application intends to
	 * use for user interaction (Display, TTS and VR)
	 * 
	 * @param languageDesired
	 *            a Language Enumeration
	 *            <p>
	 * 
	 */    
    public void setLanguageDesired(Language languageDesired) {
        if (languageDesired != null) {
            parameters.put(Names.languageDesired, languageDesired);
        } else {
        	parameters.remove(Names.languageDesired);
        }
    }

	/**
	 * Gets an enumeration indicating what language the application intends to
	 * use for user interaction ( Display)
	 * 
	 * @return Language - a Language value representing an enumeration
	 *         indicating what language the application intends to use for user
	 *         interaction ( Display)
	 * @since SmartDeviceLink 2.0
	 */
    public Language getHmiDisplayLanguageDesired() {
        Object obj = parameters.get(Names.hmiDisplayLanguageDesired);
        if (obj instanceof Language) {
            return (Language) obj;
        } else if (obj instanceof String) {
            Language theCode = null;
            try {
                theCode = Language.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.hmiDisplayLanguageDesired, e);
            }
            return theCode;
        }
        return null;
    }

	/**
	 * Sets an enumeration indicating what language the application intends to
	 * use for user interaction ( Display)
	 * 
	 * @param hmiDisplayLanguageDesired
	 * @since SmartDeviceLink 2.0
	 */
    public void setHmiDisplayLanguageDesired(Language hmiDisplayLanguageDesired) {
        if (hmiDisplayLanguageDesired != null) {
            parameters.put(Names.hmiDisplayLanguageDesired, hmiDisplayLanguageDesired);
        } else {
        	parameters.remove(Names.hmiDisplayLanguageDesired);
        }
    }

	/**
	 * Gets a list of all applicable app types stating which classifications to
	 * be given to the app.e.g. for platforms , like GEN2, this will determine
	 * which "corner(s)" the app can populate
	 * 
	 * @return Vector<AppHMIType> - a Vector value representing a list of all
	 *         applicable app types stating which classifications to be given to
	 *         the app
	 * @since SmartDeviceLinke 2.0
	 */
    @SuppressWarnings("unchecked")
    public Vector<AppHMIType> getAppHMIType() {
        if (parameters.get(Names.appHMIType) instanceof Vector<?>) {
	    	Vector<?> list = (Vector<?>)parameters.get(Names.appHMIType);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof AppHMIType) {
	                return (Vector<AppHMIType>) list;
	            } else if (obj instanceof String) {
	                Vector<AppHMIType> newList = new Vector<AppHMIType>();
	                for (Object hashObj : list) {
	                    String strFormat = (String)hashObj;
	                    AppHMIType toAdd = null;
	                    try {
	                        toAdd = AppHMIType.valueForString(strFormat);
	                    } catch (Exception e) {
	                    	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + Names.appHMIType, e);	                    }
	                    if (toAdd != null) {
	                        newList.add(toAdd);
	                    }
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }

	/**
	 * Sets a a list of all applicable app types stating which classifications
	 * to be given to the app. e.g. for platforms , like GEN2, this will
	 * determine which "corner(s)" the app can populate
	 * 
	 * @param appHMIType
	 *            a Vector<AppHMIType>
	 *            <p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>Array Minsize: = 1</li>
	 *            <li>Array Maxsize = 100</li>
	 *            </ul>
	 * @since SmartDeviceLink 2.0
	 */
    public void setAppHMIType(Vector<AppHMIType> appHMIType) {
        if (appHMIType != null) {
            parameters.put(Names.appHMIType, appHMIType);
        } else {
        	parameters.remove(Names.appHMIType);
        }
    }
    
    public String getHashID() {
        return (String) parameters.get(Names.hashID);
    }
   
    public void setHashID(String hashID) {
        if (hashID != null) {
            parameters.put(Names.hashID, hashID);
        } else {
        	parameters.remove(Names.hashID);
        }
    }        
    
	/**
	 * Gets the unique ID, which an app will be given when approved
	 * 
	 * @return String - a String value representing the unique ID, which an app
	 *         will be given when approved
	 * @since SmartDeviceLink 2.0
	 */
    public String getAppID() {
        return (String) parameters.get(Names.appID);
    }

	/**
	 * Sets a unique ID, which an app will be given when approved
	 * 
	 * @param appID
	 *            a String value representing a unique ID, which an app will be
	 *            given when approved
	 *            <p>
	 *            <b>Notes: </b>Maxlength = 100
	 * @since SmartDeviceLink 2.0
	 */
    public void setAppID(String appID) {
        if (appID != null) {
            parameters.put(Names.appID, appID);
        } else {
        	parameters.remove(Names.appID);
        }
    }
}
