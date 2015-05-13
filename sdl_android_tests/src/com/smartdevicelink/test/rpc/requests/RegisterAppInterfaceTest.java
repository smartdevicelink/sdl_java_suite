package com.smartdevicelink.test.rpc.requests;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.JsonUtils;
import com.smartdevicelink.test.Validator;
import com.smartdevicelink.test.json.rpc.JsonFileReader;

public class RegisterAppInterfaceTest extends BaseRpcTests {

	private List<TTSChunk> TTS_CHUNK_LIST = new ArrayList<TTSChunk>();
	private static final String TTS_CHUNK_TEXT_1 = "Welcome to the jungle";
	private static final SpeechCapabilities TTS_CHUNK_SPEECH_1 = SpeechCapabilities.TEXT;
	private static final String TTS_CHUNK_TEXT_2 = "Say a command";
	private static final SpeechCapabilities TTS_CHUNK_SPEECH_2 = SpeechCapabilities.LHPLUS_PHONEMES;
	
	private final List<AppHMIType> APP_HMI_TYPES = Arrays.asList(new AppHMIType[]{ AppHMIType.DEFAULT, AppHMIType.SOCIAL });	
	private static final SdlMsgVersion SDL_VERSION = new SdlMsgVersion();
	private static final String APP_NAME = "appName";
	private static final String MEDIA_SCREEN_APP_NAME = "mediaScreenAppName";
	private static final String APP_ID = "appId";
	private static final Language LANGUAGE_DESIRED = Language.EN_US;
	private static final Language HMI_LANGUAGE_DESIRED = Language.EN_US;
	private static final String HASH_ID = "hashId";
	private final List<String> VR_SYNONYMS = Arrays.asList(new String[]{"param1","param2"});
	private static final Boolean IS_MEDIA_APP = true;
	private static final DeviceInfo DEVICE_INFO = new DeviceInfo();
	
	@Override
	protected RPCMessage createMessage() {
		RegisterAppInterface msg = new RegisterAppInterface();

		createCustomObjects();
		
		msg.setSdlMsgVersion(SDL_VERSION);
		msg.setAppName(APP_NAME);
		msg.setNgnMediaScreenAppName(MEDIA_SCREEN_APP_NAME);
		msg.setAppID(APP_ID);
		msg.setLanguageDesired(LANGUAGE_DESIRED);
		msg.setHmiDisplayLanguageDesired(HMI_LANGUAGE_DESIRED);
		msg.setHashID(HASH_ID);
		msg.setTtsName(TTS_CHUNK_LIST);
		msg.setVrSynonyms(VR_SYNONYMS);
		msg.setAppHMIType(APP_HMI_TYPES);
		msg.setIsMediaApplication(IS_MEDIA_APP);
		msg.setDeviceInfo(DEVICE_INFO);

		return msg;
	}
	
	public void createCustomObjects () {
		TTSChunk ttsChunk = new TTSChunk();
		ttsChunk.setText(TTS_CHUNK_TEXT_1);
		ttsChunk.setType(TTS_CHUNK_SPEECH_1);
		TTS_CHUNK_LIST.add(ttsChunk);
		
		ttsChunk = new TTSChunk();
		ttsChunk.setText(TTS_CHUNK_TEXT_2);
		ttsChunk.setType(TTS_CHUNK_SPEECH_2);
		TTS_CHUNK_LIST.add(ttsChunk);
        
		SDL_VERSION.setMajorVersion(1);
		SDL_VERSION.setMinorVersion(0);
		
		DEVICE_INFO.setHardware("hardware");
		DEVICE_INFO.setFirmwareRev("firmware");
		DEVICE_INFO.setOs("os");
		DEVICE_INFO.setOsVersion("osVersion");
		DEVICE_INFO.setCarrier("carrier");
		DEVICE_INFO.setMaxNumberRFCOMMPorts(1);
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_REQUEST;
	}

	@Override
	protected String getCommandType() {
		return FunctionID.REGISTER_APP_INTERFACE;
	}

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result      = new JSONObject(),
				   ttsChunk    = new JSONObject();
		JSONArray  ttsChunks   = new JSONArray();

