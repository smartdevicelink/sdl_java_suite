package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;
import java.util.List;

/**
 * Read DID Response is sent, when ReadDID has been called
 * 
 * @since SmartDeviceLink 2.0
 */
public class ReadDIDResponse extends RPCResponse {
	public static final String KEY_DID_RESULT = "didResult";

    public ReadDIDResponse() {
        super(FunctionID.READ_DID.toString());
    }
    public ReadDIDResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Constructs a new ReadDIDResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public ReadDIDResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }
    public void setDidResult(List<DIDResult> didResult) {
		setParameters(KEY_DID_RESULT, didResult);
    }
    @SuppressWarnings("unchecked")
    public List<DIDResult> getDidResult() {
		return (List<DIDResult>) getObject(DIDResult.class, KEY_DID_RESULT);
    }
}
