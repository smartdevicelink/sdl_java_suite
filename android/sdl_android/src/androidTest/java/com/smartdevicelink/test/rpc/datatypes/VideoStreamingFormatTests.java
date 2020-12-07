package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.VideoStreamingFormat;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingCodec;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingProtocol;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class VideoStreamingFormatTests extends TestCase {

    private VideoStreamingFormat msg;

    @Override
    public void setUp() {
        msg = new VideoStreamingFormat();
        msg.setProtocol(TestValues.GENERAL_VIDEOSTREAMINGPROTOCOL);
        msg.setCodec(TestValues.GENERAL_VIDEOSTREAMINGCODEC);
    }

    /**
     * Tests the expected values of the RPC message.
     */
    public void testRpcValues() {
        // Test Values
        VideoStreamingProtocol protocol = msg.getProtocol();
        VideoStreamingCodec codec = msg.getCodec();

        // Valid Tests
        assertEquals(TestValues.MATCH, (VideoStreamingProtocol) TestValues.GENERAL_VIDEOSTREAMINGPROTOCOL, protocol);
        assertEquals(TestValues.MATCH, (VideoStreamingCodec) TestValues.GENERAL_VIDEOSTREAMINGCODEC, codec);

        // Invalid/Null Tests
        VideoStreamingFormat msg = new VideoStreamingFormat();
        assertNotNull(TestValues.NOT_NULL, msg);

        assertNull(TestValues.NULL, msg.getProtocol());
        assertNull(TestValues.NULL, msg.getCodec());
    }

    public void testJson() {
        JSONObject reference = new JSONObject();

        try {
            reference.put(VideoStreamingFormat.KEY_PROTOCOL, TestValues.GENERAL_VIDEOSTREAMINGPROTOCOL);
            reference.put(VideoStreamingFormat.KEY_CODEC, TestValues.GENERAL_VIDEOSTREAMINGCODEC);

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

