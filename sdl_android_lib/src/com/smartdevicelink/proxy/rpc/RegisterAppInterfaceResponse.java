package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.Version;
import com.smartdevicelink.proxy.rpc.enums.HmiZoneCapabilities;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.PrerecordedSpeech;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.enums.VrCapabilities;
import com.smartdevicelink.util.JsonUtils;

/**
 * Register AppInterface Response is sent, when RegisterAppInterface has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class RegisterAppInterfaceResponse extends RPCResponse {
	public static final String KEY_VEHICLE_TYPE = "vehicleType";
	public static final String KEY_SPEECH_CAPABILITIES = "speechCapabilities";
	public static final String KEY_VR_CAPABILITIES = "vrCapabilities";
	public static final String KEY_AUDIO_PASS_THRU_CAPABILITIES = "audioPassThruCapabilities";
	public static final String KEY_HMI_ZONE_CAPABILITIES = "hmiZoneCapabilities";
    public static final String KEY_PRERECORDED_SPEECH = "prerecordedSpeech";
    public static final String KEY_SUPPORTED_DIAG_MODES = "supportedDiagModes";
    public static final String KEY_SDL_MSG_VERSION = "syncMsgVersion";
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_BUTTON_CAPABILITIES = "buttonCapabilities";
    public static final String KEY_DISPLAY_CAPABILITIES = "displayCapabilities";
    public static final String KEY_HMI_DISPLAY_LANGUAGE = "hmiDisplayLanguage";
    public static final String KEY_SOFT_BUTTON_CAPABILITIES = "softButtonCapabilities";
    public static final String KEY_PRESET_BANK_CAPABILITIES = "presetBankCapabilities";
    
    private SdlMsgVersion sdlMsgVersion;
    private DisplayCapabilities displayCapabilities;
    private PresetBankCapabilities presetBankCapabilities;
    private VehicleType vehicleType;
    private List<ButtonCapabilities> buttonCapabilities;
    private List<SoftButtonCapabilities> softButtonCapabilities;
    private List<AudioPassThruCapabilities> audioPassThruCapabilities;
    private List<String> vrCapabilities; // represents VrCapabilities enum
    private List<String> prerecordedSpeech; // represents PrerecordedSpeech enum
    private List<String> speechCapabilities; // represents SpeechCapabilities enum
    private List<String> hmiZoneCapabilities; // represents HmiZoneCapabilities enum
    private List<Integer> supportedDiagModes;
    private String language, hmiLanguage; // represents Language enum
    
	/**
	 * Constructs a new RegisterAppInterfaceResponse object
	 */
    public RegisterAppInterfaceResponse() {
        super(FunctionID.REGISTER_APP_INTERFACE);
    }
    
    /**
     * Creates a RegisterAppInterfaceResponse object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public RegisterAppInterfaceResponse(JSONObject jsonObject){
        super(jsonObject);
        switch(sdlVersion){
        default:
            this.language = JsonUtils.readStringFromJsonObject(jsonObject, KEY_LANGUAGE);
            this.hmiLanguage = JsonUtils.readStringFromJsonObject(jsonObject, KEY_HMI_DISPLAY_LANGUAGE);
            
            this.supportedDiagModes = JsonUtils.readIntegerListFromJsonObject(jsonObject, KEY_SUPPORTED_DIAG_MODES);
            this.vrCapabilities = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_VR_CAPABILITIES);
            this.prerecordedSpeech = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_PRERECORDED_SPEECH);
            this.speechCapabilities = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_SPEECH_CAPABILITIES);
            this.hmiZoneCapabilities = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_HMI_ZONE_CAPABILITIES);
            
            JSONObject sdlMsgVersionObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_SDL_MSG_VERSION);
            if(sdlMsgVersionObj != null){
                this.sdlMsgVersion = new SdlMsgVersion(sdlMsgVersionObj);
            }
            
            JSONObject displayCapabilitiesObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_DISPLAY_CAPABILITIES);
            if(displayCapabilitiesObj != null){
                this.displayCapabilities = new DisplayCapabilities(displayCapabilitiesObj);
            }
            
            JSONObject presetBankCapabilitiesObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_PRESET_BANK_CAPABILITIES);
            if(presetBankCapabilitiesObj != null){
                this.presetBankCapabilities = new PresetBankCapabilities(presetBankCapabilitiesObj);
            }
            
            JSONObject vehicleTypeObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_VEHICLE_TYPE);
            if(vehicleTypeObj != null){
                this.vehicleType = new VehicleType(vehicleTypeObj);
            }
            
            List<JSONObject> buttonCapabilitiesObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_BUTTON_CAPABILITIES);
            if(buttonCapabilitiesObjs != null){
                this.buttonCapabilities = new ArrayList<ButtonCapabilities>(buttonCapabilitiesObjs.size());
                for(JSONObject buttonCapabilitiesObj : buttonCapabilitiesObjs){
                    this.buttonCapabilities.add(new ButtonCapabilities(buttonCapabilitiesObj));
                }
            }
            
            List<JSONObject> softButtonCapabilitiesObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_SOFT_BUTTON_CAPABILITIES);
            if(softButtonCapabilitiesObjs != null){
                this.softButtonCapabilities = new ArrayList<SoftButtonCapabilities>(softButtonCapabilitiesObjs.size());
                for(JSONObject softButtonCapabilitiesObj : softButtonCapabilitiesObjs){
                    this.softButtonCapabilities.add(new SoftButtonCapabilities(softButtonCapabilitiesObj));
                }
            }
            
            List<JSONObject> audioPassThruCapabilitiesObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_AUDIO_PASS_THRU_CAPABILITIES);
            if(audioPassThruCapabilitiesObjs != null){
                this.audioPassThruCapabilities = new ArrayList<AudioPassThruCapabilities>(audioPassThruCapabilitiesObjs.size());
                for(JSONObject audioPassThruCapabilitiesObj : audioPassThruCapabilitiesObjs){
                    this.audioPassThruCapabilities.add(new AudioPassThruCapabilities(audioPassThruCapabilitiesObj));
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
        return this.sdlMsgVersion;
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
        this.sdlMsgVersion = sdlMsgVersion;
    }

	/**
	 * Gets a Language enumeration indicating what language the application
	 * intends to use for user interaction (Display, TTS and VR)
	 * 
	 * @return Enumeration -a language enumeration
	 */
    public Language getLanguage() {
        return Language.valueForJsonName(this.language, sdlVersion);
    }

	/**
	 * Sets an enumeration indicating what language the application intends to
	 * use for user interaction (Display, TTS and VR)
	 * 
	 * @param language
	 *            a Language Enumeration
	 *            <p>
	 * 
	 */
    public void setLanguage(Language language) {
        this.language = (language == null) ? null : language.getJsonName(sdlVersion);
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
        return Language.valueForJsonName(this.hmiLanguage, sdlVersion);
    }

	/**
	 * Sets an enumeration indicating what language the application intends to
	 * use for user interaction ( Display)
	 * 
	 * @param hmiDisplayLanguage
	 * @since SmartDeviceLink 2.0
	 */
    public void setHmiDisplayLanguage(Language hmiDisplayLanguage) {
        this.hmiLanguage = (hmiDisplayLanguage == null) ? null : hmiDisplayLanguage.getJsonName(sdlVersion);
    }

	/**
	 * Gets getDisplayCapabilities set when application interface is registered.
	 * 
	 * @return DisplayCapabilities
	 */
    public DisplayCapabilities getDisplayCapabilities() {
        return this.displayCapabilities;
    }
    
    /**
     * Sets Display Capabilities
     * @param displayCapabilities
     */
    public void setDisplayCapabilities(DisplayCapabilities displayCapabilities) {
        this.displayCapabilities = displayCapabilities;
    }

	/**
	 * Gets buttonCapabilities set when application interface is registered.
	 * 
	 * @return buttonCapabilities
	 */
    public List<ButtonCapabilities> getButtonCapabilities() {
        return this.buttonCapabilities;
    }
    
    /**
     * Sets Button Capabilities
     * @param buttonCapabilities
     */
    public void setButtonCapabilities(List<ButtonCapabilities> buttonCapabilities) {
        this.buttonCapabilities = buttonCapabilities;
    }
    
    /**
	 * Gets getSoftButtonCapabilities set when application interface is registered.
	 * 
	 * @return SoftButtonCapabilities 
	 */
    public List<SoftButtonCapabilities> getSoftButtonCapabilities() {
        return this.softButtonCapabilities;
    }
    
    /**
     * Sets softButtonCapabilities
     * @param softButtonCapabilities
     */
    public void setSoftButtonCapabilities(List<SoftButtonCapabilities> softButtonCapabilities) {
        this.softButtonCapabilities = softButtonCapabilities;
    }

	/**
	 * Gets getPresetBankCapabilities set when application interface is registered.
	 * 
	 * @return PresetBankCapabilities 
	 */
    public PresetBankCapabilities getPresetBankCapabilities() {
        return this.presetBankCapabilities;
    }
    
    /**
     * Sets presetBankCapabilities
     * @param	presetBankCapabilities
     */
    public void setPresetBankCapabilities(PresetBankCapabilities presetBankCapabilities) {
        this.presetBankCapabilities = presetBankCapabilities;
    }
	
	/**
	 * Gets hmiZoneCapabilities set when application interface is registered.
	 * 
	 * @return HmiZoneCapabilities
	 */
    public List<HmiZoneCapabilities> getHmiZoneCapabilities() {
        if(this.hmiZoneCapabilities == null){
            return null;
        }
        
        List<HmiZoneCapabilities> result = new ArrayList<HmiZoneCapabilities>(this.hmiZoneCapabilities.size());
        for(String str : this.hmiZoneCapabilities){
            result.add(HmiZoneCapabilities.valueForJsonName(str, sdlVersion));
        }
        
        return result;
    }
    
    /**
     * Sets hmiZoneCapabilities
     * @param hmiZoneCapabilities
     */
    public void setHmiZoneCapabilities(List<HmiZoneCapabilities> hmiZoneCapabilities) {
        if(hmiZoneCapabilities == null){
            this.hmiZoneCapabilities = null;
        }
        else{
            this.hmiZoneCapabilities = new ArrayList<String>(hmiZoneCapabilities.size());
            for(HmiZoneCapabilities type : hmiZoneCapabilities){
                this.hmiZoneCapabilities.add(type.getJsonName(sdlVersion));
            }
        }
    }
	
	/**
	 * Gets speechCapabilities set when application interface is registered.
	 * 
	 * @return SpeechCapabilities
	 */
    public List<SpeechCapabilities> getSpeechCapabilities() {
        if(this.speechCapabilities == null){
            return null;
        }
        
        List<SpeechCapabilities> result = new ArrayList<SpeechCapabilities>(this.speechCapabilities.size());
        for(String str : this.speechCapabilities){
            result.add(SpeechCapabilities.valueForJsonName(str, sdlVersion));
        }
        
        return result;
    }
    
    /**
     * Sets speechCapabilities
     * @param speechCapabilities
     */
    public void setSpeechCapabilities(List<SpeechCapabilities> speechCapabilities) {
        if(speechCapabilities == null){
            this.speechCapabilities = null;
        }
        else{
            this.speechCapabilities = new ArrayList<String>(speechCapabilities.size());
            for(SpeechCapabilities type : speechCapabilities){
                this.speechCapabilities.add(type.getJsonName(sdlVersion));
            }
        }
    }
    
    public List<PrerecordedSpeech> getPrerecordedSpeech() {
        if(this.prerecordedSpeech == null){
            return null;
        }
        
        List<PrerecordedSpeech> result = new ArrayList<PrerecordedSpeech>(this.prerecordedSpeech.size());
        for(String str : this.prerecordedSpeech){
            result.add(PrerecordedSpeech.valueForJsonName(str, sdlVersion));
        }
        
        return result;
    }

    public void setPrerecordedSpeech(List<PrerecordedSpeech> prerecordedSpeech) {
        if(prerecordedSpeech == null){
            this.prerecordedSpeech = null;
        }
        else{
            this.prerecordedSpeech = new ArrayList<String>(prerecordedSpeech.size());
            for(PrerecordedSpeech type : prerecordedSpeech){
                this.prerecordedSpeech.add(type.getJsonName(sdlVersion));
            }
        }
    }
    
	/**
	 * Gets vrCapabilities set when application interface is registered.
	 * 
	 * @return VrCapabilities
	 */
    public List<VrCapabilities> getVrCapabilities() {
        if(this.vrCapabilities == null){
            return null;
        }
        
        List<VrCapabilities> result = new ArrayList<VrCapabilities>(this.vrCapabilities.size());
        for(String str : this.vrCapabilities){
            result.add(VrCapabilities.valueForJsonName(str, sdlVersion));
        }
        
        return result;
    }
    
    /**
     * Sets VrCapabilities
     * @param vrCapabilities
     */
    public void setVrCapabilities(List<VrCapabilities> vrCapabilities) {
        if(vrCapabilities == null){
            this.vrCapabilities = null;
        }
        else{
            this.vrCapabilities = new ArrayList<String>(vrCapabilities.size());
            for(VrCapabilities type : vrCapabilities){
                this.vrCapabilities.add(type.getJsonName(sdlVersion));
            }
        }
    }
	
	/**
	 * Gets getVehicleType set when application interface is registered.
	 * 
	 * @return vehicleType 
	 */
    public VehicleType getVehicleType() {
        return this.vehicleType;
    }
    
    /**
     * Sets vehicleType
     * @param vehicleType
     */
    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }
	
	/**
	 * Gets AudioPassThruCapabilities set when application interface is registered.
	 * 
	 * @return AudioPassThruCapabilities 
	 */
    public List<AudioPassThruCapabilities> getAudioPassThruCapabilities() {
        return this.audioPassThruCapabilities;
    }
    
    /**
     * Sets AudioPassThruCapabilities
     * @param audioPassThruCapabilities
     */
    public void setAudioPassThruCapabilities(List<AudioPassThruCapabilities> audioPassThruCapabilities) {
        this.audioPassThruCapabilities = audioPassThruCapabilities;
    }
    
    public String getProxyVersionInfo() {
		if (Version.VERSION != null)
			return  Version.VERSION;
	
		return null;
    }
    
    public void setSupportedDiagModes(List<Integer> supportedDiagModes) {
        this.supportedDiagModes = supportedDiagModes;
    }

    public List<Integer> getSupportedDiagModes() {
        return this.supportedDiagModes;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_LANGUAGE, this.language);
            JsonUtils.addToJsonObject(result, KEY_HMI_DISPLAY_LANGUAGE, this.hmiLanguage);
            
            JsonUtils.addToJsonObject(result, KEY_VR_CAPABILITIES, (this.vrCapabilities == null) ? null :
                JsonUtils.createJsonArray(this.vrCapabilities));
            
            JsonUtils.addToJsonObject(result, KEY_VEHICLE_TYPE, (this.vehicleType == null) ? null :
                this.vehicleType.getJsonParameters(sdlVersion));
            
            JsonUtils.addToJsonObject(result, KEY_SUPPORTED_DIAG_MODES, (this.supportedDiagModes == null) ? null :
                JsonUtils.createJsonArray(this.supportedDiagModes));
            
            JsonUtils.addToJsonObject(result, KEY_SPEECH_CAPABILITIES, (this.speechCapabilities == null) ? null :
                JsonUtils.createJsonArray(this.speechCapabilities));
            
            JsonUtils.addToJsonObject(result, KEY_SOFT_BUTTON_CAPABILITIES, (this.softButtonCapabilities == null) ? null : 
                JsonUtils.createJsonArrayOfJsonObjects(this.softButtonCapabilities, sdlVersion));
            
            JsonUtils.addToJsonObject(result, KEY_SDL_MSG_VERSION, (this.sdlMsgVersion == null) ? null :
                this.sdlMsgVersion.getJsonParameters(sdlVersion));
            
            JsonUtils.addToJsonObject(result, KEY_PRESET_BANK_CAPABILITIES, (this.presetBankCapabilities == null) ? null :
                this.presetBankCapabilities.getJsonParameters(sdlVersion));
            
            JsonUtils.addToJsonObject(result, KEY_PRERECORDED_SPEECH, (this.prerecordedSpeech == null) ? null : 
                JsonUtils.createJsonArray(this.prerecordedSpeech));
            
            JsonUtils.addToJsonObject(result, KEY_HMI_ZONE_CAPABILITIES, (this.hmiZoneCapabilities == null) ? null :
                JsonUtils.createJsonArray(this.hmiZoneCapabilities));
            
            JsonUtils.addToJsonObject(result, KEY_DISPLAY_CAPABILITIES, (this.displayCapabilities == null) ? null :
                this.displayCapabilities.getJsonParameters(sdlVersion));
            
            JsonUtils.addToJsonObject(result, KEY_BUTTON_CAPABILITIES, (this.buttonCapabilities == null) ? null :
                JsonUtils.createJsonArrayOfJsonObjects(this.buttonCapabilities, sdlVersion));
            
            JsonUtils.addToJsonObject(result, KEY_AUDIO_PASS_THRU_CAPABILITIES, (this.audioPassThruCapabilities == null) ? null : 
                JsonUtils.createJsonArrayOfJsonObjects(this.audioPassThruCapabilities, sdlVersion));
            break;
        }
        
        return result;
    }
}
