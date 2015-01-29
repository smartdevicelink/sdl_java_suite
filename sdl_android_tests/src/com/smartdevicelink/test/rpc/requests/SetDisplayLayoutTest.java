package com.smartdevicelink.test.rpc.requests;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SetDisplayLayout;
import com.smartdevicelink.test.BaseRpcTests;

public class SetDisplayLayoutTest extends BaseRpcTests {

	private static final String DISPLAY_LAYOUT = "displayLayout";
	
	@Override
	protected RPCMessage createMessage() {
		SetDisplayLayout msg = new SetDisplayLayout();

		msg.setDisplayLayout(DISPLAY_LAYOUT);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SET_DISPLAY_LAYOUT;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(SetDisplayLayout.KEY_DISPLAY_LAYOUT, DISPLAY_LAYOUT);
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testDisplayLayout() {
		String copy = ( (SetDisplayLayout) msg ).getDisplayLayout();
		
		assertEquals("Data didn't match input data.", DISPLAY_LAYOUT, copy);
	}

	public void testNull() {
		SetDisplayLayout msg = new SetDisplayLayout();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("Display layout wasn't set, but getter method returned an object.", msg.getDisplayLayout());
	}

}
