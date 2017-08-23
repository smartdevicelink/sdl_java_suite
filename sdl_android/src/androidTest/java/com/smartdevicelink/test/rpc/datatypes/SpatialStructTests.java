package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.SpatialStruct;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.SpatialStruct}
 */
public class SpatialStructTests extends TestCase {

	private SpatialStruct msg;

	@Override
	public void setUp() {
		msg = new SpatialStruct();

		msg.setID(Test.GENERAL_INTEGER);
		msg.setX(Test.GENERAL_FLOAT);
		msg.setY(Test.GENERAL_FLOAT);
		msg.setWidth(Test.GENERAL_FLOAT);
		msg.setHeight(Test.GENERAL_FLOAT);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		Integer id = msg.getID();
		Float x = msg.getX();
		Float y = msg.getY();
		Float width = msg.getWidth();
		Float height = msg.getHeight();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_INTEGER, id);
		assertEquals(Test.MATCH, Test.GENERAL_FLOAT, x);
		assertEquals(Test.MATCH, Test.GENERAL_FLOAT, y);
		assertEquals(Test.MATCH, Test.GENERAL_FLOAT, width);
		assertEquals(Test.MATCH, Test.GENERAL_FLOAT, height);

		// Invalid/Null Tests
		SpatialStruct msg = new SpatialStruct();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getID());
		assertNull(Test.NULL, msg.getX());
		assertNull(Test.NULL, msg.getY());
		assertNull(Test.NULL, msg.getWidth());
		assertNull(Test.NULL, msg.getHeight());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(SpatialStruct.KEY_ID, Test.GENERAL_INTEGER);
			reference.put(SpatialStruct.KEY_X, ((Float) Test.GENERAL_FLOAT));
			reference.put(SpatialStruct.KEY_Y, ((Float) Test.GENERAL_FLOAT));
			reference.put(SpatialStruct.KEY_WIDTH, ((Float) Test.GENERAL_FLOAT));
			reference.put(SpatialStruct.KEY_HEIGHT, ((Float) Test.GENERAL_FLOAT));

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
