package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.Rectangle;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link Rectangle}
 */
public class RectangleTests extends TestCase {

	private Rectangle msg;

	@Override
	public void setUp() {
		msg = new Rectangle();

		msg.setX(TestValues.GENERAL_FLOAT);
		msg.setY(TestValues.GENERAL_FLOAT);
		msg.setWidth(TestValues.GENERAL_FLOAT);
		msg.setHeight(TestValues.GENERAL_FLOAT);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		Float x = msg.getX();
		Float y = msg.getY();
		Float width = msg.getWidth();
		Float height = msg.getHeight();

		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_FLOAT, x);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_FLOAT, y);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_FLOAT, width);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_FLOAT, height);

		// Invalid/Null Tests
		Rectangle msg = new Rectangle();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getX());
		assertNull(TestValues.NULL, msg.getY());
		assertNull(TestValues.NULL, msg.getWidth());
		assertNull(TestValues.NULL, msg.getHeight());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(Rectangle.KEY_X, (TestValues.GENERAL_FLOAT));
			reference.put(Rectangle.KEY_Y, (TestValues.GENERAL_FLOAT));
			reference.put(Rectangle.KEY_WIDTH, (TestValues.GENERAL_FLOAT));
			reference.put(Rectangle.KEY_HEIGHT, (TestValues.GENERAL_FLOAT));

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
