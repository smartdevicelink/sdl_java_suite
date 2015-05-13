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
import com.smartdevicelink.test.json.rpc.JsonFileReader;

public class SliderResponseTest extends BaseRpcTests {

	private static final Integer POSITION = 0;
	
	@Override
	protected RPCMessage createMessage() {
		SliderResponse msg = new SliderResponse();

		msg.setSliderPosition(POSITION);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_RESPONSE;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SLIDER;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(SliderResponse.KEY_SLIDER_POSITION, POSITION);
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testPosition() {
		Integer copy = ( (SliderResponse) msg ).getSliderPosition();
		assertEquals("Data didn't match input data.", POSITION, copy);
	}

	public void testNull() {
		SliderResponse msg = new SliderResponse();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("Position wasn't set, but getter method returned an object.", msg.getSliderPosition());
	}
	
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SliderResponse cmd = new SliderResponse(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals("Slider position doesn't match input position", 
					JsonUtils.readIntegerFromJsonObject(parameters, SliderResponse.KEY_SLIDER_POSITION), cmd.getSliderPosition());

		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }

}