		try {
			ttsChunk.put(TTSChunk.KEY_TEXT, TTS_CHUNK_TEXT_1);
			ttsChunk.put(TTSChunk.KEY_TYPE, TTS_CHUNK_SPEECH_1);
			ttsChunks.put(ttsChunk);
			
			ttsChunk = new JSONObject();
			ttsChunk.put(TTSChunk.KEY_TEXT, TTS_CHUNK_TEXT_2);
			ttsChunk.put(TTSChunk.KEY_TYPE, TTS_CHUNK_SPEECH_2);
			ttsChunks.put(ttsChunk);
						
			result.put(RegisterAppInterface.KEY_SDL_MSG_VERSION, SDL_VERSION.serializeJSON());
			result.put(RegisterAppInterface.KEY_APP_NAME, APP_NAME);
			result.put(RegisterAppInterface.KEY_NGN_MEDIA_SCREEN_APP_NAME, MEDIA_SCREEN_APP_NAME);
			result.put(RegisterAppInterface.KEY_APP_ID, APP_ID);
			result.put(RegisterAppInterface.KEY_LANGUAGE_DESIRED, LANGUAGE_DESIRED);
			result.put(RegisterAppInterface.KEY_HMI_DISPLAY_LANGUAGE_DESIRED, HMI_LANGUAGE_DESIRED);
			result.put(RegisterAppInterface.KEY_HASH_ID, HASH_ID);
			result.put(RegisterAppInterface.KEY_TTS_NAME, ttsChunks);
			result.put(RegisterAppInterface.KEY_VR_SYNONYMS, JsonUtils.createJsonArray(VR_SYNONYMS));
			result.put(RegisterAppInterface.KEY_APP_HMI_TYPE, JsonUtils.createJsonArrayOfJsonNames(APP_HMI_TYPES, SDL_VERSION_UNDER_TEST));
			result.put(RegisterAppInterface.KEY_IS_MEDIA_APPLICATION, IS_MEDIA_APP);
			result.put(RegisterAppInterface.KEY_DEVICE_INFO, DEVICE_INFO.serializeJSON());
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testSdlVersion() {
		SdlMsgVersion copy = ( (RegisterAppInterface) msg).getSdlMsgVersion();
		
		assertTrue("Input value didn't match expected value.", Validator.validateSdlMsgVersion(SDL_VERSION,copy));
	}
	
	public void testAppName () {
		String copy = ( (RegisterAppInterface) msg).getAppName();
		
		assertEquals("Data didn't match input data.", APP_NAME, copy);
	}
	
	public void testNgnMediaAppName () {
		String copy = ( (RegisterAppInterface) msg).getNgnMediaScreenAppName();
		
		assertEquals("Data didn't match input data.", MEDIA_SCREEN_APP_NAME, copy);
	}
	
	public void testAppId () {
		String copy = ( (RegisterAppInterface) msg).getAppID();
		
		assertEquals("Data didn't match input data.", APP_ID, copy);
	}
	
	public void testLanguageDesired () {
		Language copy = ( (RegisterAppInterface) msg).getLanguageDesired();
		
		assertEquals("Data didn't match input data.", LANGUAGE_DESIRED, copy);
	}
	
	public void testHmiLanguageDesired () {
		Language copy = ( (RegisterAppInterface) msg).getHmiDisplayLanguageDesired();
		
		assertEquals("Data didn't match input data.", HMI_LANGUAGE_DESIRED, copy);
	}
	
	public void testHashId () {
		String copy = ( (RegisterAppInterface) msg).getHashID();
		
		assertEquals("Data didn't match input data.", HASH_ID, copy);
	}
	
	public void testTtsName () {
		List<TTSChunk> copy = ( (RegisterAppInterface) msg).getTtsName();
		
		assertNotNull("Tts names were null.", copy);
		assertTrue("Tts names items didn't match input data.", Validator.validateTtsChunks(TTS_CHUNK_LIST, copy));
	}
	
	public void testVrSynonyms () {
		List<String> copy = ( (RegisterAppInterface) msg).getVrSynonyms();
		
		assertEquals("Data didn't match input data.", VR_SYNONYMS, copy);
	}
	
	public void testAppHmiType () {
		List<AppHMIType> copy = ( (RegisterAppInterface) msg).getAppHMIType();
		
		assertEquals("Data didn't match input data.", APP_HMI_TYPES, copy);
	}
	
	public void testIsMediaApp () {
		Boolean copy = ( (RegisterAppInterface) msg).getIsMediaApplication();
		
		assertEquals("Data didn't match input data.", IS_MEDIA_APP, copy);
	}
	
	public void testDeviceInfo () {
		DeviceInfo copy = ( (RegisterAppInterface) msg).getDeviceInfo();
		
		assertTrue("Input value didn't match expected value.", Validator.validateDeviceInfo(DEVICE_INFO,copy));
	}

	public void testNull() {
		RegisterAppInterface msg = new RegisterAppInterface();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("Sdl version wasn't set, but getter method returned an object.", msg.getSdlMsgVersion());
		assertNull("App name wasn't set, but getter method returned an object.", msg.getAppName());
		assertNull("Media app name wasn't set, but getter method returned an object.", msg.getNgnMediaScreenAppName());
		assertNull("App id wasn't set, but getter method returned an object.", msg.getAppID());
		assertNull("Language desired wasn't set, but getter method returned an object.", msg.getLanguageDesired());
		assertNull("Hmi language desired wasn't set, but getter method returned an object.", msg.getHmiDisplayLanguageDesired());
		assertNull("Hash id wasn't set, but getter method returned an object.", msg.getHashID());
		assertNull("Tts name wasn't set, but getter method returned an object.", msg.getTtsName());
		assertNull("Vr synonyms wasn't set, but getter method returned an object.", msg.getVrSynonyms());
		assertNull("App hmi type wasn't set, but getter method returned an object.", msg.getAppHMIType());
		assertNull("Is media app wasn't set, but getter method returned an object.", msg.getIsMediaApplication());
		assertNull("Device info wasn't set, but getter method returned an object.", msg.getDeviceInfo());
	}
	
    public void testJsonConstructor () {
    	JSONObject commandJson = JsonFileReader.readId(getCommandType(), getMessageType());
    	assertNotNull("Command object is null", commandJson);
    	
		try {
			Hashtable<String, Object> hash = JsonRPCMarshaller.deserializeJSONObject(commandJson);
			RegisterAppInterface cmd = new RegisterAppInterface(hash);
			
			JSONObject body = JsonUtils.readJsonObjectFromJsonObject(commandJson, getMessageType());
			assertNotNull("Command type doesn't match expected message type", body);
			
			// test everything in the body
			assertEquals("Command name doesn't match input name", JsonUtils.readStringFromJsonObject(body, RPCMessage.KEY_FUNCTION_NAME), cmd.getFunctionName());
			assertEquals("Correlation ID doesn't match input ID", JsonUtils.readIntegerFromJsonObject(body, RPCMessage.KEY_CORRELATION_ID), cmd.getCorrelationID());

			JSONObject parameters = JsonUtils.readJsonObjectFromJsonObject(body, RPCMessage.KEY_PARAMETERS);
			
			JSONArray ttsNameArray = JsonUtils.readJsonArrayFromJsonObject(parameters, RegisterAppInterface.KEY_TTS_NAME);
			List<TTSChunk> ttsNameList = new ArrayList<TTSChunk>();
			for (int index = 0; index < ttsNameArray.length(); index++) {
	        	TTSChunk chunk = new TTSChunk(JsonRPCMarshaller.deserializeJSONObject( (JSONObject)ttsNameArray.get(index)) );
	        	ttsNameList.add(chunk);
			}
			assertTrue("TTSName list doesn't match input TTSName list",  Validator.validateTtsChunks(ttsNameList, cmd.getTtsName()));
			
			assertEquals("HMI display language desired doesn't match input language desired", 
					JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterface.KEY_HMI_DISPLAY_LANGUAGE_DESIRED), cmd.getHmiDisplayLanguageDesired().toString());
			
			JSONArray appHmiTypeArray = JsonUtils.readJsonArrayFromJsonObject(parameters, RegisterAppInterface.KEY_APP_HMI_TYPE);
			for (int index = 0; index < appHmiTypeArray.length(); index++) {
				AppHMIType appHmiTypeItem = AppHMIType.valueForString( appHmiTypeArray.get(index).toString() );
				assertEquals("App HMI type item doesn't match input HMI type", appHmiTypeItem, cmd.getAppHMIType().get(index) );
			}
			
			assertEquals("App ID doesn't match input ID", JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterface.KEY_APP_ID), cmd.getAppID());
			assertEquals("Language desired doesn't match input language", 
					JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterface.KEY_LANGUAGE_DESIRED), cmd.getLanguageDesired().toString());
			
