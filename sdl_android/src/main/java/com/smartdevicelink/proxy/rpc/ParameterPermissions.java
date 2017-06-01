package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.proxy.RPCStruct;

import java.util.Hashtable;
import java.util.List;

/**
 * Defining sets of parameters, which are permitted or prohibited for a given RPC.
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 * 			<th>SmartDeviceLink Ver. Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>allowed</td>
 * 			<td>String</td>
 * 			<td>A set of all parameters that are permitted for this given RPC.
 * 					<ul>
 *					<li>Min size: 0</li>
 *					<li>Max size: 100</li>
 *					<li>Max length: 100</li>
 *					</ul>
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>userDisallowed</td>
 * 			<td>String</td>
 * 			<td>A set of all parameters that are prohibated for this given RPC.
 * 					<ul>
 *					<li>Min size: 0</li>
 *					<li>Max size: 100</li>
 *					<li>Max length: 100</li>
 *					</ul>
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 2.0
 */
public class ParameterPermissions extends RPCStruct {
	public static final String KEY_ALLOWED = "allowed";
	public static final String KEY_USER_DISALLOWED = "userDisallowed";

	/**
	 *  Constructs a newly allocated ParameterPermissions object
	 */
    public ParameterPermissions() { }
    
    /**
     * Constructs a newly allocated ParameterPermissions object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public ParameterPermissions(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    /**
     * get a set of all parameters that are permitted for this given RPC.
     * @return a set of all parameters that are permitted for this given RPC.
     */
    @SuppressWarnings("unchecked")
    public List<String> getAllowed() {
        return (List<String>) getObject(String.class, KEY_ALLOWED);
    }
    
    /**
     * set a set of all parameters that are permitted for this given RPC.
     * @param allowed parameter that is permitted for this given RPC
     */
    public void setAllowed(List<String> allowed) {
        setValue(KEY_ALLOWED, allowed);
    }
    
    /**
     * get a set of all parameters that are prohibited for this given RPC.
     * @return a set of all parameters that are prohibited for this given RPC
     */
    @SuppressWarnings("unchecked")
    public List<String> getUserDisallowed() {
        return (List<String>) getObject(String.class, KEY_USER_DISALLOWED);
    }
    
    /**
     * set a set of all parameters that are prohibited for this given RPC.
     * @param userDisallowed paramter that is prohibited for this given RPC
     */
    public void setUserDisallowed(List<String> userDisallowed) {
        setValue(KEY_USER_DISALLOWED, userDisallowed);
    }
}
