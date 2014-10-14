package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCStruct;
import com.smartdevicelink.util.DebugTool;

public class PermissionItem extends RPCStruct {
	public static final String rpcName = "rpcName";
	public static final String hmiPermissions = "hmiPermissions";
	public static final String parameterPermissions = "parameterPermissions";

    public PermissionItem() { }
    public PermissionItem(Hashtable hash) {
        super(hash);
    }
    public String getRpcName() {
        return (String) store.get(PermissionItem.rpcName);
    }
    public void setRpcName(String rpcName) {
        if (rpcName != null) {
        	store.put(PermissionItem.rpcName, rpcName);
        } else {
        	store.remove(PermissionItem.rpcName);
        }
    }
    public HMIPermissions getHMIPermissions() {
    	Object obj = store.get(PermissionItem.hmiPermissions);
        if (obj instanceof HMIPermissions) {
            return (HMIPermissions) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new HMIPermissions((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + PermissionItem.hmiPermissions, e);
            }
        }
        return null;
    }
    public void setHMIPermissions(HMIPermissions hmiPermissions) {
        if (hmiPermissions != null) {
        	store.put(PermissionItem.hmiPermissions, hmiPermissions);
        } else {
        	store.remove(PermissionItem.hmiPermissions);
        }
    }
    public ParameterPermissions getParameterPermissions() {
    	Object obj = store.get(PermissionItem.parameterPermissions);
        if (obj instanceof ParameterPermissions) {
            return (ParameterPermissions) obj;
        } else if (obj instanceof Hashtable) {
        	try {
        		return new ParameterPermissions((Hashtable) obj);
            } catch (Exception e) {
            	DebugTool.logError("Failed to parse " + getClass().getSimpleName() + "." + PermissionItem.parameterPermissions, e);
            }
        }
        return null;
    }
    public void setParameterPermissions(ParameterPermissions parameterPermissions) {
        if (parameterPermissions != null) {
        	store.put(PermissionItem.parameterPermissions, parameterPermissions);
        } else {
        	store.remove(PermissionItem.parameterPermissions);
        }
    }
}
