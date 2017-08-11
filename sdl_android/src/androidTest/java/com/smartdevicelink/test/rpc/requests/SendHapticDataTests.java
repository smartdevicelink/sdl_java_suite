package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SendHapticData;
import com.smartdevicelink.proxy.rpc.SpatialStruct;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

public class SendHapticDataTests extends BaseRpcTests {

	private SpatialStruct spatialStruct;

	@Override
	protected RPCMessage createMessage(){
		spatialStruct = new SpatialStruct();
		spatialStruct.setID(Test.GENERAL_INTEGER);
		spatialStruct.setX(Test.GENERAL_INTEGER);
		spatialStruct.setY(Test.GENERAL_INTEGER);
		spatialStruct.setWidth(Test.GENERAL_INTEGER);
		spatialStruct.setHeight(Test.GENERAL_INTEGER);

		SendHapticData msg = new SendHapticData();
		msg.setHapticSpatialData(spatialStruct);

		return msg;
	}

	@Override
	protected String getMessageType(){
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType(){
		return FunctionID.SEND_HAPTIC_DATA.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion){
		JSONObject result = new JSONObject();

		try{
			result.put(SendHapticData.KEY_HAPTIC_SPATIAL_DATA, spatialStruct.serializeJSON());
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
		SpatialStruct testSpatialStruct = ((SendHapticData) msg).getHapticSpatialData();

		// Valid Tests
		assertEquals(Test.MATCH, spatialStruct, testSpatialStruct);
		// Invalid/Null Tests
		SendHapticData msg = new SendHapticData();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getHapticSpatialData());
	}


	/**
	 * Tests a valid JSON construction of this RPC message.
	 */
	public void testJsonConstructor () {
		JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
		assertNotNull(Test.NOT_NULL, commandJson);

		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SendHapticData cmd = new SendHapticData(hash);

			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);

			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			JSONObject sendHapticData = JsonUtils.readJsonObjectFromJsonObject(parameters, SendHapticData.KEY_HAPTIC_SPATIAL_DATA);
			SpatialStruct referenceSendHapticData = new SpatialStruct(JsonRPCMarshaller.deserializeJSONObject(sendHapticData));
			assertTrue(Test.TRUE, Validator.validateSpatialData(referenceSendHapticData, cmd.getHapticSpatialData()));
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}

}
