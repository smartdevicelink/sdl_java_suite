package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.TouchCoord;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.TouchCoord}
 */
public class TouchCoordTest extends TestCase {
	
	private TouchCoord msg;

	@Override
	public void setUp() {
		msg = new TouchCoord();
		
		msg.setX(TestValues.GENERAL_INT);
		msg.setY(TestValues.GENERAL_INT);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		Integer x = msg.getX();
		Integer y = msg.getY();
		
		// Valid Tests
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, x);
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, y);
		
		// Invalid/Null Tests
		TouchCoord msg = new TouchCoord();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getX());
		assertNull(TestValues.NULL, msg.getY());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(TouchCoord.KEY_X, TestValues.GENERAL_INT);
			reference.put(TouchCoord.KEY_Y, TestValues.GENERAL_INT);

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