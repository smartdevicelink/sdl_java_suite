package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
/**
 * 	Notification containing an updated hashID which can be used over connection cycles (i.e. loss of connection, ignition cycles, etc.).
 * Sent after initial registration and subsequently after any change in the calculated hash of all persisted app data.
 * <p></p>
 *  <p><b>Parameter List</b></p>
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
 * 			<td>hashID</td>
 * 			<td>String</td>
 * 			<td>Calculated hash ID to be referenced during RegisterAppInterface.</td>
 *                 <td>Y</td>
 *                 <td>maxlength: 100</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 3.0
 *
 */
public class OnHashChange extends RPCNotification {
	public static final String KEY_HASH_ID = "hashID";
	/**
	 * Constructs a new OnHashChange object
	 */

    public OnHashChange() {
        super(FunctionID.ON_HASH_CHANGE.toString());
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

    public OnHashChange(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    public String getHashID() {
        return (String) parameters.get(KEY_HASH_ID);
    }
   
    public void setHashID(String hashID) {
        if (hashID != null) {
            parameters.put(KEY_HASH_ID, hashID);
        } else {
        	parameters.remove(KEY_HASH_ID);
        }
    }   
    
}
