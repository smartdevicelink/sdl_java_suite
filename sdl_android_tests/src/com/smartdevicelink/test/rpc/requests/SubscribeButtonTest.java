package com.smartdevicelink.test.rpc.requests;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SubscribeButton;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.test.BaseRpcTests;

public class SubscribeButtonTest extends BaseRpcTests {

	private static final ButtonName BUTTON = ButtonName.OK;
	
	@Override
	protected RPCMessage createMessage() {
		SubscribeButton msg = new SubscribeButton();

		msg.setButtonName(BUTTON);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SUBSCRIBE_BUTTON;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(SubscribeButton.KEY_BUTTON_NAME, BUTTON);
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testButtonName() {
		ButtonName copy = ( (SubscribeButton) msg ).getButtonName();
		
		assertEquals("Data didn't match input data.", BUTTON, copy);
	}

	public void testNull() {
		SubscribeButton msg = new SubscribeButton();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("Button name wasn't set, but getter method returned an object.", msg.getButtonName());
	}

}
