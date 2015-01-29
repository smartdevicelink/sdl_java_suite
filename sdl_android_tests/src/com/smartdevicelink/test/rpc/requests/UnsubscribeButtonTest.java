package com.smartdevicelink.test.rpc.requests;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.UnsubscribeButton;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.test.BaseRpcTests;

public class UnsubscribeButtonTest extends BaseRpcTests {

	private static final ButtonName BUTTON = ButtonName.OK;
	
	@Override
	protected RPCMessage createMessage() {
		UnsubscribeButton msg = new UnsubscribeButton();

		msg.setButtonName(BUTTON);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.UNSUBSCRIBE_BUTTON;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(UnsubscribeButton.KEY_BUTTON_NAME, BUTTON);
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testButton() {
		ButtonName copy = ( (UnsubscribeButton) msg ).getButtonName();
		
		assertEquals("Data didn't match input data.", BUTTON, copy);
	}

	public void testNull() {
		UnsubscribeButton msg = new UnsubscribeButton();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("Button wasn't set, but getter method returned an object.", msg.getButtonName());
	}
}
