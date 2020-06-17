package com.smartdevicelink.test.rpc.requests;

import com.smartdevicelink.marshal.JsonRPCMarshaller;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AppInfo;
import com.smartdevicelink.proxy.rpc.DeviceInfo;
import com.smartdevicelink.proxy.rpc.RegisterAppInterface;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.TemplateColorScheme;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.TestValues;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;
import static android.support.test.InstrumentationRegistry.getContext;

/**
 * This is a unit test class for the SmartDeviceLink library project class : 
 * {@link com.smartdevicelink.proxy.rpc.RegisterAppInterface}
 */
public class RegisterAppInterfaceTests extends BaseRpcTests {

	@Override
	protected RPCMessage createMessage() {
		RegisterAppInterface msg = new RegisterAppInterface();

		msg.setSdlMsgVersion(TestValues.GENERAL_SDLMSGVERSION);
		msg.setAppName(TestValues.GENERAL_STRING);
		msg.setNgnMediaScreenAppName(TestValues.GENERAL_STRING);
		msg.setFullAppID(TestValues.GENERAL_FULL_APP_ID);
		msg.setLanguageDesired(TestValues.GENERAL_LANGUAGE);
		msg.setHmiDisplayLanguageDesired(TestValues.GENERAL_LANGUAGE);
		msg.setHashID(TestValues.GENERAL_STRING);
		msg.setTtsName(TestValues.GENERAL_TTSCHUNK_LIST);
		msg.setVrSynonyms(TestValues.GENERAL_STRING_LIST);
		msg.setAppHMIType(TestValues.GENERAL_APPHMITYPE_LIST);
		msg.setIsMediaApplication(TestValues.GENERAL_BOOLEAN);
		msg.setDeviceInfo(TestValues.GENERAL_DEVICEINFO);
		msg.setAppInfo(TestValues.GENERAL_APPINFO);
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
		return FunctionID.REGISTER_APP_INTERFACE.toString();
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result = new JSONObject();

		try {
			result.put(RegisterAppInterface.KEY_SDL_MSG_VERSION, TestValues.JSON_SDLMSGVERSION);
			result.put(RegisterAppInterface.KEY_APP_NAME, TestValues.GENERAL_STRING);
			result.put(RegisterAppInterface.KEY_NGN_MEDIA_SCREEN_APP_NAME, TestValues.GENERAL_STRING);
			result.put(RegisterAppInterface.KEY_APP_ID, TestValues.GENERAL_APP_ID);
			result.put(RegisterAppInterface.KEY_FULL_APP_ID, TestValues.GENERAL_FULL_APP_ID);
			result.put(RegisterAppInterface.KEY_LANGUAGE_DESIRED, TestValues.GENERAL_LANGUAGE);
			result.put(RegisterAppInterface.KEY_HMI_DISPLAY_LANGUAGE_DESIRED, TestValues.GENERAL_LANGUAGE);
			result.put(RegisterAppInterface.KEY_HASH_ID, TestValues.GENERAL_STRING);
			result.put(RegisterAppInterface.KEY_TTS_NAME, TestValues.JSON_TTSCHUNKS);
			result.put(RegisterAppInterface.KEY_VR_SYNONYMS, JsonUtils.createJsonArray(TestValues.GENERAL_STRING_LIST));
			result.put(RegisterAppInterface.KEY_APP_HMI_TYPE, JsonUtils.createJsonArrayOfJsonNames(TestValues.GENERAL_APPHMITYPE_LIST, SDL_VERSION_UNDER_TEST));
			result.put(RegisterAppInterface.KEY_IS_MEDIA_APPLICATION, TestValues.GENERAL_BOOLEAN);
			result.put(RegisterAppInterface.KEY_DEVICE_INFO, TestValues.JSON_DEVICEINFO);
			result.put(RegisterAppInterface.KEY_APP_INFO, TestValues.JSON_APPINFO);
			result.put(RegisterAppInterface.KEY_DAY_COLOR_SCHEME, TestValues.JSON_DAYCOLORSCHEME);
			result.put(RegisterAppInterface.KEY_NIGHT_COLOR_SCHEME, TestValues.JSON_NIGHTCOLORSCHEME);
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
		SdlMsgVersion testVersion = ( (RegisterAppInterface) msg).getSdlMsgVersion();
		String testName = ( (RegisterAppInterface) msg).getAppName();
		String testNgnName = ( (RegisterAppInterface) msg).getNgnMediaScreenAppName();
		String testAppId = ( (RegisterAppInterface) msg).getAppID();
		String testFullAppId = ( (RegisterAppInterface) msg).getFullAppID();
		Language testLang = ( (RegisterAppInterface) msg).getLanguageDesired();
		Language testHmiLang = ( (RegisterAppInterface) msg).getHmiDisplayLanguageDesired();
		String testHashId = ( (RegisterAppInterface) msg).getHashID();
		List<TTSChunk> testTts = ( (RegisterAppInterface) msg).getTtsName();
		List<String> testSynonyms = ( (RegisterAppInterface) msg).getVrSynonyms();
		List<AppHMIType> testApps = ( (RegisterAppInterface) msg).getAppHMIType();
		Boolean testMedia = ( (RegisterAppInterface) msg).getIsMediaApplication();
		DeviceInfo testDeviceInfo = ( (RegisterAppInterface) msg).getDeviceInfo();
		AppInfo testAppInfo = ( (RegisterAppInterface) msg).getAppInfo();
		TemplateColorScheme testDayColorScheme = ( (RegisterAppInterface) msg).getDayColorScheme();
		TemplateColorScheme testNightColorScheme = ( (RegisterAppInterface) msg).getNightColorScheme();

		// Valid Tests
		assertTrue(TestValues.TRUE, Validator.validateSdlMsgVersion(TestValues.GENERAL_SDLMSGVERSION, testVersion));
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testName);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testNgnName);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_APP_ID, testAppId);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_FULL_APP_ID, testFullAppId);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_LANGUAGE, testLang);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_LANGUAGE, testHmiLang);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING, testHashId);
		assertTrue(TestValues.TRUE, Validator.validateTtsChunks(TestValues.GENERAL_TTSCHUNK_LIST, testTts));
		assertEquals(TestValues.MATCH, TestValues.GENERAL_STRING_LIST, testSynonyms);
		assertEquals(TestValues.MATCH, TestValues.GENERAL_APPHMITYPE_LIST, testApps);
		assertEquals(TestValues.MATCH, (Boolean) TestValues.GENERAL_BOOLEAN, testMedia);
		assertTrue(TestValues.TRUE, Validator.validateDeviceInfo(TestValues.GENERAL_DEVICEINFO, testDeviceInfo));
		assertTrue(TestValues.TRUE, Validator.validateAppInfo(TestValues.GENERAL_APPINFO, testAppInfo));
		assertTrue(TestValues.TRUE, Validator.validateTemplateColorScheme(TestValues.GENERAL_DAYCOLORSCHEME, testDayColorScheme));
		assertTrue(TestValues.TRUE, Validator.validateTemplateColorScheme(TestValues.GENERAL_NIGHTCOLORSCHEME, testNightColorScheme));

		// Invalid/Null Tests
		RegisterAppInterface msg = new RegisterAppInterface();
		assertNotNull(TestValues.NOT_NULL, msg);
		testNullBase(msg);

		assertNull(TestValues.NULL, msg.getSdlMsgVersion());
		assertNull(TestValues.NULL, msg.getAppName());
		assertNull(TestValues.NULL, msg.getNgnMediaScreenAppName());
		assertNull(TestValues.NULL, msg.getAppID());
		assertNull(TestValues.NULL, msg.getFullAppID());
		assertNull(TestValues.NULL, msg.getLanguageDesired());
		assertNull(TestValues.NULL, msg.getHmiDisplayLanguageDesired());
		assertNull(TestValues.NULL, msg.getHashID());
		assertNull(TestValues.NULL, msg.getTtsName());
		assertNull(TestValues.NULL, msg.getVrSynonyms());
		assertNull(TestValues.NULL, msg.getAppHMIType());
		assertNull(TestValues.NULL, msg.getIsMediaApplication());
		assertNull(TestValues.NULL, msg.getDeviceInfo());
		assertNull(TestValues.NULL, msg.getAppInfo());
		assertNull(TestValues.NULL, msg.getDayColorScheme());
		assertNull(TestValues.NULL, msg.getNightColorScheme());
	}

    /**
     * Tests a valid JSON construction of this RPC message.
     */
    @Test
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getContext(), getCommandType(), getMessageType());
    	assertNotNull(TestValues.NOT_NULL, commandJson);

		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			RegisterAppInterface cmd = new RegisterAppInterface(hash);

			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull(TestValues.NOT_NULL, body);

			// Test everything in the json body.
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals(TestValues.MATCH, JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			JSONArray ttsNameArray = JsonUtils.readJsonArrayFromJsonObject(parameters, RegisterAppInterface.KEY_TTS_NAME);
			List<TTSChunk> ttsNameList = new ArrayList<TTSChunk>();
			for (int index = 0; index < ttsNameArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)ttsNameArray.get(index)) );
	        	ttsNameList.add(chunk);
			}
			assertTrue(TestValues.TRUE,  Validator.validateTtsChunks(ttsNameList, cmd.getTtsName()));
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterface.KEY_HMI_DISPLAY_LANGUAGE_DESIRED), cmd.getHmiDisplayLanguageDesired().toString());

			JSONArray appHmiTypeArray = JsonUtils.readJsonArrayFromJsonObject(parameters, RegisterAppInterface.KEY_APP_HMI_TYPE);
			for (int index = 0; index < appHmiTypeArray.length(); index++) {
				AppHMIType appHmiTypeItem = AppHMIType.valueForString( appHmiTypeArray.get(index).toString() );
				assertEquals(TestValues.MATCH, appHmiTypeItem, cmd.getAppHMIType().get(index) );
			}
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterface.KEY_APP_ID), cmd.getAppID());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterface.KEY_FULL_APP_ID), cmd.getFullAppID());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterface.KEY_LANGUAGE_DESIRED), cmd.getLanguageDesired().toString());

			JSONObject deviceInfoObj = JsonUtils.readJsonObjectFromJsonObject(parameters, RegisterAppInterface.KEY_DEVICE_INFO);
			DeviceInfo deviceInfo = new DeviceInfo(JsonRPCMarshaller.deserializeJSONObject(deviceInfoObj));
			assertTrue(TestValues.TRUE,  Validator.validateDeviceInfo(deviceInfo, cmd.getDeviceInfo()) );
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterface.KEY_APP_NAME), cmd.getAppName());
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterface.KEY_NGN_MEDIA_SCREEN_APP_NAME), cmd.getNgnMediaScreenAppName());
			assertEquals(TestValues.MATCH, JsonUtils.readBooleanFromJsonObject(parameters, RegisterAppInterface.KEY_IS_MEDIA_APPLICATION), cmd.getIsMediaApplication());

			JSONObject appInfoObj = JsonUtils.readJsonObjectFromJsonObject(parameters, RegisterAppInterface.KEY_APP_INFO);
			AppInfo appInfo = new AppInfo(JsonRPCMarshaller.deserializeJSONObject(appInfoObj));
			assertTrue(TestValues.TRUE,  Validator.validateAppInfo(appInfo, cmd.getAppInfo()));

			List<String> vrSynonymsList = JsonUtils.readStringListFromJsonObject(parameters, RegisterAppInterface.KEY_VR_SYNONYMS);
			List<String> testSynonymsList = cmd.getVrSynonyms();
			assertEquals(TestValues.MATCH, vrSynonymsList.size(), testSynonymsList.size());
			assertTrue(TestValues.TRUE, Validator.validateStringList(vrSynonymsList, testSynonymsList));

			JSONObject sdlMsgVersionObj = JsonUtils.readJsonObjectFromJsonObject(parameters, RegisterAppInterface.KEY_SDL_MSG_VERSION);
			SdlMsgVersion sdlMsgVersion = new SdlMsgVersion(JsonRPCMarshaller.deserializeJSONObject(sdlMsgVersionObj));
			assertTrue(TestValues.TRUE,  Validator.validateSdlMsgVersion(sdlMsgVersion, cmd.getSdlMsgVersion()) );
			assertEquals(TestValues.MATCH, JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterface.KEY_HASH_ID), cmd.getHashID());

			JSONObject dayColorSchemeObj = JsonUtils.readJsonObjectFromJsonObject(parameters, RegisterAppInterface.KEY_DAY_COLOR_SCHEME);
			TemplateColorScheme dayColorScheme = new TemplateColorScheme(JsonRPCMarshaller.deserializeJSONObject(dayColorSchemeObj));
			assertTrue(TestValues.TRUE,  Validator.validateTemplateColorScheme(dayColorScheme, cmd.getDayColorScheme()) );

			JSONObject nightColorSchemeObj = JsonUtils.readJsonObjectFromJsonObject(parameters, RegisterAppInterface.KEY_DAY_COLOR_SCHEME);
			TemplateColorScheme nightColorScheme = new TemplateColorScheme(JsonRPCMarshaller.deserializeJSONObject(nightColorSchemeObj));
			assertTrue(TestValues.TRUE,  Validator.validateTemplateColorScheme(nightColorScheme, cmd.getDayColorScheme()) );

		} catch (JSONException e) {
			fail(TestValues.JSON_FAIL);
		}
    }
}