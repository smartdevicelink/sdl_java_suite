package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCStruct;

/**
 * Defining sets of parameters, which are permitted or prohibited for a given RPC.
 * <p><b>Parameter List
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
	public static final String allowed = "allowed";
	public static final String userDisallowed = "userDisallowed";

	/**
	 *  Constructs a newly allocated ParameterPermissions object
	 */
    public ParameterPermissions() { }
    
    /**
     * Constructs a newly allocated ParameterPermissions object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public ParameterPermissions(Hashtable hash) {
        super(hash);
    }
    
    /**
     * get a set of all parameters that are permitted for this given RPC.
     * @return a set of all parameters that are permitted for this given RPC.
     */
    public Vector<String> getAllowed() {
        if (store.get(ParameterPermissions.allowed) instanceof Vector<?>) {
        	Vector<?> list = (Vector<?>)store.get( ParameterPermissions.allowed);
        	if (list != null && list.size() > 0) {
        		Object obj = list.get(0);
        		if (obj instanceof String) {
                	return (Vector<String>) list;        			
        		}
        	}
        }
        return null;
    }
    
    /**
     * set a set of all parameters that are permitted for this given RPC.
     * @param allowed parameter that is permitted for this given RPC
     */
    public void setAllowed(Vector<String> allowed) {
        if (allowed != null) {
            store.put(ParameterPermissions.allowed, allowed);
        } else {
    		store.remove(ParameterPermissions.allowed);
    	}
    }
    
    /**
     * get a set of all parameters that are prohibited for this given RPC.
     * @return a set of all parameters that are prohibited for this given RPC
     */
    public Vector<String> getUserDisallowed() {
        if (store.get(ParameterPermissions.userDisallowed) instanceof Vector<?>) {
        	Vector<?> list = (Vector<?>)store.get( ParameterPermissions.userDisallowed);
        	if (list != null && list.size() > 0) {
        		Object obj = list.get(0);
        		if (obj instanceof String) {
                	return (Vector<String>) list;        			
        		}
        	}
        }
        return null;
    }
    
    /**
     * set a set of all parameters that are prohibited for this given RPC.
     * @param userDisallowed paramter that is prohibited for this given RPC
     */
    public void setUserDisallowed(Vector<String> userDisallowed) {
        if (userDisallowed != null) {
            store.put(ParameterPermissions.userDisallowed, userDisallowed);
        } else {
    		store.remove(ParameterPermissions.userDisallowed);
    	}
    }
}
