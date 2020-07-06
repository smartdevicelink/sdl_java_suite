package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.StartTime;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.StartTime}
 */
public class StartTimeTest extends TestCase {
	
	private StartTime msg;

	@Override
	public void setUp() {
		msg = new StartTime();
		
		msg.setHours(TestValues.GENERAL_INT);
		msg.setMinutes(TestValues.GENERAL_INT);
		msg.setSeconds(TestValues.GENERAL_INT);
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
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, hours);
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, minutes);
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, seconds);

		// TimeInterval constructor test
		StartTime startTime = new StartTime(7000);
		assertEquals(TestValues.MATCH, (Integer) 1, startTime.getHours());
		assertEquals(TestValues.MATCH, (Integer) 56, startTime.getMinutes());
		assertEquals(TestValues.MATCH, (Integer) 40, startTime.getSeconds());

		// Invalid/Null Tests
		StartTime msg = new StartTime();
		assertNotNull(TestValues.NOT_NULL, msg);
		assertNull(TestValues.NULL, msg.getHours());
		assertNull(TestValues.NULL, msg.getMinutes());
		assertNull(TestValues.NULL, msg.getSeconds());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(StartTime.KEY_HOURS, TestValues.GENERAL_INT);
			reference.put(StartTime.KEY_MINUTES, TestValues.GENERAL_INT);
			reference.put(StartTime.KEY_SECONDS, TestValues.GENERAL_INT);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
			}
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
	}
}