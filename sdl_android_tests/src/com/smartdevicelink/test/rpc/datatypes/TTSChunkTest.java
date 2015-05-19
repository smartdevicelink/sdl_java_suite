package com.smartdevicelink.test.rpc.datatypes;

import java.util.Iterator;

import junit.framework.TestCase;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.test.utils.JsonUtils;

public class TTSChunkTest extends TestCase {

	private static final SpeechCapabilities SPEECH_TYPE = SpeechCapabilities.SILENCE;
	private static final String TEXT = "text";
	
	private TTSChunk msg;

	@Override
	public void setUp() {
		msg = new TTSChunk();
		
		msg.setText(TEXT);
		msg.setType(SPEECH_TYPE);
	}

	public void testText() {
		String copy = msg.getText();
		
		assertEquals("Input value didn't match expected value.", TEXT, copy);
	}
	
	public void testSpeechType () {
		SpeechCapabilities copy = msg.getType();
		
		assertEquals("Input value didn't match expected value.", SPEECH_TYPE, copy);
	}

	public void testJson() {
		JSONObject reference = new JSONObject();

		try {
			reference.put(TTSChunk.KEY_TEXT, TEXT);
			reference.put(TTSChunk.KEY_TYPE, SPEECH_TYPE);

			JSONObject underTest = msg.serializeJSON();

			assertEquals("JSON size didn't match expected size.",
					reference.length(), underTest.length());

			Iterator<?> iterator = reference.keys();
			while (iterator.hasNext()) {
				String key = (String) iterator.next();
				assertEquals(
						"JSON value didn't match expected value for key \""
								+ key + "\".",
						JsonUtils.readObjectFromJsonObject(reference, key),
						JsonUtils.readObjectFromJsonObject(underTest, key));
			}
		} catch (JSONException e) {
			/* do nothing */
		}
	}

	public void testNull() {
		TTSChunk msg = new TTSChunk();
		assertNotNull("Null object creation failed.", msg);

		assertNull("Text wasn't set, but getter method returned an object.", msg.getText());
		assertNull("Speech type wasn't set, but getter method returned an object.", msg.getType());
	}
}
