package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SetMediaClockTimer;
import com.smartdevicelink.proxy.rpc.StartTime;
import com.smartdevicelink.proxy.rpc.enums.UpdateMode;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.SetMediaClockTimer}
 */
public class SetMediaClockTimerTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		SetMediaClockTimer msg = new SetMediaClockTimer();

		msg.setStartTime(Test.GENERAL_STARTTIME);
		msg.setEndTime(Test.GENERAL_STARTTIME);
		msg.setUpdateMode(Test.GENERAL_UPDATEMODE);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SET_MEDIA_CLOCK_TIMER.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(SetMediaClockTimer.KEY_START_TIME, Test.JSON_STARTTIME);
			result.put(SetMediaClockTimer.KEY_END_TIME, Test.JSON_STARTTIME);
			result.put(SetMediaClockTimer.KEY_UPDATE_MODE, Test.GENERAL_UPDATEMODE);			
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}

		return result;
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
    public void testRpcValues () {  
    	// Test Values
		StartTime  testStartTime  = ( (SetMediaClockTimer) msg ).getStartTime();
		StartTime  testEndTime    = ( (SetMediaClockTimer) msg ).getEndTime();
		UpdateMode testUpdateMode = ( (SetMediaClockTimer) msg ).getUpdateMode();
		
		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_UPDATEMODE, testUpdateMode);
		assertTrue(Test.TRUE, Validator.validateStartTime(Test.GENERAL_STARTTIME, testStartTime));
		assertTrue(Test.TRUE, Validator.validateStartTime(Test.GENERAL_STARTTIME, testEndTime));
		
		// Invalid/Null Tests
		SetMediaClockTimer msg = new SetMediaClockTimer();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getStartTime());
		assertNull(Test.NULL, msg.getEndTime());
		assertNull(Test.NULL, msg.getUpdateMode());
	}
	
	/**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SetMediaClockTimer cmd = new SetMediaClockTimer(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			JSONObject startTime = JsonUtils.readJsonObjectFromJsonObject(parameters, SetMediaClockTimer.KEY_START_TIME);
			StartTime referenceStartTime = new StartTime(JsonRPCMarshaller.deserializeJSONObject(startTime));
			assertTrue(Test.TRUE, Validator.validateStartTime(referenceStartTime, cmd.getStartTime()));
			
			JSONObject endTime = JsonUtils.readJsonObjectFromJsonObject(parameters, SetMediaClockTimer.KEY_END_TIME);
			StartTime referenceEndTime = new StartTime(JsonRPCMarshaller.deserializeJSONObject(endTime));
			assertTrue(Test.TRUE, Validator.validateStartTime(referenceEndTime, cmd.getEndTime()));
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, SetMediaClockTimer.KEY_UPDATE_MODE), cmd.getUpdateMode().toString());
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}    	
    }
}