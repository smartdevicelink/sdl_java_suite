package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.WindowTypeCapabilities;
import com.smartdevicelink.proxy.rpc.enums.WindowType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class WindowTypeCapabilitiesTest extends TestCase {

    private WindowTypeCapabilities msg;

    @Override
    public void setUp() {
        msg = new WindowTypeCapabilities();

        msg.setMaximumNumberOfWindows(Test.GENERAL_INT);
        msg.setType(Test.GENERAL_WINDOWTYPE);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        int maximumNumberOfWindows = msg.getMaximumNumberOfWindows();
        WindowType type = msg.getType();

        // Valid Tests
        assertEquals(Test.MATCH, Test.GENERAL_INT, maximumNumberOfWindows);
        assertEquals(Test.MATCH, Test.GENERAL_WINDOWTYPE, type);

        // Invalid/Null Tests
        WindowTypeCapabilities msg = new WindowTypeCapabilities();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getMaximumNumberOfWindows());
        assertNull(Test.NULL, msg.getType());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(WindowTypeCapabilities.KEY_MAXIMUM_NUMBER_OF_WINDOWS, Test.GENERAL_INT);
            reference.put(WindowTypeCapabilities.KEY_TYPE, Test.GENERAL_WINDOWTYPE);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(Test.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
            }
        } catch (JSONException e) {
            fail(Test.JSON_FAIL);
        }
    }
}