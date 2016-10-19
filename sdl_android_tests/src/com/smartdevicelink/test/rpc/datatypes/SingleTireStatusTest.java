package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.SingleTireStatus;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.SingleTireStatus}
 */
public class SingleTireStatusTest extends TestCase {
	
	private SingleTireStatus msg;

	@Override
	public void setUp() {
		msg = new SingleTireStatus();
		
		msg.setStatus(Test.GENERAL_COMPONENTVOLUMESTATUS);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		ComponentVolumeStatus status = msg.getStatus();
		
		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_COMPONENTVOLUMESTATUS, status);
		
		// Invalid/Null Tests
		SingleTireStatus msg = new SingleTireStatus();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getStatus());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(SingleTireStatus.KEY_STATUS, Test.GENERAL_COMPONENTVOLUMESTATUS);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
			}
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}