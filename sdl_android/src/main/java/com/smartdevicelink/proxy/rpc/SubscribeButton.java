package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
/**
 *<p> Establishes a subscription to button notifications for HMI buttons. Buttons
 * are not necessarily physical buttons, but can also be "soft" buttons on a
 * touch screen, depending on the display in the vehicle. Once subscribed to a
 * particular button, an application will receive both
 * {@linkplain OnButtonEvent} and {@linkplain OnButtonPress} notifications
 * whenever that button is pressed. The application may also unsubscribe from
 * notifications for a button by invoking the {@linkplain UnsubscribeButton}
 * operation</p>
 * 
 * <p>When a button is depressed, an {@linkplain OnButtonEvent} notification is
 * sent to the application with a ButtonEventMode of BUTTONDOWN. When that same
 * button is released, an {@linkplain OnButtonEvent} notification is sent to the
 * application with a ButtonEventMode of BUTTONUP</p>
 * 
 * <p>When the duration of a button depression (that is, time between depression
 * and release) is less than two seconds, an {@linkplain OnButtonPress}
 * notification is sent to the application (at the moment the button is
 * released) with a ButtonPressMode of SHORT. When the duration is two or more
 * seconds, an {@linkplain OnButtonPress} notification is sent to the
 * application (at the moment the two seconds have elapsed) with a
 * ButtonPressMode of LONG</p>
 * 
 * The purpose of {@linkplain OnButtonPress} notifications is to allow for
 * programmatic detection of long button presses similar to those used to store
 * presets while listening to the radio, for example
 * 
 * <p>When a button is depressed and released, the sequence in which notifications
 * will be sent to the application is as follows:</p>
 * 
 * <p>For short presses:</p>
 * <ul>
 * <li>OnButtonEvent (ButtonEventMode = BUTTONDOWN)</li>
 * <li>OnButtonEvent (ButtonEventMode = BUTTONUP)</li>
 * <li>OnButtonPress (ButtonPressMode = SHORT)</li>
 * </ul>
 * 
 * <p>For long presses:</p>
 * <ul>
 * <li>OnButtonEvent (ButtonEventMode = BUTTONDOWN)</li>
 * <li>OnButtonEvent (ButtonEventMode = BUTTONUP)</li>
 * <li>OnButtonPress (ButtonPressMode = LONG)</li>
 * </ul>
 * 
 *<p> <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b></p>
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th>Reg.</th>
 *               <th>Notes</th>
 * 			<th>Version</th>
 * 		</tr>
 * 		<tr>
 * 			<td>buttonName</td>
 * 			<td>ButtonName</td>
 * 			<td>Name of the button to subscribe.</td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 1.0 </td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 1.0
 * @see UnsubscribeButton
 */
public class SubscribeButton extends RPCRequest {
	public static final String KEY_BUTTON_NAME = "buttonName";

	/**
	 * Constructs a new SubscribeButton object
	 */
    public SubscribeButton() {
        super(FunctionID.SUBSCRIBE_BUTTON.toString());
    }
	/**
	 * <p>Constructs a new SubscribeButton object indicated by the Hashtable
	 * parameter</p>
	 * 
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */    
    public SubscribeButton(Hashtable<String, Object> hash) {
        super(hash);
    }
	/**
	 * Gets the name of the button to subscribe to
	 * @return ButtonName -an enum value, see <i>{@linkplain com.smartdevicelink.proxy.rpc.enums.ButtonName}</i>
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
	 * Sets a name of the button to subscribe to
	 * @param buttonName a <i>{@linkplain com.smartdevicelink.proxy.rpc.enums.ButtonName}</i> value
	 */    
    public void setButtonName( ButtonName buttonName ) {
        if (buttonName != null) {
            parameters.put(KEY_BUTTON_NAME, buttonName );
        } else {
            parameters.remove(KEY_BUTTON_NAME);
        }
    }
}
