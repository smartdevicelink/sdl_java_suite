package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.Turn;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

public class TurnTests extends TestCase {
	
	private Turn msg;

	@Override
    public void setUp(){
        msg = new Turn();
        assertNotNull(TestValues.NOT_NULL, msg);
        
        msg.setTurnIcon(TestValues.GENERAL_IMAGE);
        msg.setNavigationText(TestValues.GENERAL_STRING);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		Image icon = msg.getTurnIcon();
		String text = msg.getNavigationText();
		
		// Valid Tests
		assertTrue(TestValues.MATCH, Validator.validateImage(TestValues.GENERAL_IMAGE, icon));
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, text);
		
		// Invalid/Null Tests
		Turn msg = new Turn();
		assertNotNull(TestValues.NOT_NULL, msg);
		
		assertNull(TestValues.NULL, msg.getNavigationText());
		assertNull(TestValues.NULL, msg.getTurnIcon());
	}
	
	public void testJson(){
        JSONObject reference = new JSONObject();

        try{
        	reference.put(Turn.KEY_NAVIGATION_TEXT, TestValues.GENERAL_STRING);
        	reference.put(Turn.KEY_TURN_IMAGE, TestValues.JSON_IMAGE);
        } catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }
	}
}