package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.ConfigurableKeyboards;
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.ClusterModeStatus}
 */
public class ConfigurableKeyboardsTests extends TestCase {

    private ConfigurableKeyboards msg;

    @Override
    public void setUp() {
        msg = new ConfigurableKeyboards();

        msg.setNumConfigurableKeys(TestValues.GENERAL_INTEGER);
        msg.setKeyboardLayout(TestValues.GENERAL_KEYBOARDLAYOUT);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        Integer configurableKeys = msg.getNumConfigurableKeys();
        KeyboardLayout keyboardLayout = msg.getKeyboardLayout();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER, configurableKeys);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_KEYBOARDLAYOUT, keyboardLayout);

        // Invalid/Null Tests
        ConfigurableKeyboards msg = new ConfigurableKeyboards();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getNumConfigurableKeys());
        assertNull(TestValues.NULL, msg.getKeyboardLayout());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(ConfigurableKeyboards.KEY_KEYBOARD_LAYOUT, TestValues.GENERAL_KEYBOARDLAYOUT);
            reference.put(ConfigurableKeyboards.KEY_NUM_CONFIGURABLE_KEYS, TestValues.GENERAL_INTEGER);

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
