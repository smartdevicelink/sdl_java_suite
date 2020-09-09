/*
 * Copyright (c) 2019, Livio, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartdevicelink.proxy.rpc.listeners;

import com.smartdevicelink.proxy.RPCResponse;

import java.util.Vector;

/**
 * This is the listener for sending Multiple RPCs.
 */
public abstract class OnMultipleRequestListener extends OnRPCResponseListener {

	final Vector<Integer> correlationIds;
	OnRPCResponseListener rpcResponseListener;

	public OnMultipleRequestListener(){
		setListenerType(UPDATE_LISTENER_TYPE_MULTIPLE_REQUESTS);
		correlationIds = new Vector<>();

		rpcResponseListener = new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				OnMultipleRequestListener.this.onResponse(correlationId, response);
				correlationIds.remove(Integer.valueOf(correlationId));
				onUpdate(correlationIds.size());
				if(correlationIds.size() == 0){
					onFinished();
				}
			}
		};
	}

	public void addCorrelationId(int correlationId){
		correlationIds.add(correlationId);
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
