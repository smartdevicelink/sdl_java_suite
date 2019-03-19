package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AppServiceData;
import com.smartdevicelink.proxy.rpc.OnAppServiceData;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.Test;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.OnAppServiceData}
 */
public class OnAppServiceDataTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage(){
		OnAppServiceData msg = new OnAppServiceData();

		msg.setServiceData(Test.GENERAL_APPSERVICEDATA);

		return msg;
	}

	@Override
	protected String getMessageType(){
		return RPCMessage.KEY_NOTIFICATION;
	}

	@Override
	protected String getCommandType(){
		return FunctionID.ON_APP_SERVICE_DATA.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion){
		JSONObject result = new JSONObject();

		try{
			result.put(OnAppServiceData.KEY_SERVICE_DATA, Test.GENERAL_APPSERVICEDATA.serializeJSON());
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
		AppServiceData cmdId = ( (OnAppServiceData) msg ).getServiceData();

		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_APPSERVICEDATA, cmdId);

		// Invalid/Null Tests
		OnAppServiceData msg = new OnAppServiceData();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getServiceData());

		// test constructor with param
		msg = new OnAppServiceData(Test.GENERAL_APPSERVICEDATA);
		AppServiceData serviceData = msg.getServiceData();
		assertEquals(serviceData, Test.GENERAL_APPSERVICEDATA);
	}
}
