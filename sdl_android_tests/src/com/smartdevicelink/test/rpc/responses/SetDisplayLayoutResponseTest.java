package com.smartdevicelink.test.rpc.responses;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ButtonCapabilities;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.PresetBankCapabilities;
import com.smartdevicelink.proxy.rpc.SetDisplayLayoutResponse;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.enums.DisplayType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

public class SetDisplayLayoutResponseTest extends BaseRpcTests {

	private DisplayCapabilities DISPLAY_CAPABILITIES = new DisplayCapabilities();
	private static final PresetBankCapabilities PRESET_BANK_CAPABILITIES = new PresetBankCapabilities();
	private final List<ButtonCapabilities> BUTTON_CAPABILITIES_LIST = new ArrayList<ButtonCapabilities>();
	private final List<SoftButtonCapabilities> SOFT_BUTTON_CAPABILITIES_LIST = new ArrayList<SoftButtonCapabilities>();
	private static final Boolean BUTTON_CAPABILITIES_TRUE = true;
	private static final Boolean BUTTON_CAPABILITIES_FALSE = false;
	private static final Boolean SOFT_BUTTON_CAPABILITIES_TRUE = true;
	private static final Boolean SOFT_BUTTON_CAPABILITIES_FALSE = false;
	
	@Override
	protected RPCMessage createMessage() {		
		SetDisplayLayoutResponse msg = new SetDisplayLayoutResponse();
		createCustomObjects();
		
		msg.setDisplayCapabilities(DISPLAY_CAPABILITIES);
		msg.setPresetBankCapabilities(PRESET_BANK_CAPABILITIES);
		msg.setButtonCapabilities(BUTTON_CAPABILITIES_LIST);
		msg.setSoftButtonCapabilities(SOFT_BUTTON_CAPABILITIES_LIST);

		return msg;
	}
	
	private void createCustomObjects () { 
		ButtonCapabilities buttonCapabailities = new ButtonCapabilities();
		buttonCapabailities.setUpDownAvailable(BUTTON_CAPABILITIES_TRUE);
		buttonCapabailities.setLongPressAvailable(BUTTON_CAPABILITIES_TRUE);
		buttonCapabailities.setShortPressAvailable(BUTTON_CAPABILITIES_FALSE);
		BUTTON_CAPABILITIES_LIST.add(buttonCapabailities);
		
		SoftButtonCapabilities softButtonCapabilities = new SoftButtonCapabilities();
		softButtonCapabilities.setImageSupported(SOFT_BUTTON_CAPABILITIES_FALSE);
		softButtonCapabilities.setUpDownAvailable(SOFT_BUTTON_CAPABILITIES_TRUE);
		softButtonCapabilities.setLongPressAvailable(SOFT_BUTTON_CAPABILITIES_FALSE);
		softButtonCapabilities.setShortPressAvailable(SOFT_BUTTON_CAPABILITIES_TRUE);
		SOFT_BUTTON_CAPABILITIES_LIST.add(softButtonCapabilities);

		DISPLAY_CAPABILITIES.setDisplayType(DisplayType.TYPE2);
		DISPLAY_CAPABILITIES.setGraphicSupported(true);
		PRESET_BANK_CAPABILITIES.setOnScreenPresetsAvailable(true);
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_RESPONSE;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.SET_DISPLAY_LAYOUT;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();
		JSONObject presetBank = new JSONObject();

		try {			
			presetBank.put(PresetBankCapabilities.KEY_ON_SCREEN_PRESETS_AVAILABLE, PRESET_BANK_CAPABILITIES.onScreenPresetsAvailable());
						
			result.put(SetDisplayLayoutResponse.KEY_DISPLAY_CAPABILITIES, DISPLAY_CAPABILITIES.serializeJSON());
			result.put(SetDisplayLayoutResponse.KEY_PRESET_BANK_CAPABILITIES, presetBank);
			
			JSONObject buttonCapabilitiesObj = new JSONObject();
			JSONArray buttonCapabilitiesArray = new JSONArray();
            buttonCapabilitiesObj.put(ButtonCapabilities.KEY_LONG_PRESS_AVAILABLE, true);
            buttonCapabilitiesObj.put(ButtonCapabilities.KEY_SHORT_PRESS_AVAILABLE, false);
            buttonCapabilitiesObj.put(ButtonCapabilities.KEY_UP_DOWN_AVAILABLE, true);
            buttonCapabilitiesArray.put(buttonCapabilitiesObj);
			
			result.put(SetDisplayLayoutResponse.KEY_BUTTON_CAPABILITIES, buttonCapabilitiesArray);
            
            JSONObject softButtonCapabilitiesObj = new JSONObject();
            JSONArray softButtonCapabilitiesArray = new JSONArray();
            softButtonCapabilitiesObj.put(SoftButtonCapabilities.KEY_UP_DOWN_AVAILABLE, true);
            softButtonCapabilitiesObj.put(SoftButtonCapabilities.KEY_SHORT_PRESS_AVAILABLE, true);
            softButtonCapabilitiesObj.put(SoftButtonCapabilities.KEY_LONG_PRESS_AVAILABLE, false);
            softButtonCapabilitiesObj.put(SoftButtonCapabilities.KEY_IMAGE_SUPPORTED, false);
            softButtonCapabilitiesArray.put(softButtonCapabilitiesObj);
			
			result.put(SetDisplayLayoutResponse.KEY_SOFT_BUTTON_CAPABILITIES, softButtonCapabilitiesArray);
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testDisplayCapabilities() {
		DisplayCapabilities copy = ( (SetDisplayLayoutResponse) msg ).getDisplayCapabilities();
		
		assertTrue("Input value didn't match expected value.", Validator.validateDisplayCapabilities(DISPLAY_CAPABILITIES, copy));
	}
	
	public void testPresetBankCapabilities() {
		PresetBankCapabilities copy = ( (SetDisplayLayoutResponse) msg ).getPresetBankCapabilities();
		
		assertTrue("Input value didn't match expected value.", Validator.validatePresetBankCapabilities(PRESET_BANK_CAPABILITIES, copy));
	}
	
	public void testButtonCapabilities () {
		List<ButtonCapabilities> copy = ( (SetDisplayLayoutResponse) msg ).getButtonCapabilities();
		
		assertEquals("List size didn't match expected size.", BUTTON_CAPABILITIES_LIST.size(), copy.size());
		
		for (int i = 0; i < BUTTON_CAPABILITIES_LIST.size(); i++) {
			assertEquals("Input value didn't match expected value.", BUTTON_CAPABILITIES_LIST.get(i), copy.get(i));
		}
	}
	
	public void testSoftButtonCapabilities () {
		List<SoftButtonCapabilities> copy = ( (SetDisplayLayoutResponse) msg ).getSoftButtonCapabilities();
		
		assertEquals("List size didn't match expected size.", SOFT_BUTTON_CAPABILITIES_LIST.size(), copy.size());
		
		for (int i = 0; i < SOFT_BUTTON_CAPABILITIES_LIST.size(); i++) {
			assertEquals("Input value didn't match expected value.", SOFT_BUTTON_CAPABILITIES_LIST.get(i), copy.get(i));
		}
	}

	public void testNull() {
		SetDisplayLayoutResponse msg = new SetDisplayLayoutResponse();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("Display capabilities wasn't set, but getter method returned an object.", msg.getDisplayCapabilities());
		assertNull("Preset bank capabilities wasn't set, but getter method returned an object.", msg.getPresetBankCapabilities());
		assertNull("Button capabilities wasn't set, but getter method returned an object.", msg.getButtonCapabilities());
		assertNull("Soft button capabilities wasn't set, but getter method returned an object.", msg.getSoftButtonCapabilities());
	}
	
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			SetDisplayLayoutResponse cmd = new SetDisplayLayoutResponse(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);

			JSONArray buttonCapabilitiesArray = JsonUtils.readJsonArrayFromJsonObject(parameters, SetDisplayLayoutResponse.KEY_BUTTON_CAPABILITIES);
			List<ButtonCapabilities> buttonCapabilitiesList = new ArrayList<ButtonCapabilities>();
			for (int index = 0; index < buttonCapabilitiesArray.length(); index++) {
				ButtonCapabilities buttonCapability = new ButtonCapabilities(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)buttonCapabilitiesArray.get(index) ));
				buttonCapabilitiesList.add(buttonCapability);
			}
			assertTrue("Button capabilities list doesn't match input capabilities list",  
					Validator.validateButtonCapabilities(buttonCapabilitiesList, cmd.getButtonCapabilities() ));
			
