package com.smartdevicelink.proxy.rpc;

import java.util.List;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.util.JsonUtils;

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
public class ParameterPermissions extends RPCObject {
	public static final String KEY_ALLOWED = "allowed";
	public static final String KEY_USER_DISALLOWED = "userDisallowed";

	private List<String> allowed, userDisallowed;
	
	/**
	 *  Constructs a newly allocated ParameterPermissions object
	 */
    public ParameterPermissions() { }
    
    /**
     * Creates a ParameterPermissions object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public ParameterPermissions(JSONObject jsonObject){
        switch(sdlVersion){
        default:
            this.allowed = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_ALLOWED);
            this.userDisallowed = JsonUtils.readStringListFromJsonObject(jsonObject, KEY_USER_DISALLOWED);
            break;
        }
    }
    
    /**
     * get a set of all parameters that are permitted for this given RPC.
     * @return a set of all parameters that are permitted for this given RPC.
     */
    public List<String> getAllowed() {
        return this.allowed;
    }
    
    /**
     * set a set of all parameters that are permitted for this given RPC.
     * @param allowed parameter that is permitted for this given RPC
     */
    public void setAllowed(List<String> allowed) {
        this.allowed = allowed;
    }
    
    /**
     * get a set of all parameters that are prohibited for this given RPC.
     * @return a set of all parameters that are prohibited for this given RPC
     */
    public List<String> getUserDisallowed() {
        return this.userDisallowed;
    }
    
    /**
     * set a set of all parameters that are prohibited for this given RPC.
     * @param userDisallowed paramter that is prohibited for this given RPC
     */
    public void setUserDisallowed(List<String> userDisallowed) {
        this.userDisallowed = userDisallowed;
    }

    @Override
    public JSONObject getJsonParameters(int sdlVersion){
        JSONObject result = super.getJsonParameters(sdlVersion);
        
        switch(sdlVersion){
        default:
            JsonUtils.addToJsonObject(result, KEY_ALLOWED, (this.allowed == null) ? null : 
                    JsonUtils.createJsonArray(this.allowed));
            JsonUtils.addToJsonObject(result, KEY_USER_DISALLOWED, (this.userDisallowed == null) ? null : 
                    JsonUtils.createJsonArray(this.userDisallowed));
            break;
        }
        
        return result;
    }
}
