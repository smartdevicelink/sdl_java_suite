package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Set Display Layout Response is sent, when SetDisplayLayout has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class SetDisplayLayoutResponse extends RPCResponse {
	public static final String KEY_BUTTON_CAPABILITIES = "buttonCapabilities";
	public static final String KEY_DISPLAY_CAPABILITIES = "displayCapabilities";
    public static final String KEY_SOFT_BUTTON_CAPABILITIES = "softButtonCapabilities";
    public static final String KEY_PRESET_BANK_CAPABILITIES = "presetBankCapabilities";

	/**
	 * Constructs a new SetDisplayLayoutResponse object
	 */
    public SetDisplayLayoutResponse() {
        super(FunctionID.SET_DISPLAY_LAYOUT.toString());
    }

	/**
	 * Constructs a new SetDisplayLayoutResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public SetDisplayLayoutResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    
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

    public void setDisplayCapabilities(DisplayCapabilities displayCapabilities) {
        if (displayCapabilities != null) {
            parameters.put(KEY_DISPLAY_CAPABILITIES, displayCapabilities);
        } else {
            parameters.remove(KEY_DISPLAY_CAPABILITIES);
        }
    }

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

    public void setPresetBankCapabilities(PresetBankCapabilities presetBankCapabilities) {
        if (presetBankCapabilities != null) {
            parameters.put(KEY_PRESET_BANK_CAPABILITIES, presetBankCapabilities);
        } else {
            parameters.remove(KEY_PRESET_BANK_CAPABILITIES);
        }
    }
    
}
