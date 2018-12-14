package com.smartdevicelink.test.rpc.responses;

import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SliderResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.SliderResponse}
 */
public class SliderResponseTest extends BaseRpcTests {
	
	@Override
	protected RPCMessage createMessage() {
		SliderResponse msg = new SliderResponse();

		msg.setSliderPosition(Test.GENERAL_INT);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_RESPONSE;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SLIDER.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(SliderResponse.KEY_SLIDER_POSITION, Test.GENERAL_INT);
			
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}

		return result;
	}

	public void testPosition() {
		Integer copy = ( (SliderResponse) msg ).getSliderPosition();
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, copy);
	}

	public void testNull() {
		SliderResponse msg = new SliderResponse();
		assertNotNull(Test.NOT_NULL, msg);

		testNullBase(msg);

		assertNull(Test.NULL, msg.getSliderPosition());
	}

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SliderResponse cmd = new SliderResponse(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, SliderResponse.KEY_SLIDER_POSITION), cmd.getSliderPosition());
		} catch (JSONException e) {
			e.printStackTrace();
		}    	
    }
}