package com.smartdevicelink.proxy.rpc;


import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.util.DebugTool;
/**
 * Change in permissions for a given set of RPCs
 * 
 * <p><b>Parameter List</b></p>
 * <table border="1" rules="all">
 * 		<tr>
 * 			<th>Param Name</th>
 * 			<th>Type</th>
 * 			<th>Description</th>
 *                 <th> Req.</th>
 * 			<th>Notes</th>
 * 			<th>Version Available</th>
 * 		</tr>
 * 		<tr>
 * 			<td>rpcName</td>
 * 			<td>String</td>
 * 			<td>Name of the individual RPC in the policy table.</td>
 *                 <td></td>
 * 			<td>maxlength:100</td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 * 		<tr>
 * 			<td>hmiPermissions</td>
 * 			<td>HMIPermissions</td>
 * 			<td>Sets of parameters, which are permitted or prohibited for the given RPC.</td>
 *                 <td></td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *
 * 		<tr>
 * 			<td>parameterPermissions</td>
 * 			<td>ParameterPermissions</td>
 * 			<td>Sets of parameters, which are permitted or prohibited for the given RPC.</td>
 *                 <td></td>
 * 			<td></td>
 * 			<td>SmartDeviceLink 2.0</td>
 * 		</tr>
 *  </table>
 *
 * @since SmartDeviceLink 2.0
 */
public class PermissionItem extends RPCStruct {
	public static final String KEY_RPC_NAME = "rpcName";
	public static final String KEY_HMI_PERMISSIONS = "hmiPermissions";
	public static final String KEY_PARAMETER_PERMISSIONS = "parameterPermissions";
	/**
	* <p>
	* Constructs a new PermissionItem object indicated by the Hashtable
	* parameter
	* </p>
	* 
	* @param hash
	*            The Hashtable to use
	*/

    public PermissionItem() { }
    public PermissionItem(Hashtable<String, Object> hash) {
        super(hash);
    }
    public String getRpcName() {
        return (String) store.get(KEY_RPC_NAME);
    }
    public void setRpcName(String rpcName) {
        if (rpcName != null) {
        	store.put(KEY_RPC_NAME, rpcName);
        } else {
        	store.remove(KEY_RPC_NAME);
        }
    }
    @SuppressWarnings("unchecked")
    public HMIPermissions getHMIPermissions() {
    	Object obj = store.get(KEY_HMI_PERMISSIONS);
        if (obj instanceof HMIPermissions) {
            return (HMIPermissions) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new HMIPermissions((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_HMI_PERMISSIONS, e);
            }
        }
        return null;
    }
    public void setHMIPermissions(HMIPermissions hmiPermissions) {
        if (hmiPermissions != null) {
        	store.put(KEY_HMI_PERMISSIONS, hmiPermissions);
        } else {
        	store.remove(KEY_HMI_PERMISSIONS);
        }
    }
    @SuppressWarnings("unchecked")
    public ParameterPermissions getParameterPermissions() {
    	Object obj = store.get(KEY_PARAMETER_PERMISSIONS);
        if (obj instanceof ParameterPermissions) {
            return (ParameterPermissions) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new ParameterPermissions((Hashtable<String, Object>) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + KEY_PARAMETER_PERMISSIONS, e);
            }
        }
        return null;
    }
    public void setParameterPermissions(ParameterPermissions parameterPermissions) {
        if (parameterPermissions != null) {
        	store.put(KEY_PARAMETER_PERMISSIONS, parameterPermissions);
        } else {
        	store.remove(KEY_PARAMETER_PERMISSIONS);
        }
    }
}
