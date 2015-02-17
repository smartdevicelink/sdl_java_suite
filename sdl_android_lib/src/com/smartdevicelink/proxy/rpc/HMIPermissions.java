package com.smartdevicelink.proxy.rpc;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.util.JsonUtils;
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
public class HMIPermissions extends RPCObject {
	public static final String KEY_ALLOWED = "allowed";
	public static final String KEY_USER_DISALLOWED = "userDisallowed";
	
	private List<String> allowed, userDisallowed; // represents HMILevel enum
	
	/**
	 * Constructs a newly allocated HMIPermissions object
	 */
    public HMIPermissions() { }
    
    /**
     * Creates a HMIPermissions object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public HMIPermissions(JSONObject jsonObject) {
        switch(sdlVersion){
        default:
            this.allowed = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_ALLOWED);
            this.userDisallowed = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_USER_DISALLOWED);
            break;
        }
    }
    
    /**
     * get a set of all HMI levels that are permitted for this given RPC.
     * @return   a set of all HMI levels that are permitted for this given RPC
     */
    public List<HMILevel> getAllowed() {
        if(this.allowed == null){
            return null;
        }
        
        List<HMILevel> result = new ArrayList<HMILevel>(this.allowed.size());
        for(String str : this.allowed){
            result.add(HMILevel.valueForJsonName(str, sdlVersion));
        }
        return result;
    }
    
    /**
     * set  HMI level that is permitted for this given RPC.
     * @param allowed HMI level that is permitted for this given RPC
     */
    public void setAllowed(List<HMILevel> allowed) {
        if(allowed == null){
            this.allowed = null;
        }
        else{
            this.allowed = new ArrayList<String>(allowed.size());
            for(HMILevel level : allowed){
                this.allowed.add(level.getJsonName(sdlVersion));
            }
        }
    }
    
    /**
     * get a set of all HMI levels that are prohibited for this given RPC
     * @return a set of all HMI levels that are prohibited for this given RPC
     */
    public List<HMILevel> getUserDisallowed() {
        if(this.userDisallowed == null){
            return null;
        }
        
        List<HMILevel> result = new ArrayList<HMILevel>(this.userDisallowed.size());
        for(String str : this.userDisallowed){
            result.add(HMILevel.valueForJsonName(str, sdlVersion));
        }
        return result;
    }
    
    /**
     * set a set of all HMI levels that are prohibited for this given RPC
     * @param userDisallowed  HMI level that is prohibited for this given RPC
     */
    public void setUserDisallowed(List<HMILevel> userDisallowed) {
        if(userDisallowed == null){
            this.userDisallowed = null;
        }
        else{
            this.userDisallowed = new ArrayList<String>(userDisallowed.size());
            for(HMILevel level : userDisallowed){
                this.userDisallowed.add(level.getJsonName(sdlVersion));
            }
        }
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_ALLOWED, 
                    (this.allowed == null) ? null : JsonUtils.createJsonArray(this.allowed));
            JsonUtils.addToJsonObject(result, KEY_USER_DISALLOWED, 
                    (this.userDisallowed == null) ? null : JsonUtils.createJsonArray(this.userDisallowed));
            break;
        }
        
        return result;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((allowed == null) ? 0 : allowed.hashCode());
		result = prime * result + ((userDisallowed == null) ? 0 : userDisallowed.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) { 
			return true;
		}
		if (obj == null) { 
			return false;
		}
		if (getClass() != obj.getClass()) { 
			return false;
		}
		HMIPermissions other = (HMIPermissions) obj;
		if (allowed == null) {
			if (other.allowed != null) { 
				return false;
			}
		} 
		else if (!allowed.equals(other.allowed)) { 
			return false;
		}
		if (userDisallowed == null) {
			if (other.userDisallowed != null) { 
				return false;
			}
		} 
		else if (!userDisallowed.equals(other.userDisallowed)) { 
			return false;
		}
		return true;
	}
}
