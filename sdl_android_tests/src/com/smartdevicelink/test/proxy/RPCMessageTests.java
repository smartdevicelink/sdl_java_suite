package com.smartdevicelink.test.proxy;

import java.util.Hashtable;

import com.smartdevicelink.proxy.RPCMessage;

import junit.framework.TestCase;

public class RPCMessageTests extends TestCase {
	
	private static RPCMessage msg = null;

	public void testRPCMessage () {
		
		assertNull("Null declared RPCMessage returned a value.", msg);
		
		// Test -- RPCMessage(String functionName)
		String testFunctionName = "FunctionName";
		msg = new RPCMessage(testFunctionName);
		assertNotNull("RPCMessage returned null", msg);
		assertEquals("Function name did not match expected value.", msg.getFunctionName(), testFunctionName);
		assertTrue("Message type did not match expected value.", msg.getStoreValue(RPCMessage.KEY_REQUEST));
		
		// Test -- RPCMessage(String functionName, String messageType)
		msg = new RPCMessage(testFunctionName, RPCMessage.KEY_REQUEST);
		assertNotNull("RPCMessage returned null", msg);
		assertEquals("Function name did not match expected value.", msg.getFunctionName(), testFunctionName);
		assertEquals("Message type did not match expected value.", RPCMessage.KEY_REQUEST, msg.getMessageType());
		assertTrue("Message type was not valid, it should be.", msg.getStoreValue(RPCMessage.KEY_REQUEST));
		
		msg = new RPCMessage(testFunctionName, RPCMessage.KEY_RESPONSE);
		assertNotNull("RPCMessage returned null", msg);
		assertEquals("Function name did not match expected value.", msg.getFunctionName(), testFunctionName);
		assertEquals("Message type did not match expected value.", RPCMessage.KEY_RESPONSE, msg.getMessageType());
		assertTrue("Message type was not valid, it should be.", msg.getStoreValue(RPCMessage.KEY_RESPONSE));
		
		msg = new RPCMessage(testFunctionName, RPCMessage.KEY_NOTIFICATION);
		assertNotNull("RPCMessage returned null", msg);
		assertEquals("Function name did not match expected value.", msg.getFunctionName(), testFunctionName);
		assertEquals("Message type did not match expected value.", RPCMessage.KEY_NOTIFICATION, msg.getMessageType());
		assertTrue("Message type was not valid, it should be.", msg.getStoreValue(RPCMessage.KEY_NOTIFICATION));
		
		// NOTE: There is no verification for function type or message type variables,
		// so we cannot check against invalid data, including null values as it will
		// crash in it's attempt to assign a null key in the hashtable.
		// Function name and Message type should both have a check against null or invalid values.
		// See Issue #165
		// Hashtable does not allow null keys or null values - consider changing to HashMap?
			
		// Test -- RPCMessage(Hashtable<String, Object> hash)
		Hashtable<String, Object> testHash = new Hashtable<String, Object>();
		testHash.put(RPCMessage.KEY_REQUEST, testFunctionName);
		testHash.put(RPCMessage.KEY_FUNCTION_NAME, testFunctionName);
		msg = new RPCMessage(testHash);
		assertNotNull("RPCMessage returned null", msg);
		assertEquals("Function name did not match expected value.", msg.getFunctionName(), testFunctionName);
		assertEquals("Message type did not match expected value.", RPCMessage.KEY_REQUEST, msg.getMessageType());
		assertTrue("Message type was not valid, it should be.", msg.getStoreValue(RPCMessage.KEY_REQUEST));
		
	}	
}