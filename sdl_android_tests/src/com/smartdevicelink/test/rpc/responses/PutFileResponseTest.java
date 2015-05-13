package com.smartdevicelink.test.rpc.responses;

import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.PutFileResponse;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

public class PutFileResponseTest extends BaseRpcTests {

	private static final Integer SPACE_AVAILABLE = -1;
	
	@Override
	protected RPCMessage createMessage() {
		PutFileResponse msg = new PutFileResponse();

		msg.setSpaceAvailable(SPACE_AVAILABLE);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_RESPONSE;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.PUT_FILE;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(PutFileResponse.KEY_SPACE_AVAILABLE, SPACE_AVAILABLE);
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testSpaceAvailable() {
		Integer copy = ( (PutFileResponse) msg ).getSpaceAvailable();
		
		assertEquals("Data didn't match input data.", SPACE_AVAILABLE, copy);
	}

	public void testNull() {
		PutFileResponse msg = new PutFileResponse();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("Space available wasn't set, but getter method returned an object.",  msg.getSpaceAvailable());
	}
	
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			PutFileResponse cmd = new PutFileResponse(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals("Space available doesn't match input space available", 
					JsonUtils.readIntegerFromJsonObject(parameters, PutFileResponse.KEY_SPACE_AVAILABLE), cmd.getSpaceAvailable());
			
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }

}
