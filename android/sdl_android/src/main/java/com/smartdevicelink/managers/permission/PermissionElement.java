package com.smartdevicelink.managers.permission;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;

import java.util.List;

/**
 * PermissionElement holds the RPC name that the developer wants to add a listener for.
 * It also holds any permission parameters for that RPC that the developer wants to track as well.
 */
public class PermissionElement {
    private final FunctionID rpcName;
    private final List<String> parameters;

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
    public FunctionID getRPCName() {
        return rpcName;
    }

    /**
     * Get the permission parameters for the RPC
     * @return List<String> represents the permission parameters for the RPC
     */
    public List<String> getParameters() {
        return parameters;
    }
}