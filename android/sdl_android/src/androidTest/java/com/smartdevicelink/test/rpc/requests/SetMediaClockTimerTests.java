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
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.Hashtable;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.SetMediaClockTimer}
 */
public class SetMediaClockTimerTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		SetMediaClockTimer msg = new SetMediaClockTimer();

		msg.setStartTime(TestValues.GENERAL_STARTTIME);
		msg.setEndTime(TestValues.GENERAL_STARTTIME);
		msg.setUpdateMode(TestValues.GENERAL_UPDATEMODE);
		msg.setAudioStreamingIndicator(TestValues.GENERAL_AUDIO_STREAMING_INDICATOR);

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
			result.put(SetMediaClockTimer.KEY_START_TIME, TestValues.JSON_STARTTIME);
			result.put(SetMediaClockTimer.KEY_END_TIME, TestValues.JSON_STARTTIME);
			result.put(SetMediaClockTimer.KEY_UPDATE_MODE, TestValues.GENERAL_UPDATEMODE);
			result.put(SetMediaClockTimer.KEY_AUDIO_STREAMING_INDICATOR, TestValues.GENERAL_AUDIO_STREAMING_INDICATOR);
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}

		return result;
	}

	/**
	 * Tests the expected values of the RPC message.
	 */
	@Test
    public void testRpcValues () {  
    	// Test Values
		StartTime  testStartTime  = ( (SetMediaClockTimer) msg ).getStartTime();
		StartTime  testEndTime    = ( (SetMediaClockTimer) msg ).getEndTime();
		UpdateMode testUpdateMode = ( (SetMediaClockTimer) msg ).getUpdateMode();
		AudioStreamingIndicator testAudioStreamingIndicator = ( (SetMediaClockTimer) msg ).getAudioStreamingIndicator();
		
		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_UPDATEMODE, testUpdateMode);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_AUDIO_STREAMING_INDICATOR, testAudioStreamingIndicator);
		assertTrue(TestValues.TRUE, Validator.validateStartTime(TestValues.GENERAL_STARTTIME, testStartTime));
		assertTrue(TestValues.TRUE, Validator.validateStartTime(TestValues.GENERAL_STARTTIME, testEndTime));
		
		// Invalid/Null Tests
		SetMediaClockTimer msg = new SetMediaClockTimer();
		assertNotNull(TestValues.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(TestValues.NULL, msg.getStartTime());
		assertNull(TestValues.NULL, msg.getEndTime());
		assertNull(TestValues.NULL, msg.getUpdateMode());
		assertNull(TestValues.NULL, msg.getAudioStreamingIndicator());
	}

	/**
	 * Test static initializers
	 */
	@Test
	public void testInitializers(){
		Integer timeInterval1 = 5000;
		StartTime startTime1 = new StartTime(timeInterval1);
		Integer timeInterval2 = 7000;
		StartTime startTime2 = new StartTime(timeInterval2);
		SetMediaClockTimer msg;

		msg = SetMediaClockTimer.countUpFromStartTimeInterval(timeInterval1, timeInterval2, TestValues.GENERAL_AUDIO_STREAMING_INDICATOR);
		assertEquals(TestValues.MATCH, msg.getUpdateMode(), UpdateMode.COUNTUP);
		assertTrue(TestValues.TRUE, Validator.validateStartTime(startTime1, msg.getStartTime()));
		assertTrue(TestValues.TRUE, Validator.validateStartTime(startTime2, msg.getEndTime()));
		assertEquals(TestValues.MATCH, TestValues.GENERAL_AUDIO_STREAMING_INDICATOR, msg.getAudioStreamingIndicator());

		msg = SetMediaClockTimer.countUpFromStartTime(startTime1, startTime2, TestValues.GENERAL_AUDIO_STREAMING_INDICATOR);
		assertEquals(TestValues.MATCH, msg.getUpdateMode(), UpdateMode.COUNTUP);
		assertTrue(TestValues.TRUE, Validator.validateStartTime(startTime1, msg.getStartTime()));
		assertTrue(TestValues.TRUE, Validator.validateStartTime(startTime2, msg.getEndTime()));
		assertEquals(TestValues.MATCH, TestValues.GENERAL_AUDIO_STREAMING_INDICATOR, msg.getAudioStreamingIndicator());

		msg = SetMediaClockTimer.countDownFromStartTimeInterval(timeInterval1, timeInterval2, TestValues.GENERAL_AUDIO_STREAMING_INDICATOR);
		assertEquals(TestValues.MATCH, msg.getUpdateMode(), UpdateMode.COUNTDOWN);
		assertTrue(TestValues.TRUE, Validator.validateStartTime(startTime1, msg.getStartTime()));
		assertTrue(TestValues.TRUE, Validator.validateStartTime(startTime2, msg.getEndTime()));
		assertEquals(TestValues.MATCH, TestValues.GENERAL_AUDIO_STREAMING_INDICATOR, msg.getAudioStreamingIndicator());

		msg = SetMediaClockTimer.countDownFromStartTime(startTime1, startTime2, TestValues.GENERAL_AUDIO_STREAMING_INDICATOR);
		assertEquals(TestValues.MATCH, msg.getUpdateMode(), UpdateMode.COUNTDOWN);
		assertTrue(TestValues.TRUE, Validator.validateStartTime(startTime1, msg.getStartTime()));
		assertTrue(TestValues.TRUE, Validator.validateStartTime(startTime2, msg.getEndTime()));
		assertEquals(TestValues.MATCH, TestValues.GENERAL_AUDIO_STREAMING_INDICATOR, msg.getAudioStreamingIndicator());

		msg = SetMediaClockTimer.pauseWithPlayPauseIndicator(TestValues.GENERAL_AUDIO_STREAMING_INDICATOR);
		assertEquals(TestValues.MATCH, msg.getUpdateMode(), UpdateMode.PAUSE);
		assertNull(TestValues.NULL, msg.getStartTime());
		assertNull(TestValues.NULL, msg.getEndTime());
		assertEquals(TestValues.MATCH, TestValues.GENERAL_AUDIO_STREAMING_INDICATOR, msg.getAudioStreamingIndicator());

		msg = SetMediaClockTimer.updatePauseWithNewStartTimeInterval(timeInterval1, timeInterval2, TestValues.GENERAL_AUDIO_STREAMING_INDICATOR);
		assertEquals(TestValues.MATCH, msg.getUpdateMode(), UpdateMode.PAUSE);
		assertTrue(TestValues.TRUE, Validator.validateStartTime(startTime1, msg.getStartTime()));
		assertTrue(TestValues.TRUE, Validator.validateStartTime(startTime2, msg.getEndTime()));
		assertEquals(TestValues.MATCH, TestValues.GENERAL_AUDIO_STREAMING_INDICATOR, msg.getAudioStreamingIndicator());

		msg = SetMediaClockTimer.updatePauseWithNewStartTime(startTime1, startTime2, TestValues.GENERAL_AUDIO_STREAMING_INDICATOR);
		assertEquals(TestValues.MATCH, msg.getUpdateMode(), UpdateMode.PAUSE);
		assertTrue(TestValues.TRUE, Validator.validateStartTime(startTime1, msg.getStartTime()));
		assertTrue(TestValues.TRUE, Validator.validateStartTime(startTime2, msg.getEndTime()));
		assertEquals(TestValues.MATCH, TestValues.GENERAL_AUDIO_STREAMING_INDICATOR, msg.getAudioStreamingIndicator());

		msg = SetMediaClockTimer.resumeWithPlayPauseIndicator(TestValues.GENERAL_AUDIO_STREAMING_INDICATOR);
		assertEquals(TestValues.MATCH, msg.getUpdateMode(), UpdateMode.RESUME);
		assertNull(TestValues.NULL, msg.getStartTime());
		assertNull(TestValues.NULL, msg.getEndTime());
		assertEquals(TestValues.MATCH, TestValues.GENERAL_AUDIO_STREAMING_INDICATOR, msg.getAudioStreamingIndicator());

		msg = SetMediaClockTimer.clearWithPlayPauseIndicator(TestValues.GENERAL_AUDIO_STREAMING_INDICATOR);
		assertEquals(TestValues.MATCH, msg.getUpdateMode(), UpdateMode.CLEAR);
		assertNull(TestValues.NULL, msg.getStartTime());
		assertNull(TestValues.NULL, msg.getEndTime());
		assertEquals(TestValues.MATCH, TestValues.GENERAL_AUDIO_STREAMING_INDICATOR, msg.getAudioStreamingIndicator());
	}
	
	/**
     * Tests a valid JSON construction of this RPC message.
     */
	@Test
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getInstrumentation().getTargetContext(), getCommandType(), getMessageType());
    	assertNotNull(TestValues.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SetMediaClockTimer cmd = new SetMediaClockTimer(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			JSONObject startTime = JsonUtils.readJsonObjectFromJsonObject(parameters, SetMediaClockTimer.KEY_START_TIME);
			StartTime referenceStartTime = new StartTime(JsonRPCMarshaller.deserializeJSONObject(startTime));
			assertTrue(TestValues.TRUE, Validator.validateStartTime(referenceStartTime, cmd.getStartTime()));
			
			JSONObject endTime = JsonUtils.readJsonObjectFromJsonObject(parameters, SetMediaClockTimer.KEY_END_TIME);
			StartTime referenceEndTime = new StartTime(JsonRPCMarshaller.deserializeJSONObject(endTime));
			assertTrue(TestValues.TRUE, Validator.validateStartTime(referenceEndTime, cmd.getEndTime()));
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, SetMediaClockTimer.KEY_UPDATE_MODE), cmd.getUpdateMode().toString());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, SetMediaClockTimer.KEY_AUDIO_STREAMING_INDICATOR), cmd.getAudioStreamingIndicator().toString());
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}    	
    }
}