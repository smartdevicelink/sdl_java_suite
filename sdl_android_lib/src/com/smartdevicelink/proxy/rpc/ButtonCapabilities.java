package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.util.JsonUtils;

/**
 * Provides information about the capabilities of a SDL HMI button.
 * <p><b> Parameter List
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>name</td>
 * 			<td>ButtonName</td>
 * 			<td>The name of theSDL HMI button.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>shortPressAvailable</td>
 * 			<td>Boolean</td>
 * 			<td>The button supports a SHORT press. See ButtonPressMode for more information.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *     <tr>
 * 			<td>longPressAvailable</td>
 * 			<td>Boolean</td>
 * 			<td>The button supports a LONG press. See ButtonPressMode for more information.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *     <tr>
 * 			<td>upDownAvailable</td>
 * 			<td>Boolean</td>
 * 			<td>The button supports "button down" and "button up". When the button is depressed, the <i>{@linkplain OnButtonEvent}</i> notification will be invoked with a value of BUTTONDOWN.
 *                  <p> When the button is released, the <i>{@linkplain OnButtonEvent}</i> notification will be invoked with a value of BUTTONUP.</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 * </table>
 * @since SmartDeviceLink 1.0
 */
public class ButtonCapabilities extends RPCObject {
	public static final String KEY_NAME = "name";
	public static final String KEY_SHORT_PRESS_AVAILABLE = "shortPressAvailable";
	public static final String KEY_LONG_PRESS_AVAILABLE = "longPressAvailable";
	public static final String KEY_UP_DOWN_AVAILABLE = "upDownAvailable";
	
	private String name; // represents ButtonName enum
	private Boolean shortPressAvailable, longPressAvailable, upDownAvailable;
	
	/**
	 * Constructs a newly allocated ButtonCapabilities object
	 */
    public ButtonCapabilities() { }
    
    /**
     * Creates a ButtonCapabilities object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public ButtonCapabilities(JSONObject jsonObject) {
        switch(sdlVersion){
        default:
            this.name = JsonUtils.readStringFromJsonObject(jsonObject, KEY_NAME);
            this.shortPressAvailable = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_SHORT_PRESS_AVAILABLE);
            this.longPressAvailable = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_LONG_PRESS_AVAILABLE);
            this.upDownAvailable = JsonUtils.readBooleanFromJsonObject(jsonObject, KEY_UP_DOWN_AVAILABLE);
            break;
        }
    }
    
    /**
     * Get the name of theSDL HMI button.
     * @return ButtonName the name of the Button
     */    
    public ButtonName getName() {
        return ButtonName.valueForJsonName(this.name, sdlVersion);
    }
    
    /**
     * Set the name of theSDL HMI button.
     * @param name the name of button
     */    
    public void setName( ButtonName name ) {
        this.name = (name == null) ? null : name.getJsonName(sdlVersion);
    }
    
    /**
     * Whether the button supports a SHORT press. See <i>{@linkplain com.smartdevicelink.proxy.rpc.enums.ButtonPressMode}</i> for more information.
     * @return True if support otherwise False.
     */    
    public Boolean getShortPressAvailable() {
        return this.shortPressAvailable;
    }
    
    /**
     * Set the button supports a SHORT press. See <i>{@linkplain com.smartdevicelink.proxy.rpc.enums.ButtonPressMode}</i> for more information.
     * @param shortPressAvailable True if support otherwise False.
     */    
    public void setShortPressAvailable( Boolean shortPressAvailable ) {
        this.shortPressAvailable = shortPressAvailable;
    }
    
    /**
     * Whether the button supports a LONG press. See <i>{@linkplain com.smartdevicelink.proxy.rpc.enums.ButtonPressMode}</i> for more information.
     * @return True if support otherwise False.
     */
    public Boolean getLongPressAvailable() {
        return this.longPressAvailable;
    }
    
    /**
     * Set the button supports a LONG press. See <i>{@linkplain com.smartdevicelink.proxy.rpc.enums.ButtonPressMode}</i> for more information.
     * @param longPressAvailable True if support otherwise False.
     */    
    public void setLongPressAvailable( Boolean longPressAvailable ) {
        this.longPressAvailable = longPressAvailable;
    }
    
    /**
     * Whether the button supports "button down" and "button up". When the button is depressed, the <i>{@linkplain OnButtonEvent}</i> notification will be invoked with a value of BUTTONDOWN.
     * @return True if support otherwise False.
     */    
    public Boolean getUpDownAvailable() {
        return this.upDownAvailable;
    }
    
    /**
     * Set the button supports "button down" and "button up". When the button is depressed, the <i>{@linkplain OnButtonEvent}</i> notification will be invoked with a value of BUTTONDOWN.
     * @param upDownAvailable True if support otherwise False.
     */    
    public void setUpDownAvailable( Boolean upDownAvailable ) {
        this.upDownAvailable = upDownAvailable;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_NAME, this.name);
            JsonUtils.addToJsonObject(result, KEY_SHORT_PRESS_AVAILABLE, this.shortPressAvailable);
            JsonUtils.addToJsonObject(result, KEY_LONG_PRESS_AVAILABLE, this.longPressAvailable);
            JsonUtils.addToJsonObject(result, KEY_UP_DOWN_AVAILABLE, this.upDownAvailable);
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((longPressAvailable == null) ? 0 : longPressAvailable.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((shortPressAvailable == null) ? 0 : shortPressAvailable.hashCode());
		result = prime * result + ((upDownAvailable == null) ? 0 : upDownAvailable.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { 
			return true;
		}
		if (obj == null) { 
			return false;
		}
		if (getClass() != obj.getClass()) { 
			return false;
		}
		ButtonCapabilities other = (ButtonCapabilities) obj;
		if (longPressAvailable == null) {
			if (other.longPressAvailable != null) { 
				return false;
			}
		}
		else if (!longPressAvailable.equals(other.longPressAvailable)) { 
			return false;
		}
		if (name == null) {
			if (other.name != null) { 
				return false;
			}
		} 
		else if (!name.equals(other.name)) { 
			return false;
		}
		if (shortPressAvailable == null) {
			if (other.shortPressAvailable != null) { 
				return false;
			}
		}
		else if (!shortPressAvailable.equals(other.shortPressAvailable)) { 
			return false;
		}
		if (upDownAvailable == null) {
			if (other.upDownAvailable != null) { 
				return false;
			}
		} 
		else if (!upDownAvailable.equals(other.upDownAvailable)) { 
			return false;
		}
		return true;
	}
}
