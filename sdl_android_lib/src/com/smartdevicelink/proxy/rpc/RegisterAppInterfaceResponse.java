package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.Version;
import com.smartdevicelink.proxy.rpc.enums.HmiZoneCapabilities;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.PrerecordedSpeech;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.enums.VrCapabilities;
import com.smartdevicelink.util.DebugTool;

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
        Object obj = parameters.get(KEY_SDL_MSG_VERSION);
        if (obj instanceof SdlMsgVersion) {
        	return (SdlMsgVersion)obj;
        } else if (obj instanceof Hashtable) {
        	return new SdlMsgVersion((Hashtable<String, Object>)obj);
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
            parameters.put(KEY_SDL_MSG_VERSION, sdlMsgVersion);
        } else {
            parameters.remove(KEY_SDL_MSG_VERSION);
        }
    }

	/**
	 * Gets a Language enumeration indicating what language the application
	 * intends to use for user interaction (Display, TTS and VR)
	 * 
	 * @return Enumeration -a language enumeration
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
	 * Sets an enumeration indicating what language the application intends to
	 * use for user interaction (Display, TTS and VR)
	 * 
	 * @param language
	 *            a Language Enumeration
	 *            <p>
	 * 
	 */
    public void setLanguage(Language language) {
        if (language != null) {
            parameters.put(KEY_LANGUAGE, language);
        } else {
            parameters.remove(KEY_LANGUAGE);
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

	/**
	 * Sets an enumeration indicating what language the application intends to
	 * use for user interaction ( Display)
	 * 
	 * @param hmiDisplayLanguage
	 * @since SmartDeviceLink 2.0
	 */
    public void setHmiDisplayLanguage(Language hmiDisplayLanguage) {
        if (hmiDisplayLanguage != null) {
            parameters.put(KEY_HMI_DISPLAY_LANGUAGE, hmiDisplayLanguage);
        } else {
        	parameters.remove(KEY_HMI_DISPLAY_LANGUAGE);
        }
    }

	/**
	 * Gets getDisplayCapabilities set when application interface is registered.
	 * 
	 * @return DisplayCapabilities
	 */
    @SuppressWarnings("unchecked")
    public DisplayCapabilities getDisplayCapabilities() {
        Object obj = parameters.get(KEY_DISPLAY_CAPABILITIES);
        if (obj instanceof DisplayCapabilities) {
        	return (DisplayCapabilities)obj;
        } else if (obj instanceof Hashtable) {
        	return new DisplayCapabilities((Hashtable<String, Object>)obj);
        }
        return null;
    }
    /**
     * Sets Display Capabilities
     * @param displayCapabilities
     */
    public void setDisplayCapabilities(DisplayCapabilities displayCapabilities) {
        if (displayCapabilities != null) {
            parameters.put(KEY_DISPLAY_CAPABILITIES, displayCapabilities);
        } else {
        	parameters.remove(KEY_DISPLAY_CAPABILITIES);
        }
    }

	/**
	 * Gets buttonCapabilities set when application interface is registered.
	 * 
	 * @return buttonCapabilities
	 */
    @SuppressWarnings("unchecked")
    public List<ButtonCapabilities> getButtonCapabilities() {
        if (parameters.get(KEY_BUTTON_CAPABILITIES) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_BUTTON_CAPABILITIES);
	        if (list != null && list.size() > 0) {

	        	List<ButtonCapabilities> buttonCapabilitiesList  = new ArrayList<ButtonCapabilities>();

	        	boolean flagRaw  = false;
	        	boolean flagHash = false;
	        	
	        	for ( Object obj : list ) {
	        		
	        		// This does not currently allow for a mixing of types, meaning
	        		// there cannot be a raw ButtonCapabilities and a Hashtable value in the
	        		// same same list. It will not be considered valid currently.
	        		if (obj instanceof ButtonCapabilities) {
	        			if (flagHash) {
	        				return null;
	        			}

	        			flagRaw = true;

	        		} else if (obj instanceof Hashtable) {
	        			if (flagRaw) {
	        				return null;
	        			}

	        			flagHash = true;
	        			buttonCapabilitiesList.add(new ButtonCapabilities((Hashtable<String, Object>) obj));

	        		} else {
	        			return null;
	        		}

	        	}

	        	if (flagRaw) {
	        		return (List<ButtonCapabilities>) list;
	        	} else if (flagHash) {
	        		return buttonCapabilitiesList;
	        	}
	        }
        }
        return null;
    }
    /**
     * Sets Button Capabilities
     * @param buttonCapabilities
     */
    public void setButtonCapabilities(List<ButtonCapabilities> buttonCapabilities) {

    	boolean valid = true;
    	
    	for (ButtonCapabilities item : buttonCapabilities ) {
    		if (item == null) {
    			valid = false;
    		}
    	}
    	
    	if ( (buttonCapabilities != null) && (buttonCapabilities.size() > 0) && valid) {
            parameters.put(KEY_BUTTON_CAPABILITIES, buttonCapabilities);
        } else {
        	parameters.remove(KEY_BUTTON_CAPABILITIES);
        }
    }
    /**
	 * Gets getSoftButtonCapabilities set when application interface is registered.
	 * 
	 * @return SoftButtonCapabilities 
	 */
    @SuppressWarnings("unchecked")
    public List<SoftButtonCapabilities> getSoftButtonCapabilities() {
        if (parameters.get(KEY_SOFT_BUTTON_CAPABILITIES) instanceof List<?>) {
	    	List<?> list = (List<?>)parameters.get(KEY_SOFT_BUTTON_CAPABILITIES);
	        if (list != null && list.size() > 0) {

	        	List<SoftButtonCapabilities> softButtonCapabilitiesList  = new ArrayList<SoftButtonCapabilities>();

	        	boolean flagRaw  = false;
	        	boolean flagHash = false;
	        	
	        	for ( Object obj : list ) {
	        		
	        		// This does not currently allow for a mixing of types, meaning
	        		// there cannot be a raw SoftButtonCapabilities and a Hashtable value in the
	        		// same same list. It will not be considered valid currently.
	        		if (obj instanceof SoftButtonCapabilities) {
	        			if (flagHash) {
	        				return null;
	        			}

	        			flagRaw = true;

	        		} else if (obj instanceof Hashtable) {
	        			if (flagRaw) {
	        				return null;
	        			}

	        			flagHash = true;
	        			softButtonCapabilitiesList.add(new SoftButtonCapabilities((Hashtable<String, Object>) obj));

	        		} else {
	        			return null;
	        		}

	        	}

	        	if (flagRaw) {
	        		return (List<SoftButtonCapabilities>) list;
	        	} else if (flagHash) {
	        		return softButtonCapabilitiesList;
	        	}
	        }
        }
        return null;
    }
    /**
     * Sets softButtonCapabilities
     * @param softButtonCapabilities
     */
    public void setSoftButtonCapabilities(List<SoftButtonCapabilities> softButtonCapabilities) {

    	boolean valid = true;
    	
    	for (SoftButtonCapabilities item : softButtonCapabilities ) {
    		if (item == null) {
    			valid = false;
    		}
    	}
    	
    	if ( (softButtonCapabilities != null) && (softButtonCapabilities.size() > 0) && valid) {
            parameters.put(KEY_SOFT_BUTTON_CAPABILITIES, softButtonCapabilities);
        } else {
        	parameters.remove(KEY_SOFT_BUTTON_CAPABILITIES);
        }
    }

	/**
	 * Gets getPresetBankCapabilities set when application interface is registered.
	 * 
	 * @return PresetBankCapabilities 
	 */
    @SuppressWarnings("unchecked")
    public PresetBankCapabilities getPresetBankCapabilities() {
        Object obj = parameters.get(KEY_PRESET_BANK_CAPABILITIES);
        if (obj instanceof PresetBankCapabilities) {
        	return (PresetBankCapabilities)obj;
        } else if (obj instanceof Hashtable) {
        	return new PresetBankCapabilities((Hashtable<String, Object>)obj);
        }
        return null;
    }
    /**
     * Sets presetBankCapabilities
     * @param	presetBankCapabilities
     */
    public void setPresetBankCapabilities(PresetBankCapabilities presetBankCapabilities) {
        if (presetBankCapabilities != null) {
            parameters.put(KEY_PRESET_BANK_CAPABILITIES, presetBankCapabilities);
        } else {
        	parameters.remove(KEY_PRESET_BANK_CAPABILITIES);
        }
    }
	
	/**
	 * Gets hmiZoneCapabilities set when application interface is registered.
	 * 
	 * @return HmiZoneCapabilities
	 */
    @SuppressWarnings("unchecked")
    public List<HmiZoneCapabilities> getHmiZoneCapabilities() {
        if (parameters.get(KEY_HMI_ZONE_CAPABILITIES) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_HMI_ZONE_CAPABILITIES);
	        if (list != null && list.size() > 0) {	            

	        	List<HmiZoneCapabilities> hmiZoneCapabilitiesList  = new ArrayList<HmiZoneCapabilities>();

	        	boolean flagRaw  = false;
	        	boolean flagHash = false;
	        	
	        	for ( Object obj : list ) {
	        		
	        		// This does not currently allow for a mixing of types, meaning
	        		// there cannot be a raw HmiZoneCapabilities and a String value in the
	        		// same same list. It will not be considered valid currently.
	        		if (obj instanceof HmiZoneCapabilities) {
	        			if (flagHash) {
	        				return null;
	        			}

	        			flagRaw = true;

	        		} else if (obj instanceof String) {
	        			if (flagRaw) {
	        				return null;
	        			}

	        			flagHash = true;
	        			String strFormat = (String) obj;
	                    HmiZoneCapabilities toAdd = null;
	                    try {
	                        toAdd = HmiZoneCapabilities.valueForString(strFormat);
	                    } catch (Exception e) {
	                    	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_HMI_ZONE_CAPABILITIES, e);
	                    }
	                    if (toAdd != null) {
	                    	hmiZoneCapabilitiesList.add(toAdd);
	                    }

	        		} else {
	        			return null;
	        		}

	        	}

	        	if (flagRaw) {
	        		return (List<HmiZoneCapabilities>) list;
	        	} else if (flagHash) {
	        		return hmiZoneCapabilitiesList;
	        	}
	        }
        }
        return null;
    }
    /**
     * Sets hmiZoneCapabilities
     * @param hmiZoneCapabilities
     */
    public void setHmiZoneCapabilities(List<HmiZoneCapabilities> hmiZoneCapabilities) {

    	boolean valid = true;
    	
    	for (HmiZoneCapabilities item : hmiZoneCapabilities ) {
    		if (item == null) {
    			valid = false;
    		}
    	}
    	
    	if ( (hmiZoneCapabilities != null) && (hmiZoneCapabilities.size() > 0) && valid) {
            parameters.put(KEY_HMI_ZONE_CAPABILITIES, hmiZoneCapabilities);
        } else {
        	parameters.remove(KEY_HMI_ZONE_CAPABILITIES);
        }
    }
	
	/**
	 * Gets speechCapabilities set when application interface is registered.
	 * 
	 * @return SpeechCapabilities
	 */
    @SuppressWarnings("unchecked")
    public List<SpeechCapabilities> getSpeechCapabilities() {
        if (parameters.get(KEY_SPEECH_CAPABILITIES) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_SPEECH_CAPABILITIES);
	        if (list != null && list.size() > 0) {	            

	        	List<SpeechCapabilities> speechCapabilitiesList  = new ArrayList<SpeechCapabilities>();

	        	boolean flagRaw  = false;
	        	boolean flagStr = false;
	        	
	        	for ( Object obj : list ) {
	        		
	        		// This does not currently allow for a mixing of types, meaning
	        		// there cannot be a raw SpeechCapabilities and a String value in the
	        		// same same list. It will not be considered valid currently.
	        		if (obj instanceof SpeechCapabilities) {
	        			if (flagStr) {
	        				return null;
	        			}

	        			flagRaw = true;

	        		} else if (obj instanceof String) {
	        			if (flagRaw) {
	        				return null;
	        			}

	        			flagStr = true;
	        			String strFormat = (String) obj;
	                    SpeechCapabilities toAdd = null;
	                    try {
	                        toAdd = SpeechCapabilities.valueForString(strFormat);
	                    } catch (Exception e) {
	                    	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_SPEECH_CAPABILITIES, e);
	                    }
	                    if (toAdd != null) {
	                    	speechCapabilitiesList.add(toAdd);
	                    }

	        		} else {
	        			return null;
	        		}

	        	}

	        	if (flagRaw) {
	        		return (List<SpeechCapabilities>) list;
	        	} else if (flagStr) {
	        		return speechCapabilitiesList;
	        	}
	        }
        }
        return null;
    }
    /**
     * Sets speechCapabilities
     * @param speechCapabilities
     */
    public void setSpeechCapabilities(List<SpeechCapabilities> speechCapabilities) {

    	boolean valid = true;
    	
    	for (SpeechCapabilities item : speechCapabilities ) {
    		if (item == null) {
    			valid = false;
    		}
    	}
    	
    	if ( (speechCapabilities != null) && (speechCapabilities.size() > 0) && valid) {
            parameters.put(KEY_SPEECH_CAPABILITIES, speechCapabilities);
        } else {
        	parameters.remove(KEY_SPEECH_CAPABILITIES);
        }
    }

    
    @SuppressWarnings("unchecked")
    public List<PrerecordedSpeech> getPrerecordedSpeech() {
        if (parameters.get(KEY_PRERECORDED_SPEECH) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_PRERECORDED_SPEECH);
	        if (list != null && list.size() > 0) {
	        	
	        	List<PrerecordedSpeech> prerecordedSpeechList  = new ArrayList<PrerecordedSpeech>();

	        	boolean flagRaw  = false;
	        	boolean flagStr = false;
	        	
	        	for ( Object obj : list ) {
	        		
	        		// This does not currently allow for a mixing of types, meaning
	        		// there cannot be a raw PrerecordedSpeech and a String value in the
	        		// same same list. It will not be considered valid currently.
	        		if (obj instanceof PrerecordedSpeech) {
	        			if (flagStr) {
	        				return null;
	        			}

	        			flagRaw = true;

	        		} else if (obj instanceof String) {
	        			if (flagRaw) {
	        				return null;
	        			}

	        			flagStr = true;
	        			String strFormat = (String) obj;
	                    PrerecordedSpeech toAdd = null;
	                    try {
	                        toAdd = PrerecordedSpeech.valueForString(strFormat);
	                    } catch (Exception e) {
	                    	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_PRERECORDED_SPEECH, e);
	                    }
	                    if (toAdd != null) {
	                    	prerecordedSpeechList.add(toAdd);
	                    }

	        		} else {
	        			return null;
	        		}

	        	}

	        	if (flagRaw) {
	        		return (List<PrerecordedSpeech>) list;
	        	} else if (flagStr) {
	        		return prerecordedSpeechList;
	        	}
	        }
        }
        return null;
    }

    public void setPrerecordedSpeech(List<PrerecordedSpeech> prerecordedSpeech) {

    	boolean valid = true;
    	
    	for ( PrerecordedSpeech item : prerecordedSpeech ) {
    		if (item == null) {
    			valid = false;
    		}
    	}
    	
    	if ( (prerecordedSpeech != null) && (prerecordedSpeech.size() > 0) && valid) {
            parameters.put(KEY_PRERECORDED_SPEECH, prerecordedSpeech);
        } else {
        	parameters.remove(KEY_PRERECORDED_SPEECH);
        }
    }
 
    
	/**
	 * Gets vrCapabilities set when application interface is registered.
	 * 
	 * @return VrCapabilities
	 */
    @SuppressWarnings("unchecked")
    public List<VrCapabilities> getVrCapabilities() {
        if (parameters.get(KEY_VR_CAPABILITIES) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_VR_CAPABILITIES);
	        if (list != null && list.size() > 0) {

	        	List<VrCapabilities> vrCapabilitiesList  = new ArrayList<VrCapabilities>();

	        	boolean flagRaw  = false;
	        	boolean flagStr = false;
	        	
	        	for ( Object obj : list ) {
	        		
	        		// This does not currently allow for a mixing of types, meaning
	        		// there cannot be a raw VrCapabilities and a String value in the
	        		// same same list. It will not be considered valid currently.
	        		if (obj instanceof VrCapabilities) {
	        			if (flagStr) {
	        				return null;
	        			}

	        			flagRaw = true;

	        		} else if (obj instanceof String) {
	        			if (flagRaw) {
	        				return null;
	        			}

	        			flagStr = true;
	        			String strFormat = (String) obj;
	                    VrCapabilities toAdd = null;
	                    try {
	                        toAdd = VrCapabilities.valueForString(strFormat);
	                    } catch (Exception e) {
	                    	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_VR_CAPABILITIES, e);
	                    }
	                    if (toAdd != null) {
	                    	vrCapabilitiesList.add(toAdd);
	                    }

	        		} else {
	        			return null;
	        		}

	        	}

	        	if (flagRaw) {
	        		return (List<VrCapabilities>) list;
	        	} else if (flagStr) {
	        		return vrCapabilitiesList;
	        	}
	        }
        }
        return null;
    }
    /**
     * Sets VrCapabilities
     * @param vrCapabilities
     */
    public void setVrCapabilities(List<VrCapabilities> vrCapabilities) {

    	boolean valid = true;
    	
    	for ( VrCapabilities item : vrCapabilities ) {
    		if (item == null) {
    			valid = false;
    		}
    	}
    	
    	if ( (vrCapabilities != null) && (vrCapabilities.size() > 0) && valid) {
            parameters.put(KEY_VR_CAPABILITIES, vrCapabilities);
        } else {
        	parameters.remove(KEY_VR_CAPABILITIES);
        }
    }
	
	/**
	 * Gets getVehicleType set when application interface is registered.
	 * 
	 * @return vehicleType 
	 */
    @SuppressWarnings("unchecked")
    public VehicleType getVehicleType() {
        Object obj = parameters.get(KEY_VEHICLE_TYPE);
        if (obj instanceof VehicleType) {
        	return (VehicleType)obj;
        } else if (obj instanceof Hashtable) {
        	return new VehicleType((Hashtable<String, Object>)obj);
        }
        return null;
    }
    /**
     * Sets vehicleType
     * @param vehicleType
     */
    public void setVehicleType(VehicleType vehicleType) {
        if (vehicleType != null) {
            parameters.put(KEY_VEHICLE_TYPE, vehicleType);
        } else {
        	parameters.remove(KEY_VEHICLE_TYPE);
        }
    }
	
	/**
	 * Gets AudioPassThruCapabilities set when application interface is registered.
	 * 
	 * @return AudioPassThruCapabilities 
	 */
    @SuppressWarnings("unchecked")
    public List<AudioPassThruCapabilities> getAudioPassThruCapabilities() {
        if (parameters.get(KEY_AUDIO_PASS_THRU_CAPABILITIES) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_AUDIO_PASS_THRU_CAPABILITIES);
	        if (list != null && list.size() > 0) {

	        	List<AudioPassThruCapabilities> audioPassThruCapabilitiesList  = new ArrayList<AudioPassThruCapabilities>();

	        	boolean flagRaw  = false;
	        	boolean flagHash = false;
	        	
	        	for ( Object obj : list ) {
	        		
	        		// This does not currently allow for a mixing of types, meaning
	        		// there cannot be a raw AudioPassThruCapabilities and a Hashtable value in the
	        		// same same list. It will not be considered valid currently.
	        		if (obj instanceof AudioPassThruCapabilities) {
	        			if (flagHash) {
	        				return null;
	        			}

	        			flagRaw = true;

	        		} else if (obj instanceof Hashtable) {
	        			if (flagRaw) {
	        				return null;
	        			}

	        			flagHash = true;
	        			audioPassThruCapabilitiesList.add(new AudioPassThruCapabilities((Hashtable<String, Object>) obj));

	        		} else {
	        			return null;
	        		}

	        	}

	        	if (flagRaw) {
	        		return (List<AudioPassThruCapabilities>) list;
	        	} else if (flagHash) {
	        		return audioPassThruCapabilitiesList;
	        	}
	        }
        }
        return null;
    }
    /**
     * Sets AudioPassThruCapabilities
     * @param audioPassThruCapabilities
     */
    public void setAudioPassThruCapabilities(List<AudioPassThruCapabilities> audioPassThruCapabilities) {

    	boolean valid = true;
    	
    	for ( AudioPassThruCapabilities item : audioPassThruCapabilities ) {
    		if (item == null) {
    			valid = false;
    		}
    	}
    	
    	if ( (audioPassThruCapabilities != null) && (audioPassThruCapabilities.size() > 0) && valid) {
            parameters.put(KEY_AUDIO_PASS_THRU_CAPABILITIES, audioPassThruCapabilities);
        } else {
        	parameters.remove(KEY_AUDIO_PASS_THRU_CAPABILITIES);
        }
    }
    public String getProxyVersionInfo() {
		if (Version.VERSION != null)
			return  Version.VERSION;
	
		return null;
    }
    public void setSupportedDiagModes(List<Integer> supportedDiagModes) {

    	boolean valid = true;
    	
    	for ( Integer item : supportedDiagModes ) {
    		if (item == null) {
    			valid = false;
    		}
    	}
    	
    	if ( (supportedDiagModes != null) && (supportedDiagModes.size() > 0) && valid) {
        	parameters.put(KEY_SUPPORTED_DIAG_MODES, supportedDiagModes);
        }
        else
        {
        	parameters.remove(KEY_SUPPORTED_DIAG_MODES);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Integer> getSupportedDiagModes() {
        
    	if (parameters.get(KEY_SUPPORTED_DIAG_MODES) instanceof List<?>) {
    		List<?> list = (List<?>)parameters.get( KEY_SUPPORTED_DIAG_MODES);
        	if (list != null && list.size() > 0) {
        		for( Object obj : list ) {
        			if (!(obj instanceof Integer)) {
        				return null;
        			}
        		}
        		return (List<Integer>) list;
        	}
        }
        return null;
    }
    
    public void setHmiCapabilities(HMICapabilities hmiCapabilities) {
        if (hmiCapabilities != null) {
        	parameters.put(KEY_HMI_CAPABILITIES, hmiCapabilities);
        }else{
        	parameters.remove(KEY_HMI_CAPABILITIES);
        }
    }

    @SuppressWarnings("unchecked")
    public HMICapabilities getHmiCapabilities() {
    	Object obj = parameters.get(KEY_HMI_CAPABILITIES);
        if (obj instanceof HMICapabilities) {
        	return (HMICapabilities)obj;
        } else if (obj instanceof Hashtable) {
        	return new HMICapabilities((Hashtable<String, Object>)obj);
        }
        return null;
    }  
    
    public void setSdlVersion(String sdlVersion) {
        if (sdlVersion != null) {
        	parameters.put(KEY_SDL_VERSION, sdlVersion);
        }else{
        	parameters.remove(KEY_SDL_VERSION);
        }
    }

    public String getSdlVersion() {    
    	 return (String) parameters.get(KEY_SDL_VERSION);
    } 
    
    public void setSystemSoftwareVersion(String systemSoftwareVersion) {
        if (systemSoftwareVersion != null) {
        	parameters.put(KEY_SYSTEM_SOFTWARE_VERSION, systemSoftwareVersion);
        }else{
        	parameters.remove(KEY_SYSTEM_SOFTWARE_VERSION);
        }
    }

    public String getSystemSoftwareVersion() {    
    	 return (String) parameters.get(KEY_SYSTEM_SOFTWARE_VERSION);
    } 
}
