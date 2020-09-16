package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.ButtonCapabilities;
import com.smartdevicelink.proxy.rpc.ModuleInfo;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.ButtonCapabilities}
 */
public class ButtonCapabilitiesTests extends TestCase{

    private ButtonCapabilities msg;

    @Override
    public void setUp(){
        msg = new ButtonCapabilities();

        msg.setLongPressAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setName(TestValues.GENERAL_BUTTONNAME);
        msg.setShortPressAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setUpDownAvailable(TestValues.GENERAL_BOOLEAN);
        msg.setModuleInfo(TestValues.GENERAL_MODULE_INFO);
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
        ModuleInfo info = msg.getModuleInfo();
        
        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, shortPress);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, longPress);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, upDown);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BUTTONNAME, buttonName);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_MODULE_INFO, info);
        
        // Invalid/Null Tests
        ButtonCapabilities msg = new ButtonCapabilities();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getShortPressAvailable());
        assertNull(TestValues.NULL, msg.getLongPressAvailable());
        assertNull(TestValues.NULL, msg.getUpDownAvailable());
        assertNull(TestValues.NULL, msg.getName());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(ButtonCapabilities.KEY_SHORT_PRESS_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ButtonCapabilities.KEY_LONG_PRESS_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ButtonCapabilities.KEY_UP_DOWN_AVAILABLE, TestValues.GENERAL_BOOLEAN);
            reference.put(ButtonCapabilities.KEY_NAME, TestValues.GENERAL_BUTTONNAME);
            reference.put(ButtonCapabilities.KEY_MODULE_INFO, TestValues.JSON_MODULE_INFO);

            JSONObject underTest = msg.serializeJSON();

            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if (key.equals(ButtonCapabilities.KEY_MODULE_INFO)) {
                    JSONObject o1 = (JSONObject) JsonUtils.readObjectFromJsonObject(reference, key);
                    JSONObject o2 = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> h1 = JsonRPCMarshaller.deserializeJSONObject(o1);
                    Hashtable<String, Object> h2 = JsonRPCMarshaller.deserializeJSONObject(o2);
                    assertTrue(TestValues.TRUE, Validator.validateModuleInfo(new ModuleInfo(h1), new ModuleInfo(h2)));
                } else {
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }
    }    
}