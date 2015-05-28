package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.TouchCoord;
import com.smartdevicelink.proxy.rpc.TouchEvent;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.TouchEvent}
 */
public class TouchEventTest extends TestCase {
	
	private TouchEvent msg;

	@Override
	public void setUp() {		
		msg = new TouchEvent();
		msg.setId(Test.GENERAL_INT);
		msg.setTs(Test.GENERAL_INTEGER_LIST);
		msg.setC(Test.GENERAL_TOUCHCOORD_LIST);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		Integer id = msg.getId();
		List<Integer> timestamp = msg.getTs();
		List<TouchCoord> coordTest = msg.getC();
		
		// Valid Tests
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, id);
    	assertTrue(Test.TRUE, Validator.validateIntegerList(Test.GENERAL_INTEGER_LIST, timestamp));
		assertEquals(Test.MATCH, Test.GENERAL_TOUCHCOORD_LIST.size(), coordTest.size());
		
		for (int index = 0; index < Test.GENERAL_TOUCHCOORD_LIST.size(); index++) {
			assertTrue(Test.TRUE, Validator.validateTouchCoord(Test.GENERAL_TOUCHCOORD_LIST.get(index), coordTest.get(index)));
		}
		
		// Invalid/Null Tests
		TouchEvent msg = new TouchEvent();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getId());
		assertNull(Test.NULL, msg.getTs());
		assertNull(Test.NULL, msg.getC());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();
		
		try {			
			reference.put(TouchEvent.KEY_ID, Test.GENERAL_INT);
			reference.put(TouchEvent.KEY_TS, JsonUtils.createJsonArray(Test.GENERAL_INTEGER_LIST));
			reference.put(TouchEvent.KEY_C, Test.JSON_TOUCHCOORDS);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				
				if (key.equals(TouchEvent.KEY_C)) {
	                assertTrue(Test.TRUE, JsonUtils.readIntegerFromJsonObject(reference, key) == JsonUtils.readIntegerFromJsonObject(underTest, key));
	            } else if (key.equals(TouchEvent.KEY_TS)) {				
					List<Integer> tsListReference = JsonUtils.readIntegerListFromJsonObject(reference, key);
					List<Integer> tsListTest = JsonUtils.readIntegerListFromJsonObject(underTest, key);
					assertTrue(Test.TRUE, tsListReference.containsAll(tsListTest) && tsListTest.containsAll(tsListReference));
				} else {
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
	            }
			}
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}