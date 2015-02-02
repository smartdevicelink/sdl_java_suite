package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.util.JsonUtils;

/**
 * Contains information about a SoftButton's capabilities.
 * <p><b>Parameter List
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>shortPressAvailable</td>
 * 			<td>Boolean</td>
 * 			<td>The button supports a short press.
 *					Whenever the button is pressed short, onButtonPressed( SHORT) will be invoked.
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>longPressAvailable</td>
 * 			<td>Boolean</td>
 * 			<td>The button supports a LONG press.
 * 					Whenever the button is pressed long, onButtonPressed( LONG) will be invoked.
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>upDownAvailable</td>
 * 			<td>Boolean</td>
 * 			<td>The button supports "button down" and "button up". Whenever the button is pressed, onButtonEvent( DOWN) will be invoked.
 *					Whenever the button is released, onButtonEvent( UP) will be invoked. * 			
 *			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>imageSupported</td>
 * 			<td>Boolean</td>
 * 			<td>The button supports referencing a static or dynamic image.
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr> 
 *  </table>
 * @since SmartDeviceLink 2.0
 */
public class SoftButtonCapabilities extends RPCObject {
	public static final String KEY_IMAGE_SUPPORTED = "imageSupported";
	public static final String KEY_SHORT_PRESS_AVAILABLE = "shortPressAvailable";
	public static final String KEY_LONG_PRESS_AVAILABLE = "longPressAvailable";
	public static final String KEY_UP_DOWN_AVAILABLE = "upDownAvailable";

	private Boolean imageSupported, shortPressAvailable, longPressAvailable, upDownAvailable;
	
	/**
	 * Constructs a newly allocated SoftButtonCapabilities object
	 */
    public SoftButtonCapabilities() { }
    
    /**
     * Creates a SoftButtonCapabilities object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SoftButtonCapabilities(JSONObject jsonObject){
        switch(sdlVersion){
        default:
            this.imageSupported = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_IMAGE_SUPPORTED);
            this.shortPressAvailable = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_SHORT_PRESS_AVAILABLE);
            this.longPressAvailable = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_LONG_PRESS_AVAILABLE);
            this.upDownAvailable = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_UP_DOWN_AVAILABLE);
            break;
        }
    }
    
    /**
     * set the button supports a short press.
     * @param shortPressAvailable whether the button supports a short press.
     */
    public void setShortPressAvailable(Boolean shortPressAvailable) {
        this.shortPressAvailable = shortPressAvailable;
    }
    
    /**
     * get whether the button supports a short press.
     * @return whether the button supports a short press
     */
    public Boolean getShortPressAvailable() {
        return this.shortPressAvailable;
    }
    
    /**
     * set the button supports a LONG press.
     * @param longPressAvailable whether the button supports a long press
     */
    public void setLongPressAvailable(Boolean longPressAvailable) {
        this.longPressAvailable = longPressAvailable;
    }
    
    /**
     * get whether  the button supports a LONG press.
     * @return whether  the button supports a LONG press
     */
    public Boolean getLongPressAvailable() {
        return this.longPressAvailable;
    }
    
    /**
     * set the button supports "button down" and "button up". 
     * @param upDownAvailable the button supports "button down" and "button up". 
     */
    public void setUpDownAvailable(Boolean upDownAvailable) {
        this.upDownAvailable = upDownAvailable;
    }
    
    /**
     * get the button supports "button down" and "button up".
     * @return the button supports "button down" and "button up".
     */
    public Boolean getUpDownAvailable() {
        return this.upDownAvailable;
    }
    
    /**
     * set the button supports referencing a static or dynamic image.
     * @param imageSupported whether the button supports referencing a static or dynamic image.
     */
    public void setImageSupported(Boolean imageSupported) {
        this.imageSupported = imageSupported;
    }
    
    /**
     * get the button supports referencing a static or dynamic image.
     * @return the button supports referencing a static or dynamic image.
     */
    public Boolean getImageSupported() {
        return this.imageSupported;
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_IMAGE_SUPPORTED, this.imageSupported);
            JsonUtils.addToJsonObject(result, KEY_SHORT_PRESS_AVAILABLE, this.shortPressAvailable);
            JsonUtils.addToJsonObject(result, KEY_LONG_PRESS_AVAILABLE, this.longPressAvailable);
            JsonUtils.addToJsonObject(result, KEY_UP_DOWN_AVAILABLE, this.upDownAvailable);
            break;
        }
        
        return result;
    }
}