			JSONObject displayCapabilitiesObj = JsonUtils.readJsonObjectFromJsonObject(parameters, SetDisplayLayoutResponse.KEY_DISPLAY_CAPABILITIES);
			DisplayCapabilities displayCapabilities = new DisplayCapabilities(JsonRPCMarshaller.deserializeJSONObject(displayCapabilitiesObj));
			assertTrue("Display capabilities doesn't match input capabilities",  Validator.validateDisplayCapabilities(displayCapabilities, cmd.getDisplayCapabilities()) );
			
			JSONArray softButtonCapabilitiesArray = JsonUtils.readJsonArrayFromJsonObject(parameters, SetDisplayLayoutResponse.KEY_SOFT_BUTTON_CAPABILITIES);
			List<SoftButtonCapabilities> softButtonCapabilitiesList = new ArrayList<SoftButtonCapabilities>();
			for (int index = 0; index < softButtonCapabilitiesArray.length(); index++) {
				SoftButtonCapabilities softButtonCapability = 
						new SoftButtonCapabilities(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)softButtonCapabilitiesArray.get(index) ));
				softButtonCapabilitiesList.add(softButtonCapability);
			}
			assertTrue("Soft button capabilities list doesn't match input capabilities list",  
					Validator.validateSoftButtonCapabilities(softButtonCapabilitiesList, cmd.getSoftButtonCapabilities() ));
			
			JSONObject presetBankCapabilitiesObj = JsonUtils.readJsonObjectFromJsonObject(parameters, SetDisplayLayoutResponse.KEY_PRESET_BANK_CAPABILITIES);
			PresetBankCapabilities presetBankCapabilities = new PresetBankCapabilities(JsonRPCMarshaller.deserializeJSONObject(presetBankCapabilitiesObj));
			assertTrue("Preset bank capabilities doesn't match input capabilities",  Validator.validatePresetBankCapabilities(presetBankCapabilities, cmd.getPresetBankCapabilities()) );
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }
	
}
