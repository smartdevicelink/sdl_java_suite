package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.StartTime;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.StartTime}
 */
public class StartTimeTest extends TestCase {
	
	private StartTime msg;

	@Override
	public void setUp() {
		msg = new StartTime();
		
		msg.setHours(Test.GENERAL_INT);
		msg.setMinutes(Test.GENERAL_INT);
		msg.setSeconds(Test.GENERAL_INT);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		Integer hours = msg.getHours();
		Integer minutes = msg.getMinutes();
		Integer seconds = msg.getSeconds();		
		
		// Valid Tests
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, hours);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, minutes);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, seconds);
		
		// Invalid/Null Tests
		StartTime msg = new StartTime();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getHours());
		assertNull(Test.NULL, msg.getMinutes());
		assertNull(Test.NULL, msg.getSeconds());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(StartTime.KEY_HOURS, Test.GENERAL_INT);
			reference.put(StartTime.KEY_MINUTES, Test.GENERAL_INT);
			reference.put(StartTime.KEY_SECONDS, Test.GENERAL_INT);

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