package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static com.smartdevicelink.proxy.constants.Names.displayCapabilities;

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
	 * <p></p>
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
        setParameters(KEY_DISPLAY_CAPABILITIES, displayCapabilities);
    }

    @SuppressWarnings("unchecked")
    public List<ButtonCapabilities> getButtonCapabilities() {
        if (parameters.get(KEY_BUTTON_CAPABILITIES) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_BUTTON_CAPABILITIES);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof ButtonCapabilities) {
	                return (List<ButtonCapabilities>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<ButtonCapabilities> newList = new ArrayList<ButtonCapabilities>();
	                for (Object hashObj : list) {
	                    newList.add(new ButtonCapabilities((Hashtable<String, Object>)hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }

    public void setButtonCapabilities(List<ButtonCapabilities> buttonCapabilities) {
        setParameters(KEY_BUTTON_CAPABILITIES, buttonCapabilities);
    }

    @SuppressWarnings("unchecked")
    public List<SoftButtonCapabilities> getSoftButtonCapabilities() {
        if (parameters.get(KEY_SOFT_BUTTON_CAPABILITIES) instanceof List<?>) {
            List<?> list = (List<?>)parameters.get(KEY_SOFT_BUTTON_CAPABILITIES);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof SoftButtonCapabilities) {
	                return (List<SoftButtonCapabilities>) list;
	            } else if (obj instanceof Hashtable) {
	            	List<SoftButtonCapabilities> newList = new ArrayList<SoftButtonCapabilities>();
	                for (Object hashObj : list) {
	                    newList.add(new SoftButtonCapabilities((Hashtable<String, Object>)hashObj));
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }

    public void setSoftButtonCapabilities(List<SoftButtonCapabilities> softButtonCapabilities) {
        setParameters(KEY_SOFT_BUTTON_CAPABILITIES, softButtonCapabilities);
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
        setParameters(KEY_PRESET_BANK_CAPABILITIES, presetBankCapabilities);
    }
    
}
