package com.smartdevicelink.api.PermissionManager;

import com.smartdevicelink.protocol.enums.FunctionID;

import java.util.Map;

public class PermissionStatus {
    private FunctionID rpcName;
    private boolean isRPCAllowed;
    private Map<String, Boolean> allowedParameters;

    public PermissionStatus(FunctionID rpcName, boolean isRPCAllowed, Map<String, Boolean> allowedParameters) {
        this.rpcName = rpcName;
        this.isRPCAllowed = isRPCAllowed;
        this.allowedParameters = allowedParameters;
    }

    public FunctionID getRpcName() {
        return rpcName;
    }

    protected void setRpcName(FunctionID rpcName) {
        this.rpcName = rpcName;
    }

    public boolean getIsRPCAllowed() {
        return isRPCAllowed;
    }

    protected void setIsRPCAllowed(boolean isRPCAllowed) {
        isRPCAllowed = isRPCAllowed;
    }

    public Map<String, Boolean> getAllowedParameters() {
        return allowedParameters;
    }

    protected void setAllowedParameters(Map<String, Boolean> allowedParameters) {
        this.allowedParameters = allowedParameters;
    }
}