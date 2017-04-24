package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

import java.util.Hashtable;

public class SubscribeWayPointsResponse extends RPCResponse {

    public SubscribeWayPointsResponse() {
        super(FunctionID.SUBSCRIBE_WAY_POINTS.toString());
    }
    public SubscribeWayPointsResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}