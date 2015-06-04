package com.smartdevicelink.dispatch;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

import com.smartdevicelink.dispatch.interfaces.IDispatchingStrategy;
import com.smartdevicelink.util.DebugTool;

public class ProxyMessageDispatcher<T> {
	PriorityBlockingQueue<T> dispatchQueue = null;
	private Thread dispatchThread = null;
	IDispatchingStrategy<T> dispatchStrategy = null;

	// Boolean to track if disposed
	private Boolean dispatcherDisposed = false;
	
	public ProxyMessageDispatcher(String threadName, Comparator<T> messageComparator, 
			IDispatchingStrategy<T> strategy) {
		dispatchQueue = new PriorityBlockingQueue<T>(10, messageComparator);
		
		dispatchStrategy = strategy;
		
		// Create dispatching thread
		dispatchThread = new Thread(new Runnable() {public void run(){handleMessages();}});
		dispatchThread.setName(threadName);
		dispatchThread.setDaemon(true);
		dispatchThread.start();
	}
	
	public void dispose() {
		dispatcherDisposed = true;
		
		if(dispatchThread != null) {
			dispatchThread.interrupt();
			dispatchThread = null;
		}
	}
		
	private void handleMessages() {
		
		try {
			T thisMessage;
		
			while(dispatcherDisposed == false) {
				thisMessage = dispatchQueue.take();
				dispatchStrategy.dispatch(thisMessage);
			}
		} catch (InterruptedException e) {
			// Thread was interrupted by dispose() method, no action required
			return;
		} catch (Exception e) {
			DebugTool.logError("Error occurred dispating message.", e);
			dispatchStrategy.handleDispatchingError("Error occurred dispating message.", e);
		}
	}
		
	public void queueMessage(T message) {
		try {
			dispatchQueue.put(message);
		} catch(ClassCastException e) { 
			dispatchStrategy.handleQueueingError("ClassCastException encountered when queueing message.", e);
		} catch(Exception e) {
			dispatchStrategy.handleQueueingError("Exception encountered when queueing message.", e);
		}
	}
}
