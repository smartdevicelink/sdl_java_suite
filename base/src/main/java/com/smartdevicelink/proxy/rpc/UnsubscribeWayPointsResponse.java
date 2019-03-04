package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;

public class UnsubscribeWayPointsResponse extends RPCResponse {

    public UnsubscribeWayPointsResponse() {
        super(FunctionID.UNSUBSCRIBE_WAY_POINTS.toString());
    }
    public UnsubscribeWayPointsResponse(Hashtable<String, Object> hash) {
        super(hash);
    }

	/**
	 * Constructs a new UnsubscribeWayPointsResponse object
	 * @param success whether the request is successfully processed
	 * @param resultCode whether the request is successfully processed
	 */
	public UnsubscribeWayPointsResponse(@NonNull Boolean success, @NonNull Result resultCode) {
		this();
		setSuccess(success);
		setResultCode(resultCode);
	}
}