package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.zip.CRC32;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.PutFile}
 */
public class PutFileTests extends BaseRpcTests {
	
	@Override
	protected RPCMessage createMessage() {
		PutFile msg = new PutFile();

		msg.setFileType(TestValues.GENERAL_FILETYPE);
		msg.setPersistentFile(TestValues.GENERAL_BOOLEAN);
		msg.setSystemFile(TestValues.GENERAL_BOOLEAN);
		msg.setOffset(TestValues.GENERAL_LONG);
		msg.setLength(TestValues.GENERAL_LONG);
		msg.setCRC(TestValues.GENERAL_BYTE_ARRAY);
		msg.setCRC(TestValues.GENERAL_LONG);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.PUT_FILE.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(PutFile.KEY_FILE_TYPE, TestValues.GENERAL_FILETYPE);
			result.put(PutFile.KEY_PERSISTENT_FILE, TestValues.GENERAL_BOOLEAN);
			result.put(PutFile.KEY_SYSTEM_FILE, TestValues.GENERAL_BOOLEAN);
			result.put(PutFile.KEY_OFFSET, TestValues.GENERAL_LONG);
			result.put(PutFile.KEY_LENGTH, TestValues.GENERAL_LONG);
			result.put(PutFile.KEY_CRC, TestValues.GENERAL_LONG);
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}

		return result;
	}
	
	/**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {    	
    	// Test Values
		FileType testFileType = ( (PutFile) msg ).getFileType();
		boolean  testPersistentFile = ( (PutFile) msg ).getPersistentFile();
		boolean  testSystemFile = ( (PutFile) msg ).getSystemFile();
		Long     testOffset = ( (PutFile) msg ).getOffset();
		Long     testLength = ( (PutFile) msg ).getLength();
		Long     testCRC = ( (PutFile) msg ).getCRC();

		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_FILETYPE, testFileType);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, testPersistentFile);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_BOOLEAN, testSystemFile);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_LONG, testOffset);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_LONG, testLength);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_LONG, testCRC);

		// Invalid/Null Tests
		PutFile msg = new PutFile();
		assertNotNull("Null object creation failed.", msg);
		testNullBase(msg);

		assertNull(TestValues.NULL, msg.getFileType());
		assertNull(TestValues.NULL, msg.getPersistentFile());
		assertNull(TestValues.NULL, msg.getSystemFile());
		assertNull(TestValues.NULL, msg.getOffset());
		assertNull(TestValues.NULL, msg.getLength());
		assertNull(TestValues.NULL, msg.getCRC());
	}

	/**
	 * Tests the expected values of the CRC checksum.
	 */
	public void testByteArrayCheckSum () {
		// Test Values
		PutFile msgCRC = new PutFile();
		msgCRC.setCRC(TestValues.GENERAL_BYTE_ARRAY);
		Long testCRCByteArray = msgCRC.getCRC();

		CRC32 crc = new CRC32();
		crc.update(TestValues.GENERAL_BYTE_ARRAY);
		Long crcValue = crc.getValue();

		assertEquals(TestValues.MATCH, crcValue, testCRCByteArray);
	}



    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(TestValues.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			PutFile cmd = new PutFile(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, PutFile.KEY_PERSISTENT_FILE), cmd.getPersistentFile());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, PutFile.KEY_SYSTEM_FILE), cmd.getSystemFile());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, PutFile.KEY_FILE_TYPE), cmd.getFileType().toString());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, PutFile.KEY_SDL_FILE_NAME), cmd.getSdlFileName());
			assertEquals(TestValues.MATCH, (Long) JsonUtils.readIntegerFromJsonObject(parameters, PutFile.KEY_OFFSET).longValue(), cmd.getOffset());
			assertEquals(TestValues.MATCH, (Long) JsonUtils.readIntegerFromJsonObject(parameters, PutFile.KEY_LENGTH).longValue(), cmd.getLength());
			assertEquals(TestValues.MATCH, (Long) JsonUtils.readIntegerFromJsonObject(parameters, PutFile.KEY_CRC).longValue(), cmd.getCRC());
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}    	
    }
}