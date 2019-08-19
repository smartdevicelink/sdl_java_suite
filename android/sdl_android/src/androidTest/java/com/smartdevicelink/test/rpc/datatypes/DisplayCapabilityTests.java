package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.DisplayCapability;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.WindowTypeCapabilities;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.DisplayCapability}
 */
public class DisplayCapabilityTests extends TestCase {

    private DisplayCapability msg;

    @Override
    public void setUp() {
        msg = new DisplayCapability();

        msg.setDisplayName(Test.GENERAL_STRING);
        msg.setWindowTypeSupported(Test.GENERAL_WINDOW_TYPE_CAPABILITIES_LIST);
        msg.setWindowCapabilities(Test.GENERAL_WINDOW_CAPABILITY_LIST);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        String displayName = msg.getDisplayName();
        List<WindowTypeCapabilities> windowTypeSupported = msg.getWindowTypeSupported();
        List<WindowCapability> windowCapabilities = msg.getWindowCapabilities();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_STRING, displayName);
        assertEquals(Test.MATCH, Test.GENERAL_WINDOW_TYPE_CAPABILITIES_LIST.size(), windowTypeSupported.size());
        assertEquals(Test.MATCH, Test.GENERAL_WINDOW_CAPABILITY_LIST.size(), windowCapabilities.size());

        for (int i = 0; i < Test.GENERAL_WINDOW_TYPE_CAPABILITIES_LIST.size(); i++) {
            assertTrue(Test.TRUE, Validator.validateWindowTypeCapabilities(Test.GENERAL_WINDOW_TYPE_CAPABILITIES_LIST.get(i), windowTypeSupported.get(i)));
        }

        for (int i = 0; i < Test.GENERAL_WINDOW_CAPABILITY_LIST.size(); i++) {
            assertTrue(Test.TRUE, Validator.validateWindowCapability(Test.GENERAL_WINDOW_CAPABILITY_LIST.get(i), windowCapabilities.get(i)));
        }

        // Invalid/Null Tests
        DisplayCapability msg = new DisplayCapability();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getDisplayName());
        assertNull(Test.NULL, msg.getWindowTypeSupported());
        assertNull(Test.NULL, msg.getWindowCapabilities());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(DisplayCapability.KEY_DISPLAY_NAME, Test.GENERAL_STRING);
            reference.put(DisplayCapability.KEY_WINDOW_TYPE_SUPPORTED, Test.JSON_WINDOW_TYPE_CAPABILITIES_LIST);
            reference.put(DisplayCapability.KEY_WINDOW_CAPABILITIES, Test.JSON_WINDOW_CAPABILITIES);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if (key.equals(DisplayCapability.KEY_WINDOW_TYPE_SUPPORTED)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(Test.MATCH, referenceArray.length(), underTestArray.length());

                    for (int i = 0; i < referenceArray.length(); i++) {
                        Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                        Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                        assertTrue(Test.TRUE, Validator.validateWindowTypeCapabilities(new WindowTypeCapabilities(hashReference), new WindowTypeCapabilities(hashTest)));
                    }
                } else if (key.equals(DisplayCapability.KEY_WINDOW_CAPABILITIES)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(Test.MATCH, referenceArray.length(), underTestArray.length());

                    for (int i = 0; i < referenceArray.length(); i++) {
                        Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                        Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                        assertTrue(Test.TRUE, Validator.validateWindowCapability(new WindowCapability(hashReference), new WindowCapability(hashTest)));
                    }
                } else {
                    assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(Test.JSON_FAIL);
        }
    }
}