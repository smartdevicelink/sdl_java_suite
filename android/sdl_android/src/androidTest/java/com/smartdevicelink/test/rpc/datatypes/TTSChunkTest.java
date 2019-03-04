package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;

public class TTSChunkTest extends TestCase {
	
	private TTSChunk msg;

	@Override
	public void setUp() {
		msg = new TTSChunk();
		
		msg.setText(Test.GENERAL_STRING);
		msg.setType(Test.GENERAL_SPEECHCAPABILITIES);
	}

    /**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {
    	// Test Values
		String text = msg.getText();
		SpeechCapabilities speechType = msg.getType();
		
		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_STRING, text);
		assertEquals(Test.MATCH, Test.GENERAL_SPEECHCAPABILITIES, speechType);
		
		// Invalid/Null Tests
		TTSChunk msg = new TTSChunk();
		assertNotNull(Test.NOT_NULL, msg);

		assertNull(Test.NULL, msg.getText());
		assertNull(Test.NULL, msg.getType());
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(TTSChunk.KEY_TEXT, Test.GENERAL_STRING);
			reference.put(TTSChunk.KEY_TYPE, Test.GENERAL_SPEECHCAPABILITIES);

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