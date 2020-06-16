package com.smartdevicelink.test.rpc.responses;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ButtonCapabilities;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.PresetBankCapabilities;
import com.smartdevicelink.proxy.rpc.SetDisplayLayoutResponse;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.SetDisplayLayoutResponse}
 */
public class SetDisplayLayoutResponseTest extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {		
		SetDisplayLayoutResponse msg = new SetDisplayLayoutResponse();
		
		msg.setDisplayCapabilities(TestValues.GENERAL_DISPLAYCAPABILITIES);
		msg.setPresetBankCapabilities(TestValues.GENERAL_PRESETBANKCAPABILITIES);
		msg.setButtonCapabilities(TestValues.GENERAL_BUTTONCAPABILITIES_LIST);
		msg.setSoftButtonCapabilities(TestValues.GENERAL_SOFTBUTTONCAPABILITIES_LIST);

		return msg;
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_RESPONSE;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SET_DISPLAY_LAYOUT.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(SetDisplayLayoutResponse.KEY_DISPLAY_CAPABILITIES, TestValues.GENERAL_DISPLAYCAPABILITIES.serializeJSON());
			result.put(SetDisplayLayoutResponse.KEY_PRESET_BANK_CAPABILITIES, TestValues.JSON_PRESETBANKCAPABILITIES);
			result.put(SetDisplayLayoutResponse.KEY_BUTTON_CAPABILITIES, TestValues.JSON_BUTTONCAPABILITIES);
            result.put(SetDisplayLayoutResponse.KEY_SOFT_BUTTON_CAPABILITIES, TestValues.JSON_SOFTBUTTONCAPABILITIES);
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
		DisplayCapabilities testDisplay      = ( (SetDisplayLayoutResponse) msg ).getDisplayCapabilities();
		PresetBankCapabilities testPbc       = ( (SetDisplayLayoutResponse) msg ).getPresetBankCapabilities();
		List<ButtonCapabilities> testBc      = ( (SetDisplayLayoutResponse) msg ).getButtonCapabilities();
		List<SoftButtonCapabilities> testSbc = ( (SetDisplayLayoutResponse) msg ).getSoftButtonCapabilities();
		
		// Valid Tests
		assertTrue(TestValues.TRUE, Validator.validateDisplayCapabilities(TestValues.GENERAL_DISPLAYCAPABILITIES, testDisplay));
		assertTrue(TestValues.TRUE, Validator.validatePresetBankCapabilities(TestValues.GENERAL_PRESETBANKCAPABILITIES, testPbc));
		assertEquals(TestValues.MATCH, TestValues.GENERAL_BUTTONCAPABILITIES_LIST.size(), testBc.size());
		assertEquals(TestValues.MATCH, TestValues.GENERAL_SOFTBUTTONCAPABILITIES_LIST.size(), testSbc.size());
		for (int i = 0; i < TestValues.GENERAL_BUTTONCAPABILITIES_LIST.size(); i++) {
			assertEquals(TestValues.MATCH, TestValues.GENERAL_BUTTONCAPABILITIES_LIST.get(i), testBc.get(i));
		}
		for (int i = 0; i < TestValues.GENERAL_SOFTBUTTONCAPABILITIES_LIST.size(); i++) {
			assertEquals(TestValues.MATCH, TestValues.GENERAL_SOFTBUTTONCAPABILITIES_LIST.get(i), testSbc.get(i));
		}
	
		// Invalid/Null Tests
		SetDisplayLayoutResponse msg = new SetDisplayLayoutResponse();
		assertNotNull(TestValues.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(TestValues.NULL, msg.getDisplayCapabilities());
		assertNull(TestValues.NULL, msg.getPresetBankCapabilities());
		assertNull(TestValues.NULL, msg.getButtonCapabilities());
		assertNull(TestValues.NULL, msg.getSoftButtonCapabilities());
	}
	
    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(TestValues.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SetDisplayLayoutResponse cmd = new SetDisplayLayoutResponse(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

			JSONArray buttonCapabilitiesArray = JsonUtils.readJsonArrayFromJsonObject(parameters, SetDisplayLayoutResponse.KEY_BUTTON_CAPABILITIES);
			List<ButtonCapabilities> buttonCapabilitiesList = new ArrayList<ButtonCapabilities>();
			for (int index = 0; index < buttonCapabilitiesArray.length(); index++) {
				ButtonCapabilities buttonCapability = new ButtonCapabilities(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)buttonCapabilitiesArray.get(index) ));
				buttonCapabilitiesList.add(buttonCapability);
			}
			assertTrue(TestValues.TRUE, Validator.validateButtonCapabilities(buttonCapabilitiesList, cmd.getButtonCapabilities() ));
			
			JSONObject displayCapabilitiesObj = JsonUtils.readJsonObjectFromJsonObject(parameters, SetDisplayLayoutResponse.KEY_DISPLAY_CAPABILITIES);
			DisplayCapabilities displayCapabilities = new DisplayCapabilities(JsonRPCMarshaller.deserializeJSONObject(displayCapabilitiesObj));
			assertTrue(TestValues.TRUE, Validator.validateDisplayCapabilities(displayCapabilities, cmd.getDisplayCapabilities()) );
			
			JSONArray softButtonCapabilitiesArray = JsonUtils.readJsonArrayFromJsonObject(parameters, SetDisplayLayoutResponse.KEY_SOFT_BUTTON_CAPABILITIES);
			List<SoftButtonCapabilities> softButtonCapabilitiesList = new ArrayList<SoftButtonCapabilities>();
			for (int index = 0; index < softButtonCapabilitiesArray.length(); index++) {
				SoftButtonCapabilities softButtonCapability = 
						new SoftButtonCapabilities(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)softButtonCapabilitiesArray.get(index) ));
				softButtonCapabilitiesList.add(softButtonCapability);
			}
			assertTrue(TestValues.TRUE, Validator.validateSoftButtonCapabilities(softButtonCapabilitiesList, cmd.getSoftButtonCapabilities() ));
			
			JSONObject presetBankCapabilitiesObj = JsonUtils.readJsonObjectFromJsonObject(parameters, SetDisplayLayoutResponse.KEY_PRESET_BANK_CAPABILITIES);
			PresetBankCapabilities presetBankCapabilities = new PresetBankCapabilities(JsonRPCMarshaller.deserializeJSONObject(presetBankCapabilitiesObj));
			assertTrue(TestValues.TRUE,  Validator.validatePresetBankCapabilities(presetBankCapabilities, cmd.getPresetBankCapabilities()) );
		} catch (JSONException e) {
			e.printStackTrace();
		}    	
    }	
}