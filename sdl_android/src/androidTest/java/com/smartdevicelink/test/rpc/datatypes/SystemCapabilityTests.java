package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.SystemCapability;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class SystemCapabilityTests extends TestCase {

    private SystemCapability msg;

    @Override
    public void setUp() {
        msg = new SystemCapability();

        msg.setSystemCapabilityType(Test.GENERAL_SYSTEMCAPABILITYTYPE);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues () {
        // Test Values
        SystemCapabilityType testType = msg.getSystemCapabilityType();

        // Valid Tests
        assertEquals(Test.MATCH, (SystemCapabilityType) Test.GENERAL_SYSTEMCAPABILITYTYPE, testType);

        // Invalid/Null Tests
        SystemCapability msg = new SystemCapability();
        assertNotNull(Test.NOT_NULL, msg);

        assertNull(Test.NULL, msg.getSystemCapabilityType());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(SystemCapability.KEY_SYSTEM_CAPABILITY_TYPE, Test.GENERAL_SYSTEMCAPABILITYTYPE);

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