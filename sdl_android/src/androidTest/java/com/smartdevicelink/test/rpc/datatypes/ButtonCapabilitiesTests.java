package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.ButtonCapabilities;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.ButtonCapabilities}
 */
public class ButtonCapabilitiesTests extends TestCase{

    private ButtonCapabilities msg;

    @Override
    public void setUp(){
        msg = new ButtonCapabilities();

        msg.setLongPressAvailable(Test.GENERAL_BOOLEAN);
        msg.setName(Test.GENERAL_BUTTONNAME);
        msg.setShortPressAvailable(Test.GENERAL_BOOLEAN);
        msg.setUpDownAvailable(Test.GENERAL_BOOLEAN);
    }


    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        boolean shortPress = msg.getShortPressAvailable();
        boolean longPress = msg.getLongPressAvailable();
        boolean upDown = msg.getUpDownAvailable();
        ButtonName buttonName = msg.getName();
        
        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, shortPress);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, longPress);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, upDown);
        assertEquals(Test.MATCH, Test.GENERAL_BUTTONNAME, buttonName);
        
        // Invalid/Null Tests
        ButtonCapabilities msg = new ButtonCapabilities();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getShortPressAvailable());
        assertNull(Test.NULL, msg.getLongPressAvailable());
        assertNull(Test.NULL, msg.getUpDownAvailable());
        assertNull(Test.NULL, msg.getName());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(ButtonCapabilities.KEY_SHORT_PRESS_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(ButtonCapabilities.KEY_LONG_PRESS_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(ButtonCapabilities.KEY_UP_DOWN_AVAILABLE, Test.GENERAL_BOOLEAN);
            reference.put(ButtonCapabilities.KEY_NAME, Test.GENERAL_BUTTONNAME);

            JSONObject underTest = msg.serializeJSON();

            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        } catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
    }    
}