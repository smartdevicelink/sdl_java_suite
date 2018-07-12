package com.smartdevicelink.api.PermissionManager;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;

import java.util.Map;

public class PermissionStatus {
    private FunctionID rpcName;
    private boolean isRPCAllowed;
    private Map<String, Boolean> allowedParameters;

    public PermissionStatus(@NonNull FunctionID rpcName, @NonNull boolean isRPCAllowed, Map<String, Boolean> allowedParameters) {
        this.rpcName = rpcName;
        this.isRPCAllowed = isRPCAllowed;
        this.allowedParameters = allowedParameters;
    }

    public FunctionID getRpcName() {
        return rpcName;
    }

    protected void setRpcName(@NonNull FunctionID rpcName) {
        this.rpcName = rpcName;
    }

    public boolean getIsRPCAllowed() {
        return isRPCAllowed;
    }

    protected void setIsRPCAllowed(@NonNull boolean isRPCAllowed) {
        this.isRPCAllowed = isRPCAllowed;
    }

    public Map<String, Boolean> getAllowedParameters() {
        return allowedParameters;
    }

    protected void setAllowedParameters(Map<String, Boolean> allowedParameters) {
        this.allowedParameters = allowedParameters;
    }
}