			JSONObject deviceInfoObj = JsonUtils.readJsonObjectFromJsonObject(parameters, RegisterAppInterface.KEY_DEVICE_INFO);
			DeviceInfo deviceInfo = new DeviceInfo(JsonRPCMarshaller.deserializeJSONObject(deviceInfoObj));
			assertTrue("Device info doesn't match input device info",  Validator.validateDeviceInfo(deviceInfo, cmd.getDeviceInfo()) );
			
			assertEquals("App name doesn't match input name", 
					JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterface.KEY_APP_NAME), cmd.getAppName());
			assertEquals("NGN media screen app name doesn't match input name", 
					JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterface.KEY_NGN_MEDIA_SCREEN_APP_NAME), cmd.getNgnMediaScreenAppName());
			assertEquals("Media application doesn't match input media application", 
					JsonUtils.readBooleanFromJsonObject(parameters, RegisterAppInterface.KEY_IS_MEDIA_APPLICATION), cmd.getIsMediaApplication());

			List<String> vrSynonymsList = JsonUtils.readStringListFromJsonObject(parameters, RegisterAppInterface.KEY_VR_SYNONYMS);
			List<String> testSynonymsList = cmd.getVrSynonyms();
			assertEquals("VR synonym list length not same as reference VR synonym list", vrSynonymsList.size(), testSynonymsList.size());
			assertTrue("VR synonym list doesn't match input synonym list", Validator.validateStringList(vrSynonymsList, testSynonymsList));
			
			JSONObject sdlMsgVersionObj = JsonUtils.readJsonObjectFromJsonObject(parameters, RegisterAppInterface.KEY_SDL_MSG_VERSION);
			SdlMsgVersion sdlMsgVersion = new SdlMsgVersion(JsonRPCMarshaller.deserializeJSONObject(sdlMsgVersionObj));
			assertTrue("SDL message version doesn't match input version",  Validator.validateSdlMsgVersion(sdlMsgVersion, cmd.getSdlMsgVersion()) );
			
			assertEquals("Hash ID doesn't match input ID", JsonUtils.readStringFromJsonObject(parameters, RegisterAppInterface.KEY_HASH_ID), cmd.getHashID());
			
		} 
		catch (JSONException e) {
			e.printStackTrace();
		}
    	
    }
	
}
