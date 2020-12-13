package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.ConfigurableKeyboards;
import com.smartdevicelink.proxy.rpc.KeyboardCapabilities;
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;
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

public class KeyboardCapabilitiesTests extends TestCase {
    private KeyboardCapabilities msg;

    @Override
    public void setUp() {
        msg = new KeyboardCapabilities();

        msg.setConfigurableKeys(TestValues.GENERAL_CONFIGURABLE_KEYBOARDS_LIST);
        msg.setMaskInputCharactersSupported(TestValues.GENERAL_BOOLEAN);
        msg.setSupportedKeyboardLayouts(TestValues.GENERAL_KEYBOARD_LAYOUT_LIST);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        List<ConfigurableKeyboards> configurableKeyboards = msg.getConfigurableKeys();
        Boolean maskInputCharactersSupported = msg.getMaskInputCharactersSupported();
        List<KeyboardLayout> keyboardLayouts = msg.getSupportedKeyboardLayouts();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_CONFIGURABLE_KEYBOARDS_LIST, configurableKeyboards);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, (boolean) maskInputCharactersSupported);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_KEYBOARD_LAYOUT_LIST, keyboardLayouts);

        // Invalid/Null Tests
        KeyboardCapabilities msg = new KeyboardCapabilities();
        assertNotNull(TestValues.NOT_NULL, msg);

        // Keypress mode is created in the object constructor
        assertNull(TestValues.NULL, msg.getConfigurableKeys());
        assertNull(TestValues.NULL, msg.getMaskInputCharactersSupported());
        assertNull(TestValues.NULL, msg.getSupportedKeyboardLayouts());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(KeyboardCapabilities.KEY_CONFIGURABLE_KEYS, TestValues.JSON_CONFIGURABLE_KEYBOARDS_LIST);
            reference.put(KeyboardCapabilities.KEY_MASK_INPUT_CHARACTERS_SUPPORTED, TestValues.GENERAL_BOOLEAN);
            reference.put(KeyboardCapabilities.KEY_SUPPORTED_KEYBOARD_LAYOUTS, TestValues.JSON_KEYBOARDS_LAYOUTS_LIST);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if (key.equals(KeyboardCapabilities.KEY_CONFIGURABLE_KEYS)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());

                    for (int i = 0; i < referenceArray.length(); i++) {
                        Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                        Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                        assertTrue(TestValues.TRUE, Validator.validateConfigurableKeyboards(new ConfigurableKeyboards(hashReference), new ConfigurableKeyboards(hashTest)));
                    }
                }/* else if (key.equals(KeyboardCapabilities.KEY_SUPPORTED_KEYBOARD_LAYOUTS)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());

                    for (int i = 0; i < referenceArray.length(); i++) {
                        Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                        Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                        assertTrue(TestValues.TRUE, Validator.validateKeyboardLayouts(new ConfigurableKeyboards(hashReference), new ConfigurableKeyboards(hashTest)));
                    }
                }*/ else {
                    assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
                }
            }
        } catch (JSONException e) {
            fail(TestValues.JSON_FAIL);
        }
    }
}
