package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

/**
 * Contains information about on-screen preset capabilities.
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>onScreenPresetsAvailable</td>
 * 			<td>Boolean</td>
 * 			<td>Defines, if Onscreen custom presets are available.
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 2.0
 */
public class PresetBankCapabilities extends RPCStruct {
	public static final String KEY_ON_SCREEN_PRESETS_AVAILABLE = "OnScreenPresetsAvailable";

	/**
	 * Constructs a newly allocated PresetBankCapabilities object
	 */
    public PresetBankCapabilities() { }
    
    /**
     * Constructs a newly allocated PresetBankCapabilities object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public PresetBankCapabilities(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    /**
     * set if Onscreen custom presets are available.
     * @param onScreenPresetsAvailable if Onscreen custom presets are available.
     */
    public void setOnScreenPresetsAvailable(Boolean onScreenPresetsAvailable) {
    	setValue(KEY_ON_SCREEN_PRESETS_AVAILABLE, onScreenPresetsAvailable);
    }
    
    /**
     * Defines, if Onscreen custom presets are available.
     * @return if Onscreen custom presets are available
     */
    public Boolean onScreenPresetsAvailable() {
    	return getBoolean(KEY_ON_SCREEN_PRESETS_AVAILABLE);
    }
}
