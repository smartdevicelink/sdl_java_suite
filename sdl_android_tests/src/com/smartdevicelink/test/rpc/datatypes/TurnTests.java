package com.smartdevicelink.test.rpc.datatypes;

import org.json.JSONException;
import org.json.JSONObject;

import junit.framework.TestCase;

import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.Turn;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.test.Validator;

public class TurnTests extends TestCase {
	
	private static final Image TURN_ICON = new Image();
	private static final String NAV_TEXT = "nav_text";
	
	private Turn msg;

	@Override
    public void setUp(){
        msg = new Turn();
        
        TURN_ICON.setValue("turn_icon");
        TURN_ICON.setImageType(ImageType.STATIC);
        
        msg.setTurnIcon(TURN_ICON);
        msg.setNavigationText(NAV_TEXT);
	}
	
	public void testCreation () {
        assertNotNull("Object creation failed.", msg);
    }
	
	public void testTurnIcon () {
		Image icon = msg.getTurnIcon();
		assertTrue("Turn icon didn't match expected value.", Validator.validateImage(TURN_ICON, icon));
	}
	
	public void testNavText () {
		String text = msg.getNavigationText();
		assertEquals("Navigation text didn't match expected value.",NAV_TEXT, text);
	}
	
	public void testJson(){
        JSONObject reference = new JSONObject();

        try{
        	reference.put(Turn.KEY_NAVIGATION_TEXT, NAV_TEXT);
        	reference.put(Turn.KEY_TURN_IMAGE, TURN_ICON.serializeJSON());        	
        } catch(JSONException e){
            /* do nothing */
        }
	}
	
	public void testNull() {
		Turn msg = new Turn();
		assertNotNull("Null object creation failed.", msg);
		
		assertNull("Navigation text wasn't set but getter method returned an object.", msg.getNavigationText());
		assertNull("Turn icon wasn't set but the getter method returned an object.", msg.getTurnIcon());
	}
}