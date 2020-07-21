package com.smartdevicelink.test.proxy;

import com.smartdevicelink.proxy.TTSChunkFactory;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.test.TestValues;

import junit.framework.TestCase;

import java.util.Vector;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.TTSChunkFactory}
 */
public class TTSChunkFactoryTests extends TestCase {
	
	private TTSChunk testChunk;
	
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.proxy.TTSChunkFactory#createChunk(SpeechCapabilities, String)}
	 */
	public void testCreateChunk () {
		// Valid Tests
		SpeechCapabilities testType = SpeechCapabilities.TEXT;
		testChunk = TTSChunkFactory.createChunk(testType, TestValues.GENERAL_STRING);
		assertNotNull(TestValues.NOT_NULL, testChunk);
		assertEquals(TestValues.MATCH, testType, testChunk.getType());
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testChunk.getText());
		
		testType = SpeechCapabilities.SILENCE;
		testChunk = TTSChunkFactory.createChunk(testType, TestValues.GENERAL_STRING);
		assertNotNull(TestValues.NOT_NULL, testChunk);
		assertEquals(TestValues.MATCH, testType, testChunk.getType());
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testChunk.getText());
				
		testType = SpeechCapabilities.SAPI_PHONEMES;
		testChunk = TTSChunkFactory.createChunk(testType, TestValues.GENERAL_STRING);
		assertNotNull(TestValues.NOT_NULL, testChunk);
		assertEquals(TestValues.MATCH, testType, testChunk.getType());
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testChunk.getText());
		
		testType = SpeechCapabilities.PRE_RECORDED;
		testChunk = TTSChunkFactory.createChunk(testType, TestValues.GENERAL_STRING);
		assertNotNull(TestValues.NOT_NULL, testChunk);
		assertEquals(TestValues.MATCH, testType, testChunk.getType());
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testChunk.getText());
		
		testType = SpeechCapabilities.LHPLUS_PHONEMES;
		testChunk = TTSChunkFactory.createChunk(testType, TestValues.GENERAL_STRING);
		assertNotNull(TestValues.NOT_NULL, testChunk);
		assertEquals(TestValues.MATCH, testType, testChunk.getType());
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testChunk.getText());
		
		// Invalid/Null Tests
		testChunk = TTSChunkFactory.createChunk(null, null);
		assertNotNull(TestValues.NOT_NULL, testChunk);
		assertNull(TestValues.NULL, testChunk.getType());
		assertNull(TestValues.NULL, testChunk.getText());
	}
	
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.proxy.TTSChunkFactory#createSimpleTTSChunks(String)}
	 */
	public void testCreateSimpleTTSChunks () {
		// Test Values
		Vector<TTSChunk> testChunks;
		testChunks = TTSChunkFactory.createSimpleTTSChunks(TestValues.GENERAL_STRING);
		
		// Valid Tests
		assertNotNull(TestValues.NOT_NULL, testChunks);
		assertEquals(TestValues.MATCH, SpeechCapabilities.TEXT, testChunks.get(0).getType());
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testChunks.get(0).getText());
		
		// Invalid/Null Tests
		testChunks = TTSChunkFactory.createSimpleTTSChunks(null);
		assertNull(TestValues.NULL, testChunks);
	}

	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.proxy.TTSChunkFactory#createPrerecordedTTSChunks(String)}
	 */
	public void testCreatePrerecordedTTSChunks () {
		// Test Values
		Vector<TTSChunk> testChunks;
		testChunks = TTSChunkFactory.createPrerecordedTTSChunks(TestValues.GENERAL_STRING);
		
		// Valid Tests
		assertNotNull(TestValues.NOT_NULL, testChunks);
		assertEquals(TestValues.MATCH, SpeechCapabilities.PRE_RECORDED, testChunks.get(0).getType());
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testChunks.get(0).getText());
		
		// Invalid/Null Tests
		testChunks = TTSChunkFactory.createPrerecordedTTSChunks(null);
		assertNull(TestValues.NULL, testChunks);
	}
}