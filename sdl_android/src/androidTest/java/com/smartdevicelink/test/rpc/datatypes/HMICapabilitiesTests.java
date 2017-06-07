package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.HMICapabilities;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import static com.smartdevicelink.proxy.rpc.HMICapabilities.KEY_NAVIGATION;
import static com.smartdevicelink.proxy.rpc.HMICapabilities.KEY_PHONE_CALL;

/**
 * Created by austinkirk on 6/7/17.
 */

public class HMICapabilitiesTests extends TestCase {
    private HMICapabilities msg;

    @Override
    public void setUp(){
        msg = new HMICapabilities();

        msg.setNavigationAvilable(Test.GENERAL_BOOLEAN);
        msg.setPhoneCallAvilable(Test.GENERAL_BOOLEAN);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues () {
        // Test Values
        Boolean navAvail = msg.isNavigationAvailable();
        Boolean phoneAvail = msg.isPhoneCallAvailable();

        // Valid Tests
        assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, navAvail);
        assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, phoneAvail);

        // Invalid/Null Tests
        HMICapabilities msg = new HMICapabilities();
        assertNotNull(Test.NOT_NULL, msg);

        assertFalse(msg.isNavigationAvailable());
        assertFalse(msg.isPhoneCallAvailable());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(KEY_NAVIGATION, Test.GENERAL_BOOLEAN);
            reference.put(HMICapabilities.KEY_PHONE_CALL, Test.GENERAL_BOOLEAN);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            assertEquals(Test.MATCH, JsonUtils.readStringListFromJsonObject(reference, KEY_NAVIGATION),
                    JsonUtils.readStringListFromJsonObject(underTest, KEY_NAVIGATION));

            assertEquals(Test.MATCH, JsonUtils.readStringListFromJsonObject(reference, KEY_PHONE_CALL),
                    JsonUtils.readStringListFromJsonObject(underTest, KEY_PHONE_CALL));

        } catch(JSONException e){
            fail(Test.JSON_FAIL);
        }
    }
}
