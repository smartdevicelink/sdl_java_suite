package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SetAppIcon;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.SetAppIcon}
 */
public class SetAppIconTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		SetAppIcon msg = new SetAppIcon();

		msg.setSdlFileName(Test.GENERAL_STRING);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SET_APP_ICON.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(SetAppIcon.KEY_SDL_FILE_NAME, Test.GENERAL_STRING);			
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}

		return result;
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {    	
    	// Test Values
		String copy = ( (SetAppIcon) msg ).getSdlFileName();
		
		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_STRING, copy);
	
		// Invalid/Null Tests
		SetAppIcon msg = new SetAppIcon();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getSdlFileName());
	}

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SetAppIcon cmd = new SetAppIcon(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, SetAppIcon.KEY_SDL_FILE_NAME), cmd.getSdlFileName());
			
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}    	
    }
}