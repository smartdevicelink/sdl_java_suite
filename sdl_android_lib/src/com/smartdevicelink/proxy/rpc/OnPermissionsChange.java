package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;
import java.util.Vector;

import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.constants.Names;

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
	/**
	*Constructs a newly allocated OnCommand object
	*/    
	public OnPermissionsChange() {
		super("OnPermissionsChange");
	}
	/**
     *<p>Constructs a newly allocated OnPermissionsChange object indicated by the Hashtable parameter</p>
     *@param hash The Hashtable to use
     */
	public OnPermissionsChange(Hashtable<String, Object> hash) {
		super(hash);
	}
	/**
     * <p>Returns Vector<PermissionItem> object describing change in permissions for a given set of RPCs</p>
     * @return Vector<{@linkplain PermissionItem}> an object describing describing change in permissions for a given set of RPCs
     */   
	@SuppressWarnings("unchecked")
    public Vector<PermissionItem> getPermissionItem() {
		Vector<?> list = (Vector<?>)parameters.get(Names.permissionItem);
		if (list != null && list.size()>0) {
			Object obj = list.get(0);
			if(obj instanceof PermissionItem){
				return (Vector<PermissionItem>) list;
			} else if(obj instanceof Hashtable) {
				Vector<PermissionItem> newList = new Vector<PermissionItem>();
				for (Object hash:list) {
					newList.add(new PermissionItem((Hashtable<String, Object>)hash));
				}
				return newList;
			}
		}
		return null;
	}
    /**
     * <p>Sets PermissionItems describing change in permissions for a given set of RPCs</p>    
     * @param permissionItem an vector of  PermissionItem describing change in permissions for a given set of RPCs
     */  
	public void setPermissionItem(Vector<PermissionItem> permissionItem) {
		if (permissionItem != null) {
			parameters.put(Names.permissionItem, permissionItem);
		} else {
			parameters.remove(Names.permissionItem);
        }
	}
}
