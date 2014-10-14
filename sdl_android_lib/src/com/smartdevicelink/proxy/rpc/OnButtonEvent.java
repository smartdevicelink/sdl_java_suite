package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.ButtonEventMode;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.util.DebugTool;

/**
 * Notifies application that user has depressed or released a button to which
 * the application has subscribed.</br> Further information about button events
 * and button-presses can be found at {@linkplain SubscribeButton}.
 * <p>
 * </p>
 * <b>HMI Status Requirements:</b>
 * <ul>
 * HMILevel:
 * <ul>
 * <li>The application will receive OnButtonEvent notifications for all
 * subscribed buttons when HMILevel is FULL.</li>
 * <li>The application will receive OnButtonEvent notifications for subscribed
 * media buttons when HMILevel is LIMITED.</li>
 * <li>Media buttons include SEEKLEFT, SEEKRIGHT, TUNEUP, TUNEDOWN, and
 * PRESET_0-PRESET_9.</li>
 * <li>The application will not receive OnButtonEvent notification when HMILevel
 * is BACKGROUND.</li>
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
 * <td>buttonEventMode</td>
 * <td>{@linkplain ButtonEventMode}</td>
 * <td>Indicats button was depressed (DOWN) or released (UP)</td>
 * <td></td>
 * <td></td>
 * <td>SmartDeviceLink 1.0</td>
 * </tr>
 * <tr>
 * <td>customButtonID</td>
 * <td>Integer</td>
 * <td>If ButtonName is ¡°CUSTOM_BUTTON", this references the integer ID passed
 * by a custom button. (e.g. softButton ID)</td>
 * <td>N</td>
 * <td>Minvalue=0 Maxvalue=65536</td>
 * <td>SmartDeviceLink 2.0</td>
 * </tr>
 * </table>
 * </p>
 * 
 * @since SmartDeviceLink 1.0
 * 
 * @see SubscribeButton
 * @see UnsubscribeButton
 * 
 * 
 */
public class OnButtonEvent extends RPCNotification {
	public static final String buttonEventMode = "buttonEventMode";
	public static final String buttonName = "buttonName";
	public static final String customButtonID = "customButtonID";
	/**
	*Constructs a newly allocated OnButtonEvent object
	*/
    public OnButtonEvent() {
        super("OnButtonEvent");
    }
    /**
	 * <p>
	 * Constructs a newly allocated OnButtonEvent object indicated by the
	 * Hashtable parameter
	 * </p>
	 * 
	 * @param hash
	 *            The Hashtable to use
     */    
    public OnButtonEvent(Hashtable hash) {
        super(hash);
    }
    /**
     * <p>Returns <i>{@linkplain ButtonName}</i> the button's name</p>
     * @return ButtonName Name of the button
     */    
    public ButtonName getButtonName() {
        Object obj = parameters.get(OnButtonEvent.buttonName);
        if (obj instanceof ButtonName) {
            return (ButtonName) obj;
        } else if (obj instanceof String) {
            ButtonName theCode = null;
            try {
                theCode = ButtonName.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnButtonEvent.buttonName, e);
            }
            return theCode;
        }
        return null;
    }
    /**
     * <p>Set the button's name</p>    
     * @param buttonName name of the button
     */    
    public void setButtonName(ButtonName buttonName) {
        if (buttonName != null) {
            parameters.put(OnButtonEvent.buttonName, buttonName);
        } else {
        	parameters.remove(OnButtonEvent.buttonName);
        }
    }
    /**
     * <p>Return <i>{@linkplain ButtonEventMode} indicates the button was depressed or released</i></p>
     * @return ButtonEventMode the button depressed or released
     */    
    public ButtonEventMode getButtonEventMode() {
        Object obj = parameters.get(OnButtonEvent.buttonEventMode);
        if (obj instanceof ButtonEventMode) {
            return (ButtonEventMode) obj;
        } else if (obj instanceof String) {
            ButtonEventMode theCode = null;
            try {
                theCode = ButtonEventMode.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + OnButtonEvent.buttonEventMode, e);
            }
            return theCode;
        }
        return null;
    }
    /**
     * <p> Set the event mode of the button,pressed or released
     * @param buttonEventMode indicates the button is pressed or released
     * @see ButtonEventMode
     */    
    public void setButtonEventMode(ButtonEventMode buttonEventMode) {
        if (buttonEventMode != null) {
            parameters.put(OnButtonEvent.buttonEventMode, buttonEventMode);
        } else {
    		parameters.remove(OnButtonEvent.buttonEventMode);
    	}
    }
    public void setCustomButtonID(Integer customButtonID) {
    	if (customButtonID != null) {
    		parameters.put(OnButtonEvent.customButtonID, customButtonID);
    	} else {
    		parameters.remove(OnButtonEvent.customButtonID);
    	}
    }
    public Integer getCustomButtonID() {
    	return (Integer) parameters.get(OnButtonEvent.customButtonID);
    }
}
