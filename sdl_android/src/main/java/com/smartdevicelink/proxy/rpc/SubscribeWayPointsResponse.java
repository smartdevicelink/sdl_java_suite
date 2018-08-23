package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;

public class SubscribeWayPointsResponse extends RPCResponse {

    public SubscribeWayPointsResponse() {
        super(FunctionID.SUBSCRIBE_WAY_POINTS.toString());
    }
    public SubscribeWayPointsResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new SubscribeWayPointsResponse object
	 * @param success whether the request is successfully processed
	 * @param resultCode whether the request is successfully processed
	 */
	public SubscribeWayPointsResponse(@NonNull Boolean success, @NonNull Result resultCode) {
		this();
		setSuccess(success);
		setResultCode(resultCode);
	}
}