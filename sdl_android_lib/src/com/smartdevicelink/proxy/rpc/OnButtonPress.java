package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.ButtonPressMode;

/**
 * <p>
 * Notifies application of button press events for buttons to which the
 * application is subscribed. SDL supports two button press events defined as
 * follows:
 * </p>
 * <ul>
 * <li>SHORT - Occurs when a button is depressed, then released within two
 * seconds. The event is considered to occur immediately after the button is
 * released.</li>
 * <li>LONG - Occurs when a button is depressed and held for two seconds or
 * more. The event is considered to occur immediately after the two second
 * threshold has been crossed, before the button is released</li>
 * </ul>
 * <b>HMI Status Requirements:</b>
 * <ul>
 * HMILevel:
 * <ul>
 * <li>The application will receive OnButtonPress notifications for all
 * subscribed buttons when HMILevel is FULL.</li>
 * <li>The application will receive OnButtonPress notifications for subscribed
 * media buttons when HMILevel is LIMITED.</li>
 * <li>Media buttons include SEEKLEFT, SEEKRIGHT, TUNEUP, TUNEDOWN, and
 * PRESET_0-PRESET_9.</li>
 * <li>The application will not receive OnButtonPress notification when HMILevel
 * is BACKGROUND or NONE.</li>
 * </ul>
 * AudioStreamingState:
 * <ul>
 * <li> Any </li>
 * </ul>
 * SystemContext:
 * <ul>
 * <li>MAIN, VR. In MENU, only PRESET buttons. In VR, pressing any subscribable
 * button will cancel VR.</li>
 * </ul>
 * </ul>
 * <p>
 * <b>Parameter List:</b>
 * <table  border="1" rules="all">
 * <tr>
 * <th>Name</th>
 * <th>Type</th>
 * <th>Description</th>
 * <th>Req</th>
 * <th>Notes</th>
 * <th>SmartDeviceLink Ver Available</th>
 * </tr>
 * <tr>
 * <td>buttonName</td>
 * <td>{@linkplain ButtonName}</td>
 * <td>Name of the button which triggered this event</td>
 * <td></td>
 * <td></td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * <tr>
 * <td>buttonPressMode</td>
 * <td>{@linkplain ButtonPressMode}</td>
 * <td>Indicates whether this is an SHORT or LONG button press event.</td>
 * <td></td>
 * <td></td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * <tr>
 * <td>customButtonID</td>
 * <td>Integer</td>
 * <td>If ButtonName is "CUSTOM_BUTTON", this references the integer ID passed
 * by a custom button. (e.g. softButton ID)</td>
 * <td>N</td>
 * <td>Minvalue=0 Maxvalue=65536</td>
 * <td>SmartDeviceLink 2.0</td>
 * </tr>
 * </table>
 * </p>
 * 
 * @since SmartDeviceLink 1.0
 * @see SubscribeButton
 * @see UnsubscribeButton
 */
public class OnButtonPress extends RPCNotification {
	public static final String KEY_BUTTON_PRESS_MODE = "buttonPressMode";
	public static final String KEY_BUTTON_NAME = "buttonName";
	public static final String KEY_CUSTOM_BUTTON_ID = "customButtonID";
	/**
	*Constructs a newly allocated OnButtonPress object
	*/   
    public OnButtonPress() {
        super(FunctionID.ON_BUTTON_PRESS.toString());
    }
    /**
	 * <p>
	 * Constructs a newly allocated OnButtonPress object indicated by the
	 * Hashtable parameter
	 * </p>
	 * 
	 * @param hash
	 *            The Hashtable to use
     */    
    public OnButtonPress(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * <p>Returns an <i>{@linkplain ButtonName}</i> the button's name</p>
     * @return ButtonName Name of the button
     */    
    public ButtonName getButtonName() {
        Object obj = parameters.get(KEY_BUTTON_NAME);
        if (obj instanceof ButtonName) {
            return (ButtonName) obj;
        } else if (obj instanceof String) {
            return ButtonName.valueForString((String) obj);
        }
        return null;
    }
    /**
     * <p>Set the button's name</p>    
     * @param buttonName name of the button
     */    
    public void setButtonName( ButtonName buttonName ) {
        if (buttonName != null) {
            parameters.put(KEY_BUTTON_NAME, buttonName );
        } else {
            parameters.remove(KEY_BUTTON_NAME);
        }
    }
    /**<p>Returns <i>{@linkplain ButtonPressMode}</i></p>
     * @return ButtonPressMode whether this is a long or short button press event
     */    
    public ButtonPressMode getButtonPressMode() {
        Object obj = parameters.get(KEY_BUTTON_PRESS_MODE);
        if (obj instanceof ButtonPressMode) {
            return (ButtonPressMode) obj;
        } else if (obj instanceof String) {
            return ButtonPressMode.valueForString((String) obj);
        }
        return null;
    }
    /**
     * <p>Set the button press mode of the event</p>
     * @param buttonPressMode indicates whether this is a short or long press
     */    
    public void setButtonPressMode( ButtonPressMode buttonPressMode ) {
        if (buttonPressMode != null) {
            parameters.put(KEY_BUTTON_PRESS_MODE, buttonPressMode );
        } else {
            parameters.remove(KEY_BUTTON_PRESS_MODE);
        }
    }
    public void setCustomButtonName(Integer customButtonID) {
    	if (customButtonID != null) {
    		parameters.put(KEY_CUSTOM_BUTTON_ID, customButtonID);
    	} else {
    		parameters.remove(KEY_CUSTOM_BUTTON_ID);
    	}
    }
    public Integer getCustomButtonName() {
    	return (Integer) parameters.get(KEY_CUSTOM_BUTTON_ID);
    }
}
