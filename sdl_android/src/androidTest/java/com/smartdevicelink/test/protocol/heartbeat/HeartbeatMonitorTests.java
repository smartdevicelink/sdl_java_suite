package com.smartdevicelink.test.protocol.heartbeat;

import java.util.Timer;
import java.util.TimerTask;

import com.smartdevicelink.protocol.heartbeat.HeartbeatMonitor;
import com.smartdevicelink.protocol.heartbeat.IHeartbeatMonitorListener;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.protocol.heartbeat.HeartbeatMonitor}
 */
public class HeartbeatMonitorTests extends TestCase {
	
	// TODO : Proper thread testing.
	
	private HeartbeatMonitor monitor = new HeartbeatMonitor();
	private Runnable testRunnable;
	private Timer timer;
	
	public void testValues () {
		assertNotNull(Test.NOT_NULL, monitor);
		
		testRunnable = monitor.getHeartbeatRunnable();
		assertNotNull(Test.NOT_NULL, testRunnable);
		
		int testInterval = 100;
		monitor.setInterval(testInterval);
		assertEquals("Interval did not match expected value", monitor.getInterval(), testInterval);
	}
	
	public void testThread () {
		try {
			Thread testThread = new Thread(testRunnable);
			assertNotNull(Test.NOT_NULL, testThread);
			setTimeout(5000, testThread); // Cannot leave thread hanging
			
			testThread.start();
			testThread.join();
		} catch (Exception e) {
			fail("Thread exception was thrown");
		}	
	}
	
	private void setTimeout(int duration, final Thread thread) {
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				thread.interrupt();
			}
		}, duration);
	}
	
	public void testNull () {
		// Testing a null value for listener instead of implementing and overriding
		// IHeartbeatMonitorListener methods to test the getter/setter methods of HeartbeatMonitor.
		monitor.setListener(null);
		IHeartbeatMonitorListener testListener = monitor.getListener();
		assertNull("Listener returned a value", testListener);
		
		// Interface testing issue >>
		// monitor.heartbeatACKReceived(); // Sets the ack boolean flag
		// assertTrue("ACK value was not true", monitor.getACKReceived());
	}
}