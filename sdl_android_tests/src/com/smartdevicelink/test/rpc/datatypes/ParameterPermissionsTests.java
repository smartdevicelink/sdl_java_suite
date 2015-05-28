package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.ParameterPermissions;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.ParameterPermissions}
 */
public class ParameterPermissionsTests extends TestCase{
    
    private ParameterPermissions msg;

    @Override
    public void setUp(){
        msg = new ParameterPermissions();

        msg.setAllowed(Test.GENERAL_STRING_LIST);
        msg.setUserDisallowed(Test.GENERAL_STRING_LIST);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
    	List<String> allowed = msg.getAllowed();
    	List<String> disallowed = msg.getUserDisallowed();
    	
    	// Valid Tests
    	assertTrue(Test.TRUE, Validator.validateStringList(Test.GENERAL_STRING_LIST, allowed));
    	assertTrue(Test.TRUE, Validator.validateStringList(Test.GENERAL_STRING_LIST, disallowed));
    	
    	// Invalid/Null Tests
    	ParameterPermissions msg = new ParameterPermissions();
        assertNotNull(Test.NOT_NULL, msg);
        
        assertNull(Test.NULL, msg.getAllowed());
        assertNull(Test.NULL, msg.getUserDisallowed());
    }
    
    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(ParameterPermissions.KEY_ALLOWED, JsonUtils.createJsonArray(Test.GENERAL_STRING_LIST));
            reference.put(ParameterPermissions.KEY_USER_DISALLOWED, JsonUtils.createJsonArray(Test.GENERAL_STRING_LIST));

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertTrue(Test.TRUE, Validator.validateStringList(JsonUtils.readStringListFromJsonObject(reference, key), JsonUtils.readStringListFromJsonObject(underTest, key)));
            }
        } catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
    }
}