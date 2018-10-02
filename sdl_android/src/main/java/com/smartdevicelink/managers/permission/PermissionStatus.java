package com.smartdevicelink.managers.permission;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;

import java.util.Map;

/**
 * PermissionStatus gives a detailed view about whether an RPC and its permission parameters are allowed or not
 */
public class PermissionStatus {
    private final FunctionID rpcName;
    private boolean isRPCAllowed;
    private Map<String, Boolean> allowedParameters;

    /**
     * Creates a new PermissionStatus instance
     * @param rpcName
     * @param isRPCAllowed
     * @param allowedParameters
     */
    public PermissionStatus(@NonNull FunctionID rpcName, @NonNull boolean isRPCAllowed, Map<String, Boolean> allowedParameters) {
        this.rpcName = rpcName;
        this.isRPCAllowed = isRPCAllowed;
        this.allowedParameters = allowedParameters;
    }

    /**
     * Get the name of the RPC
     * @return FunctionID value represents the name of the RPC
     */
    public FunctionID getRPCName() {
        return rpcName;
    }

    /**
     * Get whether the RCP is allowed or not
     * @return boolean represents whether the RCP is allowed or not
     */
    public boolean getIsRPCAllowed() {
        return isRPCAllowed;
    }

    /**
     * Set whether the RPC is allowed or not
     * @param isRPCAllowed
     */
    protected void setIsRPCAllowed(@NonNull boolean isRPCAllowed) {
        this.isRPCAllowed = isRPCAllowed;
    }

    /**
     * Get the status of the permission parameter for the RPC
     * @return Map<String, Boolean> object with keys that represent the permission parameter names and values that represent whether the parameters are allowed or not
     */
    public Map<String, Boolean> getAllowedParameters() {
        return allowedParameters;
    }

    /**
     * Set the status of the permission parameter for the RPC
     * @param allowedParameters
     */
    protected void setAllowedParameters(Map<String, Boolean> allowedParameters) {
        this.allowedParameters = allowedParameters;
    }
}