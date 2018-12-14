package com.smartdevicelink.test.rpc.datatypes;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.Turn;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

public class TurnTests extends TestCase {
	
	private Turn msg;

	@Override
    public void setUp(){
        msg = new Turn();
        assertNotNull(Test.NOT_NULL, msg);  
        
        msg.setTurnIcon(Test.GENERAL_IMAGE);
        msg.setNavigationText(Test.GENERAL_STRING);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		Image icon = msg.getTurnIcon();
		String text = msg.getNavigationText();
		
		// Valid Tests
		assertTrue(Test.MATCH, Validator.validateImage(Test.GENERAL_IMAGE, icon));
		assertEquals(Test.MATCH, Test.GENERAL_STRING, text);
		
		// Invalid/Null Tests
		Turn msg = new Turn();
		assertNotNull(Test.NOT_NULL, msg);
		
		assertNull(Test.NULL, msg.getNavigationText());
		assertNull(Test.NULL, msg.getTurnIcon());
	}
	
	public void testJson(){
        JSONObject reference = new JSONObject();

        try{
        	reference.put(Turn.KEY_NAVIGATION_TEXT, Test.GENERAL_STRING);
        	reference.put(Turn.KEY_TURN_IMAGE, Test.JSON_IMAGE);        	
        } catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
	}
}