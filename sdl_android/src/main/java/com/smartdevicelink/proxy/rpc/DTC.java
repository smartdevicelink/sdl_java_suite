package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;

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
    	setValue(KEY_IDENTIFIER, identifier);
    }
    
    /**
     * get identifier
     * @return identifier
     */
    public String getIdentifier() {
    	return getString(KEY_IDENTIFIER);
    }
    
    /**
     * set Hexadecimal byte string
     * @param statusByte Hexadecimal byte string
     */
    public void setStatusByte(String statusByte) {
    	setValue(KEY_STATUS_BYTE, statusByte);
    }
    
    /**
     * get Hexadecimal byte string
     * @return Hexadecimal byte string
     */
    public String getStatusByte() {
    	return getString(KEY_STATUS_BYTE);
    }
}
