package com.smartdevicelink.test.rpc.requests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.TTSChunkFactory;
import com.smartdevicelink.proxy.rpc.DeviceInfo;
import com.smartdevicelink.proxy.rpc.RegisterAppInterface;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class RegisterAppInterfaceTest extends BaseRpcTests {

	private List<TTSChunk> ttsNames;
	
	private static final List<AppHMIType> APP_HMI_TYPES = Arrays.asList(new AppHMIType[]{ AppHMIType.DEFAULT, AppHMIType.SOCIAL });	
	private static final SdlMsgVersion SDL_VERSION = new SdlMsgVersion();
	private static final String APP_NAME = "appName";
	private static final String MEDIA_SCREEN_APP_NAME = "mediaScreenAppName";
	private static final String APP_ID = "appId";
	private static final Language LANGUAGE_DESIRED = Language.EN_US;
	private static final Language HMI_LANGUAGE_DESIRED = Language.EN_US;
	private static final String HASH_ID = "hashId";
	private static final List<String> VR_SYNONYMS = Arrays.asList(new String[]{"param1","param2"});
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
		msg.setTtsName(ttsNames);
		msg.setVrSynonyms(VR_SYNONYMS);
		msg.setAppHMIType(APP_HMI_TYPES);
		msg.setIsMediaApplication(IS_MEDIA_APP);
		msg.setDeviceInfo(DEVICE_INFO);

		return msg;
	}
	
	public void createCustomObjects () {
		ttsNames = new ArrayList<TTSChunk>(2);
		ttsNames.add(TTSChunkFactory.createChunk(SpeechCapabilities.TEXT, "Welcome to the jungle"));
        ttsNames.add(TTSChunkFactory.createChunk(SpeechCapabilities.TEXT, "Say a command"));
        
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
			ttsChunk.put(TTSChunk.KEY_TEXT, "Welcome to the jungle");
			ttsChunk.put(TTSChunk.KEY_TYPE, SpeechCapabilities.TEXT);
			ttsChunks.put(ttsChunk);
			
			ttsChunk = new JSONObject();
			ttsChunk.put(TTSChunk.KEY_TEXT, "Say a command");
			ttsChunk.put(TTSChunk.KEY_TYPE, SpeechCapabilities.TEXT);
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
		
		assertNotSame("Initial prompt was not defensive copied.", SDL_VERSION, copy);
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
		assertNotSame("Tts names items were not defensive copied.", ttsNames, copy);
		assertTrue("Tts names items didn't match input data.", Validator.validateTtsChunks(ttsNames, copy));
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
		
		assertNotSame("Initial prompt was not defensive copied.", DEVICE_INFO, copy);
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
}
