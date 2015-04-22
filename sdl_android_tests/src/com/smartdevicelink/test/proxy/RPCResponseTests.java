package com.smartdevicelink.test.proxy;

import java.util.Hashtable;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;

public class RPCResponseTests extends TestCase {
	
	private RPCResponse testRPC = null;
	
	public void testResponse () {
		
		assertNull("Uninitialized RPC should be null", testRPC);
		
		// Test -- RPCResponse(String functionName)
		testRPC = new RPCResponse("testFunctionName");
		assertNotNull("RPCResponse should not be null", testRPC);
		assertEquals("Test function name should match", testRPC.getFunctionName(), "testFunctionName");
		assertEquals("Message type should be response", testRPC.getMessageType(), RPCMessage.KEY_RESPONSE);
				
		// Test -- RPCResponse(Hashtable<String, Object> hash)
		// NOTE: Hashtable from RPCMessage -> RPCStruct does not allow us to
		// test for invalid or null values here, it can crash in those instances.
		Hashtable<String, Object> testHash = new Hashtable<String, Object>();
		testHash.put("testKey", new Object());
		testHash.put(RPCMessage.KEY_RESPONSE, new Object());
		testRPC = new RPCResponse(testHash);
		assertNotNull("RPCRespons should not be null", testRPC);
		assertEquals("Test key should match", testRPC.getStoreValue("testKey"), "testKey");
		assertEquals("Message type should be response", testRPC.getMessageType(), RPCMessage.KEY_RESPONSE);
		
		// Test -- RPCResponse(RPCMessage rpcMsg)
		// NOTE: See issue #165 on GitHub
		
	}
	
	public void testCorrelationID () {
		// Is there a valid range of correlation IDs? If so, add testing for it.
		
		testRPC.setCorrelationID(1234);
		assertEquals("Value for the correlation id does not match expected value",
					testRPC.getCorrelationID(), (Integer) 1234);
		
		// Remove key
		testRPC.setCorrelationID(null);
		assertNull("Correlation id should be null", testRPC.getCorrelationID());
	}
	
	public void testSuccess () {
		testRPC.setSuccess(true);
		assertTrue("Success flag should be true", testRPC.getSuccess());
		testRPC.setSuccess(false);
		assertFalse("Success flag should be false", testRPC.getSuccess());
	}
	
	public void testResultCode () {
		testRPC.setResultCode(Result.ABORTED);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.ABORTED);
		testRPC.setResultCode(Result.APPLICATION_NOT_REGISTERED);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.APPLICATION_NOT_REGISTERED);
		testRPC.setResultCode(Result.APPLICATION_REGISTERED_ALREADY);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.APPLICATION_REGISTERED_ALREADY);
		testRPC.setResultCode(Result.CANCEL_ROUTE);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.CANCEL_ROUTE);
		testRPC.setResultCode(Result.DISALLOWED);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.DISALLOWED);
		testRPC.setResultCode(Result.DUPLICATE_NAME);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.DUPLICATE_NAME);
		testRPC.setResultCode(Result.EXPIRED_CERT);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.EXPIRED_CERT);
		testRPC.setResultCode(Result.FILE_NOT_FOUND);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.FILE_NOT_FOUND);
		testRPC.setResultCode(Result.GENERIC_ERROR);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.GENERIC_ERROR);
		testRPC.setResultCode(Result.IGNORED);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.IGNORED);
		testRPC.setResultCode(Result.IN_USE);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.IN_USE);
		testRPC.setResultCode(Result.INVALID_CERT);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.INVALID_CERT);
		testRPC.setResultCode(Result.INVALID_DATA);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.INVALID_DATA);
		testRPC.setResultCode(Result.INVALID_ID);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.INVALID_ID);
		testRPC.setResultCode(Result.OUT_OF_MEMORY);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.OUT_OF_MEMORY);
		testRPC.setResultCode(Result.REJECTED);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.REJECTED);
		testRPC.setResultCode(Result.RESUME_FAILED);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.RESUME_FAILED);
		testRPC.setResultCode(Result.RETRY);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.RETRY);
		testRPC.setResultCode(Result.SAVED);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.SAVED);
		testRPC.setResultCode(Result.SUCCESS);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.SUCCESS);
		testRPC.setResultCode(Result.TIMED_OUT);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.TIMED_OUT);
		testRPC.setResultCode(Result.TOO_MANY_APPLICATIONS);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.TOO_MANY_APPLICATIONS);
		testRPC.setResultCode(Result.TOO_MANY_PENDING_REQUESTS);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.TOO_MANY_PENDING_REQUESTS);
		testRPC.setResultCode(Result.TRUNCATED_DATA);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.TRUNCATED_DATA);
		testRPC.setResultCode(Result.UNSUPPORTED_REQUEST);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.UNSUPPORTED_REQUEST);
		testRPC.setResultCode(Result.UNSUPPORTED_RESOURCE);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.UNSUPPORTED_RESOURCE);
		testRPC.setResultCode(Result.UNSUPPORTED_VERSION);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.UNSUPPORTED_VERSION);
		testRPC.setResultCode(Result.USER_DISALLOWED);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.USER_DISALLOWED);
		testRPC.setResultCode(Result.VEHICLE_DATA_NOT_ALLOWED);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.VEHICLE_DATA_NOT_ALLOWED);
		testRPC.setResultCode(Result.VEHICLE_DATA_NOT_AVAILABLE);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.VEHICLE_DATA_NOT_AVAILABLE);
		testRPC.setResultCode(Result.WARNINGS);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.WARNINGS);
		testRPC.setResultCode(Result.WRONG_LANGUAGE);
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.WRONG_LANGUAGE);
		
		// Null value
		testRPC.setResultCode(null); // Should not have changed it from last value
		assertEquals("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.WRONG_LANGUAGE);
	}
	
	public void testInfo () {
		testRPC.setInfo("test");
		assertEquals("Value asigned did not match value retrieved", testRPC.getInfo(), "test");
		
		// Test null
		testRPC.setInfo(null); // Should not change value
		assertEquals("Value asigned did not match value retrieved", testRPC.getInfo(), "test");		
	}

}
