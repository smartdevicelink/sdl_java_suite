package com.smartdevicelink.test.proxy;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCNotification;

import junit.framework.TestCase;

public class RPCNotificationTests extends TestCase {

	public void testNotification () {
		RPCNotification testRPC = null;
		
		// Test -- RPCNotification(String functionName)
		testRPC = new RPCNotification("testFunctionName");
		assertNotNull("RPCNotification should not be null", testRPC);
		assertEquals("Test function name should match", testRPC.getFunctionName(), "testFunctionName");
		assertEquals("Message type should be notification", testRPC.getMessageType(), RPCMessage.KEY_NOTIFICATION);
				
		// Test -- RPCNotification(Hashtable<String, Object> hash)
		// NOTE: Hashtable from RPCMessage -> RPCStruct does not allow us to
		// test for invalid or null values here, it can crash in those instances.
		Hashtable<String, Object> testHash = new Hashtable<String, Object>();
		testHash.put("testKey", new Object());
		testHash.put(RPCMessage.KEY_NOTIFICATION, new Object());
		testRPC = new RPCNotification(testHash);
		assertNotNull("RPCNotification should not be null", testRPC);
		assertEquals("Test key should match", testRPC.getStoreValue("testKey"), "testKey");
		assertEquals("Message type should be notification", testRPC.getMessageType(), RPCMessage.KEY_NOTIFICATION);
		
		// Test -- RPCNotification(RPCMessage rpcMsg)
		// NOTE: See issue #165 on GitHub
	}
}
