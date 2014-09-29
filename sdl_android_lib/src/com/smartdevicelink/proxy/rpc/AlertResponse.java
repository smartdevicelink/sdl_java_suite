package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.constants.Names;

/**
 * Alert Response is sent, when Alert has been called
 * 
 * @since SmartDeviceLink 1.0
 */
public class AlertResponse extends RPCResponse {

	/**
	 * Constructs a new AlertResponse object
	 */
    public AlertResponse() {
        super("Alert");
    }

	/**
	 * Constructs a new AlertResponse object indicated by the Hashtable
	 * parameter
	 * <p>
	 * 
	 * @param hash
	 *            The Hashtable to use
	 */
    public AlertResponse(Hashtable hash) {
        super(hash);
    }
    public Integer getTryAgainTime() {
        return (Integer) parameters.get(Names.tryAgainTime);
    }
    public void setTryAgainTime(Integer tryAgainTime) {
        if (tryAgainTime != null) {
            parameters.put(Names.tryAgainTime, tryAgainTime);
        } else {
        	parameters.remove(Names.tryAgainTime);
        }
    }
}