package com.smartdevicelink.test.rpc.requests;

import java.util.Hashtable;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Slider;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.Slider}
 */
public class SliderTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		Slider msg = new Slider();

		msg.setNumTicks(Test.GENERAL_INT);
		msg.setPosition(Test.GENERAL_INT);
		msg.setTimeout(Test.GENERAL_INT);
		msg.setSliderHeader(Test.GENERAL_STRING);
		msg.setSliderFooter(Test.GENERAL_STRING_LIST);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SLIDER.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(Slider.KEY_SLIDER_HEADER, Test.GENERAL_STRING);
			result.put(Slider.KEY_SLIDER_FOOTER, JsonUtils.createJsonArray(Test.GENERAL_STRING_LIST));
			result.put(Slider.KEY_POSITION, Test.GENERAL_INT);
			result.put(Slider.KEY_TIMEOUT, Test.GENERAL_INT);
			result.put(Slider.KEY_NUM_TICKS, Test.GENERAL_INT);			
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
    	Integer testNumTicks    = ( (Slider) msg ).getNumTicks();
    	Integer testTimeout     = ( (Slider) msg ).getTimeout();
    	Integer testPosition    = ( (Slider) msg ).getPosition();
    	String  testSlider      = ( (Slider) msg ).getSliderHeader();
    	List<String> testFooter = ( (Slider) msg ).getSliderFooter();
		
    	// Valid Tests
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, testNumTicks);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, testTimeout);
		assertEquals(Test.MATCH, (Integer) Test.GENERAL_INT, testPosition);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testSlider);
		assertTrue(Test.TRUE, Validator.validateStringList(Test.GENERAL_STRING_LIST, testFooter));
		    	
    	// Invalid/Null Tests
		Slider msg = new Slider();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getSliderHeader());
		assertNull(Test.NULL, msg.getSliderFooter());
		assertNull(Test.NULL, msg.getPosition());
		assertNull(Test.NULL, msg.getTimeout());
		assertNull(Test.NULL, msg.getNumTicks());
	}
	
	/**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			Slider cmd = new Slider(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, Slider.KEY_NUM_TICKS), cmd.getNumTicks());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, Slider.KEY_SLIDER_HEADER), cmd.getSliderHeader());

			List<String> sliderFooterList = JsonUtils.readStringListFromJsonObject(parameters, Slider.KEY_SLIDER_FOOTER);
			List<String> testFooterList = cmd.getSliderFooter();
			assertEquals(Test.MATCH, sliderFooterList.size(), testFooterList.size());
			assertTrue(Test.TRUE, Validator.validateStringList(sliderFooterList, testFooterList));
			
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, Slider.KEY_POSITION), cmd.getPosition());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, Slider.KEY_TIMEOUT), cmd.getTimeout());
		} 
		catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}    	
    }	
}