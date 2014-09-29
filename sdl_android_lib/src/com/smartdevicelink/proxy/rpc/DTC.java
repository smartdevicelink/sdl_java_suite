package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.constants.Names;

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
    		store.put(Names.identifier, identifier);
    	} else {
    		store.remove(Names.identifier);
    	}
    }
    
    /**
     * get identifier
     * @return identifier
     */
    public String getIdentifier() {
    	return (String) store.get(Names.identifier);
    }
    
    /**
     * set Hexadecimal byte string
     * @param statusByte Hexadecimal byte string
     */
    public void setStatusByte(String statusByte) {
    	if (statusByte != null) {
    		store.put(Names.statusByte, statusByte);
    	} else {
    		store.remove(Names.statusByte);
    	}
    }
    
    /**
     * get Hexadecimal byte string
     * @return Hexadecimal byte string
     */
    public String getStatusByte() {
    	return (String) store.get(Names.statusByte);
    }
}
