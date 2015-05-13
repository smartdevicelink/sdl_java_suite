package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SetDisplayLayout;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

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
	
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SetDisplayLayout cmd = new SetDisplayLayout(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals("Display layout doesn't match input layout", 
					JsonUtils.readStringFromJsonObject(parameters, SetDisplayLayout.KEY_DISPLAY_LAYOUT), cmd.getDisplayLayout());
			
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }

}
