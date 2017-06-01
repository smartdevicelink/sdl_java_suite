package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;

import java.util.Hashtable;
import java.util.List;

/**
 * Provides update to app of which sets of functions are available
 * <p>
 * </p>
 * <b>HMI Status Requirements:</b>
 * <ul>
 * HMILevel:
 * <ul>
 * <li>Any</li>
 * </ul>
 * AudioStreamingState:
 * <ul>
 * <li>TBD</li>
 * </ul>
 * SystemContext:
 * <ul>
 * <li>TBD</li>
 * </ul>
 * </ul>
 * <p>
 * <b>Parameter List:</b>
 * <table border="1" rules="all">
 * <tr>
 * <th>Name</th>
 * <th>Type</th>
 * <th>Description</th>
 * <th>Req</th>
 * <th>Notes</th>
 * <th>SmartDeviceLink Ver Available</th>
 * </tr>
 * <tr>
 * <td>permissionItem</td>
 * <td>PermissionItem[]</td>
 * <td>Change in permissions for a given set of RPCs</td>
 * <td>Y</td>
 * <td>Minsize=1 Maxsize=100</td>
 * <td>SmartDeviceLink 2.0</td>
 * </tr>
 * </table>
 * </p>
 */
public class OnPermissionsChange extends RPCNotification {
	public static final String KEY_PERMISSION_ITEM = "permissionItem";
	/**
	*Constructs a newly allocated OnCommand object
	*/    
	public OnPermissionsChange() {
		super(FunctionID.ON_PERMISSIONS_CHANGE.toString());
	}
	/**
     *<p>Constructs a newly allocated OnPermissionsChange object indicated by the Hashtable parameter</p>
     *@param hash The Hashtable to use
     */
	public OnPermissionsChange(Hashtable<String, Object> hash) {
		super(hash);
	}
	/**
     * <p>Returns List<PermissionItem> object describing change in permissions for a given set of RPCs</p>
     * @return List<{@linkplain PermissionItem}> an object describing describing change in permissions for a given set of RPCs
     */   
    @SuppressWarnings("unchecked")
	public List<PermissionItem> getPermissionItem() {
		return (List<PermissionItem>) getObject(PermissionItem.class, KEY_PERMISSION_ITEM);
	}
    /**
     * <p>Sets PermissionItems describing change in permissions for a given set of RPCs</p>    
     * @param permissionItem an List of  PermissionItem describing change in permissions for a given set of RPCs
     */  
	public void setPermissionItem(List<PermissionItem> permissionItem) {
		setParameter(KEY_PERMISSION_ITEM, permissionItem);
	}
}
