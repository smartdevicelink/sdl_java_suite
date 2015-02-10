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
import com.smartdevicelink.test.json.rpc.JsonFileReader;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class SetMediaClockTimerTest extends BaseRpcTests {

	private static final StartTime START_TIME = new StartTime();
	private static final StartTime END_TIME = new StartTime();
	private static final UpdateMode UPDATE_MODE = UpdateMode.RESUME;

	@Override
	protected RPCMessage createMessage() {
		SetMediaClockTimer msg = new SetMediaClockTimer();

		createCustomObjects();
		
		msg.setStartTime(START_TIME);
		msg.setEndTime(END_TIME);
		msg.setUpdateMode(UPDATE_MODE);

		return msg;
	}
	
	public void createCustomObjects () {
		START_TIME.setHours(0);
		START_TIME.setMinutes(0);
		START_TIME.setSeconds(0);
		
		END_TIME.setHours(0);
		END_TIME.setMinutes(0);
		END_TIME.setSeconds(0);
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SET_MEDIA_CLOCK_TIMER;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();
		JSONObject start = new JSONObject(), end = new JSONObject();

		try {
			start.put(StartTime.KEY_HOURS, START_TIME.getHours());
			start.put(StartTime.KEY_MINUTES, START_TIME.getMinutes());
			start.put(StartTime.KEY_SECONDS, START_TIME.getSeconds());
			
			end.put(StartTime.KEY_HOURS, END_TIME.getHours());
			end.put(StartTime.KEY_MINUTES, END_TIME.getMinutes());
			end.put(StartTime.KEY_SECONDS, END_TIME.getSeconds());
						
			result.put(SetMediaClockTimer.KEY_START_TIME, start);
			result.put(SetMediaClockTimer.KEY_END_TIME, end);
			result.put(SetMediaClockTimer.KEY_UPDATE_MODE, UPDATE_MODE);
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testStartTime() {
		StartTime copy = ( (SetMediaClockTimer) msg ).getStartTime();
		
		assertNotSame("End time was not defensive copied", START_TIME, copy);
		assertTrue("Input value didn't match expected value.", Validator.validateStartTime(START_TIME, copy));
	}
	
	public void testEndTime() {
		StartTime copy = ( (SetMediaClockTimer) msg ).getEndTime();
		
		assertNotSame("End time was not defensive copied", END_TIME, copy);
		assertTrue("Input value didn't match expected value.", Validator.validateStartTime(END_TIME, copy));
	}
	
	public void testUpdateMode () {
		UpdateMode copy = ( (SetMediaClockTimer) msg ).getUpdateMode();
		
		assertEquals("Data didn't match input data.", UPDATE_MODE, copy);
	}

	public void testNull() {
		SetMediaClockTimer msg = new SetMediaClockTimer();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("Start time wasn't set, but getter method returned an object.", msg.getStartTime());
		assertNull("End time wasn't set, but getter method returned an object.", msg.getEndTime());
		assertNull("Update mode wasn't set, but getter method returned an object.", msg.getUpdateMode());
	}
	
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SetMediaClockTimer cmd = new SetMediaClockTimer(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			JSONObject startTime = JsonUtils.readJsonObjectFromJsonObject(parameters, SetMediaClockTimer.KEY_START_TIME);
			StartTime referenceStartTime = new StartTime(JsonRPCMarshaller.deserializeJSONObject(startTime));
			assertTrue("Start time doesn't match expected time", Validator.validateStartTime(referenceStartTime, cmd.getStartTime()));
			
			JSONObject endTime = JsonUtils.readJsonObjectFromJsonObject(parameters, SetMediaClockTimer.KEY_END_TIME);
			StartTime referenceEndTime = new StartTime(JsonRPCMarshaller.deserializeJSONObject(endTime));
			assertTrue("End time doesn't match expected time", Validator.validateStartTime(referenceEndTime, cmd.getEndTime()));
			
			assertEquals("Update mode doesn't match input mode", 
					JsonUtils.readStringFromJsonObject(parameters, SetMediaClockTimer.KEY_UPDATE_MODE), cmd.getUpdateMode().toString());
			
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }	

}
