package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.UnsubscribeButton;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.json.rpc.JsonFileReader;
import com.smartdevicelink.test.utils.JsonUtils;

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
	
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			UnsubscribeButton cmd = new UnsubscribeButton(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals("Button name doesn't match input name", 
					JsonUtils.readStringFromJsonObject(parameters, UnsubscribeButton.KEY_BUTTON_NAME), cmd.getButtonName().toString());
			
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }
	
}
