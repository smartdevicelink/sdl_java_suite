package com.smartdevicelink.test.protocol.heartbeat;

import java.util.Timer;
import java.util.TimerTask;

import com.smartdevicelink.protocol.heartbeat.HeartbeatMonitor;
import com.smartdevicelink.protocol.heartbeat.IHeartbeatMonitorListener;

import junit.framework.TestCase;

public class HeartbeatMonitorTests extends TestCase {
	
	private HeartbeatMonitor monitor = new HeartbeatMonitor();
	private Runnable testRunnable;
	private Timer timer;
	
	public void testValues () {
		assertNotNull("HeartbeatMonitor returned null", monitor);
		
		testRunnable = monitor.getHeartbeatRunnable();
		assertNotNull("HeartbeatTimeoutRunnable returned null", testRunnable);
		
		int testInterval = 100;
		monitor.setInterval(testInterval);
		assertEquals("Interval did not match expected value", monitor.getInterval(), testInterval);
	}
	
	public void testThread () {
		try {
			Thread testThread = new Thread(testRunnable);
			assertNotNull("Thread was null", testThread);
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
	
	// TODO: Test notifyTransportActivity() method
	// TODO: Test heartbeatTimeoutRunnable execution code
	// TODO: Test start/stop thread methods
	
}