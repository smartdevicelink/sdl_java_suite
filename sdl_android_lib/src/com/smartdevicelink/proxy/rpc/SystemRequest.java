package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
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
        if (parameters.get(KEY_DATA) instanceof List<?>) {
        	List<?> list = (List<?>)parameters.get(KEY_DATA);
        	if (list != null && list.size()>0) {
        		Object obj = list.get(0);
        		if (obj instanceof String) {
        			return (List<String>) list;
        		}
        	}
        }
    	return null;
    }
 
    public void setLegacyData( List<String> data ) {
    	if ( data!= null) {
    		parameters.put(KEY_DATA, data );
    	} else {
            parameters.remove(KEY_DATA);
        }
    }    
            
    public String getFileName() {
        return (String) parameters.get(KEY_FILE_NAME);
    }
    
    public void setFileName(String fileName) {
        if (fileName != null) {
            parameters.put(KEY_FILE_NAME, fileName);
        } else {
        	parameters.remove(KEY_FILE_NAME);
        }
    }    

    public RequestType getRequestType() {
        Object obj = parameters.get(KEY_REQUEST_TYPE);
        if (obj instanceof RequestType) {
            return (RequestType) obj;
        } else if (obj instanceof String) {
            return RequestType.valueForString((String) obj);
        }
        return null;
    }

    public void setRequestType(RequestType requestType) {
        if (requestType != null) {
            parameters.put(KEY_REQUEST_TYPE, requestType);
        } else {
            parameters.remove(KEY_REQUEST_TYPE);
        }
    }
}
