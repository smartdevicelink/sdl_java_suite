package com.smartdevicelink.test.proxy;

import java.util.Vector;

import com.smartdevicelink.proxy.TTSChunkFactory;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.test.Test;

import junit.framework.TestCase;

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
		testChunk = TTSChunkFactory.createChunk(testType, Test.GENERAL_STRING);
		assertNotNull(Test.NOT_NULL, testChunk);
		assertEquals(Test.MATCH, testType, testChunk.getType());
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testChunk.getText());
		
		testType = SpeechCapabilities.SILENCE;
		testChunk = TTSChunkFactory.createChunk(testType, Test.GENERAL_STRING);
		assertNotNull(Test.NOT_NULL, testChunk);
		assertEquals(Test.MATCH, testType, testChunk.getType());
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testChunk.getText());
				
		testType = SpeechCapabilities.SAPI_PHONEMES;
		testChunk = TTSChunkFactory.createChunk(testType, Test.GENERAL_STRING);
		assertNotNull(Test.NOT_NULL, testChunk);
		assertEquals(Test.MATCH, testType, testChunk.getType());
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testChunk.getText());
		
		testType = SpeechCapabilities.PRE_RECORDED;
		testChunk = TTSChunkFactory.createChunk(testType, Test.GENERAL_STRING);
		assertNotNull(Test.NOT_NULL, testChunk);
		assertEquals(Test.MATCH, testType, testChunk.getType());
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testChunk.getText());
		
		testType = SpeechCapabilities.LHPLUS_PHONEMES;
		testChunk = TTSChunkFactory.createChunk(testType, Test.GENERAL_STRING);
		assertNotNull(Test.NOT_NULL, testChunk);
		assertEquals(Test.MATCH, testType, testChunk.getType());
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testChunk.getText());
		
		// Invalid/Null Tests
		testChunk = TTSChunkFactory.createChunk(null, null);
		assertNotNull(Test.NOT_NULL, testChunk);
		assertNull(Test.NULL, testChunk.getType());
		assertNull(Test.NULL, testChunk.getText());		
	}
	
	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.proxy.TTSChunkFactory#createSimpleTTSChunks(String)}
	 */
	public void testCreateSimpleTTSChunks () {
		// Test Values
		Vector<TTSChunk> testChunks;
		testChunks = TTSChunkFactory.createSimpleTTSChunks(Test.GENERAL_STRING);
		
		// Valid Tests
		assertNotNull(Test.NOT_NULL, testChunks);
		assertEquals(Test.MATCH, SpeechCapabilities.TEXT, testChunks.get(0).getType());
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testChunks.get(0).getText());
		
		// Invalid/Null Tests
		testChunks = TTSChunkFactory.createSimpleTTSChunks(null);
		assertNull(Test.NULL, testChunks);
	}

	/**
	 * This is a unit test for the following methods : 
	 * {@link com.smartdevicelink.proxy.TTSChunkFactory#createPrerecordedTTSChunks(String)}
	 */
	public void testCreatePrerecordedTTSChunks () {
		// Test Values
		Vector<TTSChunk> testChunks;
		testChunks = TTSChunkFactory.createPrerecordedTTSChunks(Test.GENERAL_STRING);
		
		// Valid Tests
		assertNotNull(Test.NOT_NULL, testChunks);
		assertEquals(Test.MATCH, SpeechCapabilities.PRE_RECORDED, testChunks.get(0).getType());
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testChunks.get(0).getText());
		
		// Invalid/Null Tests
		testChunks = TTSChunkFactory.createPrerecordedTTSChunks(null);
		assertNull(Test.NULL, testChunks);
	}
}