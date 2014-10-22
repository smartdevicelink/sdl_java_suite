package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;

/**
 * Contains information about on-screen preset capabilities.
 * <p><b>Parameter List
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
	public static final String OnScreenPresetsAvailable = "OnScreenPresetsAvailable";

	/**
	 * Constructs a newly allocated PresetBankCapabilities object
	 */
    public PresetBankCapabilities() { }
    
    /**
     * Constructs a newly allocated PresetBankCapabilities object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public PresetBankCapabilities(Hashtable hash) {
        super(hash);
    }
    
    /**
     * set if Onscreen custom presets are available.
     * @param onScreenPresetsAvailable if Onscreen custom presets are available.
     */
    public void setOnScreenPresetsAvailable(Boolean onScreenPresetsAvailable) {
    	if (onScreenPresetsAvailable != null) {
    		store.put(PresetBankCapabilities.OnScreenPresetsAvailable, onScreenPresetsAvailable);
    	} else {
    		store.remove(PresetBankCapabilities.OnScreenPresetsAvailable);
    	}
    }
    
    /**
     * Defines, if Onscreen custom presets are available.
     * @return if Onscreen custom presets are available
     */
    public Boolean onScreenPresetsAvailable() {
    	return (Boolean) store.get(PresetBankCapabilities.OnScreenPresetsAvailable);
    }
}
