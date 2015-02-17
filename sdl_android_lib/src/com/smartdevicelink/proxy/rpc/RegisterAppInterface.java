package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;
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
	
	private SdlMsgVersion sdlMessageVersion;
	private DeviceInfo deviceInfo;
	private String appName, ngnMediaName, hashId, appId;
	private String languageDesired, hmiLanguageDesired; // represents Language enum
	private List<TTSChunk> ttsName;
	private List<String> appHmiType; // represents AppHMIType enum
	private List<String> vrSynonyms;
	private Boolean isMediaApplication;
	
	
	/**
	 * Constructs a new RegisterAppInterface object
	 */
    public RegisterAppInterface() {
        super(FunctionID.REGISTER_APP_INTERFACE);
    }
    
    /**
     * Creates a RegisterAppInterface object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public RegisterAppInterface(JSONObject jsonObject){
        super(SdlCommand.REGISTER_APP_INTERFACE, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.appName = JsonUtils.readStringFromJsonObject(jsonObject, KEY_APP_NAME);
            this.ngnMediaName = JsonUtils.readStringFromJsonObject(jsonObject, KEY_NGN_MEDIA_SCREEN_APP_NAME);
            this.hashId = JsonUtils.readStringFromJsonObject(jsonObject, KEY_HASH_ID);
            this.appId = JsonUtils.readStringFromJsonObject(jsonObject, KEY_APP_ID);
            this.languageDesired = JsonUtils.readStringFromJsonObject(jsonObject, KEY_LANGUAGE_DESIRED);
            this.hmiLanguageDesired = JsonUtils.readStringFromJsonObject(jsonObject, KEY_HMI_DISPLAY_LANGUAGE_DESIRED);
            this.isMediaApplication = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_IS_MEDIA_APPLICATION);
            
            this.appHmiType = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_APP_HMI_TYPE);
            this.vrSynonyms = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_VR_SYNONYMS);
            
            JSONObject sdlMessageVersionObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_SDL_MSG_VERSION);
            if(sdlMessageVersionObj != null){
                this.sdlMessageVersion = new SdlMsgVersion(sdlMessageVersionObj);
            }
            
            JSONObject deviceInfoObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_DEVICE_INFO);
            if(deviceInfoObj != null){
                this.deviceInfo = new DeviceInfo(deviceInfoObj);
            }
            
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
	 * Gets the version of the SDL&reg; SmartDeviceLink interface
	 * 
	 * @return SdlMsgVersion -a SdlMsgVersion object representing version of
	 *         the SDL&reg; SmartDeviceLink interface
	 */    
    public SdlMsgVersion getSdlMsgVersion() {
        return this.sdlMessageVersion;
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
        this.sdlMessageVersion = sdlMsgVersion;
    }
    
    public DeviceInfo getDeviceInfo() {
        return this.deviceInfo;
    }
    
    public void setDeviceInfo(DeviceInfo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }
    
	/**
	 * Gets Mobile Application's Name
	 * 
	 * @return String -a String representing the Mobile Application's Name
	 */    
    public String getAppName() {
        return this.appName;
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
        this.appName = appName;
    }

	/**
	 * Gets TTS string for VR recognition of the mobile application name
	 * 
	 * @return List<TTSChunk> -List value representing the TTS string
	 * @since SmartDeviceLink 2.0
	 */
    public List<TTSChunk> getTtsName() {
        return this.ttsName;
    }

	/**
	 * 
	 * @param ttsName
	 *            a List<TTSChunk> value represeting the TTS Name
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
    public void setTtsName(List<TTSChunk> ttsName) {
        this.ttsName = ttsName;
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
        return this.ngnMediaName;
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
        this.ngnMediaName = ngnMediaScreenAppName;
    }
    
	/**
	 * Gets the List<String> representing the an array of 1-100 elements, each
	 * element containing a voice-recognition synonym
	 * 
	 * @return List<String> -a List value representing the an array of
	 *         1-100 elements, each element containing a voice-recognition
	 *         synonym
	 */    
    public List<String> getVrSynonyms() {
    	return this.vrSynonyms;
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
    public void setVrSynonyms(List<String> vrSynonyms) {
        this.vrSynonyms = vrSynonyms;
    }
    
	/**
	 * Gets a Boolean representing MediaApplication
	 * 
	 * @return Boolean -a Boolean value representing a mobile application that is
	 *         a media application or not
	 */    
    public Boolean getIsMediaApplication() {
        return this.isMediaApplication;
    }
    
	/**
	 * Sets a Boolean to indicate a mobile application that is a media
	 * application or not
	 * 
	 * @param isMediaApplication
	 *            a Boolean value
	 */    
    public void setIsMediaApplication(Boolean isMediaApplication) {
        this.isMediaApplication = isMediaApplication;
    }
    
	/**
	 * Gets a Language enumeration indicating what language the application
	 * intends to use for user interaction (Display, TTS and VR)
	 * 
	 * @return Enumeration -a language enumeration
	 */    
    public Language getLanguageDesired() {
        return Language.valueForJsonName(this.languageDesired, sdlVersion);
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
        this.languageDesired = (languageDesired == null) ? null : languageDesired.getJsonName(sdlVersion);
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
        return Language.valueForJsonName(this.hmiLanguageDesired, sdlVersion);
    }

	/**
	 * Sets an enumeration indicating what language the application intends to
	 * use for user interaction ( Display)
	 * 
	 * @param hmiDisplayLanguageDesired
	 * @since SmartDeviceLink 2.0
	 */
    public void setHmiDisplayLanguageDesired(Language hmiDisplayLanguageDesired) {
        this.hmiLanguageDesired = (hmiDisplayLanguageDesired == null) ? null : hmiDisplayLanguageDesired.getJsonName(sdlVersion);
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
    public List<AppHMIType> getAppHMIType() {
        if(this.appHmiType == null){
            return null;
        }
        
        List<AppHMIType> result = new ArrayList<AppHMIType>(this.appHmiType.size());
        for(String str : this.appHmiType){
            result.add(AppHMIType.valueForJsonName(str, sdlVersion));
        }
        
        return result;
    }

	/**
	 * Sets a a list of all applicable app types stating which classifications
	 * to be given to the app. e.g. for platforms , like GEN2, this will
	 * determine which "corner(s)" the app can populate
	 * 
	 * @param appHMIType
	 *            a List<AppHMIType>
	 *            <p>
	 *            <b>Notes: </b>
	 *            <ul>
	 *            <li>Array Minsize: = 1</li>
	 *            <li>Array Maxsize = 100</li>
	 *            </ul>
	 * @since SmartDeviceLink 2.0
	 */
    public void setAppHMIType(List<AppHMIType> appHMIType) {
        if (appHMIType == null) {
            this.appHmiType = null;
        } else {
        	this.appHmiType = new ArrayList<String>(appHMIType.size());
        	for(AppHMIType type : appHMIType){
        	    this.appHmiType.add(type.getJsonName(sdlVersion));
        	}
        }
    }
    
    public String getHashID() {
        return this.hashId;
    }
   
    public void setHashID(String hashID) {
        this.hashId = hashID;
    }        
    
	/**
	 * Gets the unique ID, which an app will be given when approved
	 * 
	 * @return String - a String value representing the unique ID, which an app
	 *         will be given when approved
	 * @since SmartDeviceLink 2.0
	 */
    public String getAppID() {
        return this.appId;
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
        this.appId = appID;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_APP_ID, this.appId);
            JsonUtils.addToJsonObject(result, KEY_APP_NAME, this.appName);
            JsonUtils.addToJsonObject(result, KEY_HASH_ID, this.hashId);
            JsonUtils.addToJsonObject(result, KEY_HMI_DISPLAY_LANGUAGE_DESIRED, this.hmiLanguageDesired);
            JsonUtils.addToJsonObject(result, KEY_IS_MEDIA_APPLICATION, this.isMediaApplication);
            JsonUtils.addToJsonObject(result, KEY_LANGUAGE_DESIRED, this.languageDesired);
            JsonUtils.addToJsonObject(result, KEY_NGN_MEDIA_SCREEN_APP_NAME, this.ngnMediaName);
            
            JsonUtils.addToJsonObject(result, KEY_APP_HMI_TYPE, (this.appHmiType == null) ? null : 
                JsonUtils.createJsonArray(this.appHmiType));
            
            JsonUtils.addToJsonObject(result, KEY_VR_SYNONYMS, (this.vrSynonyms == null) ? null : 
                JsonUtils.createJsonArray(this.vrSynonyms));
    
            JsonUtils.addToJsonObject(result, KEY_TTS_NAME, (this.ttsName == null) ? null : 
                JsonUtils.createJsonArrayOfJsonObjects(this.ttsName, sdlVersion));
            
            JsonUtils.addToJsonObject(result, KEY_DEVICE_INFO, (this.deviceInfo == null) ? null :
                this.deviceInfo.getJsonParameters(sdlVersion));
            
            JsonUtils.addToJsonObject(result, KEY_SDL_MSG_VERSION, (this.sdlMessageVersion == null) ? null :
                this.sdlMessageVersion.getJsonParameters(sdlVersion));
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appHmiType == null) ? 0 : appHmiType.hashCode());
		result = prime * result + ((appId == null) ? 0 : appId.hashCode());
		result = prime * result + ((appName == null) ? 0 : appName.hashCode());
		result = prime * result + ((deviceInfo == null) ? 0 : deviceInfo.hashCode());
		result = prime * result + ((hashId == null) ? 0 : hashId.hashCode());
		result = prime * result + ((hmiLanguageDesired == null) ? 0 : hmiLanguageDesired.hashCode());
		result = prime * result + ((isMediaApplication == null) ? 0 : isMediaApplication.hashCode());
		result = prime * result + ((languageDesired == null) ? 0 : languageDesired.hashCode());
		result = prime * result + ((ngnMediaName == null) ? 0 : ngnMediaName.hashCode());
		result = prime * result + ((sdlMessageVersion == null) ? 0 : sdlMessageVersion.hashCode());
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
		RegisterAppInterface other = (RegisterAppInterface) obj;
		if (appHmiType == null) {
			if (other.appHmiType != null) { 
				return false;
			}
		} else if (!appHmiType.equals(other.appHmiType)) { 
			return false;
		}
		if (appId == null) {
			if (other.appId != null) { 
				return false;
			}
		} else if (!appId.equals(other.appId)) { 
			return false;
		}
		if (appName == null) {
			if (other.appName != null) { 
				return false;
			}
		} else if (!appName.equals(other.appName)) { 
			return false; 
		}
		if (deviceInfo == null) {
			if (other.deviceInfo != null) { 
				return false;
			}
		} else if (!deviceInfo.equals(other.deviceInfo)) { 
			return false;
		}
		if (hashId == null) {
			if (other.hashId != null) { 
				return false;
			}
		} else if (!hashId.equals(other.hashId)) { 
			return false;
		}
		if (hmiLanguageDesired == null) {
			if (other.hmiLanguageDesired != null) { 
				return false;
			}
		} else if (!hmiLanguageDesired.equals(other.hmiLanguageDesired)) { 
			return false;
		}
		if (isMediaApplication == null) {
			if (other.isMediaApplication != null) { 
				return false;
			}
		} else if (!isMediaApplication.equals(other.isMediaApplication)) { 
			return false;
		}
		if (languageDesired == null) {
			if (other.languageDesired != null) { 
				return false;
			}
		} else if (!languageDesired.equals(other.languageDesired)) { 
			return false;
		}
		if (ngnMediaName == null) {
			if (other.ngnMediaName != null) { 
				return false;
			}
		} else if (!ngnMediaName.equals(other.ngnMediaName)) { 
			return false;
		}
		if (sdlMessageVersion == null) {
			if (other.sdlMessageVersion != null) { 
				return false;
			}
		} else if (!sdlMessageVersion.equals(other.sdlMessageVersion)) { 
			return false;
		}
		if (ttsName == null) {
			if (other.ttsName != null) { 
				return false;
			}
		} else if (!ttsName.equals(other.ttsName)) { 
			return false;
		}
		if (vrSynonyms == null) {
			if (other.vrSynonyms != null) { 
				return false;
			}
		} else if (!vrSynonyms.equals(other.vrSynonyms)) { 
			return false;
		}
		return true;
	}
}
