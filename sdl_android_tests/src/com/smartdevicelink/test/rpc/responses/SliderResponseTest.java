package com.smartdevicelink.test.rpc.responses;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SliderResponse;
import com.smartdevicelink.test.BaseRpcTests;

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

}
