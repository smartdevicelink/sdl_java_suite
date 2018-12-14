package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.TouchEventCapabilities;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.TouchEventCapabilties}
 */
public class TouchEventCapabilitiesTest extends TestCase {
	
	private TouchEventCapabilities msg;

	@Override
	public void setUp() {
		msg = new TouchEventCapabilities();
		
		msg.setPressAvailable(Test.GENERAL_BOOLEAN);
		msg.setDoublePressAvailable(Test.GENERAL_BOOLEAN);
		msg.setMultiTouchAvailable(Test.GENERAL_BOOLEAN);
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
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, press);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, multiTouch);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, doublePress);
		
		// Invalid/Null Tests
		TouchEventCapabilities msg = new TouchEventCapabilities();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getPressAvailable());
		assertNull(Test.NULL, msg.getMultiTouchAvailable());
		assertNull(Test.NULL, msg.getDoublePressAvailable());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(TouchEventCapabilities.KEY_PRESS_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(TouchEventCapabilities.KEY_MULTI_TOUCH_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(TouchEventCapabilities.KEY_DOUBLE_PRESS_AVAILABLE, Test.GENERAL_BOOLEAN);

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