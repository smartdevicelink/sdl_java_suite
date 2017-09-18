package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.Version;
import com.smartdevicelink.proxy.rpc.enums.HmiZoneCapabilities;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.PrerecordedSpeech;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.enums.VrCapabilities;

import java.util.Hashtable;
import java.util.List;

/**
 * Register AppInterface Response is sent, when RegisterAppInterface has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class RegisterAppInterfaceResponse extends RPCResponse {
	public static final String KEY_VEHICLE_TYPE 				= "vehicleType";
	public static final String KEY_SPEECH_CAPABILITIES 			= "speechCapabilities";
	public static final String KEY_VR_CAPABILITIES 				= "vrCapabilities";
	public static final String KEY_AUDIO_PASS_THRU_CAPABILITIES = "audioPassThruCapabilities";
	public static final String KEY_HMI_ZONE_CAPABILITIES 		= "hmiZoneCapabilities";
    public static final String KEY_PRERECORDED_SPEECH 			= "prerecordedSpeech";
    public static final String KEY_SUPPORTED_DIAG_MODES 		= "supportedDiagModes";
    public static final String KEY_SDL_MSG_VERSION 				= "syncMsgVersion";
    public static final String KEY_LANGUAGE 					= "language";
    public static final String KEY_BUTTON_CAPABILITIES 			= "buttonCapabilities";
    public static final String KEY_DISPLAY_CAPABILITIES 		= "displayCapabilities";
    public static final String KEY_HMI_DISPLAY_LANGUAGE 		= "hmiDisplayLanguage";
    public static final String KEY_SOFT_BUTTON_CAPABILITIES 	= "softButtonCapabilities";
    public static final String KEY_PRESET_BANK_CAPABILITIES 	= "presetBankCapabilities";
    public static final String KEY_HMI_CAPABILITIES 			= "hmiCapabilities"; //As of v4.0
    public static final String KEY_SDL_VERSION 					= "sdlVersion"; //As of v4.0
    public static final String KEY_SYSTEM_SOFTWARE_VERSION		= "systemSoftwareVersion"; //As of v4.0

    
	/**
	 * Constructs a new RegisterAppInterfaceResponse object
	 */
    public RegisterAppInterfaceResponse() {
        super(FunctionID.REGISTER_APP_INTERFACE.toString());
    }

	/**
	 * Constructs a new RegisterAppInterfaceResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public RegisterAppInterfaceResponse(Hashtable<String, Object> hash) {
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
		return (SdlMsgVersion) getObject(SdlMsgVersion.class, KEY_SDL_MSG_VERSION);
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
		setParameters(KEY_SDL_MSG_VERSION, sdlMsgVersion);
    }

	/**
	 * Gets a Language enumeration indicating what language the application
	 * intends to use for user interaction (Display, TTS and VR)
	 * 
	 * @return Enumeration -a language enumeration
	 */
    public Language getLanguage() {
		return (Language) getObject(Language.class, KEY_LANGUAGE);
    }

	/**
	 * Sets an enumeration indicating what language the application intends to
	 * use for user interaction (Display, TTS and VR)
	 * 
	 * @param language
	 *            a Language Enumeration
	 *           
	 * 
	 */
    public void setLanguage(Language language) {
		setParameters(KEY_LANGUAGE, language);
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
    public Language getHmiDisplayLanguage() {
		return (Language) getObject(Language.class, KEY_HMI_DISPLAY_LANGUAGE);
    }

	/**
	 * Sets an enumeration indicating what language the application intends to
	 * use for user interaction ( Display)
	 * 
	 * @param hmiDisplayLanguage
	 * @since SmartDeviceLink 2.0
	 */
    public void setHmiDisplayLanguage(Language hmiDisplayLanguage) {
		setParameters(KEY_HMI_DISPLAY_LANGUAGE, hmiDisplayLanguage);
    }

	/**
	 * Gets getDisplayCapabilities set when application interface is registered.
	 * 
	 * @return DisplayCapabilities
	 */
    @SuppressWarnings("unchecked")
    public DisplayCapabilities getDisplayCapabilities() {
		return (DisplayCapabilities) getObject(DisplayCapabilities.class, KEY_DISPLAY_CAPABILITIES);
    }
    /**
     * Sets Display Capabilities
     * @param displayCapabilities
     */
    public void setDisplayCapabilities(DisplayCapabilities displayCapabilities) {
		setParameters(KEY_DISPLAY_CAPABILITIES, displayCapabilities);
    }

	/**
	 * Gets buttonCapabilities set when application interface is registered.
	 * 
	 * @return buttonCapabilities
	 */
    @SuppressWarnings("unchecked")
    public List<ButtonCapabilities> getButtonCapabilities() {
		return (List<ButtonCapabilities>) getObject(ButtonCapabilities.class, KEY_BUTTON_CAPABILITIES);
    }
    /**
     * Sets Button Capabilities
     * @param buttonCapabilities
     */
    public void setButtonCapabilities(List<ButtonCapabilities> buttonCapabilities) {
		setParameters(KEY_BUTTON_CAPABILITIES, buttonCapabilities);
    }
    /**
	 * Gets getSoftButtonCapabilities set when application interface is registered.
	 * 
	 * @return SoftButtonCapabilities 
	 */
    @SuppressWarnings("unchecked")
    public List<SoftButtonCapabilities> getSoftButtonCapabilities() {
		return (List<SoftButtonCapabilities>) getObject(SoftButtonCapabilities.class, KEY_SOFT_BUTTON_CAPABILITIES);
    }
    /**
     * Sets softButtonCapabilities
     * @param softButtonCapabilities
     */
    public void setSoftButtonCapabilities(List<SoftButtonCapabilities> softButtonCapabilities) {
		setParameters(KEY_SOFT_BUTTON_CAPABILITIES, softButtonCapabilities);
    }

	/**
	 * Gets getPresetBankCapabilities set when application interface is registered.
	 * 
	 * @return PresetBankCapabilities 
	 */
    @SuppressWarnings("unchecked")
    public PresetBankCapabilities getPresetBankCapabilities() {
		return (PresetBankCapabilities) getObject(PresetBankCapabilities.class, KEY_PRESET_BANK_CAPABILITIES);
    }
    /**
     * Sets presetBankCapabilities
     * @param	presetBankCapabilities
     */
    public void setPresetBankCapabilities(PresetBankCapabilities presetBankCapabilities) {
		setParameters(KEY_PRESET_BANK_CAPABILITIES, presetBankCapabilities);
    }
	
	/**
	 * Gets hmiZoneCapabilities set when application interface is registered.
	 * 
	 * @return HmiZoneCapabilities
	 */
    @SuppressWarnings("unchecked")
    public List<HmiZoneCapabilities> getHmiZoneCapabilities() {
		return (List<HmiZoneCapabilities>) getObject(HmiZoneCapabilities.class, KEY_HMI_ZONE_CAPABILITIES);
    }
    /**
     * Sets hmiZoneCapabilities
     * @param hmiZoneCapabilities
     */
    public void setHmiZoneCapabilities(List<HmiZoneCapabilities> hmiZoneCapabilities) {
		setParameters(KEY_HMI_ZONE_CAPABILITIES, hmiZoneCapabilities);
    }
	
	/**
	 * Gets speechCapabilities set when application interface is registered.
	 * 
	 * @return SpeechCapabilities
	 */
    @SuppressWarnings("unchecked")
    public List<SpeechCapabilities> getSpeechCapabilities() {
		return (List<SpeechCapabilities>) getObject(SpeechCapabilities.class, KEY_SPEECH_CAPABILITIES);
    }
    /**
     * Sets speechCapabilities
     * @param speechCapabilities
     */
    public void setSpeechCapabilities(List<SpeechCapabilities> speechCapabilities) {
		setParameters(KEY_SPEECH_CAPABILITIES, speechCapabilities);
    }

    
    @SuppressWarnings("unchecked")
    public List<PrerecordedSpeech> getPrerecordedSpeech() {
		return (List<PrerecordedSpeech>) getObject(PrerecordedSpeech.class, KEY_PRERECORDED_SPEECH);
    }

    public void setPrerecordedSpeech(List<PrerecordedSpeech> prerecordedSpeech) {
		setParameters(KEY_PRERECORDED_SPEECH, prerecordedSpeech);
    }
 
    
	/**
	 * Gets vrCapabilities set when application interface is registered.
	 * 
	 * @return VrCapabilities
	 */
    @SuppressWarnings("unchecked")
    public List<VrCapabilities> getVrCapabilities() {
		return (List<VrCapabilities>) getObject(VrCapabilities.class, KEY_VR_CAPABILITIES);
    }
    /**
     * Sets VrCapabilities
     * @param vrCapabilities
     */
    public void setVrCapabilities(List<VrCapabilities> vrCapabilities) {
		setParameters(KEY_VR_CAPABILITIES, vrCapabilities);
    }
	
	/**
	 * Gets getVehicleType set when application interface is registered.
	 * 
	 * @return vehicleType 
	 */
    @SuppressWarnings("unchecked")
    public VehicleType getVehicleType() {
		return (VehicleType) getObject(VehicleType.class, KEY_VEHICLE_TYPE);
    }
    /**
     * Sets vehicleType
     * @param vehicleType
     */
    public void setVehicleType(VehicleType vehicleType) {
		setParameters(KEY_VEHICLE_TYPE, vehicleType);
    }
	
	/**
	 * Gets AudioPassThruCapabilities set when application interface is registered.
	 * 
	 * @return AudioPassThruCapabilities 
	 */
    @SuppressWarnings("unchecked")
    public List<AudioPassThruCapabilities> getAudioPassThruCapabilities() {
		return (List<AudioPassThruCapabilities>) getObject(AudioPassThruCapabilities.class, KEY_AUDIO_PASS_THRU_CAPABILITIES);
    }
    /**
     * Sets AudioPassThruCapabilities
     * @param audioPassThruCapabilities
     */
    public void setAudioPassThruCapabilities(List<AudioPassThruCapabilities> audioPassThruCapabilities) {
		setParameters(KEY_AUDIO_PASS_THRU_CAPABILITIES, audioPassThruCapabilities);
    }
    public String getProxyVersionInfo() {
		if (Version.VERSION != null)
			return  Version.VERSION;
	
		return null;
    }
    public void setSupportedDiagModes(List<Integer> supportedDiagModes) {
		setParameters(KEY_SUPPORTED_DIAG_MODES, supportedDiagModes);
    }

    @SuppressWarnings("unchecked")
    public List<Integer> getSupportedDiagModes() {
		return (List<Integer>) getObject(Integer.class, KEY_SUPPORTED_DIAG_MODES);
    }
    
    public void setHmiCapabilities(HMICapabilities hmiCapabilities) {
		setParameters(KEY_HMI_CAPABILITIES, hmiCapabilities);
    }

    @SuppressWarnings("unchecked")
    public HMICapabilities getHmiCapabilities() {
		return (HMICapabilities) getObject(HMICapabilities.class, KEY_HMI_CAPABILITIES);
    }  
    
    public void setSdlVersion(String sdlVersion) {
		setParameters(KEY_SDL_VERSION, sdlVersion);
    }

    public String getSdlVersion() {    
    	 return getString(KEY_SDL_VERSION);
    } 
    
    public void setSystemSoftwareVersion(String systemSoftwareVersion) {
		setParameters(KEY_SYSTEM_SOFTWARE_VERSION, systemSoftwareVersion);
    }

    public String getSystemSoftwareVersion() {    
    	 return getString(KEY_SYSTEM_SOFTWARE_VERSION);
    } 
}
