package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

import java.util.Hashtable;
import java.util.List;

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
        return (DisplayCapabilities) getObject(DisplayCapabilities.class, KEY_DISPLAY_CAPABILITIES);
    }

    public void setDisplayCapabilities(DisplayCapabilities displayCapabilities) {
        setParameters(KEY_DISPLAY_CAPABILITIES, displayCapabilities);
    }

    @SuppressWarnings("unchecked")
    public List<ButtonCapabilities> getButtonCapabilities() {
        return (List<ButtonCapabilities>) getObject(ButtonCapabilities.class, KEY_BUTTON_CAPABILITIES);
    }

    public void setButtonCapabilities(List<ButtonCapabilities> buttonCapabilities) {
        setParameters(KEY_BUTTON_CAPABILITIES, buttonCapabilities);
    }

    @SuppressWarnings("unchecked")
    public List<SoftButtonCapabilities> getSoftButtonCapabilities() {
        return (List<SoftButtonCapabilities>) getObject(SoftButtonCapabilities.class, KEY_SOFT_BUTTON_CAPABILITIES);
    }

    public void setSoftButtonCapabilities(List<SoftButtonCapabilities> softButtonCapabilities) {
        setParameters(KEY_SOFT_BUTTON_CAPABILITIES, softButtonCapabilities);
    }

    @SuppressWarnings("unchecked")
    public PresetBankCapabilities getPresetBankCapabilities() {
        return (PresetBankCapabilities) getObject(PresetBankCapabilities.class, KEY_PRESET_BANK_CAPABILITIES);
    }

    public void setPresetBankCapabilities(PresetBankCapabilities presetBankCapabilities) {
        setParameters(KEY_PRESET_BANK_CAPABILITIES, presetBankCapabilities);
    }
    
}
