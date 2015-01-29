package com.smartdevicelink.test.rpc.responses;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.ButtonCapabilities;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.PresetBankCapabilities;
import com.smartdevicelink.proxy.rpc.SetDisplayLayoutResponse;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.enums.DisplayType;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class SetDisplayLayoutResponseTest extends BaseRpcTests {

	private DisplayCapabilities DISPLAY_CAPABILITIES = new DisplayCapabilities();
	private static final PresetBankCapabilities PRESET_BANK_CAPABILITIES = new PresetBankCapabilities();
	private static final List<ButtonCapabilities> BUTTON_CAPABILITIES_LIST = new ArrayList<ButtonCapabilities>();
	private static final List<SoftButtonCapabilities> SOFT_BUTTON_CAPABILITIES_LIST = new ArrayList<SoftButtonCapabilities>();
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
	
	public void createCustomObjects () { 
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
			result.put(SetDisplayLayoutResponse.KEY_BUTTON_CAPABILITIES, JsonUtils.createJsonArray(BUTTON_CAPABILITIES_LIST)); // more?
			result.put(SetDisplayLayoutResponse.KEY_SOFT_BUTTON_CAPABILITIES, JsonUtils.createJsonArray(SOFT_BUTTON_CAPABILITIES_LIST)); //more?
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testDisplayCapabilities() {
		DisplayCapabilities copy = ( (SetDisplayLayoutResponse) msg ).getDisplayCapabilities();
		
		assertNotSame("Display capabilities was not defensive copied", DISPLAY_CAPABILITIES, copy);
		assertTrue("Input value didn't match expected value.", Validator.validateDisplayCapabilities(DISPLAY_CAPABILITIES, copy));
	}
	
	public void testPresetBankCapabilities() {
		PresetBankCapabilities copy = ( (SetDisplayLayoutResponse) msg ).getPresetBankCapabilities();
		
		assertNotSame("Preset bank capabilities was not defensive copied", PRESET_BANK_CAPABILITIES, copy);
		assertTrue("Input value didn't match expected value.", Validator.validatePresetBankCapabilities(PRESET_BANK_CAPABILITIES, copy));
	}
	
	public void testButtonCapabilities () {
		List<ButtonCapabilities> copy = ( (SetDisplayLayoutResponse) msg ).getButtonCapabilities();
		
		assertNotSame("Variable under test was not defensive copied.", BUTTON_CAPABILITIES_LIST, copy);
		assertEquals("List size didn't match expected size.", BUTTON_CAPABILITIES_LIST.size(), copy.size());
		
		for (int i = 0; i < BUTTON_CAPABILITIES_LIST.size(); i++) {
			assertEquals("Input value didn't match expected value.", BUTTON_CAPABILITIES_LIST.get(i), copy.get(i));
		}
	}
	
	public void testSoftButtonCapabilities () {
		List<SoftButtonCapabilities> copy = ( (SetDisplayLayoutResponse) msg ).getSoftButtonCapabilities();
		
		assertNotSame("Variable under test was not defensive copied.", SOFT_BUTTON_CAPABILITIES_LIST, copy);
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
	
}
