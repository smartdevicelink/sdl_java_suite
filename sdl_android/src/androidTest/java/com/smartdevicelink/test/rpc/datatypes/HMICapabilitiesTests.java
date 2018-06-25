package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.HMICapabilities;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import static com.smartdevicelink.proxy.rpc.HMICapabilities.KEY_NAVIGATION;
import static com.smartdevicelink.proxy.rpc.HMICapabilities.KEY_PHONE_CALL;
import static com.smartdevicelink.proxy.rpc.HMICapabilities.KEY_VIDEO_STREAMING;

public class HMICapabilitiesTests extends TestCase {
    private HMICapabilities msg;

    @Override
    public void setUp(){
        msg = new HMICapabilities();

        msg.setNavigationAvilable(Test.GENERAL_BOOLEAN);
        msg.setPhoneCallAvilable(Test.GENERAL_BOOLEAN);
	    msg.setVideoStreamingAvailable(Test.GENERAL_BOOLEAN);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues () {
        // Test Values
        Boolean navAvail = msg.isNavigationAvailable();
        Boolean phoneAvail = msg.isPhoneCallAvailable();
	    Boolean vidStreamAvail = msg.isVideoStreamingAvailable();

        // Valid Tests
        assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, navAvail);
        assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, phoneAvail);
	    assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, vidStreamAvail);

        // Invalid/Null Tests
        HMICapabilities msg = new HMICapabilities();
        assertNotNull(Test.NOT_NULL, msg);

        assertFalse(msg.isNavigationAvailable());
        assertFalse(msg.isPhoneCallAvailable());
	    assertFalse(msg.isVideoStreamingAvailable());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(KEY_NAVIGATION, Test.GENERAL_BOOLEAN);
            reference.put(HMICapabilities.KEY_PHONE_CALL, Test.GENERAL_BOOLEAN);
	        reference.put(HMICapabilities.KEY_VIDEO_STREAMING, Test.GENERAL_BOOLEAN);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            assertEquals(Test.MATCH, JsonUtils.readStringListFromJsonObject(reference, KEY_NAVIGATION),
                    JsonUtils.readStringListFromJsonObject(underTest, KEY_NAVIGATION));

            assertEquals(Test.MATCH, JsonUtils.readStringListFromJsonObject(reference, KEY_PHONE_CALL),
                    JsonUtils.readStringListFromJsonObject(underTest, KEY_PHONE_CALL));

	        assertEquals(Test.MATCH, JsonUtils.readStringListFromJsonObject(reference, KEY_VIDEO_STREAMING),
			        JsonUtils.readStringListFromJsonObject(underTest, KEY_VIDEO_STREAMING));
        } catch(JSONException e){
            fail(Test.JSON_FAIL);
        }
    }
}
