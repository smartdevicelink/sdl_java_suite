package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.WindowTypeCapabilities;
import com.smartdevicelink.proxy.rpc.enums.WindowType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class WindowTypeCapabilitiesTest extends TestCase {

    private WindowTypeCapabilities msg;

    @Override
    public void setUp() {
        msg = new WindowTypeCapabilities();

        msg.setMaximumNumberOfWindows(TestValues.GENERAL_INT);
        msg.setType(TestValues.GENERAL_WINDOWTYPE);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        int maximumNumberOfWindows = msg.getMaximumNumberOfWindows();
        WindowType type = msg.getType();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INT, maximumNumberOfWindows);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_WINDOWTYPE, type);

        // Invalid/Null Tests
        WindowTypeCapabilities msg = new WindowTypeCapabilities();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getMaximumNumberOfWindows());
        assertNull(TestValues.NULL, msg.getType());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(WindowTypeCapabilities.KEY_MAXIMUM_NUMBER_OF_WINDOWS, TestValues.GENERAL_INT);
            reference.put(WindowTypeCapabilities.KEY_TYPE, TestValues.GENERAL_WINDOWTYPE);

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