package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetInteriorVehicleDataConsent;
import com.smartdevicelink.proxy.rpc.enums.ModuleType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.List;

public class GetInteriorVehicleDataConsentTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		GetInteriorVehicleDataConsent msg = new GetInteriorVehicleDataConsent();
		msg.setModuleType(Test.GENERAL_MODULETYPE);
		msg.setModuleIds(Test.GENERAL_STRING_LIST);
		return msg;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();
		try {
			result.put(GetInteriorVehicleDataConsent.KEY_MODULE_TYPE, Test.GENERAL_MODULETYPE);
			result.put(GetInteriorVehicleDataConsent.KEY_MODULE_ID, JsonUtils.createJsonArray(Test.GENERAL_STRING_LIST));
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
		return result;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.GET_INTERIOR_VEHICLE_DATA_CONSENT.toString();
	}

	@Override
	protected String getMessageType(){
		return RPCMessage.KEY_REQUEST;
	}

	public void testRpcValues() {
		ModuleType type = ((GetInteriorVehicleDataConsent) msg).getModuleType();
		List<String> ids = ((GetInteriorVehicleDataConsent) msg).getModuleIds();

		//valid tests
		assertEquals(Test.MATCH, Test.GENERAL_MODULETYPE, type);
		assertEquals(Test.MATCH, Test.GENERAL_STRING_LIST, ids);

		//null tests
		GetInteriorVehicleDataConsent msg = new GetInteriorVehicleDataConsent();
		assertNull(Test.NULL, msg.getModuleType());
		assertNull(Test.NULL, msg.getModuleIds());

		//test require param constructor
		GetInteriorVehicleDataConsent msg2 = new GetInteriorVehicleDataConsent(Test.GENERAL_MODULETYPE, Test.GENERAL_STRING_LIST);
		assertEquals(Test.MATCH, Test.GENERAL_MODULETYPE, msg2.getModuleType());
		assertEquals(Test.MATCH, Test.GENERAL_STRING_LIST, msg2.getModuleIds());

	}

	public void testJsonConstructor() {
		JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
		assertNotNull(Test.NOT_NULL, commandJson);

		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			GetInteriorVehicleDataConsent cmd = new GetInteriorVehicleDataConsent(hash);

			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);

			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readObjectFromJsonObject(parameters, GetInteriorVehicleDataConsent.KEY_MODULE_TYPE).toString(), cmd.getModuleType().toString());
			assertEquals(Test.MATCH, JsonUtils.readStringListFromJsonObject(parameters, GetInteriorVehicleDataConsent.KEY_MODULE_ID), cmd.getModuleIds());
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}
	}


}
