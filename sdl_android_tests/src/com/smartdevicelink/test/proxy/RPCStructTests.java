package com.smartdevicelink.test.proxy;

import java.util.Hashtable;

import org.json.JSONException;

import com.smartdevicelink.proxy.RPCStruct;

import junit.framework.TestCase;

public class RPCStructTests extends TestCase {
	
	private RPCStruct testRPCStruct = null;
	
	public void testStruct () {
		assertNull("Expected null, but received object", testRPCStruct);
		
		// Test -- RPCStruct()
		testRPCStruct = new RPCStruct();
		assertNotNull("RPCStruct should not be null, but was", testRPCStruct);
		assertNotNull("RPCStruct hashtable 'store' should not be null, but was", testRPCStruct.getStore());
		
		// Test -- RPCStruct(Hashtable<String, Object> hashtable)
		Hashtable<String, Object> testHash = new Hashtable<String, Object>();
		testHash.put("test", new Object());
		testRPCStruct = new RPCStruct(testHash);
		assertNotNull("RPCStruct should not be null, but was", testRPCStruct);
		assertNotNull("RPCStruct hashtable 'store' should not be null, but was", testRPCStruct.getStore());
		assertTrue("Test value could not be retrieved from Hashtable", testRPCStruct.getStoreValue("test"));
		assertNotNull("Test object was null", testRPCStruct.getStore().get("test"));
	}
	
	public void testJSONStuff () {
		try {
			assertNotNull("Json object should not be null, but is", testRPCStruct.serializeJSON());
		} catch (JSONException e) {
			fail("Json Exception was thrown");
		}
		// TODO: test other json methods
	}
	
	public void testBulkData () {
		assertNull("Bulk data should be null, but wasn't", testRPCStruct.getBulkData());
		
		byte[] testByteArray = {(byte) 0x00, (byte) 0x01};
		testRPCStruct.setBulkData(testByteArray);
		assertNotNull("Bulk data should not be null, but is", testRPCStruct.getBulkData());
	}

}