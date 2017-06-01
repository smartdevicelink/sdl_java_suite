package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

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
    public void setDidResult(List<DIDResult> didResult) {
		setParameter(KEY_DID_RESULT, didResult);
    }
    @SuppressWarnings("unchecked")
    public List<DIDResult> getDidResult() {
		return (List<DIDResult>) getObject(DIDResult.class, KEY_DID_RESULT);
    }
}
