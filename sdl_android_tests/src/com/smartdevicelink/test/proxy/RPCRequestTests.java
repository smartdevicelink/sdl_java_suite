package com.smartdevicelink.test.proxy;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCRequest;

import junit.framework.TestCase;

public class RPCRequestTests extends TestCase {
	
	private RPCRequest testRPC = null;

	public void testRequest () {		
		
		// Test -- RPCNotification(String functionName)
		testRPC = new RPCRequest("testFunctionName");
		assertNotNull("RPCRequest should not be null", testRPC);
		assertSame("Test function name should match", testRPC.getFunctionName(), "testFunctionName");
		assertSame("Message type should be request", testRPC.getMessageType(), RPCMessage.KEY_REQUEST);
		
		// Test -- RPCRequest(Hashtable<String, Object> hash)
		// NOTE: Hashtable from RPCMessage -> RPCStruct does not allow us to
		// test for invalid or null values here, it can crash in those instances.
		// See issue #165 on GitHub
		Hashtable<String, Object> testHash = new Hashtable<String, Object>();
		testHash.put("testKey", new Object());
		testHash.put(RPCMessage.KEY_REQUEST, new Object());
		testRPC = new RPCRequest(testHash);
		assertNotNull("RPCRequest should not be null", testRPC);
		assertSame("Test key should match", testRPC.getStoreValue("testKey"), "testKey");
		assertSame("Message type should be request", testRPC.getMessageType(), RPCMessage.KEY_REQUEST);
	}
	
	public void testCorrelationID () {
		// Is there a valid range of correlation IDs? If so, add testing for it.
		
		testRPC.setCorrelationID(1234);
		assertSame("Value for the correlation id does not match expected value",
					testRPC.getCorrelationID(), 1234);
		
		// Remove key
		testRPC.setCorrelationID(null);
		assertNull("Correlation id should be null", testRPC.getCorrelationID());
	}
}