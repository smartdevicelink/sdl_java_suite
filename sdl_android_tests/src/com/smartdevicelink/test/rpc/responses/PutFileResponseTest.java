package com.smartdevicelink.test.rpc.responses;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.PutFileResponse;
import com.smartdevicelink.test.BaseRpcTests;

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

}
