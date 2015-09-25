package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.Language;
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
 * <p></p>
 * Until the application receives its first <i>{@linkplain OnHMIStatus}</i>
 * Notification, its HMI Status is assumed to be: <i>
 * {@linkplain com.smartdevicelink.proxy.rpc.enums.HMILevel}</i>=NONE, <i>
 * {@linkplain com.smartdevicelink.proxy.rpc.enums.AudioStreamingState}
 * </i>=NOT_AUDIBLE, <i>
 * {@linkplain com.smartdevicelink.proxy.rpc.enums.SystemContext}</i>=MAIN
 * <p></p>
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
 * <p></p>
 * Resources and settings whose lifespan is tied to the duration of an
 * application's interface registration:
 * <ul>
 * <li>Choice Sets</li>
 * <li>Command Menus (built by successive calls to <i>{@linkplain AddCommand}
 * </i>)</li>
 * <li>Media clock timer display value</li>
 * <li>Media clock timer display value</li>
 * <li>Media clock timer display value</li>
 * </ul>
 * <p></p>
 * The autoActivateID is used to grant an application the HMILevel and
 * AudioStreamingState it had when it last disconnected
 * <p></p>
 * <b>Notes: </b>The autoActivateID parameter, and associated behavior, is
 * currently ignored by SDL&reg;
 * <p></p>
 * When first calling this method (i.e. first time within life cycle of mobile
 * app), an autoActivateID should not be included. After successfully
 * registering an interface, an autoActivateID is returned to the mobile
 * application for it to use in subsequent connections. If the connection
 * between SDL&reg; and the mobile application is lost, such as the vehicle is
 * turned off while the application is running, the autoActivateID can then be
 * passed in another call to RegisterAppInterface to re-acquire <i>
 * {@linkplain com.smartdevicelink.proxy.rpc.enums.HMILevel}</i>=FULL
 * <p></p>
 * If the application intends to stream audio it is important to indicate so via
 * the isMediaApp parameter. When set to true, audio will reliably stream
 * without any configuration required by the user. When not set, audio may
 * stream, depending on what the user might have manually configured as a media
 * source on SDL&reg;
 * <p></p>
 * There is no time limit for how long the autoActivateID is "valid" (i.e. would
 * confer focus and opt-in)
 * 
 *<p> <b>HMILevel is not defined before registering</b></p>
 * 
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 * 			<th>Version</th>
 * 		</tr>
 * 		<tr>
 * 			<td>MsgVersion</td>
 * 			<td>MsgVersion</td>
 * 			<td>Declares what version of the SDL interface the application expects to use with SDL</td>
 *                 <td>Y</td>
 *                 <td>To be compatible, app msg major version number must be less than or equal to SDL major version number. <p>If msg versions are incompatible, app has 20 seconds to attempt successful RegisterAppInterface (w.r.t. msg version) on underlying protocol session, else will be terminated. Major version number is a compatibility declaration. Minor version number indicates minor functional variations (e.g. features, capabilities, bug fixes) when sent from SDL to app (in RegisterAppInterface response).</p>However, the minor version number sent from the app to SDL (in RegisterAppInterface request) is ignored by SDL.</td>
 * 			<td>SmartDeviceLink 1.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>appName</td>
 * 			<td>String</td>
 * 			<td>The mobile application's name. This name is displayed in the SDL Mobile Applications menu. It also serves as the unique identifier of the application for SDL .</td>
 *                 <td>Y</td>
 *                 <td><p> Must be 1-100 characters in length. Must consist of following characters: </p><p>May not be the same (by case insensitive comparison) as the name or any synonym of any currently registered application.</p> </td>
 * 			<td>SmartDeviceLink 1.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>ttsName</td>
 * 			<td>TTSChunk</td>
 * 			<td>TTS string for VR recognition of the mobile application name. Meant to overcome any failing on speech engine in properly pronouncing / understanding app name.</td>
 *                 <td>N</td>
 *                 <td><p>Size must be 1-100 Needs to be unique over all applications. May not be empty.<p>May not start with a new line character.</p></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>ngnMediaScreenAppName</td>
 * 			<td>String</td>
 * 			<td>Provides an abbreviated version of the app name (if necessary) that will be displayed on the NGN media screen.</td>
 *                 <td>N</td>
 *                 <td>- Must be 1-5 characters. If not provided, value will be derived from appName truncated to 5 characters.</td>
 * 			<td>SmartDeviceLink 1.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>vrSynonyms</td>
 * 			<td>String</td>
 * 			<td>An array of 1-100 elements, each element containing a voice-recognition synonym by which this app can be called when being addressed in the mobile applications menu.</td>
 *                 <td>N</td>
 *                 <td>Each vr synonym is limited to 40 characters, and there can be 1-100 synonyms in array. May not be the same (by case insensitive comparison) as the name or any synonym of any currently-registered application.</td>
 * 			<td>SmartDeviceLink 1.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>isMediaApplication</td>
 * 			<td>Boolean</td>
 * 			<td>Indicates that the application will be streaming audio to SDL (via A2DP) that is audible outside of the BT media source.</td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 1.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>languageDesired</td>
 * 			<td>Language</td>
 * 			<td>An enumeration indicating what language the application intends to use for user interaction (Display, TTS and VR).</td>
 *                 <td>Y</td>
 *                 <td>If the language indicated does not match the active language on SDL, the interface registration will be rejected.If the user changes the SDL language while this interface registration is active, the interface registration will be terminated. </td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>hmiDisplayLanguageDesired</td>
 * 			<td>Language</td>
 * 			<td>An enumeration indicating what language the application intends to use for user interaction ( Display).</td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>appHMIType</td>
 * 			<td>AppHMIType</td>
 * 			<td>List of all applicable app types stating which classifications to be given to the app.e.g. for platforms , like GEN2, this will determine which "corner(s)" the app can populate</td>
 *                 <td>N</td>
 *                 <td>Array Minsize: 1; Array Maxsize: 100</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>hashID</td>
 * 			<td>String</td>
 * 			<td>ID used to uniquely identify current state of all app data that can persist through connection cycles (e.g. ignition cycles).This registered data (commands, submenus, choice sets, etc.) can be reestablished without needing to explicitly reregister each piece. If omitted, then the previous state of an app's commands, etc. will not be restored.When sending hashID, all RegisterAppInterface parameters should still be provided (e.g. ttsName, etc.). </td>
 *                 <td>N</td>
 *                 <td>maxlength:100</td>
 * 			<td>SmartDeviceLink 2.3.1 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>deviceInfo</td>
 * 			<td>DeviceInfo</td>
 * 			<td>Various information abount connecting device.</td>
 *                 <td>N</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 2.3.1 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>appID</td>
 * 			<td>String</td>
 * 			<td>ID used to validate app with policy table entries</td>
 *                 <td>Y</td>
 *                 <td>Maxlength: 100</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 
 * 		<tr>
 * 			<td>hmiCapabilities</td>
 * 			<td>HMICapabilities</td>
 * 			<td>Specifies the HMI capabilities.</td>
 *                 <td>N</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 2.3.2.2 </td>
 * 		</tr>
 * 
 * 		<tr>
 * 			<td>sdlVersion</td>
 * 			<td>String</td>
 * 			<td>The SmartDeviceLink version.</td>
 *                 <td>N</td>
 *                 <td>Maxlength: 100</td>
 * 			<td>SmartDeviceLink 2.3.2.2</td>
 * 		</tr>
 * 
 * 		<tr>
 * 			<td>systemSoftwareVersion</td>
 * 			<td>String</td>
 * 			<td>The software version of the system that implements the SmartDeviceLink core.</td>
 *                 <td>N</td>
 *                 <td>Maxlength: 100</td>
 * 			<td>SmartDeviceLink 2.3.2.2</td>
 * 		</tr>
 *  </table>
 *  <p></p>
 * @since SmartDeviceLink 1.0
 * @see UnregisterAppInterface
 * @see OnAppInterfaceUnregistered
 */
