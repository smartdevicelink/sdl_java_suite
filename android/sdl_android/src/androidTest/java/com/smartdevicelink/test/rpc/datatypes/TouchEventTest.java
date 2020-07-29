package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.TouchCoord;
import com.smartdevicelink.proxy.rpc.TouchEvent;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.TouchEvent}
 */
public class TouchEventTest extends TestCase {
	
	private TouchEvent msg;

	@Override
	public void setUp() {		
		msg = new TouchEvent();
		msg.setId(TestValues.GENERAL_INT);
		msg.setTs(TestValues.GENERAL_LONG_LIST);
		msg.setC(TestValues.GENERAL_TOUCHCOORD_LIST);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		Integer id = msg.getId();
		List<Long> timestamp = msg.getTs();
		List<TouchCoord> coordTest = msg.getC();
		
		// Valid Tests
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, id);
    	assertTrue(TestValues.TRUE, Validator.validateLongList(TestValues.GENERAL_LONG_LIST, timestamp));
		assertEquals(TestValues.MATCH, TestValues.GENERAL_TOUCHCOORD_LIST.size(), coordTest.size());
		
		for (int index = 0; index < TestValues.GENERAL_TOUCHCOORD_LIST.size(); index++) {
			assertTrue(TestValues.TRUE, Validator.validateTouchCoord(TestValues.GENERAL_TOUCHCOORD_LIST.get(index), coordTest.get(index)));
		}
		
		// Invalid/Null Tests
		TouchEvent msg = new TouchEvent();
		assertNotNull(TestValues.NOT_NULL, msg);

		assertNull(TestValues.NULL, msg.getId());
		assertNull(TestValues.NULL, msg.getTs());
		assertNull(TestValues.NULL, msg.getC());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();
		
		try {			
			reference.put(TouchEvent.KEY_ID, TestValues.GENERAL_INT);
			reference.put(TouchEvent.KEY_TS, JsonUtils.createJsonArray(TestValues.GENERAL_LONG_LIST));
			reference.put(TouchEvent.KEY_C, TestValues.JSON_TOUCHCOORDS);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(TestValues.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				
				if (key.equals(TouchEvent.KEY_C)) {
	                assertTrue(TestValues.TRUE, JsonUtils.readIntegerFromJsonObject(reference, key) == JsonUtils.readIntegerFromJsonObject(underTest, key));
	            } else if (key.equals(TouchEvent.KEY_TS)) {				
					List<Long> tsListReference = JsonUtils.readLongListFromJsonObject(reference, key);
					List<Long> tsListTest = JsonUtils.readLongListFromJsonObject(underTest, key);
					assertTrue(TestValues.TRUE, tsListReference.containsAll(tsListTest) && tsListTest.containsAll(tsListReference));
				} else {
					assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
	            }
			}
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
	}
}