package com.smartdevicelink.proxy.rpc;

import java.util.Hashtable;

import com.smartdevicelink.protocol.enums.FunctionId;
import com.smartdevicelink.proxy.RPCResponse;

/**
 * Dial Number Response is sent, when DialNumber has been called
 * 
 * @since SmartDeviceLink 4.0
 */
public class DialNumberResponse extends RPCResponse {

    public DialNumberResponse() {
        super(FunctionId.DIAL_NUMBER.toString());
    }
    
	public DialNumberResponse(Hashtable<String, Object> hash) {
		super(hash);
	}

}
