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
import com.smartdevicelink.proxy.rpc.AudioPassThruCapabilities;
import com.smartdevicelink.proxy.rpc.ButtonCapabilities;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.PresetBankCapabilities;
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.VehicleType;
import com.smartdevicelink.proxy.rpc.enums.HmiZoneCapabilities;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.PrerecordedSpeech;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.enums.VrCapabilities;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.RegisterAppInterfaceResponse}
 */
public class RegisterAppInterfaceResponseTest extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		RegisterAppInterfaceResponse msg = new RegisterAppInterfaceResponse();

		msg.setSdlMsgVersion(Test.GENERAL_SDLMSGVERSION);
		msg.setLanguage(Test.GENERAL_LANGUAGE);
		msg.setHmiDisplayLanguage(Test.GENERAL_LANGUAGE);
		msg.setDisplayCapabilities(Test.GENERAL_DISPLAYCAPABILITIES);
		msg.setPresetBankCapabilities(Test.GENERAL_PRESETBANKCAPABILITIES);
		msg.setVehicleType(Test.GENERAL_VEHICLETYPE);
		msg.setButtonCapabilities(Test.GENERAL_BUTTONCAPABILITIES_LIST);
		msg.setSoftButtonCapabilities(Test.GENERAL_SOFTBUTTONCAPABILITIES_LIST);
		msg.setAudioPassThruCapabilities(Test.GENERAL_AUDIOPASSTHRUCAPABILITIES_LIST);
		msg.setHmiZoneCapabilities(Test.GENERAL_HMIZONECAPABILITIES_LIST);
		msg.setSpeechCapabilities(Test.GENERAL_SPEECHCAPABILITIES_LIST);
		msg.setVrCapabilities(Test.GENERAL_VRCAPABILITIES_LIST);
		msg.setPrerecordedSpeech(Test.GENERAL_PRERECORDEDSPEECH_LIST);
		msg.setSupportedDiagModes(Test.GENERAL_INTEGER_LIST);

		return msg;
	}
	
	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_RESPONSE;
	}
	
	@Override
	protected String getCommandType() {
		return FunctionID.REGISTER_APP_INTERFACE.toString();
	}
	

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {		
			result.put(RegisterAppInterfaceResponse.KEY_LANGUAGE, Test.GENERAL_LANGUAGE);
			result.put(RegisterAppInterfaceResponse.KEY_HMI_DISPLAY_LANGUAGE, Test.GENERAL_LANGUAGE);
			result.put(RegisterAppInterfaceResponse.KEY_SUPPORTED_DIAG_MODES, JsonUtils.createJsonArray(Test.GENERAL_INTEGER_LIST));			
			result.put(RegisterAppInterfaceResponse.KEY_SDL_MSG_VERSION, Test.JSON_SDLMSGVERSION);
			result.put(RegisterAppInterfaceResponse.KEY_VEHICLE_TYPE, Test.GENERAL_VEHICLETYPE.serializeJSON());
			result.put(RegisterAppInterfaceResponse.KEY_PRESET_BANK_CAPABILITIES, Test.JSON_PRESETBANKCAPABILITIES);
			result.put(RegisterAppInterfaceResponse.KEY_DISPLAY_CAPABILITIES, Test.JSON_DISPLAYCAPABILITIES);	
			result.put(RegisterAppInterfaceResponse.KEY_BUTTON_CAPABILITIES, Test.JSON_BUTTONCAPABILITIES);
			result.put(RegisterAppInterfaceResponse.KEY_SOFT_BUTTON_CAPABILITIES, Test.JSON_SOFTBUTTONCAPABILITIES);
			result.put(RegisterAppInterfaceResponse.KEY_AUDIO_PASS_THRU_CAPABILITIES, Test.JSON_AUDIOPASSTHRUCAPABILITIES);
			result.put(RegisterAppInterfaceResponse.KEY_SPEECH_CAPABILITIES, JsonUtils.createJsonArray(Test.GENERAL_SPEECHCAPABILITIES_LIST));
			result.put(RegisterAppInterfaceResponse.KEY_VR_CAPABILITIES, JsonUtils.createJsonArray(Test.GENERAL_VRCAPABILITIES_LIST));	
			result.put(RegisterAppInterfaceResponse.KEY_HMI_ZONE_CAPABILITIES, JsonUtils.createJsonArray(Test.GENERAL_HMIZONECAPABILITIES_LIST));
			result.put(RegisterAppInterfaceResponse.KEY_PRERECORDED_SPEECH, JsonUtils.createJsonArray(Test.GENERAL_PRERECORDEDSPEECH_LIST));
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
		List<Integer>            testSupportedDiagModes = ( (RegisterAppInterfaceResponse) msg ).getSupportedDiagModes();
		List<PrerecordedSpeech>  testPrerecordedSpeech  = ( (RegisterAppInterfaceResponse) msg ).getPrerecordedSpeech();
		List<VrCapabilities>     testVrCapabilities     = ( (RegisterAppInterfaceResponse) msg ).getVrCapabilities();
		List<SpeechCapabilities> testSpeechCapabilities = ( (RegisterAppInterfaceResponse) msg ).getSpeechCapabilities();
		List<HmiZoneCapabilities> testHmiZoneCapabilities = ( (RegisterAppInterfaceResponse) msg ).getHmiZoneCapabilities();
		List<SoftButtonCapabilities> testSoftButtonCapabilities = ( (RegisterAppInterfaceResponse) msg ).getSoftButtonCapabilities();
		List<ButtonCapabilities> testButtonCapabilities = ( (RegisterAppInterfaceResponse) msg ).getButtonCapabilities();
		VehicleType testVehicleType = ( (RegisterAppInterfaceResponse) msg ).getVehicleType();
		PresetBankCapabilities testPbc = ( (RegisterAppInterfaceResponse) msg ).getPresetBankCapabilities();
		DisplayCapabilities testDc = ( (RegisterAppInterfaceResponse) msg ).getDisplayCapabilities();
		Language testHmiLang = ( (RegisterAppInterfaceResponse) msg ).getHmiDisplayLanguage();
		Language testLang = ( (RegisterAppInterfaceResponse) msg ).getLanguage();
		SdlMsgVersion testMsgVersion = ( (RegisterAppInterfaceResponse) msg ).getSdlMsgVersion();
		List<AudioPassThruCapabilities> testAptc = ( (RegisterAppInterfaceResponse) msg ).getAudioPassThruCapabilities();
		
		// Valid Tests
		assertEquals(Test.MATCH, Test.GENERAL_INTEGER_LIST, testSupportedDiagModes);
		assertEquals(Test.MATCH, Test.GENERAL_PRERECORDEDSPEECH_LIST, testPrerecordedSpeech);
		assertEquals(Test.MATCH, Test.GENERAL_VRCAPABILITIES_LIST, testVrCapabilities);
		assertEquals(Test.MATCH, Test.GENERAL_SPEECHCAPABILITIES_LIST, testSpeechCapabilities);
		assertEquals(Test.MATCH, Test.GENERAL_HMIZONECAPABILITIES_LIST, testHmiZoneCapabilities);
		assertTrue(Test.TRUE, Validator.validateSoftButtonCapabilities(Test.GENERAL_SOFTBUTTONCAPABILITIES_LIST, testSoftButtonCapabilities));
		assertTrue(Test.TRUE, Validator.validateButtonCapabilities(Test.GENERAL_BUTTONCAPABILITIES_LIST, testButtonCapabilities));
		assertTrue(Test.TRUE, Validator.validateVehicleType(Test.GENERAL_VEHICLETYPE, testVehicleType));
		assertTrue(Test.TRUE, Validator.validatePresetBankCapabilities(Test.GENERAL_PRESETBANKCAPABILITIES, testPbc));
		assertTrue(Test.TRUE, Validator.validateDisplayCapabilities(Test.GENERAL_DISPLAYCAPABILITIES, testDc));
		assertEquals(Test.MATCH, Test.GENERAL_LANGUAGE, testHmiLang);
		assertEquals(Test.MATCH, Test.GENERAL_LANGUAGE, testLang);
		assertTrue(Test.TRUE, Validator.validateSdlMsgVersion(Test.GENERAL_SDLMSGVERSION, testMsgVersion));
		assertTrue(Test.TRUE, Validator.validateAudioPassThruCapabilities(Test.GENERAL_AUDIOPASSTHRUCAPABILITIES_LIST, testAptc));
		
		// Invalid/Null Tests
		RegisterAppInterfaceResponse msg = new RegisterAppInterfaceResponse();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getSdlMsgVersion());
		assertNull(Test.NULL, msg.getLanguage());
		assertNull(Test.NULL, msg.getHmiDisplayLanguage());
		assertNull(Test.NULL, msg.getDisplayCapabilities());
		assertNull(Test.NULL, msg.getPresetBankCapabilities());
		assertNull(Test.NULL, msg.getVehicleType());
		assertNull(Test.NULL, msg.getButtonCapabilities());
		assertNull(Test.NULL, msg.getSoftButtonCapabilities());
		assertNull(Test.NULL, msg.getAudioPassThruCapabilities());
		assertNull(Test.NULL, msg.getHmiZoneCapabilities());
		assertNull(Test.NULL, msg.getSpeechCapabilities());
		assertNull(Test.NULL, msg.getVrCapabilities());
		assertNull(Test.NULL, msg.getPrerecordedSpeech());
		assertNull(Test.NULL, msg.getSupportedDiagModes());
	}
	
    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			RegisterAppInterfaceResponse cmd = new RegisterAppInterfaceResponse(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			JSONObject vehicleTypeObj = JsonUtils.readJsonObjectFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_VEHICLE_TYPE);
			VehicleType vehicleType = new VehicleType(JsonRPCMarshaller.deserializeJSONObject(vehicleTypeObj));
			assertTrue(Test.TRUE,  Validator.validateVehicleType(vehicleType, cmd.getVehicleType()));
			
			JSONArray speechCapabilitiesArray = JsonUtils.readJsonArrayFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_SPEECH_CAPABILITIES);
			for (int index = 0; index < speechCapabilitiesArray.length(); index++) {
				SpeechCapabilities speechCapability = SpeechCapabilities.valueForString( speechCapabilitiesArray.get(index).toString() );
				assertEquals(Test.MATCH, speechCapability, cmd.getSpeechCapabilities().get(index));
			}
			
			JSONArray vrCapabilitiesArray = JsonUtils.readJsonArrayFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_VR_CAPABILITIES);
			for (int index = 0; index < vrCapabilitiesArray.length(); index++) {
				VrCapabilities vrCapability = VrCapabilities.valueForString( vrCapabilitiesArray.get(index).toString() );
				assertEquals(Test.MATCH, vrCapability, cmd.getVrCapabilities().get(index));
			}
			
			JSONArray audioPassThruCapabilitiesArray = JsonUtils.readJsonArrayFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_AUDIO_PASS_THRU_CAPABILITIES);
			List<AudioPassThruCapabilities> audioPassThruCapabilitiesList = new ArrayList<AudioPassThruCapabilities>();
			for (int index = 0; index < audioPassThruCapabilitiesArray.length(); index++) {
				AudioPassThruCapabilities audioPassThruCapability = 
						new AudioPassThruCapabilities(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)audioPassThruCapabilitiesArray.get(index) ));
				audioPassThruCapabilitiesList.add(audioPassThruCapability);
			}
			assertTrue(Test.TRUE, Validator.validateAudioPassThruCapabilities(audioPassThruCapabilitiesList, cmd.getAudioPassThruCapabilities() ));
			
			JSONArray hmiZoneCapabilitiesArray = JsonUtils.readJsonArrayFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_HMI_ZONE_CAPABILITIES);
			for (int index = 0; index < hmiZoneCapabilitiesArray.length(); index++) {
				HmiZoneCapabilities hmiZoneCapability = HmiZoneCapabilities.valueForString( hmiZoneCapabilitiesArray.get(index).toString() );
				assertEquals(Test.MATCH, hmiZoneCapability, cmd.getHmiZoneCapabilities().get(index));
			}
			
			JSONArray prerecordedSpeechArray = JsonUtils.readJsonArrayFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_PRERECORDED_SPEECH);
			for (int index = 0; index < prerecordedSpeechArray.length(); index++) {
				PrerecordedSpeech prerecordedSpeech = PrerecordedSpeech.valueForString( prerecordedSpeechArray.get(index).toString() );
				assertEquals(Test.MATCH, prerecordedSpeech, cmd.getPrerecordedSpeech().get(index));
			}
			
			List<Integer> supportedDiagnosticModesList = JsonUtils.readIntegerListFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_SUPPORTED_DIAG_MODES);
			List<Integer> testDiagnosticModesList = cmd.getSupportedDiagModes();
			assertEquals(Test.MATCH, supportedDiagnosticModesList.size(), testDiagnosticModesList.size());
			assertTrue(Test.TRUE, Validator.validateIntegerList(supportedDiagnosticModesList, testDiagnosticModesList));
			
			JSONObject sdlMsgVersionObj = JsonUtils.readJsonObjectFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_SDL_MSG_VERSION);
			SdlMsgVersion sdlMsgVersion = new SdlMsgVersion(JsonRPCMarshaller.deserializeJSONObject(sdlMsgVersionObj));
			assertTrue(Test.TRUE, Validator.validateSdlMsgVersion(sdlMsgVersion, cmd.getSdlMsgVersion()) );
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_LANGUAGE), cmd.getLanguage().toString());
			
			JSONArray buttonCapabilitiesArray = JsonUtils.readJsonArrayFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_BUTTON_CAPABILITIES);
			List<ButtonCapabilities> buttonCapabilitiesList = new ArrayList<ButtonCapabilities>();
			for (int index = 0; index < buttonCapabilitiesArray.length(); index++) {
				ButtonCapabilities buttonCapability = new ButtonCapabilities(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)buttonCapabilitiesArray.get(index) ));
				buttonCapabilitiesList.add(buttonCapability);
			}
			assertTrue(Test.TRUE, Validator.validateButtonCapabilities(buttonCapabilitiesList, cmd.getButtonCapabilities() ));
			
			JSONObject displayCapabilitiesObj = JsonUtils.readJsonObjectFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_DISPLAY_CAPABILITIES);
			DisplayCapabilities displayCapabilities = new DisplayCapabilities(JsonRPCMarshaller.deserializeJSONObject(displayCapabilitiesObj));
			assertTrue(Test.TRUE,  Validator.validateDisplayCapabilities(displayCapabilities, cmd.getDisplayCapabilities()) );
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_HMI_DISPLAY_LANGUAGE), cmd.getHmiDisplayLanguage().toString());
			
			JSONArray softButtonCapabilitiesArray = JsonUtils.readJsonArrayFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_SOFT_BUTTON_CAPABILITIES);
			List<SoftButtonCapabilities> softButtonCapabilitiesList = new ArrayList<SoftButtonCapabilities>();
			for (int index = 0; index < softButtonCapabilitiesArray.length(); index++) {
				SoftButtonCapabilities softButtonCapability = 
						new SoftButtonCapabilities(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)softButtonCapabilitiesArray.get(index) ));
				softButtonCapabilitiesList.add(softButtonCapability);
			}
			assertTrue(Test.TRUE, Validator.validateSoftButtonCapabilities(softButtonCapabilitiesList, cmd.getSoftButtonCapabilities() ));
			
			JSONObject presetBankCapabilitiesObj = JsonUtils.readJsonObjectFromJsonObject(parameters, RegisterAppInterfaceResponse.KEY_PRESET_BANK_CAPABILITIES);
			PresetBankCapabilities presetBankCapabilities = new PresetBankCapabilities(JsonRPCMarshaller.deserializeJSONObject(presetBankCapabilitiesObj));
			assertTrue(Test.TRUE,  Validator.validatePresetBankCapabilities(presetBankCapabilities, cmd.getPresetBankCapabilities()) );
		} catch (JSONException e) {
			e.printStackTrace();
		}    	
    }
}