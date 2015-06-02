package com.smartdevicelink.dispatcher;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

import com.smartdevicelink.util.DebugTool;

public class Dispatcher<T> {
	
	private Boolean                  isDisposed       = false;
	private Thread                   dispatchThread   = null;
	private IDispatchingStrategy<T>  dispatchStrategy = null;
	private PriorityBlockingQueue<T> dispatchQueue    = null;	
	
	/**
	 * Initializes a message queue and a new thread to dispatch the messages
	 * in the queue that will follow the provided dispatch strategy.
	 * 
	 * @param threadName The name for the thread.
	 * @param strategy Stores the procedure for dispatching messages and how
	 * dispatch errors will be handled by the program.
	 */
	public Dispatcher(String threadName, IDispatchingStrategy<T> strategy) {
		
		// A FIFO ordered queue of messages to be dispatched.
		dispatchQueue = new PriorityBlockingQueue<T>(10, new MessageComparator());
		
		// The procedure definition for dispatching messages.
		dispatchStrategy = strategy;
		
		// The thread used to dispatch messages.
		dispatchThread = new Thread(new Runnable() {
			public void run(){
				handleMessages();
			}
		});
		dispatchThread.setName(threadName);
		dispatchThread.setDaemon(true);
		dispatchThread.start();
	}
	
	/**
	 * Interrupts the current thread process and drops the reference to that 
	 * thread, creating a zombie that will eventually be cleaned up by the
	 * garbage collector.
	 */
	public void dispose() {
		isDisposed = true;
		
		if(dispatchThread != null) {
			dispatchThread.interrupt();
			dispatchThread = null;
		}
	}
	
	/**
	 * Removes the next message in the priority queue and dispatches it
	 * according to the dispatch strategy. If the message was not or could not
	 * be dispatched correctly for a reason other than a thread interruption,
	 * the error is also handled according to the dispatch strategy.
	 */
	private void handleMessages() {
		
		try {
			T thisMessage;
		
			while(isDisposed == false) {
				// Will hold until there is a message in the queue.
				thisMessage = dispatchQueue.take();
				dispatchStrategy.dispatch(thisMessage);
			}
		} catch (InterruptedException e) {
			// Thread was interrupted by dispose() method, no action required.
			return;
		} catch (Exception e) {
			DebugTool.logError("Error occurred dispatching message.", e);
			dispatchStrategy.handleDispatchingError("Error occurred dispatching message.", e);
		}
	}

	/**
	 * Adds the given message to the priority queue, or if it cannot be added
	 * it handles the error according to the dispatch strategy.
	 * 
	 * @param message The message to be dispatched.
	 */
	public void queueMessage(T message) {
		try {
			dispatchQueue.put(message);
		} catch(ClassCastException e) { 
			dispatchStrategy.handleQueueingError("ClassCastException encountered when queueing message.", e);
		} catch(Exception e) {
			dispatchStrategy.handleQueueingError("Exception encountered when queueing message.", e);
		}
	}
	
	/**
	 * A private internal comparator class that will always return 0, indicating
	 * the objects being compared are the same, turning the priority queue into 
	 * a FIFO queue.
	 */
	private class MessageComparator implements Comparator<Object> {
		@Override
		public int compare(Object arg0, Object arg1) {
			return 0;
		}
	}
}