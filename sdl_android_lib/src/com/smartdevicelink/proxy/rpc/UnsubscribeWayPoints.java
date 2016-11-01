package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

public class UnsubscribeWayPoints extends RPCRequest {
	public UnsubscribeWayPoints() {
        super(FunctionID.UNSUBSCRIBE_WAY_POINTS.toString());
    }
	public UnsubscribeWayPoints(Hashtable<String, Object> hash) {
        super(hash);
    }
}
