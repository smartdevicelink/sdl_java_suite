package com.smartdevicelink.proxy.rpc;


import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
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
        return getString(KEY_RPC_NAME);
    }
    public void setRpcName(String rpcName) {
        setValue(KEY_RPC_NAME, rpcName);
    }
    @SuppressWarnings("unchecked")
    public HMIPermissions getHMIPermissions() {
        return (HMIPermissions) getObject(HMIPermissions.class, KEY_HMI_PERMISSIONS);
    }
    public void setHMIPermissions(HMIPermissions hmiPermissions) {
        setValue(KEY_HMI_PERMISSIONS, hmiPermissions);
    }
    @SuppressWarnings("unchecked")
    public ParameterPermissions getParameterPermissions() {
    	return (ParameterPermissions) getObject(ParameterPermissions.class, KEY_PARAMETER_PERMISSIONS);
    }
    public void setParameterPermissions(ParameterPermissions parameterPermissions) {
        setValue(KEY_PARAMETER_PERMISSIONS, parameterPermissions);
    }
}
