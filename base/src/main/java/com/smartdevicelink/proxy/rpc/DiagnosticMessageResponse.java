package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;
import java.util.List;

/**
 * Diagnostic Message Response is sent, when DiagnosticMessage has been called.
 * 
 * @since SmartDeviceLink 3.0
 */
public class DiagnosticMessageResponse extends RPCResponse {
	public static final String KEY_MESSAGE_DATA_RESULT = "messageDataResult";
	/** 
	 * Constructs a new DiagnosticMessageResponse object
	 */

    public DiagnosticMessageResponse() {
        super(FunctionID.DIAGNOSTIC_MESSAGE.toString());
    }
    public DiagnosticMessageResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Constructs a new DiagnosticMessageResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public DiagnosticMessageResponse(@NonNull Boolean success, @NonNull Result resultCode, @NonNull List<Integer> messageDataResult) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
        setMessageDataResult(messageDataResult);
    }
    @SuppressWarnings("unchecked")
    public List<Integer> getMessageDataResult() {
        return (List<Integer>) getObject(Integer.class, KEY_MESSAGE_DATA_RESULT);
    }
    
    public void setMessageDataResult(@NonNull List<Integer> messageDataResult) {
        setParameters(KEY_MESSAGE_DATA_RESULT, messageDataResult);
    }


}
