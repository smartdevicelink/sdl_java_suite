package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;

/**
 * String containing hexadecimal identifier as well as other common names.
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>statusByte</td>
 * 			<td>String</td>
 * 			<td>Hexadecimal byte string
 *				 <ul>
 *					<li>Maxlength = 500</li>
 *				 </ul> 
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 2.0
 */
public class DTC extends RPCStruct {
	public static final String KEY_IDENTIFIER = "identifier";
	public static final String KEY_STATUS_BYTE = "statusByte";
	/**
	 * Constructs a newly allocated DTC object
	 */
    public DTC() { }
    
    /**
     * Constructs a newly allocated DTC object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public DTC(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    /**
     * set identifier
     * @param identifier
     */
    public void setIdentifier(String identifier) {
    	if (identifier != null) {
    		store.put(KEY_IDENTIFIER, identifier);
    	} else {
    		store.remove(KEY_IDENTIFIER);
    	}
    }
    
    /**
     * get identifier
     * @return identifier
     */
    public String getIdentifier() {
    	return (String) store.get(KEY_IDENTIFIER);
    }
    
    /**
     * set Hexadecimal byte string
     * @param statusByte Hexadecimal byte string
     */
    public void setStatusByte(String statusByte) {
    	if (statusByte != null) {
    		store.put(KEY_STATUS_BYTE, statusByte);
    	} else {
    		store.remove(KEY_STATUS_BYTE);
    	}
    }
    
    /**
     * get Hexadecimal byte string
     * @return Hexadecimal byte string
     */
    public String getStatusByte() {
    	return (String) store.get(KEY_STATUS_BYTE);
    }
}
