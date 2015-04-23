package com.smartdevicelink.test.proxy;

import java.util.Vector;

import com.smartdevicelink.proxy.TTSChunkFactory;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;

import junit.framework.TestCase;

public class TTSChunkFactoryTests extends TestCase {
	
	private final String MSG = "Value did not match expected value.";
	private final String TEST = "test";
	private TTSChunk testChunk;
	
	public void testCreateChunk () {

		// Test -- createChunk(SpeechCapabilities type, String text)
		SpeechCapabilities testType = SpeechCapabilities.TEXT;
		testChunk = TTSChunkFactory.createChunk(testType, TEST);
		assertNotNull(MSG, testChunk);
		assertEquals(MSG, testType, testChunk.getType());
		assertEquals(MSG, TEST, testChunk.getText());
		
		testType = SpeechCapabilities.SILENCE;
		testChunk = TTSChunkFactory.createChunk(testType, TEST);
		assertNotNull(MSG, testChunk);
		assertEquals(MSG, testType, testChunk.getType());
		assertEquals(MSG, TEST, testChunk.getText());
				
		testType = SpeechCapabilities.SAPI_PHONEMES;
		testChunk = TTSChunkFactory.createChunk(testType, TEST);
		assertNotNull(MSG, testChunk);
		assertEquals(MSG, testType, testChunk.getType());
		assertEquals(MSG, TEST, testChunk.getText());
		
		testType = SpeechCapabilities.PRE_RECORDED;
		testChunk = TTSChunkFactory.createChunk(testType, TEST);
		assertNotNull(MSG, testChunk);
		assertEquals(MSG, testType, testChunk.getType());
		assertEquals(MSG, TEST, testChunk.getText());
		
		testType = SpeechCapabilities.LHPLUS_PHONEMES;
		testChunk = TTSChunkFactory.createChunk(testType, TEST);
		assertNotNull(MSG, testChunk);
		assertEquals(MSG, testType, testChunk.getType());
		assertEquals(MSG, TEST, testChunk.getText());
		
		testChunk = TTSChunkFactory.createChunk(null, null);
		assertNotNull(MSG, testChunk);
		assertNull(MSG, testChunk.getType());
		assertNull(MSG, testChunk.getText());
		
	}
	
	public void testCreateSimpleTTSChunks () {
		
		Vector<TTSChunk> testChunks;
		// Test -- createSimpleTTSChunks(String simple)
		testChunks = TTSChunkFactory.createSimpleTTSChunks(TEST);
		assertNotNull(MSG, testChunks);
		assertEquals(MSG, SpeechCapabilities.TEXT, testChunks.get(0).getType());
		assertEquals(MSG, TEST, testChunks.get(0).getText());
		
	}
	
	public void testCreatePrerecordedTTSChunks () {
		
		Vector<TTSChunk> testChunks;
		// Test -- createPrerecorededTTSChunks(String prerecorded)
		testChunks = TTSChunkFactory.createPrerecordedTTSChunks(TEST);
		assertNotNull(MSG, testChunks);
		assertEquals(MSG, SpeechCapabilities.PRE_RECORDED, testChunks.get(0).getType());
		assertEquals(MSG, TEST, testChunks.get(0).getText());
		
	}
}