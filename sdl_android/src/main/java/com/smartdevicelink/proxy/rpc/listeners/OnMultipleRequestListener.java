package com.smartdevicelink.proxy.rpc.listeners;

import com.smartdevicelink.proxy.RPCResponse;

import java.util.Vector;

/**
 * This is the listener for sending Multiple RPCs.
 */
public abstract class OnMultipleRequestListener extends OnRPCResponseListener {

	Vector<Integer> correlationIds;
	OnRPCResponseListener rpcResponseListener;

	public OnMultipleRequestListener(){
		setListenerType(UPDATE_LISTENER_TYPE_MULTIPLE_REQUESTS);
		correlationIds = new Vector<>();
		rpcResponseListener = new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				correlationIds.remove(correlationId);
				if(correlationIds.size()>0){
					onUpdate(correlationIds.size());
				}else{
					onFinished();
				}
			}
		};
	}
	public void setCorrelationIds(Vector<Integer> correlationIds){
		this.correlationIds = correlationIds;
	}

	public void addCorrelationId(int correlationid){
		if(correlationIds == null){
			correlationIds = new Vector<>();
		}
		correlationIds.add(correlationid);
	}
	/**
	 * onUpdate is called during multiple stream request
	 * @param remainingRequests of the original request
	 */
	public abstract void onUpdate(int remainingRequests);
	public abstract void onFinished();

	public OnRPCResponseListener getSingleRpcResponseListener(){
		return rpcResponseListener;
	}
}