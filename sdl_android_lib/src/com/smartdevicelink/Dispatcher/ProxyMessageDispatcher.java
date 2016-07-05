package com.smartdevicelink.Dispatcher;

import java.util.concurrent.LinkedBlockingQueue;

import com.smartdevicelink.util.DebugTool;

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
