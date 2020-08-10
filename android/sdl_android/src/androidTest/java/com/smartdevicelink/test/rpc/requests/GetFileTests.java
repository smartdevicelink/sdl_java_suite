package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetFile;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;
import static androidx.test.InstrumentationRegistry.getTargetContext;


public class GetFileTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		GetFile msg = new GetFile();

		msg.setFileName(TestValues.GENERAL_STRING);
		msg.setAppServiceId(TestValues.GENERAL_STRING);
		msg.setFileType(TestValues.GENERAL_FILETYPE);
		msg.setOffset(TestValues.GENERAL_INT);
		msg.setLength(TestValues.GENERAL_INT);

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
			result.put(GetFile.KEY_FILE_NAME, TestValues.GENERAL_STRING);
			result.put(GetFile.KEY_APP_SERVICE_ID, TestValues.GENERAL_STRING);
			result.put(GetFile.KEY_FILE_TYPE, TestValues.GENERAL_FILETYPE);
			result.put(GetFile.KEY_OFFSET, TestValues.GENERAL_INTEGER);
			result.put(GetFile.KEY_LENGTH, TestValues.GENERAL_INTEGER);
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}

		return result;
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	@Test
	public void testRpcValues () {
		// Test Values
		String fileName = ( (GetFile) msg ).getFileName();
		String appServiceId = ( (GetFile) msg ).getAppServiceId();
		FileType fileType = ( (GetFile) msg ).getFileType();
		Integer offset = ( (GetFile) msg ).getOffset();
		Integer length = ( (GetFile) msg ).getLength();

		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, fileName);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, appServiceId);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_FILETYPE, fileType);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER, offset);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER, length);

		// Invalid/Null Tests
		GetFile msg = new GetFile();
		assertNotNull(TestValues.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(TestValues.NULL, msg.getFileName());
		assertNull(TestValues.NULL, msg.getAppServiceId());
		assertNull(TestValues.NULL, msg.getFileType());
		assertNull(TestValues.NULL, msg.getOffset());
		assertNull(TestValues.NULL, msg.getLength());
	}

	/**
	 * Tests constructor with required params
	 */
	@Test
	public void testRequiredParamsConstructor () {

		GetFile msg = new GetFile(TestValues.GENERAL_STRING);
		assertNotNull(TestValues.NOT_NULL, msg);
		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, msg.getFileName());
	}

	/**
	 * Tests a valid JSON construction of this RPC message.
	 */
	@Test
	public void testJsonConstructor () {
		JSONObject commandJson = JsonFileReader.readId(getTargetContext(), getCommandType(), getMessageType());
		assertNotNull(TestValues.NOT_NULL, commandJson);

		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			GetFile cmd = new GetFile(hash);

			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);

			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, GetFile.KEY_APP_SERVICE_ID), cmd.getAppServiceId());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, GetFile.KEY_FILE_NAME), cmd.getFileName());
			assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(parameters, GetFile.KEY_FILE_TYPE).toString(), cmd.getFileType().toString());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, GetFile.KEY_LENGTH), cmd.getLength());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, GetFile.KEY_OFFSET), cmd.getOffset());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
