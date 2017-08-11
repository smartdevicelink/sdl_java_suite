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
		msg.setX(Test.GENERAL_INTEGER);
		msg.setY(Test.GENERAL_INTEGER);
		msg.setWidth(Test.GENERAL_INTEGER);
		msg.setHeight(Test.GENERAL_INTEGER);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		Integer id = msg.getID();
		Integer x = msg.getX();
		Integer y = msg.getY();
		Integer width = msg.getWidth();
		Integer height = msg.getHeight();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_INTEGER, id);
		assertEquals(Test.MATCH, Test.GENERAL_INTEGER, x);
		assertEquals(Test.MATCH, Test.GENERAL_INTEGER, y);
		assertEquals(Test.MATCH, Test.GENERAL_INTEGER, width);
		assertEquals(Test.MATCH, Test.GENERAL_INTEGER, height);

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
			reference.put(SpatialStruct.KEY_X, (Test.GENERAL_INTEGER));
			reference.put(SpatialStruct.KEY_Y, (Test.GENERAL_INTEGER));
			reference.put(SpatialStruct.KEY_WIDTH, (Test.GENERAL_INTEGER));
			reference.put(SpatialStruct.KEY_HEIGHT, (Test.GENERAL_INTEGER));

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
