package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetFile;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;


public class GetFileTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		GetFile msg = new GetFile();

		msg.setFileName(Test.GENERAL_STRING);
		msg.setAppServiceId(Test.GENERAL_STRING);
		msg.setFileType(Test.GENERAL_FILETYPE);
		msg.setOffset(Test.GENERAL_INT);
		msg.setLength(Test.GENERAL_INT);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.GET_FILE.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(GetFile.KEY_FILE_NAME, Test.GENERAL_STRING);
			result.put(GetFile.KEY_APP_SERVICE_ID, Test.GENERAL_STRING);
			result.put(GetFile.KEY_FILE_TYPE, Test.GENERAL_FILETYPE);
			result.put(GetFile.KEY_OFFSET, Test.GENERAL_INTEGER);
			result.put(GetFile.KEY_LENGTH, Test.GENERAL_INTEGER);
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
		String fileName = ( (GetFile) msg ).getFileName();
		String appServiceId = ( (GetFile) msg ).getAppServiceId();
		FileType fileType = ( (GetFile) msg ).getFileType();
		Integer offset = ( (GetFile) msg ).getOffset();
		Integer length = ( (GetFile) msg ).getLength();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_STRING, fileName);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, appServiceId);
		assertEquals(Test.MATCH, Test.GENERAL_FILETYPE, fileType);
		assertEquals(Test.MATCH, Test.GENERAL_INTEGER, offset);
		assertEquals(Test.MATCH, Test.GENERAL_INTEGER, length);

		// Invalid/Null Tests
		GetFile msg = new GetFile();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getFileName());
		assertNull(Test.NULL, msg.getAppServiceId());
		assertNull(Test.NULL, msg.getFileType());
		assertNull(Test.NULL, msg.getOffset());
		assertNull(Test.NULL, msg.getLength());
	}

	/**
	 * Tests constructor with required params
	 */
	public void testRequiredParamsConstructor () {

		GetFile msg = new GetFile(Test.GENERAL_STRING);
		assertNotNull(Test.NOT_NULL, msg);
		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_STRING, msg.getFileName());
	}

	/**
	 * Tests a valid JSON construction of this RPC message.
	 */
	public void testJsonConstructor () {
		JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
		assertNotNull(Test.NOT_NULL, commandJson);

		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			GetFile cmd = new GetFile(hash);

			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);

			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, GetFile.KEY_APP_SERVICE_ID), cmd.getAppServiceId());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, GetFile.KEY_FILE_NAME), cmd.getFileName());
			assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(parameters, GetFile.KEY_FILE_TYPE).toString(), cmd.getFileType().toString());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, GetFile.KEY_LENGTH), cmd.getLength());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, GetFile.KEY_OFFSET), cmd.getOffset());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
