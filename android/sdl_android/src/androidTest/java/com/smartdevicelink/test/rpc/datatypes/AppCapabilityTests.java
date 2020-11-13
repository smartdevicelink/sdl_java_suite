package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.AppCapability;
import com.smartdevicelink.proxy.rpc.VideoStreamingCapability;
import com.smartdevicelink.proxy.rpc.enums.AppCapabilityType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;

import static com.smartdevicelink.proxy.rpc.AppCapability.KEY_APP_CAPABILITY_TYPE;
import static com.smartdevicelink.proxy.rpc.AppCapability.KEY_VIDEO_STREAMING_CAPABILITY;

public class AppCapabilityTests extends TestCase {
    private AppCapability msg;

    @Override
    public void setUp() {

        msg = new AppCapability();
        msg.setAppCapabilityType(TestValues.GENERAL_APP_CAPABILITY_TYPE);
        msg.setVideoStreamingCapability(TestValues.GENERAL_VIDEOSTREAMINGCAPABILITY);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues () {
        // Test Values
        VideoStreamingCapability videoStreamingCapability = msg.getVideoStreamingCapability();
        AppCapabilityType videoStreamingCapabilityType = msg.getAppCapabilityType();

        // Valid Tests
        assertEquals(TestValues.GENERAL_VIDEOSTREAMINGCAPABILITY, videoStreamingCapability);
        assertEquals(TestValues.GENERAL_APP_CAPABILITY_TYPE, videoStreamingCapabilityType);

        // Invalid/Null Tests
        AppCapability msg = new AppCapability();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getAppCapabilityType());
        assertNull(TestValues.NULL, msg.getVideoStreamingCapability());
    }

    public void testJson(){
        JSONObject reference = new JSONObject();

        try{
            reference.put(KEY_APP_CAPABILITY_TYPE, TestValues.GENERAL_APP_CAPABILITY_TYPE);
            reference.put(KEY_VIDEO_STREAMING_CAPABILITY, TestValues.GENERAL_VIDEOSTREAMINGCAPABILITY);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while(iterator.hasNext()){

                String key = (String) iterator.next();

                if (key.equals(KEY_APP_CAPABILITY_TYPE)) {
                    assertEquals(
                        TestValues.MATCH,
                        JsonUtils.readObjectFromJsonObject(reference, key),
                        JsonUtils.readObjectFromJsonObject(underTest, key)
                    );

                } else if (key.equals(KEY_VIDEO_STREAMING_CAPABILITY)) {
                    JSONObject testEquals = (JSONObject) JsonUtils.readObjectFromJsonObject(underTest, key);
                    Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(testEquals);
                    assertTrue(
                        TestValues.TRUE,
                        Validator.validateVideoStreamingCapability(
                            TestValues.GENERAL_VIDEOSTREAMINGCAPABILITY, new VideoStreamingCapability(hashTest)
                        )
                    );
                }
            }
        } catch(JSONException e){
            fail(TestValues.JSON_FAIL);
        }
    }
}
