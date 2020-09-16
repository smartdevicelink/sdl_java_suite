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
package com.smartdevicelink.managers;

import androidx.annotation.CallSuper;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;

import com.smartdevicelink.transport.utl.TransportRecord;

import java.util.List;

/**
 * <strong>BaseSubManager</strong> <br>
 *
 * Note: This class is extended by SubManagers <br>
 *
 * It is broken down to these areas: <br>
 *
 * 1. <br>
 */
@RestrictTo(RestrictTo.Scope.LIBRARY)
public abstract class BaseSubManager {

	// states - if this gets more complicated we can move elsewhere
	private int state;
	private final Object STATE_LOCK = new Object();
	public static final int SETTING_UP = 0x00, READY = 0x30, LIMITED = 0x50, SHUTDOWN = 0x80, ERROR = 0xC0;
	protected final ISdl internalInterface;
	private CompletionListener completionListener;

	public BaseSubManager(@NonNull ISdl internalInterface){
		this.internalInterface = internalInterface;
		transitionToState(SETTING_UP);
	}

	/**
	 * Starts up a BaseSubManager, and calls provided callback once BaseSubManager is done setting up or failed setup.
	 * @param listener CompletionListener that is called once the BaseSubManager's state is READY, LIMITED, or ERROR
	 */
	@CallSuper
	public void start(CompletionListener listener){
		this.completionListener = listener;
		int state = getState();
		if((state == READY || state == LIMITED || state == ERROR) && completionListener != null){
			completionListener.onComplete(state == READY || state == LIMITED);
			completionListener = null;
		}
	}

	/**
	 * <p>Called when manager is being torn down</p>
	 */
	@CallSuper
	public void dispose(){
		transitionToState(SHUTDOWN);
	}

	protected void transitionToState(int state) {
		synchronized (STATE_LOCK) {
			this.state = state;
		}
		if((state == READY || state == LIMITED || state == ERROR) && completionListener != null ){
			completionListener.onComplete(state == READY || state == LIMITED);
			completionListener = null;
		}
	}

	public int getState() {
		synchronized (STATE_LOCK) {
			return state;
		}
	}

	//This allows the method to not be exposed to developers
	protected void handleTransportUpdated(List<TransportRecord> connectedTransports, boolean audioStreamTransportAvail, boolean videoStreamTransportAvail){
		this.onTransportUpdate(connectedTransports,audioStreamTransportAvail,videoStreamTransportAvail);
	}

	/**
	 * Transport status has been updated
	 * @param connectedTransports currently connected transports
	 * @param audioStreamTransportAvail if there is a transport that could be used to carry the
	 *                                     audio service
	 * @param videoStreamTransportAvail if there is a transport that could be used to carry the
	 *                                     video service
	 */
	protected void onTransportUpdate(List<TransportRecord> connectedTransports, boolean audioStreamTransportAvail, boolean videoStreamTransportAvail){}
}
