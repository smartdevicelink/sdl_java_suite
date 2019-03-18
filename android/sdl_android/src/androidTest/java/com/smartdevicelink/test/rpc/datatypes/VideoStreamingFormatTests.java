package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.VideoStreamingFormat;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingCodec;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingProtocol;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class VideoStreamingFormatTests extends TestCase {

	private VideoStreamingFormat msg;

	@Override
	public void setUp() {
		msg = new VideoStreamingFormat();
		msg.setProtocol(Test.GENERAL_VIDEOSTREAMINGPROTOCOL);
		msg.setCodec(Test.GENERAL_VIDEOSTREAMINGCODEC);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		VideoStreamingProtocol protocol = msg.getProtocol();
		VideoStreamingCodec codec = msg.getCodec();

		// Valid Tests
		assertEquals(Test.MATCH, (VideoStreamingProtocol) Test.GENERAL_VIDEOSTREAMINGPROTOCOL, protocol);
		assertEquals(Test.MATCH, (VideoStreamingCodec) Test.GENERAL_VIDEOSTREAMINGCODEC, codec);

		// Invalid/Null Tests
		VideoStreamingFormat msg = new VideoStreamingFormat();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getProtocol());
		assertNull(Test.NULL, msg.getCodec());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(VideoStreamingFormat.KEY_PROTOCOL, Test.GENERAL_VIDEOSTREAMINGPROTOCOL);
			reference.put(VideoStreamingFormat.KEY_CODEC, Test.GENERAL_VIDEOSTREAMINGCODEC);

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

