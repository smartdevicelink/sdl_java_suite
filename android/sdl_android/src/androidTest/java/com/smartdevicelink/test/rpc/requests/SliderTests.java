package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.Slider;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.Slider}
 */
public class SliderTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		Slider msg = new Slider();

		msg.setNumTicks(TestValues.GENERAL_INT);
		msg.setPosition(TestValues.GENERAL_INT);
		msg.setTimeout(TestValues.GENERAL_INT);
		msg.setSliderHeader(TestValues.GENERAL_STRING);
		msg.setSliderFooter(TestValues.GENERAL_STRING_LIST);
		msg.setCancelID(TestValues.GENERAL_INTEGER);

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
			result.put(Slider.KEY_SLIDER_HEADER, TestValues.GENERAL_STRING);
			result.put(Slider.KEY_SLIDER_FOOTER, JsonUtils.createJsonArray(TestValues.GENERAL_STRING_LIST));
			result.put(Slider.KEY_POSITION, TestValues.GENERAL_INT);
			result.put(Slider.KEY_TIMEOUT, TestValues.GENERAL_INT);
			result.put(Slider.KEY_NUM_TICKS, TestValues.GENERAL_INT);
			result.put(Slider.KEY_CANCEL_ID, TestValues.GENERAL_INTEGER);
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
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
    	Integer testCancelID = ( (Slider) msg ).getCancelID();

    	// Valid Tests
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, testNumTicks);
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, testTimeout);
		assertEquals(TestValues.MATCH, (Integer) TestValues.GENERAL_INT, testPosition);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testSlider);
		assertTrue(TestValues.TRUE, Validator.validateStringList(TestValues.GENERAL_STRING_LIST, testFooter));
		assertEquals(TestValues.MATCH, TestValues.GENERAL_INTEGER, testCancelID);
		    	
    	// Invalid/Null Tests
		Slider msg = new Slider();
		assertNotNull(TestValues.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(TestValues.NULL, msg.getSliderHeader());
		assertNull(TestValues.NULL, msg.getSliderFooter());
		assertNull(TestValues.NULL, msg.getPosition());
		assertNull(TestValues.NULL, msg.getTimeout());
		assertNull(TestValues.NULL, msg.getNumTicks());
		assertNull(TestValues.NULL, msg.getCancelID());
	}
	
	/**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(TestValues.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			Slider cmd = new Slider(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, Slider.KEY_NUM_TICKS), cmd.getNumTicks());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, Slider.KEY_SLIDER_HEADER), cmd.getSliderHeader());

			List<String> sliderFooterList = JsonUtils.readStringListFromJsonObject(parameters, Slider.KEY_SLIDER_FOOTER);
			List<String> testFooterList = cmd.getSliderFooter();
			assertEquals(TestValues.MATCH, sliderFooterList.size(), testFooterList.size());
			assertTrue(TestValues.TRUE, Validator.validateStringList(sliderFooterList, testFooterList));
			
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, Slider.KEY_POSITION), cmd.getPosition());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, Slider.KEY_TIMEOUT), cmd.getTimeout());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(parameters, Slider.KEY_CANCEL_ID), cmd.getCancelID());
		} 
		catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}    	
    }	
}