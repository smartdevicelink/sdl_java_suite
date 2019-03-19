package com.smartdevicelink.test.rpc.responses;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetFileResponse;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.GetFileResponse}
 */
public class GetFileResponseTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage(){

		GetFileResponse msg = new GetFileResponse();

		msg.setCRC(Test.GENERAL_INT);
		msg.setFileType(Test.GENERAL_FILETYPE);
		msg.setOffset(Test.GENERAL_INT);
		msg.setLength(Test.GENERAL_INT);

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
			result.put(GetFileResponse.KEY_CRC, Test.GENERAL_INTEGER);
			result.put(GetFileResponse.KEY_FILE_TYPE, Test.GENERAL_FILETYPE);
			result.put(GetFileResponse.KEY_OFFSET, Test.GENERAL_INTEGER);
			result.put(GetFileResponse.KEY_LENGTH, Test.GENERAL_INTEGER);
		}catch(JSONException e){
			fail(Test.JSON_FAIL);
		}

		return result;
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Test Values
		Integer crc = ( (GetFileResponse) msg ).getCRC();
		FileType fileType = ( (GetFileResponse) msg ).getFileType();
		Integer offset = ( (GetFileResponse) msg ).getOffset();
		Integer length = ( (GetFileResponse) msg ).getLength();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_INTEGER, crc);
		assertEquals(Test.MATCH, Test.GENERAL_FILETYPE, fileType);
		assertEquals(Test.MATCH, Test.GENERAL_INTEGER, offset);
		assertEquals(Test.MATCH, Test.GENERAL_INTEGER, length);

		// Invalid/Null Tests
		GetFileResponse msg = new GetFileResponse();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getCRC());
		assertNull(Test.NULL, msg.getFileType());
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
			GetFileResponse cmd = new GetFileResponse (hash);

			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);

			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, GetFileResponse.KEY_CRC), cmd.getCRC());
			assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(parameters, GetFileResponse.KEY_FILE_TYPE).toString(), cmd.getFileType().toString());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, GetFileResponse.KEY_LENGTH), cmd.getLength());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, GetFileResponse.KEY_OFFSET), cmd.getOffset());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
