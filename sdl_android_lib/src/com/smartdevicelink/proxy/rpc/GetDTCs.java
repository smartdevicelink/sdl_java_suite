package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

/**
 * This RPC allows to request diagnostic module trouble codes from a certain
 * vehicle module
 * <p>
 * Function Group: ProprietaryData
 * <p>
 * <b>HMILevel needs to be FULL, LIMITED or BACKGROUND</b>
 * <p>
  * <p><b>Parameter List</b>
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
 * 			<td>ecuName</td>
 * 			<td>Integer</td>
 * 			<td>Name of ECU.</td>
 *                 <td>Y</td>
 *                 <td>Min Value: 0<br>Max Value: 65535</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 * 		<tr>
 * 			<td>dtcMask</td>
 * 			<td>Integer</td>
 * 			<td>DTC Mask Byte to be sent in diagnostic request to module.</td>
 *                 <td>N</td>
 *                 <td>Min Value: 0<br>Max Value: 255</td>
 * 			<td>SmartDeviceLink 2.0 </td>
 * 		</tr>
 *  </table>
 * <b>Response</b><br>
 * <p>
 * <b>Non-default Result Codes:</b><br>- 
 * SUCCESS<br>- 
 * INVALID_DATA<br>- OUT_OF_MEMORY<br>- 
 * TOO_MANY_PENDING_REQUESTS<br>
 * - APPLICATION_NOT_REGISTERED<br>
 * - GENERIC_ERROR<br>    
 * - REJECTED<br>  
 * - DISALLOWED  <br> 
 * - USER_DISALLOWED<br>   
 * @since SmartDeviceLink 2.0
 */
public class GetDTCs extends RPCRequest {
	public static final String KEY_DTC_MASK = "dtcMask";
	public static final String KEY_ECU_NAME = "ecuName";

	/**
	 * Constructs a new GetDTCs object
	 */
    public GetDTCs() {
        super(FunctionID.GET_DTCS);
    }

	/**
	 * Constructs a new GetDTCs object indicated by the Hashtable parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public GetDTCs(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Sets a name of the module to receive the DTC form
	 * 
	 * @param ecuName
	 *            an Integer value representing a name of the module to receive
	 *            the DTC form
	 *            <p>
	 *            <b>Notes: </b>Minvalue:0; Maxvalue:65535
	 */
    public void setEcuName(Integer ecuName) {
    	if (ecuName != null) {
    		parameters.put(KEY_ECU_NAME, ecuName);
    	} else {
    		parameters.remove(KEY_ECU_NAME);
    	}
    }

	/**
	 * Gets a name of the module to receive the DTC form
	 * 
	 * @return Integer -an Integer value representing a name of the module to
	 *         receive the DTC form
	 */
    public Integer getEcuName() {
    	return (Integer) parameters.get(KEY_ECU_NAME);
    }
    public void setDtcMask(Integer dtcMask) {
    	if (dtcMask != null) {
    		parameters.put(KEY_DTC_MASK, dtcMask);
    	} else {
    		parameters.remove(KEY_DTC_MASK);
    	}
    }
    public Integer getDtcMask() {
    	return (Integer) parameters.get(KEY_DTC_MASK);
    }
}
