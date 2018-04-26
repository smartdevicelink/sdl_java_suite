package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.RGBColor;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.RGBColor}
 */
public class RGBColorTest extends TestCase {
	
	private RGBColor msg;

	@Override
	public void setUp() {
		msg = new RGBColor(Test.GENERAL_INT, Test.GENERAL_INT, Test.GENERAL_INT);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		Integer red = msg.getRed();
		Integer green = msg.getGreen();
		Integer blue = msg.getBlue();

		// Valid Tests
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, red);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, green);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, blue);

		// Invalid/Null Tests
		RGBColor msg = new RGBColor(null, null, null);
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getRed());
		assertNull(Test.NULL, msg.getGreen());
		assertNull(Test.NULL, msg.getBlue());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(RGBColor.KEY_RED, Test.GENERAL_INT);
			reference.put(RGBColor.KEY_GREEN, Test.GENERAL_INT);
			reference.put(RGBColor.KEY_BLUE, Test.GENERAL_INT);

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