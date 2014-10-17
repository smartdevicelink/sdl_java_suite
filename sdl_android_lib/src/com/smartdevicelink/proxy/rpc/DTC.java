package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;

/**
 * String containing hexadecimal identifier as well as other common names.
 * <p><b>Parameter List
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
	public static final String identifier = "identifier";
	public static final String statusByte = "statusByte";
	/**
	 * Constructs a newly allocated DTC object
	 */
    public DTC() { }
    
    /**
     * Constructs a newly allocated DTC object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public DTC(Hashtable hash) {
        super(hash);
    }
    
    /**
     * set identifier
     * @param identifier
     */
    public void setIdentifier(String identifier) {
    	if (identifier != null) {
    		store.put(DTC.identifier, identifier);
    	} else {
    		store.remove(DTC.identifier);
    	}
    }
    
    /**
     * get identifier
     * @return identifier
     */
    public String getIdentifier() {
    	return (String) store.get(DTC.identifier);
    }
    
    /**
     * set Hexadecimal byte string
     * @param statusByte Hexadecimal byte string
     */
    public void setStatusByte(String statusByte) {
    	if (statusByte != null) {
    		store.put(DTC.statusByte, statusByte);
    	} else {
    		store.remove(DTC.statusByte);
    	}
    }
    
    /**
     * get Hexadecimal byte string
     * @return Hexadecimal byte string
     */
    public String getStatusByte() {
    	return (String) store.get(DTC.statusByte);
    }
}
