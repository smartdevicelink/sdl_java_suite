package com.smartdevicelink.test.rpc.responses;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetInteriorVehicleDataConsentResponse;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.List;

public class GetInteriorVehicleDataConsentResponseTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		GetInteriorVehicleDataConsentResponse msg = new GetInteriorVehicleDataConsentResponse();
		msg.setAllowances(Test.GENERAL_BOOLEAN_LIST);
		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_RESPONSE;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.GET_INTERIOR_VEHICLE_DATA_CONSENT.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try{
			result.put(GetInteriorVehicleDataConsentResponse.KEY_ALLOWED, JsonUtils.createJsonArray(Test.GENERAL_BOOLEAN_LIST));
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
		return result;
	}

	public void testRpcValues() {
		List<Boolean> list = ((GetInteriorVehicleDataConsentResponse) msg).getAllowances();
		assertEquals(Test.MATCH, list, Test.GENERAL_BOOLEAN_LIST);
	}

	public void testRequiredParams(){
		GetInteriorVehicleDataConsentResponse msg = new GetInteriorVehicleDataConsentResponse(true, Result.SUCCESS);
		assertTrue(msg.getSuccess());
		assertEquals(Test.MATCH, msg.getResultCode(), Result.SUCCESS);
	}

	public void testJsonConstructor() {
		JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
		assertNotNull(Test.NOT_NULL, commandJson);

		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			GetInteriorVehicleDataConsentResponse cmd = new GetInteriorVehicleDataConsentResponse(hash);

			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);

			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readBooleanListFromJsonObject(parameters,
					GetInteriorVehicleDataConsentResponse.KEY_ALLOWED), cmd.getAllowances());
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}
}
