package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;

public class ReleaseInteriorVehicleDataModuleResponse extends RPCResponse {

    public ReleaseInteriorVehicleDataModuleResponse() {
        super(FunctionID.RELEASE_INTERIOR_VEHICLE_MODULE.toString());
    }

    public ReleaseInteriorVehicleDataModuleResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

    public ReleaseInteriorVehicleDataModuleResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }
}