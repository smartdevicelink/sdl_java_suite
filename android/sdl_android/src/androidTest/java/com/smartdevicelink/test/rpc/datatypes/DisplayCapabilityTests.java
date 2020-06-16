package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.DisplayCapability;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.WindowTypeCapabilities;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
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

        msg.setDisplayName(TestValues.GENERAL_STRING);
        msg.setWindowTypeSupported(TestValues.GENERAL_WINDOW_TYPE_CAPABILITIES_LIST);
        msg.setWindowCapabilities(TestValues.GENERAL_WINDOW_CAPABILITY_LIST);
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
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, displayName);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_WINDOW_TYPE_CAPABILITIES_LIST.size(), windowTypeSupported.size());
        assertEquals(TestValues.MATCH, TestValues.GENERAL_WINDOW_CAPABILITY_LIST.size(), windowCapabilities.size());

        for (int i = 0; i < TestValues.GENERAL_WINDOW_TYPE_CAPABILITIES_LIST.size(); i++) {
            assertTrue(TestValues.TRUE, Validator.validateWindowTypeCapabilities(TestValues.GENERAL_WINDOW_TYPE_CAPABILITIES_LIST.get(i), windowTypeSupported.get(i)));
        }

        for (int i = 0; i < TestValues.GENERAL_WINDOW_CAPABILITY_LIST.size(); i++) {
            assertTrue(TestValues.TRUE, Validator.validateWindowCapability(TestValues.GENERAL_WINDOW_CAPABILITY_LIST.get(i), windowCapabilities.get(i)));
        }

        // Invalid/Null Tests
        DisplayCapability msg = new DisplayCapability();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getDisplayName());
        assertNull(TestValues.NULL, msg.getWindowTypeSupported());
        assertNull(TestValues.NULL, msg.getWindowCapabilities());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(DisplayCapability.KEY_DISPLAY_NAME, TestValues.GENERAL_STRING);
            reference.put(DisplayCapability.KEY_WINDOW_TYPE_SUPPORTED, TestValues.JSON_WINDOW_TYPE_CAPABILITIES_LIST);
            reference.put(DisplayCapability.KEY_WINDOW_CAPABILITIES, TestValues.JSON_WINDOW_CAPABILITIES);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if (key.equals(DisplayCapability.KEY_WINDOW_TYPE_SUPPORTED)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());

                    for (int i = 0; i < referenceArray.length(); i++) {
                        Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                        Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                        assertTrue(TestValues.TRUE, Validator.validateWindowTypeCapabilities(new WindowTypeCapabilities(hashReference), new WindowTypeCapabilities(hashTest)));
                    }
                } else if (key.equals(DisplayCapability.KEY_WINDOW_CAPABILITIES)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());

                    for (int i = 0; i < referenceArray.length(); i++) {
                        Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                        Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                        assertTrue(TestValues.TRUE, Validator.validateWindowCapability(new WindowCapability(hashReference), new WindowCapability(hashTest)));
                    }
                } else {
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}