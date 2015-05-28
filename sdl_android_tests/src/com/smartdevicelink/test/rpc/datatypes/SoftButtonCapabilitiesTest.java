package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.SoftButtonCapabilities}
 */
public class SoftButtonCapabilitiesTest extends TestCase {

	private SoftButtonCapabilities msg;

	@Override
	public void setUp() {
		msg = new SoftButtonCapabilities();
		
		msg.setImageSupported(Test.GENERAL_BOOLEAN);
		msg.setShortPressAvailable(Test.GENERAL_BOOLEAN);
		msg.setLongPressAvailable(Test.GENERAL_BOOLEAN);
		msg.setUpDownAvailable(Test.GENERAL_BOOLEAN);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		Boolean imageSupp = msg.getImageSupported();
		Boolean updown = msg.getUpDownAvailable();
		Boolean longPress = msg.getLongPressAvailable();
		Boolean shortPress = msg.getShortPressAvailable();
		
		// Valid Tests
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, imageSupp);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, updown);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, longPress);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, shortPress);
		
		// Invalid/Null Tests
		SoftButtonCapabilities msg = new SoftButtonCapabilities();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getImageSupported());
		assertNull(Test.NULL, msg.getLongPressAvailable());
		assertNull(Test.NULL, msg.getShortPressAvailable());
		assertNull(Test.NULL, msg.getUpDownAvailable());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(SoftButtonCapabilities.KEY_IMAGE_SUPPORTED, Test.GENERAL_BOOLEAN);
			reference.put(SoftButtonCapabilities.KEY_UP_DOWN_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(SoftButtonCapabilities.KEY_LONG_PRESS_AVAILABLE, Test.GENERAL_BOOLEAN);
			reference.put(SoftButtonCapabilities.KEY_SHORT_PRESS_AVAILABLE, Test.GENERAL_BOOLEAN);

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