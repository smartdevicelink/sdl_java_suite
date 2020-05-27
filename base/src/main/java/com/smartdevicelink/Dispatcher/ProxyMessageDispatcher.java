/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
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
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
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
package com.smartdevicelink.Dispatcher;

import com.smartdevicelink.util.DebugTool;

import java.util.concurrent.LinkedBlockingQueue;

@Deprecated
public class ProxyMessageDispatcher<T> {
	LinkedBlockingQueue<T> _queue = null;
	private Thread _messageDispatchingThread = null;
	IDispatchingStrategy<T> _strategy = null;

	// Boolean to track if disposed
	private Boolean dispatcherDisposed = false;
	
	public ProxyMessageDispatcher(String THREAD_NAME, IDispatchingStrategy<T> strategy) {
		_queue = new LinkedBlockingQueue<T>();
		
		_strategy = strategy;
		
		// Create dispatching thread
		_messageDispatchingThread = new Thread(new Runnable() {public void run(){handleMessages();}});
		_messageDispatchingThread.setName(THREAD_NAME);
		_messageDispatchingThread.setDaemon(true);
		_messageDispatchingThread.start();
	}
	
	public void dispose() {
		dispatcherDisposed = true;
		
		if(_messageDispatchingThread != null) {
			_messageDispatchingThread.interrupt();
			_messageDispatchingThread = null;
		}
	}
		
	private void handleMessages() {
		
		try {
			T thisMessage;
		
			while(dispatcherDisposed == false) {
				thisMessage = _queue.take();
				_strategy.dispatch(thisMessage);
			}
		} catch (InterruptedException e) {
			// Thread was interrupted by dispose() method, no action required
			return;
		} catch (Exception e) {
			DebugTool.logError("Error occurred dispating message.", e);
			_strategy.handleDispatchingError("Error occurred dispating message.", e);
		}
	}
		
	public void queueMessage(T message) {
		try {
			_queue.put(message);
		} catch(ClassCastException e) { 
			_strategy.handleQueueingError("ClassCastException encountered when queueing message.", e);
		} catch(Exception e) {
			_strategy.handleQueueingError("Exception encountered when queueing message.", e);
		}
	}
}
