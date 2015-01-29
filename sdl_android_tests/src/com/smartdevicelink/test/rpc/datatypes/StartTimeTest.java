package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.StartTime;
import com.smartdevicelink.test.utils.JsonUtils;

public class StartTimeTest extends TestCase {

	private static final Integer HOURS = 0;
	private static final Integer MINUTES = 0;
	private static final Integer SECONDS = 0;
	
	private StartTime msg;

	@Override
	public void setUp() {
		msg = new StartTime();
		
		msg.setHours(HOURS);
		msg.setMinutes(MINUTES);
		msg.setSeconds(SECONDS);
	}

	public void testHours() {
		Integer copy = msg.getHours();
		
		assertEquals("Input value didn't match expected value.", HOURS, copy);
	}
	
	public void testMinutes () {
		Integer copy = msg.getMinutes();
		
		assertEquals("Input value didn't match expected value.", MINUTES, copy);
	}
	
	public void testSeconds () {
		Integer copy = msg.getSeconds();
		
		assertEquals("Input value didn't match expected value.", SECONDS, copy);
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(StartTime.KEY_HOURS, HOURS);
			reference.put(StartTime.KEY_MINUTES, MINUTES);
			reference.put(StartTime.KEY_SECONDS, SECONDS);

			JSONObject underTest = msg.serializeJSON();

			assertEquals("JSON size didn't match expected size.",
					reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				assertEquals(
						"JSON value didn't match expected value for key \""
								+ key + "\".",
						JsonUtils.readObjectFromJsonObject(reference, key),
						JsonUtils.readObjectFromJsonObject(underTest, key));
			}
		} catch (JSONException e) {
			/* do nothing */
		}
	}

	public void testNull() {
		StartTime msg = new StartTime();
		assertNotNull("Null object creation failed.", msg);

		assertNull("Hours wasn't set, but getter method returned an object.", msg.getHours());
		assertNull("Minutes wasn't set, but getter method returned an object.", msg.getMinutes());
		assertNull("Seconds wasn't set, but getter method returned an object.", msg.getSeconds());
	}
}
