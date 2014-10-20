package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.constants.Names;

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
    public Vector<String> getAllowed() {
        if (store.get(Names.allowed) instanceof Vector<?>) {
        	Vector<?> list = (Vector<?>)store.get( Names.allowed);
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
            store.put(Names.allowed, allowed);
        } else {
    		store.remove(Names.allowed);
    	}
    }
    
    /**
     * get a set of all parameters that are prohibited for this given RPC.
     * @return a set of all parameters that are prohibited for this given RPC
     */
    @SuppressWarnings("unchecked")
    public Vector<String> getUserDisallowed() {
        if (store.get(Names.userDisallowed) instanceof Vector<?>) {
        	Vector<?> list = (Vector<?>)store.get( Names.userDisallowed);
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
            store.put(Names.userDisallowed, userDisallowed);
        } else {
    		store.remove(Names.userDisallowed);
    	}
    }
}
