package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.PhoneCapability;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.PhoneCapability}
 */
public class PhoneCapabilityTests extends TestCase {

    private PhoneCapability msg;

    @Override
    public void setUp() {
        msg = new PhoneCapability();

        msg.setDialNumberEnabled(TestValues.GENERAL_BOOLEAN);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        boolean dialNumberEnabled = msg.getDialNumberEnabled();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, dialNumberEnabled);

        // Invalid/Null Tests
        PhoneCapability msg = new PhoneCapability();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getDialNumberEnabled());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(PhoneCapability.KEY_DIALNUMBER_ENABLED, TestValues.GENERAL_BOOLEAN);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}