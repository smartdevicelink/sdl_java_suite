package com.smartdevicelink.proxy.rpc.listeners;

import android.util.Log;

import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

import org.json.JSONException;

import java.util.Vector;

/**
 * This is the listener for sending Multiple RPCs.
 */
public abstract class OnMultipleRequestListener extends OnRPCResponseListener {

	final Vector<Integer> correlationIds;
	OnRPCResponseListener rpcResponseListener;
	private static String TAG = "OnMultipleRequestListener";

	public OnMultipleRequestListener(){
		setListenerType(UPDATE_LISTENER_TYPE_MULTIPLE_REQUESTS);
		correlationIds = new Vector<>();

		rpcResponseListener = new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				correlationIds.remove(Integer.valueOf(correlationId));
				if(correlationIds.size()>0){
					onUpdate(correlationIds.size());
				}else{
					onFinished();
				}
			}
		};
	}

	public void addCorrelationId(int correlationid){
		correlationIds.add(correlationid);
	}
	/**
	 * onUpdate is called during multiple stream request
	 * @param remainingRequests of the original request
	 */
	public abstract void onUpdate(int remainingRequests);
	public abstract void onFinished();
	public abstract void onError(int correlationId, Result resultCode, String info);

	public OnRPCResponseListener getSingleRpcResponseListener(){
		return rpcResponseListener;
	}
}