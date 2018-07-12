package com.smartdevicelink.api.PermissionManager;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;

import java.util.List;

public class PermissionElement {
    private FunctionID rpcName;
    private List<String> parameters;

    public PermissionElement(@NonNull FunctionID rpcName, List<String> parameters){
        this.rpcName = rpcName;
        this.parameters = parameters;
    }

    public FunctionID getRpcName() {
        return rpcName;
    }

    public void setRpcName(@NonNull FunctionID rpcName) {
        this.rpcName = rpcName;
    }

    public List<String> getParameters() {
        return parameters;
    }

    public void setParameters(List<String> parameters) {
        this.parameters = parameters;
    }
}