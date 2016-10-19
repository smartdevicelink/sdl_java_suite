package com.smartdevicelink.test.rpc.requests;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.DeviceInfo;
import com.smartdevicelink.proxy.rpc.RegisterAppInterface;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Test;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.rpc.RegisterAppInterface}
 */
public class RegisterAppInterfaceTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		RegisterAppInterface msg = new RegisterAppInterface();

		msg.setSdlMsgVersion(Test.GENERAL_SDLMSGVERSION);
		msg.setAppName(Test.GENERAL_STRING);
		msg.setNgnMediaScreenAppName(Test.GENERAL_STRING);
		msg.setAppID(Test.GENERAL_STRING);
		msg.setLanguageDesired(Test.GENERAL_LANGUAGE);
		msg.setHmiDisplayLanguageDesired(Test.GENERAL_LANGUAGE);
		msg.setHashID(Test.GENERAL_STRING);
		msg.setTtsName(Test.GENERAL_TTSCHUNK_LIST);
		msg.setVrSynonyms(Test.GENERAL_STRING_LIST);
		msg.setAppHMIType(Test.GENERAL_APPHMITYPE_LIST);
		msg.setIsMediaApplication(Test.GENERAL_BOOLEAN);
		msg.setDeviceInfo(Test.GENERAL_DEVICEINFO);

		return msg;
	}
	
	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.REGISTER_APP_INTERFACE.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(RegisterAppInterface.KEY_SDL_MSG_VERSION, Test.JSON_SDLMSGVERSION);
			result.put(RegisterAppInterface.KEY_APP_NAME, Test.GENERAL_STRING);
			result.put(RegisterAppInterface.KEY_NGN_MEDIA_SCREEN_APP_NAME, Test.GENERAL_STRING);
			result.put(RegisterAppInterface.KEY_APP_ID, Test.GENERAL_STRING);
			result.put(RegisterAppInterface.KEY_LANGUAGE_DESIRED, Test.GENERAL_LANGUAGE);
			result.put(RegisterAppInterface.KEY_HMI_DISPLAY_LANGUAGE_DESIRED, Test.GENERAL_LANGUAGE);
			result.put(RegisterAppInterface.KEY_HASH_ID, Test.GENERAL_STRING);
			result.put(RegisterAppInterface.KEY_TTS_NAME, Test.JSON_TTSCHUNKS);
			result.put(RegisterAppInterface.KEY_VR_SYNONYMS, JsonUtils.createJsonArray(Test.GENERAL_STRING_LIST));
			result.put(RegisterAppInterface.KEY_APP_HMI_TYPE, JsonUtils.createJsonArrayOfJsonNames(Test.GENERAL_APPHMITYPE_LIST, SDL_VERSION_UNDER_TEST));
			result.put(RegisterAppInterface.KEY_IS_MEDIA_APPLICATION, Test.GENERAL_BOOLEAN);
			result.put(RegisterAppInterface.KEY_DEVICE_INFO, Test.JSON_DEVICEINFO);
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
		SdlMsgVersion testVersion = ( (RegisterAppInterface) msg).getSdlMsgVersion();
		String testName = ( (RegisterAppInterface) msg).getAppName();
		String testNgnName = ( (RegisterAppInterface) msg).getNgnMediaScreenAppName();
		String testAppId = ( (RegisterAppInterface) msg).getAppID();
		Language testLang = ( (RegisterAppInterface) msg).getLanguageDesired();
		Language testHmiLang = ( (RegisterAppInterface) msg).getHmiDisplayLanguageDesired();
		String testHashId = ( (RegisterAppInterface) msg).getHashID();
		List<TTSChunk> testTts = ( (RegisterAppInterface) msg).getTtsName();
		List<String> testSynonyms = ( (RegisterAppInterface) msg).getVrSynonyms();		
		List<AppHMIType> testApps = ( (RegisterAppInterface) msg).getAppHMIType();
		Boolean testMedia = ( (RegisterAppInterface) msg).getIsMediaApplication();
		DeviceInfo testDeviceInfo = ( (RegisterAppInterface) msg).getDeviceInfo();
		
		// Valid Tests
		assertTrue(Test.TRUE, Validator.validateSdlMsgVersion(Test.GENERAL_SDLMSGVERSION, testVersion));
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testName);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testNgnName);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testAppId);
		assertEquals(Test.MATCH, Test.GENERAL_LANGUAGE, testLang);
		assertEquals(Test.MATCH, Test.GENERAL_LANGUAGE, testHmiLang);
		assertEquals(Test.MATCH, Test.GENERAL_STRING, testHashId);
		assertTrue(Test.TRUE, Validator.validateTtsChunks(Test.GENERAL_TTSCHUNK_LIST, testTts));
		assertEquals(Test.MATCH, Test.GENERAL_STRING_LIST, testSynonyms);
		assertEquals(Test.MATCH, Test.GENERAL_APPHMITYPE_LIST, testApps);
		assertEquals(Test.MATCH, (Boolean) Test.GENERAL_BOOLEAN, testMedia);
		assertTrue(Test.TRUE, Validator.validateDeviceInfo(Test.GENERAL_DEVICEINFO, testDeviceInfo));
		
		// Invalid/Null Tests
		RegisterAppInterface msg = new RegisterAppInterface();
		assertNotNull(Test.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(Test.NULL, msg.getSdlMsgVersion());
		assertNull(Test.NULL, msg.getAppName());
		assertNull(Test.NULL, msg.getNgnMediaScreenAppName());
		assertNull(Test.NULL, msg.getAppID());
		assertNull(Test.NULL, msg.getLanguageDesired());
		assertNull(Test.NULL, msg.getHmiDisplayLanguageDesired());
		assertNull(Test.NULL, msg.getHashID());
		assertNull(Test.NULL, msg.getTtsName());
		assertNull(Test.NULL, msg.getVrSynonyms());
		assertNull(Test.NULL, msg.getAppHMIType());
		assertNull(Test.NULL, msg.getIsMediaApplication());
		assertNull(Test.NULL, msg.getDeviceInfo());
	}

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(this.mContext, getCommandType(), getMessageType());
    	assertNotNull(Test.NOT_NULL, commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			RegisterAppInterface cmd = new RegisterAppInterface(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(Test.NOT_NULL, body);
			
			// Test everything in the json body.
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(Test.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			JSONArray ttsNameArray = JsonUtils.readJsonArrayFromJsonObject(parameters, RegisterAppInterface.KEY_TTS_NAME);
			List<TTSChunk> ttsNameList = new ArrayList<TTSChunk>();
			for (int index = 0; index < ttsNameArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)ttsNameArray.get(index)) );
	        	ttsNameList.add(chunk);
			}
			assertTrue(Test.TRUE,  Validator.validateTtsChunks(ttsNameList, cmd.getTtsName()));
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterface.KEY_HMI_DISPLAY_LANGUAGE_DESIRED), cmd.getHmiDisplayLanguageDesired().toString());
			
			JSONArray appHmiTypeArray = JsonUtils.readJsonArrayFromJsonObject(parameters, RegisterAppInterface.KEY_APP_HMI_TYPE);
			for (int index = 0; index < appHmiTypeArray.length(); index++) {
				AppHMIType appHmiTypeItem = AppHMIType.valueForString( appHmiTypeArray.get(index).toString() );
				assertEquals(Test.MATCH, appHmiTypeItem, cmd.getAppHMIType().get(index) );
			}
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterface.KEY_APP_ID), cmd.getAppID());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterface.KEY_LANGUAGE_DESIRED), cmd.getLanguageDesired().toString());
			
			JSONObject deviceInfoObj = JsonUtils.readJsonObjectFromJsonObject(parameters, RegisterAppInterface.KEY_DEVICE_INFO);
			DeviceInfo deviceInfo = new DeviceInfo(JsonRPCMarshaller.deserializeJSONObject(deviceInfoObj));
			assertTrue(Test.TRUE,  Validator.validateDeviceInfo(deviceInfo, cmd.getDeviceInfo()) );
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterface.KEY_APP_NAME), cmd.getAppName());
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterface.KEY_NGN_MEDIA_SCREEN_APP_NAME), cmd.getNgnMediaScreenAppName());
			assertEquals(Test.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, RegisterAppInterface.KEY_IS_MEDIA_APPLICATION), cmd.getIsMediaApplication());

			List<String> vrSynonymsList = JsonUtils.readStringListFromJsonObject(parameters, RegisterAppInterface.KEY_VR_SYNONYMS);
			List<String> testSynonymsList = cmd.getVrSynonyms();
			assertEquals(Test.MATCH, vrSynonymsList.size(), testSynonymsList.size());
			assertTrue(Test.TRUE, Validator.validateStringList(vrSynonymsList, testSynonymsList));
			
			JSONObject sdlMsgVersionObj = JsonUtils.readJsonObjectFromJsonObject(parameters, RegisterAppInterface.KEY_SDL_MSG_VERSION);
			SdlMsgVersion sdlMsgVersion = new SdlMsgVersion(JsonRPCMarshaller.deserializeJSONObject(sdlMsgVersionObj));
			assertTrue(Test.TRUE,  Validator.validateSdlMsgVersion(sdlMsgVersion, cmd.getSdlMsgVersion()) );
			assertEquals(Test.MATCH, JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterface.KEY_HASH_ID), cmd.getHashID());
		} catch (JSONException e) {
			fail(Test.JSON_FAIL);
		}    	
    }	
}