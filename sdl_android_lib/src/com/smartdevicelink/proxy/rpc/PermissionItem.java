package com.smartdevicelink.proxy.rpc;

import org.json.JSONObject;

import com.smartdevicelink.proxy.RPCObject;
import com.smartdevicelink.util.JsonUtils;

public class PermissionItem extends RPCObject {
	public static final String KEY_RPC_NAME = "rpcName";
	public static final String KEY_HMI_PERMISSIONS = "hmiPermissions";
	public static final String KEY_PARAMETER_PERMISSIONS = "parameterPermissions";

	private String rpcName;
	private HMIPermissions hmiPermissions;
	private ParameterPermissions parameterPermissions;
	
    public PermissionItem() { }
    
    /**
     * Creates a PermissionItem object from a JSON object.
     * 
     * @param jsonObject The JSON object to read from
     */
    public PermissionItem(JSONObject jsonObject){
        switch(sdlVersion){
        default:
            this.rpcName = JsonUtils.readStringFromJsonObject(jsonObject, KEY_RPC_NAME);
            
            JSONObject hmiPermissionsObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_HMI_PERMISSIONS);
            if(hmiPermissionsObj != null){
                this.hmiPermissions = new HMIPermissions(hmiPermissionsObj);
            }
            
            JSONObject parameterPermissionsObj = JsonUtils.readJsonObjectFromJsonObject(jsonObject, KEY_PARAMETER_PERMISSIONS);
            if(parameterPermissionsObj != null){
                this.parameterPermissions = new ParameterPermissions(parameterPermissionsObj);
            }
            break;
        }
    }
    
    public String getRpcName() {
        return this.rpcName;
    }
    
    public void setRpcName(String rpcName) {
        this.rpcName = rpcName;
    }
    
    public HMIPermissions getHMIPermissions() {
    	return this.hmiPermissions;
    }
    
    public void setHMIPermissions(HMIPermissions hmiPermissions) {
        this.hmiPermissions = hmiPermissions;
    }
    
    public ParameterPermissions getParameterPermissions() {
    	return this.parameterPermissions;
    }
    
    public void setParameterPermissions(ParameterPermissions parameterPermissions) {
        this.parameterPermissions = parameterPermissions;
    }
    
    @Override
public JSONObject getJsonParameters(int sdlVersion){
    JSONObject result = super.getJsonParameters(sdlVersion);
    
    switch(sdlVersion){
    default:
        JsonUtils.addToJsonObject(result, KEY_RPC_NAME, this.rpcName);
        JsonUtils.addToJsonObject(result, KEY_HMI_PERMISSIONS, (this.hmiPermissions == null) ? null :
            this.hmiPermissions.getJsonParameters(sdlVersion));
        break;
    }
    
    return result;
}
}
