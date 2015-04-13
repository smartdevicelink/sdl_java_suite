package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.test.utils.JsonUtils;

public class SoftButtonCapabilitiesTest extends TestCase {
	
	private static final Boolean SHORT_PRESS_AVAILABLE =  false;
	private static final Boolean LONG_PRESS_AVAILABLE = false;
	private static final Boolean UP_DOWN_AVAILABLE = false;
	private static final Boolean IMAGE_SUPPORTED = false;

	private SoftButtonCapabilities msg;

	@Override
	public void setUp() {
		msg = new SoftButtonCapabilities();
		
		msg.setImageSupported(IMAGE_SUPPORTED);
		msg.setShortPressAvailable(SHORT_PRESS_AVAILABLE);
		msg.setLongPressAvailable(LONG_PRESS_AVAILABLE);
		msg.setUpDownAvailable(UP_DOWN_AVAILABLE);
	}

	public void testImageSupported () {
		Boolean copy = msg.getImageSupported();
		
		assertEquals("Input value didn't match expected value.", IMAGE_SUPPORTED, copy);
	}
	
	public void testUpDownAvailable () {
		Boolean copy = msg.getUpDownAvailable();
		
		assertEquals("Input value didn't match expected value.", UP_DOWN_AVAILABLE, copy);
	}
	
	public void testLongPressAvailable () {
		Boolean copy = msg.getLongPressAvailable();
		
		assertEquals("Input value didn't match expected value.", LONG_PRESS_AVAILABLE, copy);
	}
	
	public void testShortPressAvailable () {
		Boolean copy = msg.getShortPressAvailable();
		
		assertEquals("Input value didn't match expected value.", SHORT_PRESS_AVAILABLE, copy);
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(SoftButtonCapabilities.KEY_IMAGE_SUPPORTED, IMAGE_SUPPORTED);
			reference.put(SoftButtonCapabilities.KEY_UP_DOWN_AVAILABLE, UP_DOWN_AVAILABLE);
			reference.put(SoftButtonCapabilities.KEY_LONG_PRESS_AVAILABLE, LONG_PRESS_AVAILABLE);
			reference.put(SoftButtonCapabilities.KEY_SHORT_PRESS_AVAILABLE, SHORT_PRESS_AVAILABLE);

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
		SoftButtonCapabilities msg = new SoftButtonCapabilities();
		assertNotNull("Null object creation failed.", msg);

		assertNull("Image supported wasn't set, but getter method returned an object.", msg.getImageSupported());
		assertNull("Long press available wasn't set, but getter method returned an object.", msg.getLongPressAvailable());
		assertNull("Short press available wasn't set, but getter method returned an object.", msg.getShortPressAvailable());
		assertNull("Up down available wasn't set, but getter method returned an object.", msg.getUpDownAvailable());
	}
}
