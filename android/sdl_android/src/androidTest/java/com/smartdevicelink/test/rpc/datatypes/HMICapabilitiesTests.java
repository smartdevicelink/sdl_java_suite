package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.HMICapabilities;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

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

        msg.setNavigationAvilable(TestValues.GENERAL_BOOLEAN);
        msg.setPhoneCallAvilable(TestValues.GENERAL_BOOLEAN);
	    msg.setVideoStreamingAvailable(TestValues.GENERAL_BOOLEAN);
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
        assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, navAvail);
        assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, phoneAvail);
	    assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, vidStreamAvail);

        // Invalid/Null Tests
        HMICapabilities msg = new HMICapabilities();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertFalse(msg.isNavigationAvailable());
        assertFalse(msg.isPhoneCallAvailable());
	    assertFalse(msg.isVideoStreamingAvailable());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(KEY_NAVIGATION, TestValues.GENERAL_BOOLEAN);
            reference.put(HMICapabilities.KEY_PHONE_CALL, TestValues.GENERAL_BOOLEAN);
	        reference.put(HMICapabilities.KEY_VIDEO_STREAMING, TestValues.GENERAL_BOOLEAN);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            assertEquals(TestValues.MATCH, JsonUtils.readStringListFromJsonObject(reference, KEY_NAVIGATION),
                    JsonUtils.readStringListFromJsonObject(underTest, KEY_NAVIGATION));

            assertEquals(TestValues.MATCH, JsonUtils.readStringListFromJsonObject(reference, KEY_PHONE_CALL),
                    JsonUtils.readStringListFromJsonObject(underTest, KEY_PHONE_CALL));

	        assertEquals(TestValues.MATCH, JsonUtils.readStringListFromJsonObject(reference, KEY_VIDEO_STREAMING),
			        JsonUtils.readStringListFromJsonObject(underTest, KEY_VIDEO_STREAMING));
        } catch(JSONException e){
            fail(TestValues.JSON_FAIL);
        }
    }
}
