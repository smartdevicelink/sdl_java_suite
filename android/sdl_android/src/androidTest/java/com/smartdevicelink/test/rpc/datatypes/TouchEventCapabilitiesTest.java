package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.TouchEventCapabilities;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.TouchEventCapabilties}
 */
public class TouchEventCapabilitiesTest extends TestCase {
	
	private TouchEventCapabilities msg;

	@Override
	public void setUp() {
		msg = new TouchEventCapabilities();
		
		msg.setPressAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setDoublePressAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setMultiTouchAvailable(TestValues.GENERAL_BOOLEAN);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		Boolean press = msg.getPressAvailable();
		Boolean multiTouch = msg.getMultiTouchAvailable();
		Boolean doublePress = msg.getDoublePressAvailable();
		
		// Valid Tests
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, press);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, multiTouch);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, doublePress);
		
		// Invalid/Null Tests
		TouchEventCapabilities msg = new TouchEventCapabilities();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getPressAvailable());
		assertNull(TestValues.NULL, msg.getMultiTouchAvailable());
		assertNull(TestValues.NULL, msg.getDoublePressAvailable());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(TouchEventCapabilities.KEY_PRESS_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(TouchEventCapabilities.KEY_MULTI_TOUCH_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(TouchEventCapabilities.KEY_DOUBLE_PRESS_AVAILABLE, TestValues.GENERAL_BOOLEAN);

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