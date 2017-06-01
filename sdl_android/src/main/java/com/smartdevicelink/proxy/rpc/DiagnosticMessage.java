package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;
import java.util.List;

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
		setParameter(KEY_TARGET_ID, targetID);
    }
    /**
	 * <p>
	 * Returns an <i>Integer</i> object representing the Target ID that you want to add
	 * </p>
	 * 
	 * @return Integer -an integer representation a Unique Target ID
	 */

    public Integer getTargetID() {
    	return getInteger(KEY_TARGET_ID);
    }    

    public void setMessageLength(Integer messageLength) {
		setParameter(KEY_MESSAGE_LENGTH, messageLength);
    }
    public Integer getMessageLength() {
    	return getInteger(KEY_MESSAGE_LENGTH);
    }

    @SuppressWarnings("unchecked")
    public List<Integer> getMessageData() {
        return (List<Integer>) getObject(Integer.class, KEY_MESSAGE_DATA);
    }
    
    public void setMessageData(List<Integer> messageData) {
		setParameter(KEY_MESSAGE_DATA, messageData);
    }    
}
