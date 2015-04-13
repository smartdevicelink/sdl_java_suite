package com.smartdevicelink.test.rpc.datatypes;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.ParameterPermissions;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class ParameterPermissionsTests extends TestCase{

    private final List<String> ALLOWED = Arrays.asList(new String[]{"param1, param3"});
    private final List<String> USER_DISALLOWED = Arrays.asList(new String[]{"param2"});
    
    private ParameterPermissions msg;

    @Override
    public void setUp(){
        msg = new ParameterPermissions();

        msg.setAllowed(ALLOWED);
        msg.setUserDisallowed(USER_DISALLOWED);
    }

    public void testAllowed () {
    	List<String> copy = msg.getAllowed();
    	
    	assertTrue("Input value didn't match expected value.", Validator.validateStringList(ALLOWED, copy));
    }
    
    public void testUserDisallowed () {
    	List<String> copy = msg.getUserDisallowed();
    	
    	assertTrue("Input value didn't match expected value.", Validator.validateStringList(USER_DISALLOWED, copy));
    }
    
    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(ParameterPermissions.KEY_ALLOWED, JsonUtils.createJsonArray(ALLOWED));
            reference.put(ParameterPermissions.KEY_USER_DISALLOWED, JsonUtils.createJsonArray(USER_DISALLOWED));

            JSONObject underTest = msg.serializeJSON();

            assertEquals("JSON size didn't match expected size.", reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                	assertTrue("JSON value didn't match expected value for key \"" + key + "\".",
	                        Validator.validateStringList(JsonUtils.readStringListFromJsonObject(reference, key),
	                        		JsonUtils.readStringListFromJsonObject(underTest, key)));
            }
        }catch(JSONException e){
            /* do nothing */
        }
    }

    public void testNull(){
        ParameterPermissions msg = new ParameterPermissions();
        assertNotNull("Null object creation failed.", msg);
        
        assertNull("Allowed wasn't set, but getter method returned an oblect.", msg.getAllowed());
        assertNull("User disallowed wasn't set, but getter method returned an object.", msg.getUserDisallowed());
    }
}
