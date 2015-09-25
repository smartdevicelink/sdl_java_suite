package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * Removes a command from the Command Menu.
 * 
 * <p><b>HMI Status Requirements:</b></p>
 * <p>HMILevel: FULL, LIMITED or BACKGROUND</p>
 * AudioStreamingState: N/A
 * <p>SystemContext: Should not be attempted when VRSESSION or MENU</p>
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
 * 			<td>cmdID</td>
 * 			<td>Integer</td>
 * 			<td>Unique ID that identifies the Command to be deleted from Command Menu</td>
 *                 <td>Y</td>
 *                 <td>Min Value: 0;Max Value: 2000000000</td>
 * 			<td>SmartDeviceLink 1.0</td>
 * 		</tr>
 *  </table>
 *   
 *<p><b> Response:</b></p>
 *
 * Indicates that the corresponding request either failed or succeeded. If the response returns with a SUCCESS result code,this means a command was removed from the Command Menu successfully. 
 * 
 *<p><b> Non-default Result Codes:</b></p>
 * 	<p>SUCCESS</p>
 * 	<p>INVALID_DATA</p>
 * 	<p>OUT_OF_MEMORY</p>
 * 	<p>TOO_MANY_PENDING_REQUESTS</p>
 * 	<p>APPLICATION_NOT_REGISTERED</p>
 * <p>	GENERIC_ERROR</p>
 * 	<p>REJECTED</p> 
 *  <p>  INVALID_ID</p>
 *   <p> IN_USER</p>  
 * @since SmartDeviceLink 1.0
 * @see AddCommand
 * @see AddSubMenu
 * @see DeleteSubMenu
 */
public class DeleteCommand extends RPCRequest {
	public static final String KEY_CMD_ID = "cmdID";

	/**
	 * Constructs a new DeleteCommand object
	 */
	public DeleteCommand() {
        super(FunctionID.DELETE_COMMAND.toString());
    }
	/**
	 * Constructs a new DeleteCommand object indicated by the Hashtable
	 * parameter
	 * 
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */    
	public DeleteCommand(Hashtable<String, Object> hash) {
        super(hash);
    }
	/**
	 * Gets the Command ID that identifies the Command to be deleted from
	 * Command Menu
	 * 
	 * @return Integer - Integer value representing Command ID that identifies
	 *         the Command to be deleted from Command Menu
	 */	
    public Integer getCmdID() {
        return (Integer) parameters.get( KEY_CMD_ID );
    }
	/**
	 * Sets the Command ID that identifies the Command to be deleted from Command Menu
	 * 
	 * @param cmdID
	 *            an Integer value representing Command ID
	 *            
	 *            <p><b>Notes: </b>Min Value: 0; Max Value: 2000000000</p>
	 */    
    public void setCmdID( Integer cmdID ) {
        if (cmdID != null) {
            parameters.put(KEY_CMD_ID, cmdID );
        } else {
            parameters.remove(KEY_CMD_ID);
        }
    }
}
