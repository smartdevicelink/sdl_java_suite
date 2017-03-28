package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.PutFile}
 */
public class PutFileTests extends BaseRpcTests {
	
	@Override
	protected RPCMessage createMessage() {
		PutFile msg = new PutFile();

		msg.setFileType(Test.GENERAL_FILETYPE);
		msg.setPersistentFile(Test.GENERAL_BOOLEAN);
		msg.setSystemFile(Test.GENERAL_BOOLEAN);
		msg.setOffset(Test.GENERAL_LONG);
		msg.setLength(Test.GENERAL_LONG);

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
			result.put(PutFile.KEY_FILE_TYPE, Test.GENERAL_FILETYPE);
			result.put(PutFile.KEY_PERSISTENT_FILE, Test.GENERAL_BOOLEAN);
			result.put(PutFile.KEY_SYSTEM_FILE, Test.GENERAL_BOOLEAN);
			result.put(PutFile.KEY_OFFSET, Test.GENERAL_LONG);
			result.put(PutFile.KEY_LENGTH, Test.GENERAL_LONG);			
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
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
		
		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_FILETYPE, testFileType);
		assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, testPersistentFile);
		assertEquals(Test.MATCH, Test.GENERAL_BOOLEAN, testSystemFile);
		assertEquals(Test.MATCH, Test.GENERAL_LONG, testOffset);
		assertEquals(Test.MATCH, Test.GENERAL_LONG, testLength);
	
		// Invalid/Null Tests
		PutFile msg = new PutFile();
		assertNotNull("Null object creation failed.", msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getFileType());
		assertNull(Test.NULL, msg.getPersistentFile());
		assertNull(Test.NULL, msg.getSystemFile());
		assertNull(Test.NULL, msg.getOffset());
		assertNull(Test.NULL, msg.getLength());
	}

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			PutFile cmd = new PutFile(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, PutFile.KEY_PERSISTENT_FILE), cmd.getPersistentFile());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, PutFile.KEY_SYSTEM_FILE), cmd.getSystemFile());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, PutFile.KEY_FILE_TYPE), cmd.getFileType().toString());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, PutFile.KEY_SDL_FILE_NAME), cmd.getSdlFileName());
			assertEquals(Test.MATCH, (Long) JsonUtils.readIntegerFromJsonObject(parameters, PutFile.KEY_OFFSET).longValue(), cmd.getOffset());
			assertEquals(Test.MATCH, (Long) JsonUtils.readIntegerFromJsonObject(parameters, PutFile.KEY_LENGTH).longValue(), cmd.getLength());		
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}    	
    }
}