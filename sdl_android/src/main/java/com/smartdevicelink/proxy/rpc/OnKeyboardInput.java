package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.rpc.enums.KeyboardEvent;

import java.util.Hashtable;

/**
 * On-screen keyboard event. Can be full string or individual keypresses depending on keyboard mode.
 * <p></p>
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
 * 			<td>event</td>
 * 			<td>KeyboardEvent</td>
 * 			<td>On-screen keyboard input data.</td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 3.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>data</td>
 * 			<td>String</td>
 * 			<td>On-screen keyboard input data.For dynamic keypress events, this will be the current compounded string of entry text.For entry cancelled and entry aborted events, this data param will be omitted.</td>
 *                 <td></td>
 *                 <td>Maxlength: 500</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		
 *  </table>
 *
 */

public class OnKeyboardInput extends RPCNotification {
	public static final String KEY_DATA = "data";
	public static final String KEY_EVENT = "event";
	/**
	 * Constructs a new OnKeyboardInput object
	 */

    public OnKeyboardInput() {
        super(FunctionID.ON_KEYBOARD_INPUT.toString());
    }
    /**
	* <p>
	* Constructs a new OnKeyboardInput object indicated by the Hashtable
	* parameter
	* </p>
	* 
	* @param hash
	*            The Hashtable to use
	*/

    public OnKeyboardInput(Hashtable<String, Object> hash) {
        super(hash);
    }

    public KeyboardEvent getEvent() {
        return (KeyboardEvent) getObject(KeyboardEvent.class, KEY_EVENT);
    }

    public void setEvent(KeyboardEvent event) {
        setParameters(KEY_EVENT, event);
    }

    public void setData(String data) {
        setParameters(KEY_DATA, data);
    }
    public String getData() {
        Object obj = getParameters(KEY_DATA);
        if (obj instanceof String) {
            return (String) obj;
        }
        return null;
    }

    @Override
    public String toString(){
        String result =  this.getFunctionName() +": " + " data: " + this.getData() + " event:" + this.getEvent().toString();
        return result;
    }

}
