package com.smartdevicelink.test.rpc.requests;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SetAppIcon;
import com.smartdevicelink.test.BaseRpcTests;

public class SetAppIconTest extends BaseRpcTests {
	
	private static final String FILE_NAME = "fileName";

	@Override
	protected RPCMessage createMessage() {
		SetAppIcon msg = new SetAppIcon();

		msg.setSdlFileName(FILE_NAME);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SET_APP_ICON;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(SetAppIcon.KEY_SDL_FILE_NAME, FILE_NAME);
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testFileName() {
		String copy = ( (SetAppIcon) msg ).getSdlFileName();
		
		assertEquals("Data didn't match input data.", FILE_NAME, copy);
	}

	public void testNull() {
		SetAppIcon msg = new SetAppIcon();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("File name wasn't set, but getter method returned an object.", msg.getSdlFileName());
	}

}
