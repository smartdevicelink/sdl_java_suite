package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class TTSChunkTest extends TestCase {

    private TTSChunk msg;

    @Override
    public void setUp() {
        msg = new TTSChunk();

        msg.setText(TestValues.GENERAL_STRING);
        msg.setType(TestValues.GENERAL_SPEECHCAPABILITIES);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        String text = msg.getText();
        SpeechCapabilities speechType = msg.getType();

        // Valid Tests
        assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, text);
        assertEquals(TestValues.MATCH, TestValues.GENERAL_SPEECHCAPABILITIES, speechType);

        // Invalid/Null Tests
        TTSChunk msg = new TTSChunk();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getText());
        assertNull(TestValues.NULL, msg.getType());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(TTSChunk.KEY_TEXT, TestValues.GENERAL_STRING);
            reference.put(TTSChunk.KEY_TYPE, TestValues.GENERAL_SPEECHCAPABILITIES);

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