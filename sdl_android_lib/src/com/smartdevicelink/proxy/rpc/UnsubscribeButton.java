package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.util.DebugTool;

/**
 * Deletes a subscription to button notifications for the specified button. For
 * more information about button subscriptions, see {@linkplain SubscribeButton}
 * <p>
 * Application can unsubscribe from a button that is currently being pressed
 * (i.e. has not yet been released), but app will not get button event
 * <p>
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b>
 * <p>
 * <p><b>Parameter List</b>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th> Req.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>buttonName</td>
 * 			<td>ButtonName</td>
 * 			<td>Name of the button to unsubscribe.</td>
 *                 <td>Y</td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *  </table>
 *  <p>
 *  <b>Response</b><br>
 *  <p>
 *  <b>Non-default Result Codes:</b><p>
 *  - SUCCESS<br>
 *  - INVALID_DATA<br>
 *  - OUT_OF_MEMORY<br>
 *  - TOO_MANY_PENDING_REQUESTS<br>
 *  - APPLICATION_NOT_REGISTERED<br>
 *  - GENERIC_ERROR     <br> 
 *  - UNSUPPORTED_RESOURCE<br>
 *  - IGNORED<br>
 *  - REJECTED<br>
 * @since SmartDeviceLink 1.0
 * @see SubscribeButton
 */
public class UnsubscribeButton extends RPCRequest {
	public static final String KEY_BUTTON_NAME = "buttonName";

	/**
	 * Constructs a new UnsubscribeButton object
	 */    
	public UnsubscribeButton() {
        super(FunctionID.UNSUBSCRIBE_BUTTON);
    }
	/**
	 * Constructs a new UnsubscribeButton object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */	
    public UnsubscribeButton(Hashtable<String, Object> hash) {
        super(hash);
    }
	/**
	 * Gets a name of the button to unsubscribe from
	 * 
	 * @return ButtonName -an Enumeration value, see <i>
	 *         {@linkplain com.smartdevicelink.proxy.rpc.enums.ButtonName}</i>
	 */    
    public ButtonName getButtonName() {
        Object obj = parameters.get(KEY_BUTTON_NAME);
        if (obj instanceof ButtonName) {
            return (ButtonName) obj;
        } else if (obj instanceof String) {
            ButtonName theCode = null;
            try {
                theCode = ButtonName.valueForString((String) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_BUTTON_NAME, e);
            }
            return theCode;
        }
        return null;
    }
	/**
	 * Sets the name of the button to unsubscribe from
	 * 
	 * @param buttonName
	 *            an enum value, see <i>
	 *            {@linkplain com.smartdevicelink.proxy.rpc.enums.ButtonName}</i>
	 */    
    public void setButtonName( ButtonName buttonName ) {
        if (buttonName != null) {
            parameters.put(KEY_BUTTON_NAME, buttonName );
        } else {
            parameters.remove(KEY_BUTTON_NAME);
        }
    }
}
