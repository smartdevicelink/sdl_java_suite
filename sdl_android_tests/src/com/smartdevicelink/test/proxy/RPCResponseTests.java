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
		assertSame("Test function name should match", testRPC.getFunctionName(), "testFunctionName");
		assertSame("Message type should be response", testRPC.getMessageType(), RPCMessage.KEY_RESPONSE);
				
		// Test -- RPCResponse(Hashtable<String, Object> hash)
		// NOTE: Hashtable from RPCMessage -> RPCStruct does not allow us to
		// test for invalid or null values here, it can crash in those instances.
		Hashtable<String, Object> testHash = new Hashtable<String, Object>();
		testHash.put("testKey", new Object());
		testHash.put(RPCMessage.KEY_RESPONSE, new Object());
		testRPC = new RPCResponse(testHash);
		assertNotNull("RPCRespons should not be null", testRPC);
		assertSame("Test key should match", testRPC.getStoreValue("testKey"), "testKey");
		assertSame("Message type should be response", testRPC.getMessageType(), RPCMessage.KEY_RESPONSE);
		
		// Test -- RPCResponse(RPCMessage rpcMsg)
		// NOTE: See issue #165 on GitHub
		
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
	
	public void testSuccess () {
		testRPC.setSuccess(true);
		assertTrue("Success flag should be true", testRPC.getSuccess());
		testRPC.setSuccess(false);
		assertFalse("Success flag should be false", testRPC.getSuccess());
	}
	
	public void testResultCode () {
		testRPC.setResultCode(Result.ABORTED);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.ABORTED);
		testRPC.setResultCode(Result.APPLICATION_NOT_REGISTERED);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.APPLICATION_NOT_REGISTERED);
		testRPC.setResultCode(Result.APPLICATION_REGISTERED_ALREADY);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.APPLICATION_REGISTERED_ALREADY);
		testRPC.setResultCode(Result.CANCEL_ROUTE);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.CANCEL_ROUTE);
		testRPC.setResultCode(Result.DISALLOWED);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.DISALLOWED);
		testRPC.setResultCode(Result.DUPLICATE_NAME);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.DUPLICATE_NAME);
		testRPC.setResultCode(Result.EXPIRED_CERT);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.EXPIRED_CERT);
		testRPC.setResultCode(Result.FILE_NOT_FOUND);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.FILE_NOT_FOUND);
		testRPC.setResultCode(Result.GENERIC_ERROR);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.GENERIC_ERROR);
		testRPC.setResultCode(Result.IGNORED);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.IGNORED);
		testRPC.setResultCode(Result.IN_USE);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.IN_USE);
		testRPC.setResultCode(Result.INVALID_CERT);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.INVALID_CERT);
		testRPC.setResultCode(Result.INVALID_DATA);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.INVALID_DATA);
		testRPC.setResultCode(Result.INVALID_ID);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.INVALID_ID);
		testRPC.setResultCode(Result.OUT_OF_MEMORY);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.OUT_OF_MEMORY);
		testRPC.setResultCode(Result.REJECTED);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.REJECTED);
		testRPC.setResultCode(Result.RESUME_FAILED);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.RESUME_FAILED);
		testRPC.setResultCode(Result.RETRY);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.RETRY);
		testRPC.setResultCode(Result.SAVED);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.SAVED);
		testRPC.setResultCode(Result.SUCCESS);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.SUCCESS);
		testRPC.setResultCode(Result.TIMED_OUT);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.TIMED_OUT);
		testRPC.setResultCode(Result.TOO_MANY_APPLICATIONS);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.TOO_MANY_APPLICATIONS);
		testRPC.setResultCode(Result.TOO_MANY_PENDING_REQUESTS);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.TOO_MANY_PENDING_REQUESTS);
		testRPC.setResultCode(Result.TRUNCATED_DATA);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.TRUNCATED_DATA);
		testRPC.setResultCode(Result.UNSUPPORTED_REQUEST);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.UNSUPPORTED_REQUEST);
		testRPC.setResultCode(Result.UNSUPPORTED_RESOURCE);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.UNSUPPORTED_RESOURCE);
		testRPC.setResultCode(Result.UNSUPPORTED_VERSION);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.UNSUPPORTED_VERSION);
		testRPC.setResultCode(Result.USER_DISALLOWED);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.USER_DISALLOWED);
		testRPC.setResultCode(Result.VEHICLE_DATA_NOT_ALLOWED);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.VEHICLE_DATA_NOT_ALLOWED);
		testRPC.setResultCode(Result.VEHICLE_DATA_NOT_AVAILABLE);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.VEHICLE_DATA_NOT_AVAILABLE);
		testRPC.setResultCode(Result.WARNINGS);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.WARNINGS);
		testRPC.setResultCode(Result.WRONG_LANGUAGE);
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.WRONG_LANGUAGE);
		
		// Null value
		testRPC.setResultCode(null); // Should not have changed it from last value
		assertSame("Value assigned did not match value retrieved.", testRPC.getResultCode(), Result.WRONG_LANGUAGE);
	}
	
	public void testInfo () {
		testRPC.setInfo("test");
		assertSame("Value asigned did not match value retrieved", testRPC.getInfo(), "test");
		
		// Test null
		testRPC.setInfo(null); // Should not change value
		assertSame("Value asigned did not match value retrieved", testRPC.getInfo(), "test");		
	}

}
