package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.TouchCoord;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.TouchCoord}
 */
public class TouchCoordTest extends TestCase {
	
	private TouchCoord msg;

	@Override
	public void setUp() {
		msg = new TouchCoord();
		
		msg.setX(Test.GENERAL_INT);
		msg.setY(Test.GENERAL_INT);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		Integer x = msg.getX();
		Integer y = msg.getY();
		
		// Valid Tests
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, x);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, y);
		
		// Invalid/Null Tests
		TouchCoord msg = new TouchCoord();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getX());
		assertNull(Test.NULL, msg.getY());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(TouchCoord.KEY_X, Test.GENERAL_INT);
			reference.put(TouchCoord.KEY_Y, Test.GENERAL_INT);

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