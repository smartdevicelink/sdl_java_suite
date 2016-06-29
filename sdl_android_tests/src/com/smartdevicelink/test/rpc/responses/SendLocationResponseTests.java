package com.smartdevicelink.test.rpc.responses;

import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SendLocationResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.SendLocaitonResponse}
 */
public class SendLocationResponseTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		SendLocationResponse msg = new SendLocationResponse();
		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_RESPONSE;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SEND_LOCATION.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		return new JSONObject();
	}
	
	/**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {   
    	// Invalid/Null Tests
        SendLocationResponse msg = new SendLocationResponse();
        assertNotNull(Test.NOT_NULL, msg);
        testNullBase(msg);
	}

    /**
     * Tests a valid JSON construction of this RPC message.
     */
	public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SendLocationResponse cmd = new SendLocationResponse(hash);
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());		
		} catch (JSONException e) {
			e.printStackTrace();
		}    	
    }
}