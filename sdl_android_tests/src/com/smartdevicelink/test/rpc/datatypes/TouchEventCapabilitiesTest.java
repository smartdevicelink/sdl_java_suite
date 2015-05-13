package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.TouchEventCapabilities;
import com.smartdevicelink.test.JsonUtils;

public class TouchEventCapabilitiesTest extends TestCase {

	private static final Boolean PRESS_AVAILABLE = false;
	private static final Boolean MULTI_TOUCH_AVAILABLE = false;
	private static final Boolean DOUBLE_PRESS_AVAILABLE = false;
	
	private TouchEventCapabilities msg;

	@Override
	public void setUp() {
		msg = new TouchEventCapabilities();
		
		msg.setPressAvailable(PRESS_AVAILABLE);
		msg.setDoublePressAvailable(DOUBLE_PRESS_AVAILABLE);
		msg.setMultiTouchAvailable(MULTI_TOUCH_AVAILABLE);
	}

	public void testPressAvailable () {
		Boolean copy = msg.getPressAvailable();
		
		assertEquals("Input value didn't match expected value.", PRESS_AVAILABLE, copy);
	}
	
	public void testMultiTouchAvailable () {
		Boolean copy = msg.getMultiTouchAvailable();
		
		assertEquals("Input value didn't match expected value.", MULTI_TOUCH_AVAILABLE, copy);
	}
	
	public void testDoublePressAvialable () {
		Boolean copy = msg.getDoublePressAvailable();
		
		assertEquals("Input value didn't match expected value.", DOUBLE_PRESS_AVAILABLE, copy);
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(TouchEventCapabilities.KEY_PRESS_AVAILABLE, PRESS_AVAILABLE);
			reference.put(TouchEventCapabilities.KEY_MULTI_TOUCH_AVAILABLE, MULTI_TOUCH_AVAILABLE);
			reference.put(TouchEventCapabilities.KEY_DOUBLE_PRESS_AVAILABLE, DOUBLE_PRESS_AVAILABLE);

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
		TouchEventCapabilities msg = new TouchEventCapabilities();
		assertNotNull("Null object creation failed.", msg);

		assertNull("Press available wasn't set, but getter method returned an object.", msg.getPressAvailable());
		assertNull("Multi touch available wasn't set, but getter method returned an object.", msg.getMultiTouchAvailable());
		assertNull("Double press available wasn't set, but getter method returned an object.", msg.getDoublePressAvailable());
	}
}
