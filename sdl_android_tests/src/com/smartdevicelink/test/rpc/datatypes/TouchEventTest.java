package com.smartdevicelink.test.rpc.datatypes;

import java.util.ArrayList;
import java.util.Arrays;
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
	
	private static final Integer TS_ITEM_1 = 2;
	private static final Integer TS_ITEM_2 = 3;
	private static final Integer TS_ITEM_3 = 5;
	private static final Integer TS_ITEM_4 = 7;
	private static final Integer TS_ITEM_CHANGED = 40;
    private final List<Integer> TS_LIST = Arrays.asList(new Integer[]{TS_ITEM_1, TS_ITEM_2, TS_ITEM_3, TS_ITEM_4});
    
    
    private final List<TouchCoord> C_LIST = new ArrayList<TouchCoord>();
	private final TouchCoord TOUCH_COORDINATE = new TouchCoord();
	private static final Integer TOUCH_COORDINATE_VALUE_X = 3;
	private static final Integer TOUCH_COORDINATE_VALUE_Y = 6;
	private static final Integer TOUCH_COORDINATE_VALUE_Y_CHANGED = 8;
	
	private final TouchCoord TOUCH_COORDINATE_2 = new TouchCoord();
	private static final Integer TOUCH_COORDINATE_VALUE_X_2 = 2;
	private static final Integer TOUCH_COORDINATE_VALUE_Y_2 = 8;
	
	private TouchEvent msg;

	@Override
	public void setUp() {
		TOUCH_COORDINATE.setX(TOUCH_COORDINATE_VALUE_X);
		TOUCH_COORDINATE.setY(TOUCH_COORDINATE_VALUE_Y);
		TOUCH_COORDINATE_2.setX(TOUCH_COORDINATE_VALUE_X_2);
		TOUCH_COORDINATE_2.setY(TOUCH_COORDINATE_VALUE_Y_2);
		
		msg = new TouchEvent();
		msg.setId(ID);
		msg.setTs(TS_LIST);
		
		C_LIST.add(TOUCH_COORDINATE);
		C_LIST.add(TOUCH_COORDINATE_2);
		msg.setC(C_LIST);
	}

	public void testId () {
		Integer copy = msg.getId();
		
		assertEquals("Input value didn't match expected value.", ID, copy);
	}
	
	public void testTimestamp () {
		List<Integer> copy = msg.getTs();
		
    	assertTrue("Input value didn't match expected value.", Validator.validateIntegerList(TS_LIST, copy));
	}
	
    public void testGetTimestamp() {
    	List<Integer> copy1 = msg.getTs();
    	copy1.set(0, TS_ITEM_CHANGED);
    	List<Integer> copy2 = msg.getTs();
    	
    	assertNotSame("Timestamp list was not defensive copied", copy1, copy2);
    	assertFalse("Copies have the same values", Validator.validateIntegerList(copy1, copy2));
    }
    
    public void testSetTimestamp() {
    	List<Integer> copy1 = msg.getTs();
    	msg.setTs(copy1);
    	copy1.set(0, TS_ITEM_CHANGED);
    	List<Integer> copy2 = msg.getTs();
    	
    	assertNotSame("Timestamp list was not defensive copied", copy1, copy2);
    	assertFalse("Copies have the same values", Validator.validateIntegerList(copy1, copy2));
    }
	
	public void testTouchCoordinate () {
		List<TouchCoord> coordTest = msg.getC();
		
		assertEquals("Touch coordinate list lengths do not match", C_LIST.size(), coordTest.size());
		
		for (int index = 0; index < C_LIST.size(); index++) {
			assertTrue("Input value didn't match expected value", Validator.validateTouchCoord(C_LIST.get(index), coordTest.get(index)));
		}
	}
	
    public void testGetTouchCoordinate() {
    	List<TouchCoord> copy1 = msg.getC();
    	TouchCoord firstItem = copy1.get(0);
    	firstItem.setY(TOUCH_COORDINATE_VALUE_Y_CHANGED);
    	List<TouchCoord> copy2 = msg.getC();
    	
    	assertNotSame("Touch coordinate list was not defensive copied", copy1, copy2);
    	//test the first object for different values, and test the rest of the objects in the list for same values
		TouchCoord touchFirst1 = copy1.get(0);
		TouchCoord touchFirst2 = copy2.get(0);
		
		assertNotSame("Touch coordinate was not defensive copied", touchFirst1, touchFirst2);
		assertFalse("Touch coordinate objects matched", Validator.validateTouchCoord(touchFirst1, touchFirst2));
		
    	for (int index = 1; index < copy1.size(); index++) {
    		TouchCoord touchCopy1 = copy1.get(index);
    		TouchCoord touchCopy2 = copy2.get(index);
    		
    		assertNotSame("Touch coordinate was not defensive copied", touchCopy1, touchCopy2);
    		assertTrue("Input value didn't match expected value", Validator.validateTouchCoord(touchCopy1, touchCopy2));
    	}
    }
    
    public void testSetTouchCoordinate() {
    	List<TouchCoord> copy1 = msg.getC();
    	TouchCoord firstItem = copy1.get(0);
    	msg.setC(copy1);
    	firstItem.setY(TOUCH_COORDINATE_VALUE_Y_CHANGED);
    	List<TouchCoord> copy2 = msg.getC();
    	
    	assertNotSame("Touch coordinate list was not defensive copied", copy1, copy2);
    	//test the first object for different values, and test the rest of the objects in the list for same values
		TouchCoord touchFirst1 = copy1.get(0);
		TouchCoord touchFirst2 = copy2.get(0);
		
		assertNotSame("Touch coordinate was not defensive copied", touchFirst1, touchFirst2);
		assertFalse("Touch coordinate objects matched", Validator.validateTouchCoord(touchFirst1, touchFirst2));
		
    	for (int index = 1; index < copy1.size(); index++) {
    		TouchCoord touchCopy1 = copy1.get(index);
    		TouchCoord touchCopy2 = copy2.get(index);
    		
    		assertNotSame("Touch coordinate was not defensive copied", touchCopy1, touchCopy2);
    		assertTrue("Input value didn't match expected value", Validator.validateTouchCoord(touchCopy1, touchCopy2));
    	}
    }

	public void testJson() {
		JSONObject reference = new JSONObject();
		JSONArray cArray = new JSONArray();
		
		try {			
			reference.put(TouchEvent.KEY_ID, ID);
			
			List<Integer> tsList = new ArrayList<Integer>();
			tsList.add(TS_ITEM_1);
			tsList.add(TS_ITEM_2);
			tsList.add(TS_ITEM_3);
			tsList.add(TS_ITEM_4);
			
			JSONArray tsArray = JsonUtils.createJsonArray(tsList);
			reference.put(TouchEvent.KEY_TS, tsArray);
			
			JSONObject coord = new JSONObject();
			coord.put(TouchCoord.KEY_X, TOUCH_COORDINATE.getX());
			coord.put(TouchCoord.KEY_Y, TOUCH_COORDINATE.getY());
			cArray.put(coord);
			coord.put(TouchCoord.KEY_X, TOUCH_COORDINATE_2.getX());
			coord.put(TouchCoord.KEY_Y, TOUCH_COORDINATE_2.getY());
			cArray.put(coord);
			reference.put(TouchEvent.KEY_C, cArray);

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
