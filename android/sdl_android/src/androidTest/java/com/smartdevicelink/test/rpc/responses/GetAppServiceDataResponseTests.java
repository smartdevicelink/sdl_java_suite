package com.smartdevicelink.test.rpc.responses;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AppServiceData;
import com.smartdevicelink.proxy.rpc.GetAppServiceDataResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.GetAppServiceDataResponse}
 */
public class GetAppServiceDataResponseTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage(){
		return new GetAppServiceDataResponse();
	}

	@Override
	protected String getMessageType(){
		return RPCMessage.KEY_RESPONSE;
	}

	@Override
	protected String getCommandType(){
		return FunctionID.GET_APP_SERVICE_DATA.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion){
		return new JSONObject();
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	public void testRpcValues () {
		// Invalid/Null Tests
		GetAppServiceDataResponse msg = new GetAppServiceDataResponse();
		msg.setServiceData(Test.GENERAL_APPSERVICEDATA);
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		// test getter
		AppServiceData serviceData = msg.getServiceData();
		assertEquals(Test.GENERAL_APPSERVICEDATA, serviceData);
	}

	/**
	 * Tests a valid JSON construction of this RPC message.
	 */
	public void testJsonConstructor () {
		JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
		assertNotNull(Test.NOT_NULL, commandJson);

		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			GetAppServiceDataResponse cmd = new GetAppServiceDataResponse(hash);

			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);

			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}