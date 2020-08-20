package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.SetDisplayLayout;
import com.smartdevicelink.proxy.rpc.TemplateColorScheme;
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
 * {@link com.smartdevicelink.proxy.rpc.SetDisplayLayout}
 */
public class SetDisplayLayoutTests extends BaseRpcTests {
	
	@Override
	protected RPCMessage createMessage() {
		SetDisplayLayout msg = new SetDisplayLayout();

		msg.setDisplayLayout(TestValues.GENERAL_STRING);
		msg.setDayColorScheme(TestValues.GENERAL_DAYCOLORSCHEME);
		msg.setNightColorScheme(TestValues.GENERAL_NIGHTCOLORSCHEME);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SET_DISPLAY_LAYOUT.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(SetDisplayLayout.KEY_DISPLAY_LAYOUT, TestValues.GENERAL_STRING);
			result.put(SetDisplayLayout.KEY_DAY_COLOR_SCHEME, TestValues.JSON_DAYCOLORSCHEME);
			result.put(SetDisplayLayout.KEY_NIGHT_COLOR_SCHEME, TestValues.JSON_NIGHTCOLORSCHEME);
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
		String testDisplayLayout = ( (SetDisplayLayout) msg ).getDisplayLayout();
		TemplateColorScheme testDayColorScheme = ( (SetDisplayLayout) msg).getDayColorScheme();
		TemplateColorScheme testNightColorScheme = ( (SetDisplayLayout) msg).getNightColorScheme();
		
		// Valid Tests
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testDisplayLayout);
		assertTrue(TestValues.TRUE, Validator.validateTemplateColorScheme(TestValues.GENERAL_DAYCOLORSCHEME, testDayColorScheme));
		assertTrue(TestValues.TRUE, Validator.validateTemplateColorScheme(TestValues.GENERAL_NIGHTCOLORSCHEME, testNightColorScheme));
		
		// Invalid/Null Tests
		SetDisplayLayout msg = new SetDisplayLayout();
		assertNotNull(TestValues.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(TestValues.NULL, msg.getDisplayLayout());
		assertNull(TestValues.NULL, msg.getDayColorScheme());
		assertNull(TestValues.NULL, msg.getNightColorScheme());
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
			SetDisplayLayout cmd = new SetDisplayLayout(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.MATCH, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, SetDisplayLayout.KEY_DISPLAY_LAYOUT), cmd.getDisplayLayout());


			JSONObject dayColorSchemeObj = JsonUtils.readJsonObjectFromJsonObject(parameters, SetDisplayLayout.KEY_DAY_COLOR_SCHEME);
			TemplateColorScheme dayColorScheme = new TemplateColorScheme(JsonRPCMarshaller.deserializeJSONObject(dayColorSchemeObj));
			assertTrue(TestValues.TRUE,  Validator.validateTemplateColorScheme(dayColorScheme, cmd.getDayColorScheme()) );

			JSONObject nightColorSchemeObj = JsonUtils.readJsonObjectFromJsonObject(parameters, SetDisplayLayout.KEY_DAY_COLOR_SCHEME);
			TemplateColorScheme nightColorScheme = new TemplateColorScheme(JsonRPCMarshaller.deserializeJSONObject(nightColorSchemeObj));
			assertTrue(TestValues.TRUE,  Validator.validateTemplateColorScheme(nightColorScheme, cmd.getDayColorScheme()) );

			
		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}    	
    }
}