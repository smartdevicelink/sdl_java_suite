package com.smartdevicelink.test.rpc.datatypes;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.TouchCoord;
import com.smartdevicelink.proxy.rpc.TouchEvent;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class TouchEventTest extends TestCase {

	private static final Integer ID = 0;
	private static final Integer TIMESTAMP = 0;
	private static final TouchCoord TOUCH_COORDINATE = new TouchCoord();
	
	private TouchEvent msg;

	@Override
	public void setUp() {
		TOUCH_COORDINATE.setX(0);
		TOUCH_COORDINATE.setY(0);
		
		msg = new TouchEvent();
		msg.setId(ID);
		
		List<Integer> tsList = new ArrayList<Integer>();
		tsList.add(TIMESTAMP);
		msg.setTs(tsList);
	
		List<TouchCoord> cList = new ArrayList<TouchCoord>();
		cList.add(TOUCH_COORDINATE);
		msg.setC(cList);
	}

	public void testId () {
		Integer copy = msg.getId();
		
		assertEquals("Input value didn't match expected value.", ID, copy);
	}
	
	public void testTimestamp () {
		//TODO: correct way to perform test?
		Integer copy = msg.getTs().get(0);
		
		assertEquals("Input value didn't match expected value.", TIMESTAMP, copy);
	}
	
	//TODO: correct?
	public void testTouchCoordinate () {
		TouchCoord coordTest = msg.getC().get(0);
		
		assertNotSame("Touch coordinate was not defensive copied", TOUCH_COORDINATE, coordTest);
	    assertTrue("Input value didn't match expected value.", Validator.validateTouchCoord(TOUCH_COORDINATE, coordTest));
	}

	public void testJson() {
		JSONObject reference = new JSONObject();
		JSONObject coord = new JSONObject();

		try {
			coord.put(TouchCoord.KEY_X, TOUCH_COORDINATE.getX());
			coord.put(TouchCoord.KEY_Y, TOUCH_COORDINATE.getY());
			
			reference.put(TouchEvent.KEY_ID, ID);
			
			List<Integer> tsList = new ArrayList<Integer>();
			tsList.add(TIMESTAMP);
			JSONArray tsArray = JsonUtils.createJsonArray(tsList);
			reference.put(TouchEvent.KEY_TS, tsArray);
			reference.put(TouchEvent.KEY_C, coord);

			JSONObject underTest = msg.serializeJSON();

			assertEquals("JSON size didn't match expected size.",
					reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				
				//TODO: how does this return true?
				if (key.equals(TouchEvent.KEY_C)) {
	                assertTrue("JSON value didn't match expected value for key \"" + key + "\"",
	                        JsonUtils.readIntegerFromJsonObject(reference, key) == JsonUtils.readIntegerFromJsonObject(underTest, key));
	            } 
				else if (key.equals(TouchEvent.KEY_TS)) {				
					List<Integer> tsListReference = JsonUtils.readIntegerListFromJsonObject(reference, key);
					List<Integer> tsListTest = JsonUtils.readIntegerListFromJsonObject(underTest, key);
					
					assertTrue("Time stamp lists not the same for key \"" + key + "\".", 
							tsListReference.containsAll(tsListTest) && tsListTest.containsAll(tsListReference));
					 
				}
				else {
					assertEquals("JSON value didn't match expected value for key \"" + key + "\".",
							JsonUtils.readObjectFromJsonObject(reference, key),
							JsonUtils.readObjectFromJsonObject(underTest, key));
	            }
			}
		} catch (JSONException e) {
			/* do nothing */
		}
	}

	public void testNull() {
		TouchEvent msg = new TouchEvent();
		assertNotNull("Null object creation failed.", msg);

		assertNull("Id wasn't set, but getter method returned an object.", msg.getId());
		assertNull("Timestamp wasn't set, but getter method returned an object.", msg.getTs());
		assertNull("Touch coordinate wasn't set, but getter method returned an object.", msg.getC());
	}
}