public class RegisterAppInterface extends RPCRequest {
	public static final String KEY_TTS_NAME = "ttsName";
	public static final String KEY_HMI_DISPLAY_LANGUAGE_DESIRED = "hmiDisplayLanguageDesired";
	public static final String KEY_APP_HMI_TYPE = "appHMIType";
	public static final String KEY_APP_ID = "appID";
	public static final String KEY_LANGUAGE_DESIRED = "languageDesired";
	public static final String KEY_DEVICE_INFO = "deviceInfo";
	public static final String KEY_APP_NAME = "appName";
	public static final String KEY_NGN_MEDIA_SCREEN_APP_NAME = "ngnMediaScreenAppName";
	public static final String KEY_IS_MEDIA_APPLICATION = "isMediaApplication";
	public static final String KEY_VR_SYNONYMS = "vrSynonyms";
	public static final String KEY_SDL_MSG_VERSION = "syncMsgVersion";
	public static final String KEY_HASH_ID = "hashID";
	/**
	 * Constructs a new RegisterAppInterface object
	 */
    public RegisterAppInterface() {
        super(FunctionID.REGISTER_APP_INTERFACE.toString());
    }
	/**
	 * Constructs a new RegisterAppInterface object indicated by the Hashtable
	 * parameter
	 * <p></p>
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
        Object obj = parameters.get(KEY_SDL_MSG_VERSION);
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
	 *            <p></p>
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
            parameters.put(KEY_SDL_MSG_VERSION, sdlMsgVersion);
        } else {
        	parameters.remove(KEY_SDL_MSG_VERSION);
        }
    }
    
    @SuppressWarnings("unchecked")
    public DeviceInfo getDeviceInfo() {
        Object obj = parameters.get(KEY_DEVICE_INFO);
        if (obj instanceof DeviceInfo) {
        	return (DeviceInfo) obj;
        } else if (obj instanceof Hashtable) {
        	return new DeviceInfo((Hashtable<String, Object>) obj);
        }
        return null;
    }    
    
    public void setDeviceInfo(DeviceInfo deviceInfo) {
        if (deviceInfo != null) {
            parameters.put(KEY_DEVICE_INFO, deviceInfo);
        } else {
        	parameters.remove(KEY_DEVICE_INFO);
        }
    }    
	/**
	 * Gets Mobile Application's Name
	 * 
	 * @return String -a String representing the Mobile Application's Name
	 */    
    public String getAppName() {
        return (String) parameters.get(KEY_APP_NAME);
    }
	/**
	 * Sets Mobile Application's Name, This name is displayed in the SDL&reg;
	 * Mobile Applications menu. It also serves as the unique identifier of the
	 * application for SmartDeviceLink
	 * 
	 * @param appName
	 *            a String value representing the Mobile Application's Name
	 *            <p></p>
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
            parameters.put(KEY_APP_NAME, appName);
        } else {
        	parameters.remove(KEY_APP_NAME);
        }
    }

	/**
	 * Gets TTS string for VR recognition of the mobile application name
	 * 
	 * @return List<TTSChunk> -List value representing the TTS string
	 * @since SmartDeviceLink 2.0
	 */
    @SuppressWarnings("unchecked")
    public List<TTSChunk> getTtsName() {
        if (parameters.get(KEY_TTS_NAME) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_TTS_NAME);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof TTSChunk) {
	                return (List<TTSChunk>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<TTSChunk> newList = new ArrayList<TTSChunk>();
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
	 *            a List<TTSChunk> value represeting the TTS Name
	 *            <p></p>
	 *            <b>Notes: </b>
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
    public void setTtsName(List<TTSChunk> ttsName) {
        if (ttsName != null) {
            parameters.put(KEY_TTS_NAME, ttsName);
        } else {
        	parameters.remove(KEY_TTS_NAME);
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
        return (String) parameters.get(KEY_NGN_MEDIA_SCREEN_APP_NAME);
    }
	/**
	 * Sets a String representing an abbreviated version of the mobile
	 * applincation's name (if necessary) that will be displayed on the NGN
	 * media screen
	 * 
	 * @param ngnMediaScreenAppName
	 *            a String value representing an abbreviated version of the
	 *            mobile applincation's name
	 *            <p></p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>Must be 1-5 characters</li>
	 *            <li>If not provided, value will be derived from appName
	 *            truncated to 5 characters</li>
	 *            </ul>
	 */    
    public void setNgnMediaScreenAppName(String ngnMediaScreenAppName) {
        if (ngnMediaScreenAppName != null) {
            parameters.put(KEY_NGN_MEDIA_SCREEN_APP_NAME, ngnMediaScreenAppName);
        } else {
        	parameters.remove(KEY_NGN_MEDIA_SCREEN_APP_NAME);
        }
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
	 *            <p></p>
	 *            <b>Notes: </b>
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
	/**
	 * Gets a Boolean representing MediaApplication
	 * 
	 * @return Boolean -a Boolean value representing a mobile application that is
	 *         a media application or not
	 */    
    public Boolean getIsMediaApplication() {
        return (Boolean) parameters.get(KEY_IS_MEDIA_APPLICATION);
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
            parameters.put(KEY_IS_MEDIA_APPLICATION, isMediaApplication);
        } else {
        	parameters.remove(KEY_IS_MEDIA_APPLICATION);
        }
    }
	/**
	 * Gets a Language enumeration indicating what language the application
	 * intends to use for user interaction (Display, TTS and VR)
	 * 
	 * @return Enumeration -a language enumeration
	 */    
    public Language getLanguageDesired() {
        Object obj = parameters.get(KEY_LANGUAGE_DESIRED);
        if (obj instanceof Language) {
            return (Language) obj;
        } else if (obj instanceof String) {
            return Language.valueForString((String) obj);
        }
        return null;
    }
	/**
	 * Sets an enumeration indicating what language the application intends to
	 * use for user interaction (Display, TTS and VR)
	 * 
	 * @param languageDesired
	 *            a Language Enumeration
	 *            
	 * 
	 */    
    public void setLanguageDesired(Language languageDesired) {
        if (languageDesired != null) {
            parameters.put(KEY_LANGUAGE_DESIRED, languageDesired);
        } else {
        	parameters.remove(KEY_LANGUAGE_DESIRED);
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
        Object obj = parameters.get(KEY_HMI_DISPLAY_LANGUAGE_DESIRED);
        if (obj instanceof Language) {
            return (Language) obj;
        } else if (obj instanceof String) {
            return Language.valueForString((String) obj);
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
            parameters.put(KEY_HMI_DISPLAY_LANGUAGE_DESIRED, hmiDisplayLanguageDesired);
        } else {
        	parameters.remove(KEY_HMI_DISPLAY_LANGUAGE_DESIRED);
        }
    }

	/**
	 * Gets a list of all applicable app types stating which classifications to
	 * be given to the app.e.g. for platforms , like GEN2, this will determine
	 * which "corner(s)" the app can populate
	 * 
	 * @return List<AppHMIType> - a List value representing a list of all
	 *         applicable app types stating which classifications to be given to
	 *         the app
	 * @since SmartDeviceLinke 2.0
	 */
    @SuppressWarnings("unchecked")
    public List<AppHMIType> getAppHMIType() {
        if (parameters.get(KEY_APP_HMI_TYPE) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_APP_HMI_TYPE);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof AppHMIType) {
	                return (List<AppHMIType>) list;
	            } else if (obj instanceof String) {
	            	List<AppHMIType> newList = new ArrayList<AppHMIType>();
	                for (Object hashObj : list) {
	                    String strFormat = (String)hashObj;
	                    AppHMIType toAdd = AppHMIType.valueForString(strFormat);
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
	 *            a List<AppHMIType>
	 *            <p></p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>Array Minsize: = 1</li>
	 *            <li>Array Maxsize = 100</li>
	 *            </ul>
	 * @since SmartDeviceLink 2.0
	 */
    public void setAppHMIType(List<AppHMIType> appHMIType) {
        if (appHMIType != null) {
            parameters.put(KEY_APP_HMI_TYPE, appHMIType);
        } else {
        	parameters.remove(KEY_APP_HMI_TYPE);
        }
    }
    
    public String getHashID() {
        return (String) parameters.get(KEY_HASH_ID);
    }
   
    public void setHashID(String hashID) {
        if (hashID != null) {
            parameters.put(KEY_HASH_ID, hashID);
        } else {
        	parameters.remove(KEY_HASH_ID);
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
        return (String) parameters.get(KEY_APP_ID);
    }

	/**
	 * Sets a unique ID, which an app will be given when approved
	 * 
	 * @param appID
	 *            a String value representing a unique ID, which an app will be
	 *            given when approved
	 *            <p></p>
	 *            <b>Notes: </b>Maxlength = 100
	 * @since SmartDeviceLink 2.0
	 */
    public void setAppID(String appID) {
        if (appID != null) {
            parameters.put(KEY_APP_ID, appID);
        } else {
        	parameters.remove(KEY_APP_ID);
        }
    }
}
