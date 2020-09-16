package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.HMIPermissions;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.HMIPermissions}
 */
public class HMIPermissionsTests extends TestCase{

    private HMIPermissions msg;

    @Override
    public void setUp(){
        msg = new HMIPermissions();

        msg.setAllowed(TestValues.GENERAL_HMILEVEL_LIST);
        msg.setUserDisallowed(TestValues.GENERAL_HMILEVEL_LIST);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        List<HMILevel> allowed = msg.getAllowed();
        List<HMILevel> disallowed = msg.getUserDisallowed();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_HMILEVEL_LIST.size(), allowed.size());
        assertEquals(TestValues.MATCH, TestValues.GENERAL_HMILEVEL_LIST.size(), disallowed.size());
        
        for(int i = 0; i < TestValues.GENERAL_HMILEVEL_LIST.size(); i++){
            assertEquals(TestValues.MATCH, TestValues.GENERAL_HMILEVEL_LIST.get(i), allowed.get(i));
        }
        for(int i = 0; i < TestValues.GENERAL_HMILEVEL_LIST.size(); i++){
            assertEquals(TestValues.MATCH, TestValues.GENERAL_HMILEVEL_LIST.get(i), disallowed.get(i));
        }
        
        // Invalid/Null Tests
        HMIPermissions msg = new HMIPermissions();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getAllowed());
        assertNull(TestValues.NULL, msg.getUserDisallowed());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(HMIPermissions.KEY_ALLOWED, TestValues.JSON_HMILEVELS);
            reference.put(HMIPermissions.KEY_USER_DISALLOWED, TestValues.JSON_HMILEVELS);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                List<String> referenceList = JsonUtils.readStringListFromJsonObject(reference, key);
                List<String> underTestList = JsonUtils.readStringListFromJsonObject(underTest, key);

                assertEquals(TestValues.MATCH, referenceList.size(), underTestList.size());
                for(int i = 0; i < referenceList.size(); i++){
                    assertEquals(TestValues.MATCH, referenceList.get(i), underTestList.get(i));
                }
            }
        } catch(JSONException e){
        	fail(TestValues.JSON_FAIL);
        }
    }
}