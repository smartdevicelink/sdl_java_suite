package com.smartdevicelink.test.rpc.requests;

import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ReadDID;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.utils.JsonUtils;

public class ReadDIDTest extends BaseRpcTests {
	
	private static final Integer ECU_NAME = -1;
	private static final List<Integer> DID_LOCATIONS = Arrays.asList(new Integer[]{-1,-2});
	
	@Override
	protected RPCMessage createMessage() {
		ReadDID msg = new ReadDID();

		msg.setEcuName(ECU_NAME);
		msg.setDidLocation(DID_LOCATIONS);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.READ_DID;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(ReadDID.KEY_ECU_NAME, ECU_NAME);
			result.put(ReadDID.KEY_DID_LOCATION, JsonUtils.createJsonArray(DID_LOCATIONS));
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testEcuName() {
		Integer copy = ( (ReadDID) msg ).getEcuName();
		
		assertEquals("Data didn't match input data.", ECU_NAME, copy);
	}
	
	public void testDidLocation () {
		List<Integer> copy = ( (ReadDID) msg ).getDidLocation();
		
		assertEquals("Data didn't match input data.", DID_LOCATIONS, copy);
	}

	public void testNull() {
		ReadDID msg = new ReadDID();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("Ecu name wasn't set, but getter method returned an object.", msg.getEcuName());
		assertNull("Did locations wasn't set, but getter method returned an object.", msg.getDidLocation());
	}

}
