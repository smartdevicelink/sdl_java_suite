package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.ParameterPermissions;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.ParameterPermissions}
 */
public class ParameterPermissionsTests extends TestCase {

    private ParameterPermissions msg;

    @Override
    public void setUp() {
        msg = new ParameterPermissions();

        msg.setAllowed(TestValues.GENERAL_STRING_LIST);
        msg.setUserDisallowed(TestValues.GENERAL_STRING_LIST);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        List<String> allowed = msg.getAllowed();
        List<String> disallowed = msg.getUserDisallowed();

        // Valid Tests
        assertTrue(TestValues.TRUE, Validator.validateStringList(TestValues.GENERAL_STRING_LIST, allowed));
        assertTrue(TestValues.TRUE, Validator.validateStringList(TestValues.GENERAL_STRING_LIST, disallowed));

        // Invalid/Null Tests
        ParameterPermissions msg = new ParameterPermissions();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getAllowed());
        assertNull(TestValues.NULL, msg.getUserDisallowed());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(ParameterPermissions.KEY_ALLOWED, JsonUtils.createJsonArray(TestValues.GENERAL_STRING_LIST));
            reference.put(ParameterPermissions.KEY_USER_DISALLOWED, JsonUtils.createJsonArray(TestValues.GENERAL_STRING_LIST));

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                assertTrue(TestValues.TRUE, Validator.validateStringList(JsonUtils.readStringListFromJsonObject(reference, key), JsonUtils.readStringListFromJsonObject(underTest, key)));
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}