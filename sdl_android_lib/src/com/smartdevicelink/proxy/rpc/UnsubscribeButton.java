package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;

/**
 * Deletes a subscription to button notifications for the specified button. For
 * more information about button subscriptions, see {@linkplain SubscribeButton}
 * 
 * <p>Application can unsubscribe from a button that is currently being pressed
 * (i.e. has not yet been released), but app will not get button event</p>
 * 
 * <p><b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b></p>
 * 
 * <p><b>Parameter List</b></p>
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
 *  
 * <p> <b>Response</b></p>
 * <p><b>Non-default Result Codes:</b></p>
 *  <p>SUCCESS</p>
 *  <p>INVALID_DATA</p>
 *  <p>OUT_OF_MEMORY</p>
 *  <p>TOO_MANY_PENDING_REQUESTS</p>
 *  <p>APPLICATION_NOT_REGISTERED</p>
 *  <p>GENERIC_ERROR </p> 
 *  <p>UNSUPPORTED_RESOURCE</p>
 *  <p>IGNORED</p>
 *  <p>REJECTED</p>
 * @since SmartDeviceLink 1.0
 * @see SubscribeButton
 */
public class UnsubscribeButton extends RPCRequest {
	public static final String KEY_BUTTON_NAME = "buttonName";

	/**
	 * Constructs a new UnsubscribeButton object
	 */    
	public UnsubscribeButton() {
        super(FunctionID.UNSUBSCRIBE_BUTTON.toString());
    }
	/**
	 * Constructs a new UnsubscribeButton object indicated by the Hashtable
	 * parameter
	 * <p></p>
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
            return ButtonName.valueForString((String) obj);
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
