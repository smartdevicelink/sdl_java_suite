package com.smartdevicelink.proxy.rpc;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;

import java.util.Hashtable;

public class UnsubscribeWayPointsResponse extends RPCResponse {

    public UnsubscribeWayPointsResponse() {
        super(FunctionID.UNSUBSCRIBE_WAY_POINTS.toString());
    }
    public UnsubscribeWayPointsResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
}