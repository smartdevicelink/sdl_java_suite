package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.util.JsonUtils;

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
	
	private List<PermissionItem> permissionItems;
	
	/**
	*Constructs a newly allocated OnCommand object
	*/    
	public OnPermissionsChange() {
		super(FunctionID.ON_PERMISSIONS_CHANGE);
	}
	
    /**
     * Creates a OnPermissionsChange object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public OnPermissionsChange(JSONObject jsonObject){
        super(jsonObject);
        switch(sdlVersion){
        default:
            List<JSONObject> permissionItemObjs = JsonUtils.readJsonObjectListFromJsonObject(jsonObject, KEY_PERMISSION_ITEM);
            if(permissionItemObjs != null){
                this.permissionItems = new ArrayList<PermissionItem>(permissionItemObjs.size());
                for(JSONObject permissionItemObj : permissionItemObjs){
                    this.permissionItems.add(new PermissionItem(permissionItemObj));
                }
            }
            break;
        }
    }
	
	/**
     * <p>Returns List<PermissionItem> object describing change in permissions for a given set of RPCs</p>
     * @return List<{@linkplain PermissionItem}> an object describing describing change in permissions for a given set of RPCs
     */
	public List<PermissionItem> getPermissionItem() {
		return this.permissionItems;
	}
	
    /**
     * <p>Sets PermissionItems describing change in permissions for a given set of RPCs</p>    
     * @param permissionItem an List of  PermissionItem describing change in permissions for a given set of RPCs
     */  
	public void setPermissionItem(List<PermissionItem> permissionItem) {
		this.permissionItems = permissionItem;
	}
	
	@Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_PERMISSION_ITEM, 
                    (this.permissionItems == null) ? null : JsonUtils.createJsonArrayOfJsonObjects(this.permissionItems, sdlVersion));
            break;
        }
        
        return result;
    }
}
