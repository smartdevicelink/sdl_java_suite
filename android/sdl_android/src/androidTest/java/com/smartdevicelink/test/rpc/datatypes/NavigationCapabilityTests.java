package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.NavigationCapability;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.NavigationCapability}
 */
public class NavigationCapabilityTests extends TestCase{

    private NavigationCapability msg;

    @Override
    public void setUp(){
        msg = new NavigationCapability();

        msg.setSendLocationEnabled(Test.GENERAL_BOOLEAN);
        msg.setWayPointsEnabled(Test.GENERAL_BOOLEAN);
    }

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
        boolean sendLocationEnabled = msg.getSendLocationEnabled();
        boolean getWayPointsEnabled = msg.getWayPointsEnabled();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, sendLocationEnabled);
        assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, getWayPointsEnabled);

        // Invalid/Null Tests
        NavigationCapability msg = new NavigationCapability();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getSendLocationEnabled());
        assertNull(Test.NULL, msg.getWayPointsEnabled());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(NavigationCapability.KEY_GETWAYPOINTS_ENABLED, Test.GENERAL_BOOLEAN);
            reference.put(NavigationCapability.KEY_LOCATION_ENABLED, Test.GENERAL_BOOLEAN);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){
                String key = (String) iterator.next();
                assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        }catch(JSONException e){
        	fail(Test.JSON_FAIL);
        }
    }
}