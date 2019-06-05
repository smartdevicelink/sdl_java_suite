package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SetMediaClockTimer;
import com.smartdevicelink.proxy.rpc.StartTime;
import com.smartdevicelink.proxy.rpc.enums.AudioStreamingIndicator;
import com.smartdevicelink.proxy.rpc.enums.UpdateMode;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.SetMediaClockTimer}
 */
public class SetMediaClockTimerTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		SetMediaClockTimer msg = new SetMediaClockTimer();

		msg.setStartTime(Test.GENERAL_STARTTIME);
		msg.setEndTime(Test.GENERAL_STARTTIME);
		msg.setUpdateMode(Test.GENERAL_UPDATEMODE);
		msg.setAudioStreamingIndicator(Test.GENERAL_AUDIO_STREAMING_INDICATOR);

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
			result.put(SetMediaClockTimer.KEY_AUDIO_STREAMING_INDICATOR, Test.GENERAL_AUDIO_STREAMING_INDICATOR);
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
		AudioStreamingIndicator testAudioStreamingIndicator = ( (SetMediaClockTimer) msg ).getAudioStreamingIndicator();
		
		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_UPDATEMODE, testUpdateMode);
		assertEquals(Test.MATCH, Test.GENERAL_AUDIO_STREAMING_INDICATOR, testAudioStreamingIndicator);
		assertTrue(Test.TRUE, Validator.validateStartTime(Test.GENERAL_STARTTIME, testStartTime));
		assertTrue(Test.TRUE, Validator.validateStartTime(Test.GENERAL_STARTTIME, testEndTime));
		
		// Invalid/Null Tests
		SetMediaClockTimer msg = new SetMediaClockTimer();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getStartTime());
		assertNull(Test.NULL, msg.getEndTime());
		assertNull(Test.NULL, msg.getUpdateMode());
		assertNull(Test.NULL, msg.getAudioStreamingIndicator());
	}

	/**
	 * Test static initializers
	 */
	public void testInitializers(){
		Integer timeInterval1 = 5000;
		StartTime startTime1 = new StartTime(timeInterval1);
		Integer timeInterval2 = 7000;
		StartTime startTime2 = new StartTime(timeInterval2);
		SetMediaClockTimer msg;

		msg = SetMediaClockTimer.countUpFromStartTimeInterval(timeInterval1, timeInterval2, Test.GENERAL_AUDIO_STREAMING_INDICATOR);
		assertEquals(Test.MATCH, msg.getUpdateMode(), UpdateMode.COUNTUP);
		assertTrue(Test.TRUE, Validator.validateStartTime(startTime1, msg.getStartTime()));
		assertTrue(Test.TRUE, Validator.validateStartTime(startTime2, msg.getEndTime()));
		assertEquals(Test.MATCH, Test.GENERAL_AUDIO_STREAMING_INDICATOR, msg.getAudioStreamingIndicator());

		msg = SetMediaClockTimer.countUpFromStartTime(startTime1, startTime2, Test.GENERAL_AUDIO_STREAMING_INDICATOR);
		assertEquals(Test.MATCH, msg.getUpdateMode(), UpdateMode.COUNTUP);
		assertTrue(Test.TRUE, Validator.validateStartTime(startTime1, msg.getStartTime()));
		assertTrue(Test.TRUE, Validator.validateStartTime(startTime2, msg.getEndTime()));
		assertEquals(Test.MATCH, Test.GENERAL_AUDIO_STREAMING_INDICATOR, msg.getAudioStreamingIndicator());

		msg = SetMediaClockTimer.countDownFromStartTimeInterval(timeInterval1, timeInterval2, Test.GENERAL_AUDIO_STREAMING_INDICATOR);
		assertEquals(Test.MATCH, msg.getUpdateMode(), UpdateMode.COUNTDOWN);
		assertTrue(Test.TRUE, Validator.validateStartTime(startTime1, msg.getStartTime()));
		assertTrue(Test.TRUE, Validator.validateStartTime(startTime2, msg.getEndTime()));
		assertEquals(Test.MATCH, Test.GENERAL_AUDIO_STREAMING_INDICATOR, msg.getAudioStreamingIndicator());

		msg = SetMediaClockTimer.countDownFromStartTime(startTime1, startTime2, Test.GENERAL_AUDIO_STREAMING_INDICATOR);
		assertEquals(Test.MATCH, msg.getUpdateMode(), UpdateMode.COUNTDOWN);
		assertTrue(Test.TRUE, Validator.validateStartTime(startTime1, msg.getStartTime()));
		assertTrue(Test.TRUE, Validator.validateStartTime(startTime2, msg.getEndTime()));
		assertEquals(Test.MATCH, Test.GENERAL_AUDIO_STREAMING_INDICATOR, msg.getAudioStreamingIndicator());

		msg = SetMediaClockTimer.pauseWithPlayPauseIndicator(Test.GENERAL_AUDIO_STREAMING_INDICATOR);
		assertEquals(Test.MATCH, msg.getUpdateMode(), UpdateMode.PAUSE);
		assertNull(Test.NULL, msg.getStartTime());
		assertNull(Test.NULL, msg.getEndTime());
		assertEquals(Test.MATCH, Test.GENERAL_AUDIO_STREAMING_INDICATOR, msg.getAudioStreamingIndicator());

		msg = SetMediaClockTimer.updatePauseWithNewStartTimeInterval(timeInterval1, timeInterval2, Test.GENERAL_AUDIO_STREAMING_INDICATOR);
		assertEquals(Test.MATCH, msg.getUpdateMode(), UpdateMode.PAUSE);
		assertTrue(Test.TRUE, Validator.validateStartTime(startTime1, msg.getStartTime()));
		assertTrue(Test.TRUE, Validator.validateStartTime(startTime2, msg.getEndTime()));
		assertEquals(Test.MATCH, Test.GENERAL_AUDIO_STREAMING_INDICATOR, msg.getAudioStreamingIndicator());

		msg = SetMediaClockTimer.updatePauseWithNewStartTime(startTime1, startTime2, Test.GENERAL_AUDIO_STREAMING_INDICATOR);
		assertEquals(Test.MATCH, msg.getUpdateMode(), UpdateMode.PAUSE);
		assertTrue(Test.TRUE, Validator.validateStartTime(startTime1, msg.getStartTime()));
		assertTrue(Test.TRUE, Validator.validateStartTime(startTime2, msg.getEndTime()));
		assertEquals(Test.MATCH, Test.GENERAL_AUDIO_STREAMING_INDICATOR, msg.getAudioStreamingIndicator());

		msg = SetMediaClockTimer.resumeWithPlayPauseIndicator(Test.GENERAL_AUDIO_STREAMING_INDICATOR);
		assertEquals(Test.MATCH, msg.getUpdateMode(), UpdateMode.RESUME);
		assertNull(Test.NULL, msg.getStartTime());
		assertNull(Test.NULL, msg.getEndTime());
		assertEquals(Test.MATCH, Test.GENERAL_AUDIO_STREAMING_INDICATOR, msg.getAudioStreamingIndicator());

		msg = SetMediaClockTimer.clearWithPlayPauseIndicator(Test.GENERAL_AUDIO_STREAMING_INDICATOR);
		assertEquals(Test.MATCH, msg.getUpdateMode(), UpdateMode.CLEAR);
		assertNull(Test.NULL, msg.getStartTime());
		assertNull(Test.NULL, msg.getEndTime());
		assertEquals(Test.MATCH, Test.GENERAL_AUDIO_STREAMING_INDICATOR, msg.getAudioStreamingIndicator());
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
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, SetMediaClockTimer.KEY_AUDIO_STREAMING_INDICATOR), cmd.getAudioStreamingIndicator().toString());
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}    	
    }
}