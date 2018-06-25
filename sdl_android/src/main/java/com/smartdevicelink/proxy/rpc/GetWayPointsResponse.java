package com.smartdevicelink.proxy.rpc;

import android.support.annotation.NonNull;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import java.util.Hashtable;
import java.util.List;

public class GetWayPointsResponse extends RPCResponse {
	public static final String KEY_WAY_POINTS = "wayPoints";

    public GetWayPointsResponse() {
        super(FunctionID.GET_WAY_POINTS.toString());
    }
    public GetWayPointsResponse(Hashtable<String, Object> hash) {
        super(hash);
    }
    /**
     * Constructs a new GetWayPointsResponse object
     * @param success whether the request is successfully processed
     * @param resultCode whether the request is successfully processed
     */
    public GetWayPointsResponse(@NonNull Boolean success, @NonNull Result resultCode) {
        this();
        setSuccess(success);
        setResultCode(resultCode);
    }
    public void setWayPoints(List<LocationDetails> wayPoints) {
		setParameters(KEY_WAY_POINTS, wayPoints);
    }
    @SuppressWarnings("unchecked")
    public List<LocationDetails> getWayPoints() {
		return (List<LocationDetails>) getObject(LocationDetails.class, KEY_WAY_POINTS);
    }
}
