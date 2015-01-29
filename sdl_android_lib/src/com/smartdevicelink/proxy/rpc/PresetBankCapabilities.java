package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.util.JsonUtils;

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
public class PresetBankCapabilities extends RPCObject {
	public static final String KEY_ON_SCREEN_PRESETS_AVAILABLE = "OnScreenPresetsAvailable";

	private Boolean onScreenPresetsAvailable;
	
	/**
	 * Constructs a newly allocated PresetBankCapabilities object
	 */
    public PresetBankCapabilities() { }
    
    /**
     * Creates a PresetBankCapabilities object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public PresetBankCapabilities(JSONObject jsonObject){
        switch(sdlVersion){
        default:
            this.onScreenPresetsAvailable = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_ON_SCREEN_PRESETS_AVAILABLE);
            break;
        }
    }
    
    /**
     * set if Onscreen custom presets are available.
     * @param onScreenPresetsAvailable if Onscreen custom presets are available.
     */
    public void setOnScreenPresetsAvailable(Boolean onScreenPresetsAvailable) {
    	this.onScreenPresetsAvailable = onScreenPresetsAvailable;
    }
    
    /**
     * Defines, if Onscreen custom presets are available.
     * @return if Onscreen custom presets are available
     */
    public Boolean onScreenPresetsAvailable() {
    	return this.onScreenPresetsAvailable;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_ON_SCREEN_PRESETS_AVAILABLE, this.onScreenPresetsAvailable);
            break;
        }
        
        return result;
    }
}
