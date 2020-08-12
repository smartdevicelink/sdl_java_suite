package com.smartdevicelink.proxy.rpc;

import androidx.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;
import java.util.List;

public class GetInteriorVehicleDataConsentResponse extends RPCResponse {

    public static final String KEY_ALLOWED = "allowed";

    public GetInteriorVehicleDataConsentResponse() {
        super(FunctionID.GET_INTERIOR_VEHICLE_DATA_CONSENT.toString());
    }

    public GetInteriorVehicleDataConsentResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

    public GetInteriorVehicleDataConsentResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }

    /**
     * Sets the list of allowances for this class
     * @param allowances the allowances to be set
     */
    public void setAllowances(@NonNull List<Boolean> allowances) {
        setParameters(KEY_ALLOWED, allowances);
    }

    /**
     * Gets the list of allowances of this class
     * @return the list of allowances of this class
     */
    @SuppressWarnings("unchecked")
    public List<Boolean> getAllowances() {
        return (List<Boolean>) getObject(Boolean.class, KEY_ALLOWED);
    }
}
