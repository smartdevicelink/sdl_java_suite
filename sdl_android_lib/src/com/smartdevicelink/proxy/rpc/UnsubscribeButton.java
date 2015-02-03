package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.util.JsonUtils;

/**
 * Deletes a subscription to button notifications for the specified button. For
 * more information about button subscriptions, see {@linkplain SubscribeButton}
 * <p>
 * Application can unsubscribe from a button that is currently being pressed
 * (i.e. has not yet been released), but app will not get button event
 * <p>
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b>
 * <p>
 * 
 * @since SmartDeviceLink 1.0
 * @see SubscribeButton
 */
public class UnsubscribeButton extends RPCRequest {
	public static final String KEY_BUTTON_NAME = "buttonName";
	
	private String buttonName; // represents ButtonName enum

	/**
	 * Constructs a new UnsubscribeButton object
	 */    
	public UnsubscribeButton() {
        super(FunctionID.UNSUBSCRIBE_BUTTON);
    }
	
    /**
     * Creates a UnsubscribeButton object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public UnsubscribeButton(JSONObject jsonObject){
        super(jsonObject);
        switch(sdlVersion){
        default:
            this.buttonName = JsonUtils.readStringFromJsonObject(jsonObject, KEY_BUTTON_NAME);
            break;
        }
    }
    
	/**
	 * Gets a name of the button to unsubscribe from
	 * 
	 * @return ButtonName -an Enumeration value, see <i>
	 *         {@linkplain com.smartdevicelink.proxy.rpc.enums.ButtonName}</i>
	 */    
    public ButtonName getButtonName() {
        return ButtonName.valueForJsonName(this.buttonName, sdlVersion);
    }
    
	/**
	 * Sets the name of the button to unsubscribe from
	 * 
	 * @param buttonName
	 *            an enum value, see <i>
	 *            {@linkplain com.smartdevicelink.proxy.rpc.enums.ButtonName}</i>
	 */    
    public void setButtonName( ButtonName buttonName ) {
        this.buttonName = (buttonName == null) ? null : buttonName.getJsonName(sdlVersion);
    }
    
    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_BUTTON_NAME, this.buttonName);
            break;
        }
        
        return result;
    }
}
