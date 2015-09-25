package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
/** Non periodic vehicle diagnostic request.
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
 * 			<td>targetID</td>
 * 			<td>Integer</td>
 * 			<td>Name of target ECU.</td>
 *                 <td>Y</td>
 *                 <td>Min Value: 0; Max Value: 65535</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>messageLength</td>
 * 			<td>Integer</td>
 * 			<td>Length of message (in bytes).</td>
 *                 <td>Y</td>
 *                 <td>Min Value: 0; Max Value:65535</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>messageData</td>
 * 			<td>Integer</td>
 * 			<td>Array of bytes comprising CAN message.</td>
 *                 <td>Y</td>
 *                 <td>Min Value: 0; Max Value:255; Min Size:1; Max Size:65535</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 *  </table>
 * <p><b>HMI must:</b> </p>
 * 
 * <p>1.	Check the requested data using provided information of targetID (name of ECU),messageLength and messageData.</p>
 * <p> 2.	Respond with one of the appropriate result codes.And in case of SUCCESS return messageDataResult which is an array of bytes comprising CAN message result.</p>
 * @since SmartDeviceLink 3.0
 *
 */

public class DiagnosticMessage extends RPCRequest {
	public static final String KEY_TARGET_ID = "targetID";
	public static final String KEY_MESSAGE_LENGTH = "messageLength";
	public static final String KEY_MESSAGE_DATA = "messageData";
	/**
	 * Constructs a new DiagnosticMessage object
	 */

    public DiagnosticMessage() {
        super(FunctionID.DIAGNOSTIC_MESSAGE.toString());
    }
    /**
	* <p>
	* Constructs a new DiagnosticMessage object indicated by the Hashtable
	* parameter
	* </p>
	* 
	* @param hash
	*            The Hashtable to use
	*/

    public DiagnosticMessage(Hashtable<String, Object> hash) {
        super(hash);
    }
    /** Sets TargetID
     * 
     * @param targetID
     */
    

    public void setTargetID(Integer targetID) {
    	if (targetID != null) {
    		parameters.put(KEY_TARGET_ID, targetID);
    	} else {
    		parameters.remove(KEY_TARGET_ID);
    	}
    }
    /**
	 * <p>
	 * Returns an <i>Integer</i> object representing the Target ID that you want to add
	 * </p>
	 * 
	 * @return Integer -an integer representation a Unique Target ID
	 */

    public Integer getTargetID() {
    	return (Integer) parameters.get(KEY_TARGET_ID);
    }    

    public void setMessageLength(Integer messageLength) {
    	if (messageLength != null) {
    		parameters.put(KEY_MESSAGE_LENGTH, messageLength);
    	} else {
    		parameters.remove(KEY_MESSAGE_LENGTH);
    	}
    }
    public Integer getMessageLength() {
    	return (Integer) parameters.get(KEY_MESSAGE_LENGTH);
    }

    @SuppressWarnings("unchecked")
    public List<Integer> getMessageData() {
    	if(parameters.get(KEY_MESSAGE_DATA) instanceof List<?>){
    		List<?> list = (List<?>)parameters.get(KEY_MESSAGE_DATA);
    		if(list != null && list.size()>0){
        		Object obj = list.get(0);
        		if(obj instanceof Integer){
        			return (List<Integer>) list;
        		}
    		}
    	}
        return null;
    }
    
    public void setMessageData(List<Integer> messageData) {
        if (messageData != null) {
            parameters.put(KEY_MESSAGE_DATA, messageData);
        } else {
        	parameters.remove(KEY_MESSAGE_DATA);
        }
    }    
}
