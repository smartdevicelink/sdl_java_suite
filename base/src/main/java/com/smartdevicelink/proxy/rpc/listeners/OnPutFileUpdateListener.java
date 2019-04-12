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

public abstract class OnPutFileUpdateListener extends OnRPCResponseListener{

	long totalSize = 0;

	public OnPutFileUpdateListener(){
		setListenerType(UPDATE_LISTENER_TYPE_PUT_FILE);
	}
	
	@Override
	public final void onStart(int correlationId) {
		super.onStart(correlationId);
		onStart(correlationId, totalSize); //We do this so we can send back the total size
	}

	public void onStart(int correlationId, long totalSize){
		
	}
	
	@Override
	public final void onResponse(int correlationId, RPCResponse response) {
		onResponse(correlationId, response, totalSize); //Calling our special abstract method
	}
	/**
	 * Called when the putfile request is responded to.
	 * @param correlationId correlation ID tied to the original response
	 * @param response the PutFile response RPC
	 * @param totalSize total size of the file sent
	 */
	public abstract void onResponse(int correlationId, RPCResponse response, long totalSize);
	
	/**
	 * onUpdate is called during a putfile stream request
	 * @param correlationId of the original request
	 * @param bytesWritten the amount of bytes that have been written so far
	 * @param totalSize total size of the file that is being sent
	 */
	public void onUpdate(int correlationId, long bytesWritten, long totalSize){
		
	};
	
	public final void setTotalSize(long totalSize){
		this.totalSize = totalSize;
	}
}
