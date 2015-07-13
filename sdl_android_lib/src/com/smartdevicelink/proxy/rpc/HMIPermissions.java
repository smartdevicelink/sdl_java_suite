package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.util.DebugTool;
/**
 * Defining sets of HMI levels, which are permitted or prohibited for a given RPC.
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
 * 			<td>HMILevel</td>
 * 			<td>A set of all HMI levels that are permitted for this given RPC.
 * 					<ul>
 *					<li>Min: 0</li>
 *					<li>Max: 100</li>
 *					</ul>
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>userDisallowed</td>
 * 			<td>HMILevel</td>
 * 			<td>A set of all HMI levels that are prohibated for this given RPC.
 * 					<ul>
 *					<li>Min: 0</li>
 *					<li>Max: 100</li>
 *					</ul>
 * 			</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 * @since SmartDeviceLink 2.0
 */
public class HMIPermissions extends RPCStruct {
	public static final String KEY_ALLOWED = "allowed";
	public static final String KEY_USER_DISALLOWED = "userDisallowed";
	/**
	 * Constructs a newly allocated HMIPermissions object
	 */
    public HMIPermissions() { }
    
    /**
     * Constructs a newly allocated HMIPermissions object indicated by the Hashtable parameter
     * @param hash The Hashtable to use
     */
    public HMIPermissions(Hashtable<String, Object> hash) {
        super(hash);
    }
    
    /**
     * get a set of all HMI levels that are permitted for this given RPC.
     * @return   a set of all HMI levels that are permitted for this given RPC
     */
    @SuppressWarnings("unchecked")
    public List<HMILevel> getAllowed() {
        if (store.get(KEY_ALLOWED) instanceof List<?>) {
	    	List<?> list = (List<?>)store.get(KEY_ALLOWED);
	        if (list != null && list.size() > 0) {
	            
	        	List<HMILevel> hmiLevelList  = new ArrayList<HMILevel>();

	        	boolean flagRaw = false;
	        	boolean flagStr = false;
	        	
	        	for ( Object obj : list ) {
	        		
	        		// This does not currently allow for a mixing of types, meaning
	        		// there cannot be a raw HMILevel and a String value in the
	        		// same same list. It will not be considered valid currently.
	        		if (obj instanceof HMILevel) {
	        			if (flagStr) {
	        				return null;
	        			}

	        			flagRaw = true;

	        		} else if (obj instanceof String) {
	        			if (flagRaw) {
	        				return null;
	        			}

	        			flagStr = true;
	        			
	        			String strFormat = (String) obj;
	                    HMILevel toAdd = null;
	                    try {
	                        toAdd = HMILevel.valueForString(strFormat);
	                    } catch (Exception e) {
	                    	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_ALLOWED, e);
	                    }

	                    if (toAdd != null) {
	                    	hmiLevelList.add(toAdd);
	                    }

	        		} else {
	        			return null;
	        		}

	        	}

	        	if (flagRaw) {
	        		return (List<HMILevel>) list;
	        	} else if (flagStr) {
	        		return hmiLevelList;
	        	}
	        }
        }
        return null;
    }
    
    /**
     * set  HMI level that is permitted for this given RPC.
     * @param allowed HMI level that is permitted for this given RPC
     */
    public void setAllowed(List<HMILevel> allowed) {

    	boolean valid = true;
    	
    	for ( HMILevel item : allowed ) {
    		if (item == null) {
    			valid = false;
    		}
    	}
    	
    	if ( (allowed != null) && (allowed.size() > 0) && valid) {
            store.put(KEY_ALLOWED, allowed);
        } else {
    		store.remove(KEY_ALLOWED);
    	}
    }
    
    /**
     * get a set of all HMI levels that are prohibited for this given RPC
     * @return a set of all HMI levels that are prohibited for this given RPC
     */
    @SuppressWarnings("unchecked")
    public List<HMILevel> getUserDisallowed() {
        if (store.get(KEY_USER_DISALLOWED) instanceof List<?>) {
	    	List<?> list = (List<?>)store.get(KEY_USER_DISALLOWED);
	        if (list != null && list.size() > 0) {
	            Object obj = list.get(0);
	            if (obj instanceof HMILevel) {
	                return (List<HMILevel>) list;
	            } else if (obj instanceof String) {
	                List<HMILevel> newList = new ArrayList<HMILevel>();
	                for (Object hashObj : list) {
	                    String strFormat = (String)hashObj;
	                    HMILevel toAdd = HMILevel.valueForString(strFormat);
	                    if (toAdd != null) {
	                        newList.add(toAdd);
	                    }
	                }
	                return newList;
	            }
	        }
        }
        return null;
    }
    
    /**
     * set a set of all HMI levels that are prohibited for this given RPC
     * @param userDisallowed  HMI level that is prohibited for this given RPC
     */
    public void setUserDisallowed(List<HMILevel> userDisallowed) {
        if (userDisallowed != null) {
            store.put(KEY_USER_DISALLOWED, userDisallowed);
        } else {
    		store.remove(KEY_USER_DISALLOWED);
    	}
    }
}
