package com.smartdevicelink.test.rpc.responses;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SendLocationResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.SendLocationResponse}
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
	@Test
    public void testRpcValues () {   
    	// Invalid/Null Tests
        SendLocationResponse msg = new SendLocationResponse();
        assertNotNull(TestValues.NOT_NULL, msg);
        testNullBase(msg);
	}

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    @Test
	public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getInstrumentation().getTargetContext(), getCommandType(), getMessageType());
    	assertNotNull(TestValues.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SendLocationResponse cmd = new SendLocationResponse(hash);
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());
		} catch (JSONException e) {
			e.printStackTrace();
		}    	
    }
}