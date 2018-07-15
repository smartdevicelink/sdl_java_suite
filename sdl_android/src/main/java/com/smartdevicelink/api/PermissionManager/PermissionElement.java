package com.smartdevicelink.api.PermissionManager;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;

import java.util.List;

/**
 * PermissionElement holds the RPC name that the developer wants to add a listener for.
 * It also holds any permission parameters for that RPC that the developer wants to track as well.
 */
public class PermissionElement {
    private FunctionID rpcName;
    private List<String> parameters;

    /**
     * Create a new instance of PermissionElement
     * @param rpcName
     * @param parameters
     */
    public PermissionElement(@NonNull FunctionID rpcName, List<String> parameters){
        this.rpcName = rpcName;
        this.parameters = parameters;
    }

    /**
     * Get the RPC name
     * @return FunctionID value represents the RPC name
     */
    public FunctionID getRpcName() {
        return rpcName;
    }

    /**
     * Set the RPC name
     * @param rpcName
     */
    public void setRpcName(@NonNull FunctionID rpcName) {
        this.rpcName = rpcName;
    }

    /**
     * Get the permission parameters for the RPC
     * @return List<String> represents the permission parameters for the RPC
     */
    public List<String> getParameters() {
        return parameters;
    }

    /**
     * Set the permission parameters for the RPC
     * @param parameters
     */
    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }
}