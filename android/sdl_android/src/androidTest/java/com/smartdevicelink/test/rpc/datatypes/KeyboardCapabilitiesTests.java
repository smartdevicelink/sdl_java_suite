package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.proxy.rpc.KeyboardCapabilities;
import com.smartdevicelink.proxy.rpc.KeyboardLayoutCapability;
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

        msg.setMaskInputCharactersSupported(TestValues.GENERAL_BOOLEAN);
        msg.setSupportedKeyboards(TestValues.GENERAL_SUPPORTED_KEYBOARDS_LIST);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        Boolean maskInputCharactersSupported = msg.getMaskInputCharactersSupported();
        List<KeyboardLayoutCapability> supportedKeyboards = msg.getSupportedKeyboards();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, (boolean) maskInputCharactersSupported);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_SUPPORTED_KEYBOARDS_LIST, supportedKeyboards);

        // Invalid/Null Tests
        KeyboardCapabilities msg = new KeyboardCapabilities();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getMaskInputCharactersSupported());
        assertNull(TestValues.NULL, msg.getSupportedKeyboards());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(KeyboardCapabilities.KEY_MASK_INPUT_CHARACTERS_SUPPORTED, TestValues.GENERAL_BOOLEAN);
            reference.put(KeyboardCapabilities.KEY_SUPPORTED_KEYBOARDS, TestValues.JSON_SUPPORTED_KEYBOARDS_LIST);

            JSONObject underTest = msg.serializeJSON();
            assertEquals(TestValues.MATCH, reference.length(), underTest.length());

            Iterator<?> iterator = reference.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                if (key.equals(KeyboardCapabilities.KEY_SUPPORTED_KEYBOARDS)) {
                    JSONArray referenceArray = JsonUtils.readJsonArrayFromJsonObject(reference, key);
                    JSONArray underTestArray = JsonUtils.readJsonArrayFromJsonObject(underTest, key);
                    assertEquals(TestValues.MATCH, referenceArray.length(), underTestArray.length());

                    for (int i = 0; i < referenceArray.length(); i++) {
                        Hashtable<String, Object> hashReference = JsonRPCMarshaller.deserializeJSONObject(referenceArray.getJSONObject(i));
                        Hashtable<String, Object> hashTest = JsonRPCMarshaller.deserializeJSONObject(underTestArray.getJSONObject(i));
                        assertTrue(TestValues.TRUE, Validator.validateKeyboardLayoutCapability(new KeyboardLayoutCapability(hashReference), new KeyboardLayoutCapability(hashTest)));
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
