package com.smartdevicelink.test.rpc.notifications;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AppServiceData;
import com.smartdevicelink.proxy.rpc.OnAppServiceData;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.TestValues;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.fail;


/**
 * This is a unit test class for the SmartDeviceLink library project class :
 * {@link com.smartdevicelink.proxy.rpc.OnAppServiceData}
 */
public class OnAppServiceDataTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage(){
		OnAppServiceData msg = new OnAppServiceData();

		msg.setServiceData(TestValues.GENERAL_APPSERVICEDATA);

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
			result.put(OnAppServiceData.KEY_SERVICE_DATA, TestValues.GENERAL_APPSERVICEDATA.serializeJSON());
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
		AppServiceData cmdId = ( (OnAppServiceData) msg ).getServiceData();

		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_APPSERVICEDATA, cmdId);

		// Invalid/Null Tests
		OnAppServiceData msg = new OnAppServiceData();
		assertNotNull(TestValues.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(TestValues.NULL, msg.getServiceData());

		// test constructor with param
		msg = new OnAppServiceData(TestValues.GENERAL_APPSERVICEDATA);
		AppServiceData serviceData = msg.getServiceData();
		assertEquals(serviceData, TestValues.GENERAL_APPSERVICEDATA);
	}
}
