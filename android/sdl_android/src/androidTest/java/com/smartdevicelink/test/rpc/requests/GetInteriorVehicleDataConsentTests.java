package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.GetInteriorVehicleDataConsent;
import com.smartdevicelink.proxy.rpc.enums.ModuleType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;
import java.util.List;
import static android.support.test.InstrumentationRegistry.getTargetContext;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;

public class GetInteriorVehicleDataConsentTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		GetInteriorVehicleDataConsent msg = new GetInteriorVehicleDataConsent();
		msg.setModuleType(TestValues.GENERAL_MODULETYPE);
		msg.setModuleIds(TestValues.GENERAL_STRING_LIST);
		return msg;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();
		try {
			result.put(GetInteriorVehicleDataConsent.KEY_MODULE_TYPE, TestValues.GENERAL_MODULETYPE);
			result.put(GetInteriorVehicleDataConsent.KEY_MODULE_ID, JsonUtils.createJsonArray(TestValues.GENERAL_STRING_LIST));
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
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

	@Test
	public void testRpcValues() {
		ModuleType type = ((GetInteriorVehicleDataConsent) msg).getModuleType();
		List<String> ids = ((GetInteriorVehicleDataConsent) msg).getModuleIds();

		//valid tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_MODULETYPE, type);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING_LIST, ids);

		//null tests
		GetInteriorVehicleDataConsent msg = new GetInteriorVehicleDataConsent();
		assertNull(TestValues.NULL, msg.getModuleType());
		assertNull(TestValues.NULL, msg.getModuleIds());

		//test require param constructor
		GetInteriorVehicleDataConsent msg2 = new GetInteriorVehicleDataConsent(TestValues.GENERAL_MODULETYPE, TestValues.GENERAL_STRING_LIST);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_MODULETYPE, msg2.getModuleType());
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING_LIST, msg2.getModuleIds());

	}

	@Test
	public void testJsonConstructor() {
		JSONObject commandJson = JsonFileReader.readId(getTargetContext(), getCommandType(), getMessageType());
		assertNotNull(TestValues.NOT_NULL, commandJson);

		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			GetInteriorVehicleDataConsent cmd = new GetInteriorVehicleDataConsent(hash);

			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);

			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(TestValues.MATCH, JsonUtils.readObjectFromJsonObject(parameters, GetInteriorVehicleDataConsent.KEY_MODULE_TYPE).toString(), cmd.getModuleType().toString());
			assertEquals(TestValues.MATCH, JsonUtils.readStringListFromJsonObject(parameters, GetInteriorVehicleDataConsent.KEY_MODULE_ID), cmd.getModuleIds());
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
	}


}
