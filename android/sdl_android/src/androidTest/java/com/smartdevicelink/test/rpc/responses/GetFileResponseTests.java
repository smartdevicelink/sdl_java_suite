package com.smartdevicelink.test.rpc.responses;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetFileResponse;
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


/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.GetFileResponse}
 */
public class GetFileResponseTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage(){

		GetFileResponse msg = new GetFileResponse();

		msg.setCRC(TestValues.GENERAL_INT);
		msg.setFileType(TestValues.GENERAL_FILETYPE);
		msg.setOffset(TestValues.GENERAL_INT);
		msg.setLength(TestValues.GENERAL_INT);

		return msg;
	}

	@Override
	protected String getMessageType(){
		return RPCMessage.KEY_RESPONSE;
	}

	@Override
	protected String getCommandType(){
		return FunctionID.GET_FILE.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion){
		JSONObject result = new JSONObject();

		try{
			result.put(GetFileResponse.KEY_CRC, TestValues.GENERAL_INTEGER);
			result.put(GetFileResponse.KEY_FILE_TYPE, TestValues.GENERAL_FILETYPE);
			result.put(GetFileResponse.KEY_OFFSET, TestValues.GENERAL_INTEGER);
			result.put(GetFileResponse.KEY_LENGTH, TestValues.GENERAL_INTEGER);
		}catch(JSONException e){
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
		Integer crc = ( (GetFileResponse) msg ).getCRC();
		FileType fileType = ( (GetFileResponse) msg ).getFileType();
		Integer offset = ( (GetFileResponse) msg ).getOffset();
		Integer length = ( (GetFileResponse) msg ).getLength();

		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER, crc);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_FILETYPE, fileType);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER, offset);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER, length);

		// Invalid/Null Tests
		GetFileResponse msg = new GetFileResponse();
		assertNotNull(TestValues.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(TestValues.NULL, msg.getCRC());
		assertNull(TestValues.NULL, msg.getFileType());
		assertNull(TestValues.NULL, msg.getOffset());
		assertNull(TestValues.NULL, msg.getLength());
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
			GetFileResponse cmd = new GetFileResponse (hash);

			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);

			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, GetFileResponse.KEY_CRC), cmd.getCRC());
			assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(parameters, GetFileResponse.KEY_FILE_TYPE).toString(), cmd.getFileType().toString());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, GetFileResponse.KEY_LENGTH), cmd.getLength());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, GetFileResponse.KEY_OFFSET), cmd.getOffset());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
