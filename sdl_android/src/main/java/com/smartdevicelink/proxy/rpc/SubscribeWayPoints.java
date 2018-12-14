package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCRequest;

import java.util.Hashtable;

public class SubscribeWayPoints extends RPCRequest {
	public SubscribeWayPoints() {
        super(FunctionID.SUBSCRIBE_WAY_POINTS.toString());
    }
	public SubscribeWayPoints(Hashtable<String, Object> hash) {
        super(hash);
    }
}
