package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.SdlCommand;
import com.smartdevicelink.util.JsonUtils;
/**
 * Establishes a subscription to button notifications for HMI buttons. Buttons
 * are not necessarily physical buttons, but can also be "soft" buttons on a
 * touch screen, depending on the display in the vehicle. Once subscribed to a
 * particular button, an application will receive both
 * {@linkplain OnButtonEvent} and {@linkplain OnButtonPress} notifications
 * whenever that button is pressed. The application may also unsubscribe from
 * notifications for a button by invoking the {@linkplain UnsubscribeButton}
 * operation
 * <p>
 * When a button is depressed, an {@linkplain OnButtonEvent} notification is
 * sent to the application with a ButtonEventMode of BUTTONDOWN. When that same
 * button is released, an {@linkplain OnButtonEvent} notification is sent to the
 * application with a ButtonEventMode of BUTTONUP
 * <p>
 * When the duration of a button depression (that is, time between depression
 * and release) is less than two seconds, an {@linkplain OnButtonPress}
 * notification is sent to the application (at the moment the button is
 * released) with a ButtonPressMode of SHORT. When the duration is two or more
 * seconds, an {@linkplain OnButtonPress} notification is sent to the
 * application (at the moment the two seconds have elapsed) with a
 * ButtonPressMode of LONG
 * <p>
 * The purpose of {@linkplain OnButtonPress} notifications is to allow for
 * programmatic detection of long button presses similar to those used to store
 * presets while listening to the radio, for example
 * <p>
 * When a button is depressed and released, the sequence in which notifications
 * will be sent to the application is as follows:
 * <p>
 * For short presses:<br/>
 * <ul>
 * <li>OnButtonEvent (ButtonEventMode = BUTTONDOWN)</li>
 * <li>OnButtonEvent (ButtonEventMode = BUTTONUP)</li>
 * <li>OnButtonPress (ButtonPressMode = SHORT)</li>
 * </ul>
 * <p>
 * For long presses:<br/>
 * <ul>
 * <li>OnButtonEvent (ButtonEventMode = BUTTONDOWN)</li>
 * <li>OnButtonEvent (ButtonEventMode = BUTTONUP)</li>
 * <li>OnButtonPress (ButtonPressMode = LONG)</li>
 * </ul>
 * <p>
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b>
 * </p>
 * 
 * @since SmartDeviceLink 1.0
 * @see UnsubscribeButton
 */
public class SubscribeButton extends RPCRequest {
	public static final String KEY_BUTTON_NAME = "buttonName";
	
	private String buttonName; // represents ButtonName enum

	/**
	 * Constructs a new SubscribeButton object
	 */
    public SubscribeButton() {
        super(FunctionID.SUBSCRIBE_BUTTON);
    }
    
    /**
     * Creates a SubscribeButton object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public SubscribeButton(JSONObject jsonObject){
        super(SdlCommand.SUBSCRIBE_BUTTON, jsonObject);
        jsonObject = getParameters(jsonObject);
        switch(sdlVersion){
        default:
            this.buttonName = JsonUtils.readStringFromJsonObject(jsonObject, KEY_BUTTON_NAME);
            break;
        }
    }
    
	/**
	 * Gets the name of the button to subscribe to
	 * @return ButtonName -an enum value, see <i>{@linkplain com.smartdevicelink.proxy.rpc.enums.ButtonName}</i>
	 */    
    public ButtonName getButtonName() {
        return ButtonName.valueForJsonName(this.buttonName, sdlVersion);
    }
    
	/**
	 * Sets a name of the button to subscribe to
	 * @param buttonName a <i>{@linkplain com.smartdevicelink.proxy.rpc.enums.ButtonName}</i> value
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
