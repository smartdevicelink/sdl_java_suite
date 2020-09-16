package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.SoftButtonCapabilities}
 */
public class SoftButtonCapabilitiesTest extends TestCase {

	private SoftButtonCapabilities msg;

	@Override
	public void setUp() {
		msg = new SoftButtonCapabilities();
		
		msg.setImageSupported(TestValues.GENERAL_BOOLEAN);
		msg.setShortPressAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setLongPressAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setUpDownAvailable(TestValues.GENERAL_BOOLEAN);
		msg.setTextSupported(TestValues.GENERAL_BOOLEAN);
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
		Boolean textSupported = msg.getTextSupported();
		
		// Valid Tests
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, imageSupp);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, updown);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, longPress);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, shortPress);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, textSupported);
		
		// Invalid/Null Tests
		SoftButtonCapabilities msg = new SoftButtonCapabilities();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getImageSupported());
		assertNull(TestValues.NULL, msg.getLongPressAvailable());
		assertNull(TestValues.NULL, msg.getShortPressAvailable());
		assertNull(TestValues.NULL, msg.getUpDownAvailable());
		assertNull(TestValues.NULL, msg.getTextSupported());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(SoftButtonCapabilities.KEY_IMAGE_SUPPORTED, TestValues.GENERAL_BOOLEAN);
			reference.put(SoftButtonCapabilities.KEY_UP_DOWN_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(SoftButtonCapabilities.KEY_LONG_PRESS_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(SoftButtonCapabilities.KEY_SHORT_PRESS_AVAILABLE, TestValues.GENERAL_BOOLEAN);
			reference.put(SoftButtonCapabilities.KEY_TEXT_SUPPORTED, TestValues.GENERAL_BOOLEAN);

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