package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

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
    public Integer getTryAgainTime() {
        return getInteger(KEY_TRY_AGAIN_TIME);
    }
    public void setTryAgainTime(Integer tryAgainTime) {
        setParameter(KEY_TRY_AGAIN_TIME, tryAgainTime);
    }
}
