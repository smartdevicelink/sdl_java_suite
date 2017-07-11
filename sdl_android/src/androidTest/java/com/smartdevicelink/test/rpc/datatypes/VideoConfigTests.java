package com.smartdevicelink.test.rpc.datatypes;

import com.smartdevicelink.proxy.rpc.TouchCoord;
import com.smartdevicelink.proxy.rpc.TouchEvent;
import com.smartdevicelink.proxy.rpc.VideoConfig;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingCodec;
import com.smartdevicelink.proxy.rpc.enums.VideoStreamingProtocol;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class VideoConfigTests extends TestCase {

	private VideoConfig msg;

	@Override
	public void setUp() {
		msg = new VideoConfig();
		msg.setProtocol(Test.GENERAL_VIDEOSTREAMINGPROTOCOL);
		msg.setCodec(Test.GENERAL_VIDEOSTREAMINGCODEC);
		msg.setWidth(Test.GENERAL_INT);
		msg.setHeight(Test.GENERAL_INT);
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		VideoStreamingProtocol protocol = msg.getProtocol();
		VideoStreamingCodec codec = msg.getCodec();
		Integer width = msg.getWidth();
		Integer height = msg.getHeight();

		// Valid Tests
		assertEquals(Test.MATCH, (VideoStreamingProtocol) Test.GENERAL_VIDEOSTREAMINGPROTOCOL, protocol);
		assertEquals(Test.MATCH, (VideoStreamingCodec) Test.GENERAL_VIDEOSTREAMINGCODEC, codec);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, width);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, height);

		// Invalid/Null Tests
		VideoConfig msg = new VideoConfig();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getProtocol());
		assertNull(Test.NULL, msg.getCodec());
		assertNull(Test.NULL, msg.getWidth());
		assertNull(Test.NULL, msg.getHeight());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(VideoConfig.KEY_PROTOCOL, Test.GENERAL_VIDEOSTREAMINGPROTOCOL);
			reference.put(VideoConfig.KEY_CODEC, Test.GENERAL_VIDEOSTREAMINGCODEC);
			reference.put(VideoConfig.KEY_WIDTH, Test.GENERAL_INT);
			reference.put(VideoConfig.KEY_HEIGHT, Test.GENERAL_INT);

			JSONObject underTest = msg.serializeJSON();
			assertEquals(Test.MATCH, reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();

				if (key.equals(VideoConfig.KEY_WIDTH) || key.equals(VideoConfig.KEY_HEIGHT)) {
					assertTrue(Test.TRUE, JsonUtils.readIntegerFromJsonObject(reference, key) == JsonUtils.readIntegerFromJsonObject(underTest, key));
				} else {
					assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(reference, key), JsonUtils.readObjectFromJsonObject(underTest, key));
				}
			}
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}
