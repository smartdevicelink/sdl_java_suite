package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;

/**
 * Alert Response is sent, when Alert has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class AlertResponse extends RPCResponse {
	public static final String KEY_TRY_AGAIN_TIME = "tryAgainTime";

	/**
	 * Constructs a new AlertResponse object
	 */
    public AlertResponse() {
        super(FunctionID.ALERT.toString());
    }

	/**
	 * <p>Constructs a new AlertResponse object indicated by the Hashtable
	 * parameter
	 * </p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public AlertResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Constructs a new AlertResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public AlertResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }
    public Integer getTryAgainTime() {
        return getInteger(KEY_TRY_AGAIN_TIME);
    }
    public void setTryAgainTime(Integer tryAgainTime) {
        setParameters(KEY_TRY_AGAIN_TIME, tryAgainTime);
    }
}
