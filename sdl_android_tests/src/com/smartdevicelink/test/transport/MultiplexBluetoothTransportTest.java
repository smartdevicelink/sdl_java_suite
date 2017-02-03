package com.smartdevicelink.test.transport;

import junit.framework.TestCase;
import android.os.Handler;
import android.os.Message;

import com.smartdevicelink.transport.MultiplexBluetoothTransport;
import com.smartdevicelink.transport.SdlRouterService;



public class MultiplexBluetoothTransportTest extends TestCase {
	
	private static final Object REQUEST_LOCK = new Object();
	
	MultiplexBluetoothTransport bluetooth;
	boolean didCorrectThing = false, isWaitingForResponse = false;
	
	//Example handler
	Handler stateChangeHandler = new Handler(){
		int stateDesired = MultiplexBluetoothTransport.STATE_LISTEN;
		@Override
		public void handleMessage(Message msg) {
			if(!isWaitingForResponse){
				return;
			}
			switch(msg.what){
				case SdlRouterService.MESSAGE_STATE_CHANGE:
					if(msg.arg1 == stateDesired){
							didCorrectThing = true;
							break;
					}
					default:
						didCorrectThing = false;
			}
			REQUEST_LOCK.notify();
		}
		
	};
	
	public void testStateTransitions() {
		//TODO test for more than the two states
		bluetooth = MultiplexBluetoothTransport.getBluetoothSerialServerInstance();
		assertNull(bluetooth);

		bluetooth = MultiplexBluetoothTransport.getBluetoothSerialServerInstance(stateChangeHandler);
		assertEquals(bluetooth.getState(), MultiplexBluetoothTransport.STATE_NONE);
		
		bluetooth.start();
		assertEquals(bluetooth.getState(), MultiplexBluetoothTransport.STATE_LISTEN);
		
		bluetooth.stop();
		assertEquals(bluetooth.getState(), MultiplexBluetoothTransport.STATE_NONE);

	}
	
	private void notifyResponseReceived(){
		REQUEST_LOCK.notify();
	}
	
	private void waitForResponse(){
		synchronized(REQUEST_LOCK){
			try {
				REQUEST_LOCK.wait();
				assertTrue(didCorrectThing);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}