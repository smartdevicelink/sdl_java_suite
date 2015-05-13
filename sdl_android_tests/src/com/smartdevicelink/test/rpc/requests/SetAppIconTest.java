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
import com.smartdevicelink.test.json.rpc.JsonFileReader;

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
	
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SetAppIcon cmd = new SetAppIcon(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals("File name doesn't match input name", JsonUtils.readStringFromJsonObject(parameters, SetAppIcon.KEY_SDL_FILE_NAME), cmd.getSdlFileName());
			
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }

}
