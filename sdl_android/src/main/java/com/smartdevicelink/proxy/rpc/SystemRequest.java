package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.RequestType;

import java.util.Hashtable;
import java.util.List;

/** An asynchronous request from the device; binary data can be included in hybrid part of message for some requests (such as HTTP, Proprietary, or Authentication requests)
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
 * 			<td>requestType</td>
 * 			<td>RequestType</td>
 * 			<td>The type of system request. Note that Proprietary requests should forward the binary data to the known proprietary module on the system.</td>
 *                 <td>Y</td>
 *                 <td></td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 * 		
 * 		<tr>
 * 			<td>fileName</td>
 * 			<td>String</td>
 * 			<td>Filename of HTTP data to store in predefined system staging area. Mandatory if requestType is HTTP. PROPRIETARY requestType should ignore this parameter. </td>
 *                 <td>N</td>
 *                 <td>Max Length: 255</td>
 * 			<td>SmartDeviceLink 3.0 </td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 3.0
 * 
 * 
 *
 */

public class SystemRequest extends RPCRequest {
	public static final String KEY_FILE_NAME = "fileName";
	public static final String KEY_REQUEST_TYPE = "requestType";
	public static final String KEY_DATA = "data";
	/**
	 * Constructs a new SystemRequest object
	 */ 

    public SystemRequest() {
        super(FunctionID.SYSTEM_REQUEST.toString());
    }

	public SystemRequest(boolean bLegacy) {
        super(FunctionID.ENCODED_SYNC_P_DATA.toString());
    }
    
    public SystemRequest(Hashtable<String, Object> hash) {
        super(hash);
    }

    @SuppressWarnings("unchecked")    
    public List<String> getLegacyData() {
        return (List<String>) getObject(String.class, KEY_DATA);
    }
 
    public void setLegacyData( List<String> data ) {
        setParameter(KEY_DATA, data);
    }    
            
    public String getFileName() {
        return getString(KEY_FILE_NAME);
    }
    
    public void setFileName(String fileName) {
        setParameter(KEY_FILE_NAME, fileName);
    }    

    public RequestType getRequestType() {
        return (RequestType) getObject(RequestType.class, KEY_REQUEST_TYPE);
    }

    public void setRequestType(RequestType requestType) {
        setParameter(KEY_REQUEST_TYPE, requestType);
    }
}
