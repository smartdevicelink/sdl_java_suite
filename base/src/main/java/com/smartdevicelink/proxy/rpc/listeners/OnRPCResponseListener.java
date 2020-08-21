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

import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCResponse;

public abstract class OnRPCResponseListener extends OnRPCListener {


	public OnRPCResponseListener(){
		setListenerType(OnRPCListener.UPDATE_LISTENER_TYPE_BASE_RPC);
	}

	public final void onReceived(final RPCMessage message){
		if (message != null && message instanceof RPCResponse){
			onResponse(((RPCResponse) message).getCorrelationID(), (RPCResponse)message);
		}
	}

	/* *****************************************************************
	 ************************* Event Callbacks *************************
	 *******************************************************************/
	
	/**
	 * This method will be called once the packet has been entered into the queue of messages to send
	 * @param correlationId
	 */
	public void onStart(int correlationId){
		
	};

	/**
	 * This is the only method that must be extended. Other methods that are able to be extended are 
	 * onStart and onError.
	 * @param correlationId
	 * @param response This will be the response message received from the core side. It should be cast into a corresponding RPC Response type. ie, if setting this
	 * for a PutFile request, the message parameter should be cast to a PutFileResponse class.
	 */
	public abstract void onResponse(int correlationId, final RPCResponse response);
}
