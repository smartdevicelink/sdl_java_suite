package com.smartdevicelink.test.rpc.responses;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AudioPassThruCapabilities;
import com.smartdevicelink.proxy.rpc.ButtonCapabilities;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.PresetBankCapabilities;
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.ScreenParams;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.TouchEventCapabilities;
import com.smartdevicelink.proxy.rpc.VehicleType;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.CharacterSet;
import com.smartdevicelink.proxy.rpc.enums.DisplayType;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.HmiZoneCapabilities;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.MediaClockFormat;
import com.smartdevicelink.proxy.rpc.enums.PrerecordedSpeech;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.proxy.rpc.enums.VrCapabilities;
import com.smartdevicelink.test.BaseRpcTests;
import com.smartdevicelink.test.utils.JsonUtils;
import com.smartdevicelink.test.utils.Validator;

public class RegisterAppInterfaceResponseTest extends BaseRpcTests {

	private static final List<PrerecordedSpeech>   PRERECORDED_SPEECH = 
			Arrays.asList(new PrerecordedSpeech[]{ PrerecordedSpeech.POSITIVE_JINGLE, PrerecordedSpeech.NEGATIVE_JINGLE });
	private static final List<HmiZoneCapabilities> HMI_ZONE_CAPABILITIES = 
			Arrays.asList(new HmiZoneCapabilities[]{ HmiZoneCapabilities.BACK, HmiZoneCapabilities.FRONT });
	private static final List<SpeechCapabilities>  SPEECH_CAPABILITIES = 
			Arrays.asList(new SpeechCapabilities[]{ SpeechCapabilities.SILENCE, SpeechCapabilities.TEXT });
	private static final List<VrCapabilities>      VR_CAPABILITIES = 
			Arrays.asList(new VrCapabilities[]{ VrCapabilities.Text });
	
	private DisplayCapabilities             displayCapabilities;
	private List<ButtonCapabilities>        buttonCapabilities;
	private List<SoftButtonCapabilities>    softButtonCapabilities;
	private List<AudioPassThruCapabilities> audioPassThruCapabilities;
	
	private static final PresetBankCapabilities PRESET_BANK_CAPABILITIES = new PresetBankCapabilities();
	private static final VehicleType            VEHICLE_TYPE             = new VehicleType();	
	private static final SdlMsgVersion          SDL_MSG_VERSION          = new SdlMsgVersion();
	
	private static final Language LANGUAGE     = Language.EN_US;
	private static final Language HMI_LANGUAGE = Language.EN_US;
	
	private static final List<Integer> SUPPORTED_DIAG_MODES = Arrays.asList(new Integer[]{ 0,1 });	
	
	// Variables for displayCapabilities
	private ScreenParams screenParams;
	private List<String> temps = Arrays.asList(new String[]{"a","b"});
	private List<ImageField> imageList;
	private List<TextField>  textFields;
	private List<MediaClockFormat> mediaClockFormatList;
	
	@Override
	protected RPCMessage createMessage() {
		RegisterAppInterfaceResponse msg = new RegisterAppInterfaceResponse();

		createCustomObjects();
		
		msg.setSdlMsgVersion(SDL_MSG_VERSION);
		msg.setLanguage(LANGUAGE);
		msg.setHmiDisplayLanguage(HMI_LANGUAGE);
		msg.setDisplayCapabilities(displayCapabilities);
		msg.setPresetBankCapabilities(PRESET_BANK_CAPABILITIES);
		msg.setVehicleType(VEHICLE_TYPE);
		msg.setButtonCapabilities(buttonCapabilities);
		msg.setSoftButtonCapabilities(softButtonCapabilities);
		msg.setAudioPassThruCapabilities(audioPassThruCapabilities);
		msg.setHmiZoneCapabilities(HMI_ZONE_CAPABILITIES);
		msg.setSpeechCapabilities(SPEECH_CAPABILITIES);
		msg.setVrCapabilities(VR_CAPABILITIES);
		msg.setPrerecordedSpeech(PRERECORDED_SPEECH);
		msg.setSupportedDiagModes(SUPPORTED_DIAG_MODES);

		return msg;
	}
	
	private void createCustomObjects () {
		screenParams			  = new ScreenParams();
		displayCapabilities       = new DisplayCapabilities();
		imageList				  = new ArrayList<ImageField>(2);
		textFields				  = new ArrayList<TextField>(2);
		buttonCapabilities        = new ArrayList<ButtonCapabilities>(2);	
		mediaClockFormatList      = new ArrayList<MediaClockFormat>(2);
		softButtonCapabilities    = new ArrayList<SoftButtonCapabilities>(2);
		audioPassThruCapabilities = new ArrayList<AudioPassThruCapabilities>(2);		
		
		ImageResolution resolution = new ImageResolution();
		resolution.setResolutionHeight(10);
		resolution.setResolutionWidth(10);
		
		ImageField image = new ImageField();
		image.setName(ImageFieldName.appIcon);
		image.setImageResolution(resolution);

		List<FileType> fileListBinary = new ArrayList<FileType>();
		fileListBinary.add(FileType.BINARY);
		image.setImageTypeSupported(fileListBinary);
		imageList.add(image);
		
		resolution = new ImageResolution();
		resolution.setResolutionHeight(50);
		resolution.setResolutionWidth(50);
		
		image = new ImageField();
		image.setName(ImageFieldName.graphic);
		image.setImageResolution(resolution);

		List<FileType> fileListJPEG = new ArrayList<FileType>();
		fileListJPEG.add(FileType.GRAPHIC_JPEG);
		image.setImageTypeSupported(fileListJPEG);
		imageList.add(image);
		
		mediaClockFormatList.add(MediaClockFormat.CLOCK1);
		mediaClockFormatList.add(MediaClockFormat.CLOCK2);
		
		TouchEventCapabilities touch = new TouchEventCapabilities();
		touch.setPressAvailable(true);
		touch.setDoublePressAvailable(true);
		touch.setMultiTouchAvailable(false);
		
		screenParams.setImageResolution(resolution);
		screenParams.setTouchEventAvailable(touch);
		
		TextField text = new TextField();
		text.setName(TextFieldName.ETA);
		text.setRows(5);
		text.setWidth(5);
		text.setCharacterSet(CharacterSet.TYPE5SET);
		textFields.add(text);
		
		text = new TextField();
		text.setName(TextFieldName.ETA);
		text.setRows(10);
		text.setWidth(10);
		text.setCharacterSet(CharacterSet.TYPE2SET);
		textFields.add(text);
		
		displayCapabilities.setDisplayType(DisplayType.CID);
		displayCapabilities.setGraphicSupported(true);
		displayCapabilities.setImageFields(imageList);
		displayCapabilities.setMediaClockFormats(mediaClockFormatList);
		displayCapabilities.setNumCustomPresetsAvailable(1);
		displayCapabilities.setScreenParams(screenParams);
		displayCapabilities.setTemplatesAvailable(temps);
		displayCapabilities.setTextFields(textFields);
		
		ButtonCapabilities button1 = new ButtonCapabilities();
		button1.setName(ButtonName.OK);
		button1.setShortPressAvailable(true);
		button1.setLongPressAvailable(true);
		button1.setUpDownAvailable(false);
		buttonCapabilities.add(button1);
		
		button1 = new ButtonCapabilities();
		button1.setName(ButtonName.CUSTOM_BUTTON);
		button1.setShortPressAvailable(false);
		button1.setLongPressAvailable(false);
		button1.setUpDownAvailable(false);
		buttonCapabilities.add(button1);
		
		SoftButtonCapabilities button2 = new SoftButtonCapabilities();
		button2.setImageSupported(false);
		button2.setLongPressAvailable(true);
		button2.setShortPressAvailable(true);
		button2.setUpDownAvailable(true);
		softButtonCapabilities.add(button2);
		
		button2 = new SoftButtonCapabilities();
		button2.setImageSupported(true);
		button2.setLongPressAvailable(false);
		button2.setShortPressAvailable(false);
		button2.setUpDownAvailable(false);
		softButtonCapabilities.add(button2);
		
		AudioPassThruCapabilities audio = new AudioPassThruCapabilities();
		audio.setAudioType(AudioType.PCM);
		audio.setSamplingRate(SamplingRate._8KHZ);
		audio.setBitsPerSample(BitsPerSample._8_BIT);
		audioPassThruCapabilities.add(audio);
		
		audio = new AudioPassThruCapabilities();
		audio.setAudioType(AudioType.PCM);
		audio.setSamplingRate(SamplingRate._16KHZ);
		audio.setBitsPerSample(BitsPerSample._16_BIT);
		audioPassThruCapabilities.add(audio);
		
		PRESET_BANK_CAPABILITIES.setOnScreenPresetsAvailable(true);
		
		VEHICLE_TYPE.setMake("make");
		VEHICLE_TYPE.setModel("model");
		VEHICLE_TYPE.setModelYear("year");
		VEHICLE_TYPE.setTrim("trim");
		
		SDL_MSG_VERSION.setMajorVersion(0);
		SDL_MSG_VERSION.setMinorVersion(1);
	}

	@Override
	protected String getMessageType() {
		return RPCMessage.KEY_RESPONSE;
	}
	
	@Override
	protected String getCommandType() {
		return FunctionID.REGISTER_APP_INTERFACE;
	}
	

	@Override
	protected JSONObject getExpectedParameters(int sdlVersion) {
		JSONObject result      = new JSONObject(),
				   audioPass   = new JSONObject(),
				   softButton  = new JSONObject(),
				   button      = new JSONObject(),
				   display     = new JSONObject(),
				   image       = new JSONObject(),
				   resolution  = new JSONObject(),
				   screen      = new JSONObject(),
				   touch       = new JSONObject(),
				   field       = new JSONObject();
		JSONArray  fields      = new JSONArray(),
				   images      = new JSONArray(),
				   buttons     = new JSONArray(),
		           softButtons = new JSONArray(),
		           audioPasses = new JSONArray();

		try {		
			audioPass.put(AudioPassThruCapabilities.KEY_AUDIO_TYPE,      AudioType.PCM);
			audioPass.put(AudioPassThruCapabilities.KEY_SAMPLING_RATE,   SamplingRate._8KHZ);
			audioPass.put(AudioPassThruCapabilities.KEY_BITS_PER_SAMPLE, BitsPerSample._8_BIT);
			audioPasses.put(audioPass);
			
			audioPass = new JSONObject();
			audioPass.put(AudioPassThruCapabilities.KEY_AUDIO_TYPE,      AudioType.PCM);
			audioPass.put(AudioPassThruCapabilities.KEY_SAMPLING_RATE,   SamplingRate._16KHZ);
			audioPass.put(AudioPassThruCapabilities.KEY_BITS_PER_SAMPLE, BitsPerSample._8_BIT);
			audioPasses.put(audioPass);
			
			softButton.put(SoftButtonCapabilities.KEY_IMAGE_SUPPORTED,       false);
			softButton.put(SoftButtonCapabilities.KEY_UP_DOWN_AVAILABLE,     true);
			softButton.put(SoftButtonCapabilities.KEY_LONG_PRESS_AVAILABLE,  true);
			softButton.put(SoftButtonCapabilities.KEY_SHORT_PRESS_AVAILABLE, true);
			softButtons.put(softButton);
			
			softButton = new JSONObject();
			softButton.put(SoftButtonCapabilities.KEY_IMAGE_SUPPORTED,       true);
			softButton.put(SoftButtonCapabilities.KEY_UP_DOWN_AVAILABLE,     false);
			softButton.put(SoftButtonCapabilities.KEY_LONG_PRESS_AVAILABLE,  false);
			softButton.put(SoftButtonCapabilities.KEY_SHORT_PRESS_AVAILABLE, false);
			softButtons.put(softButton);
			
			button.put(ButtonCapabilities.KEY_NAME,           ButtonName.OK);
			button.put(ButtonCapabilities.KEY_UP_DOWN_AVAILABLE,     false);
			button.put(ButtonCapabilities.KEY_LONG_PRESS_AVAILABLE,  true);
			button.put(ButtonCapabilities.KEY_SHORT_PRESS_AVAILABLE, true);
			buttons.put(button);
			
			button = new JSONObject();
			button.put(ButtonCapabilities.KEY_NAME,           ButtonName.CUSTOM_BUTTON);
			button.put(ButtonCapabilities.KEY_UP_DOWN_AVAILABLE,     true);
			button.put(ButtonCapabilities.KEY_LONG_PRESS_AVAILABLE,  false);
			button.put(ButtonCapabilities.KEY_SHORT_PRESS_AVAILABLE, false);
			
			resolution.put(ImageResolution.KEY_RESOLUTION_HEIGHT, 10);
			resolution.put(ImageResolution.KEY_RESOLUTION_WIDTH,  10);
			//TODO: correct value to put in? ImageFieldName.appIcon.name()
			image.put(ImageField.KEY_NAME,                 ImageFieldName.appIcon.name());
			image.put(ImageField.KEY_IMAGE_RESOLUTION,     resolution);
			image.put(ImageField.KEY_IMAGE_TYPE_SUPPORTED, FileType.BINARY);
			images.put(image);
			
			resolution = new JSONObject();
			resolution.put(ImageResolution.KEY_RESOLUTION_HEIGHT, 50);
			resolution.put(ImageResolution.KEY_RESOLUTION_WIDTH,  50);
			//TODO: correct value to put in? ImageFieldName.graphic.name()
			image = new JSONObject();
			image.put(ImageField.KEY_NAME,                 ImageFieldName.graphic.name());
			image.put(ImageField.KEY_IMAGE_RESOLUTION,     resolution);
			image.put(ImageField.KEY_IMAGE_TYPE_SUPPORTED, FileType.GRAPHIC_JPEG);
			images.put(image);	
			
			touch.put(TouchEventCapabilities.KEY_PRESS_AVAILABLE,        true);
			touch.put(TouchEventCapabilities.KEY_MULTI_TOUCH_AVAILABLE,  false);
			touch.put(TouchEventCapabilities.KEY_DOUBLE_PRESS_AVAILABLE, true);
			
			screen.put(ScreenParams.KEY_RESOLUTION, resolution);
			screen.put(ScreenParams.KEY_TOUCH_EVENT_AVAILABLE, touch);
			
			field.put(TextField.KEY_NAME,          TextFieldName.ETA);
			field.put(TextField.KEY_WIDTH,         5);
			field.put(TextField.KEY_ROWS,          5);
			field.put(TextField.KEY_CHARACTER_SET, CharacterSet.TYPE5SET);
			fields.put(field);
			
			field = new JSONObject();
			field.put(TextField.KEY_NAME,          TextFieldName.ETA);
			field.put(TextField.KEY_WIDTH,         10);
			field.put(TextField.KEY_ROWS,          10);
			field.put(TextField.KEY_CHARACTER_SET, CharacterSet.TYPE2SET);
			fields.put(field);
			
			display.put(DisplayCapabilities.KEY_DISPLAY_TYPE,             DisplayType.CID);
			display.put(DisplayCapabilities.KEY_GRAPHIC_SUPPORTED,        true);
			display.put(DisplayCapabilities.KEY_IMAGE_FIELDS,             images);
			display.put(DisplayCapabilities.KEY_MEDIA_CLOCK_FORMATS,      JsonUtils.createJsonArrayOfJsonNames(mediaClockFormatList, SDL_VERSION_UNDER_TEST));
			display.put(DisplayCapabilities.KEY_NUM_CUSTOM_PRESETS_AVAILABLE, 1);	
			display.put(DisplayCapabilities.KEY_SCREEN_PARAMS,            screen);
			display.put(DisplayCapabilities.KEY_TEMPLATES_AVAILABLE,      JsonUtils.createJsonArray(temps));
			display.put(DisplayCapabilities.KEY_TEXT_FIELDS,              fields);
			
			result.put(RegisterAppInterfaceResponse.KEY_LANGUAGE,     LANGUAGE);
			result.put(RegisterAppInterfaceResponse.KEY_HMI_DISPLAY_LANGUAGE, HMI_LANGUAGE);
			
			result.put(RegisterAppInterfaceResponse.KEY_SUPPORTED_DIAG_MODES, JsonUtils.createJsonArray(SUPPORTED_DIAG_MODES));			
			
			result.put(RegisterAppInterfaceResponse.KEY_SDL_MSG_VERSION,          SDL_MSG_VERSION.serializeJSON());
			result.put(RegisterAppInterfaceResponse.KEY_VEHICLE_TYPE,             VEHICLE_TYPE.serializeJSON());
			result.put(RegisterAppInterfaceResponse.KEY_PRESET_BANK_CAPABILITIES, PRESET_BANK_CAPABILITIES.serializeJSON());
			
			result.put(RegisterAppInterfaceResponse.KEY_DISPLAY_CAPABILITIES,         display);	
			result.put(RegisterAppInterfaceResponse.KEY_BUTTON_CAPABILITIES,          buttons);
			result.put(RegisterAppInterfaceResponse.KEY_SOFT_BUTTON_CAPABILITIES,     softButtons);
			result.put(RegisterAppInterfaceResponse.KEY_AUDIO_PASS_THRU_CAPABILITIES, audioPasses);				
			
			result.put(RegisterAppInterfaceResponse.KEY_SPEECH_CAPABILITIES,   
					JsonUtils.createJsonArrayOfJsonNames(SPEECH_CAPABILITIES, SDL_VERSION_UNDER_TEST));
			result.put(RegisterAppInterfaceResponse.KEY_VR_CAPABILITIES,       
					JsonUtils.createJsonArrayOfJsonNames(VR_CAPABILITIES, SDL_VERSION_UNDER_TEST));	
			result.put(RegisterAppInterfaceResponse.KEY_HMI_ZONE_CAPABILITIES, 
					JsonUtils.createJsonArrayOfJsonNames(HMI_ZONE_CAPABILITIES, SDL_VERSION_UNDER_TEST));
			result.put(RegisterAppInterfaceResponse.KEY_PRERECORDED_SPEECH,    
					JsonUtils.createJsonArrayOfJsonNames(PRERECORDED_SPEECH, SDL_VERSION_UNDER_TEST));
			
		} catch (JSONException e) {
			/* do nothing */
		}

		return result;
	}

	public void testSdlMsgVersion () {
		SdlMsgVersion copy = ( (RegisterAppInterfaceResponse) msg ).getSdlMsgVersion();
		
		assertNotSame("Initial prompt was not defensive copied.", SDL_MSG_VERSION, copy);
		assertTrue("Data didn't match input data.", Validator.validateSdlMsgVersion(SDL_MSG_VERSION, copy));
	}
	
	public void testLanguage () {
		Language copy = ( (RegisterAppInterfaceResponse) msg ).getLanguage();
		
		assertEquals("Data didn't match input data.", LANGUAGE, copy);
	}
	
	public void testHmiLanguage () {
		Language copy = ( (RegisterAppInterfaceResponse) msg ).getHmiDisplayLanguage();
		
		assertEquals("Data didn't match input data.", HMI_LANGUAGE, copy);
	}
	
	public void testDisplayCapabilities () {
		DisplayCapabilities copy = ( (RegisterAppInterfaceResponse) msg ).getDisplayCapabilities();
		
		assertNotNull("Display capabilities prompt were null.", copy);
		assertNotSame("Display capabilities was not defensive copied.", displayCapabilities, copy);
		assertTrue("Data didn't match input data.", Validator.validateDisplayCapabilities(displayCapabilities, copy));
	}
	
	public void testPresetBankCapabilities () {
		PresetBankCapabilities copy = ( (RegisterAppInterfaceResponse) msg ).getPresetBankCapabilities();
		
		assertNotSame("Initial prompt was not defensive copied.", PRESET_BANK_CAPABILITIES, copy);
		assertTrue("Data didn't match input data.", Validator.validatePresetBankCapabilities(PRESET_BANK_CAPABILITIES, copy));
	}
	
	public void testVehicleType () {
		VehicleType copy = ( (RegisterAppInterfaceResponse) msg ).getVehicleType();
		
		assertNotSame("Initial prompt was not defensive copied.", VEHICLE_TYPE, copy);
		assertTrue("Data didn't match input data.", Validator.validateVehicleType(VEHICLE_TYPE, copy));
	}
	
	public void testButtonCapabilities () {
		List<ButtonCapabilities> copy = ( (RegisterAppInterfaceResponse) msg ).getButtonCapabilities();
		
		assertNotNull("Button capabilties were null.", copy);
		assertNotSame("Button capabilities were not defensive copied.", buttonCapabilities, copy);
		assertTrue("Button capabilities didn't match input.", Validator.validateButtonCapabilities(buttonCapabilities, copy));
	}
	
	public void testSoftButtonCapabilities () {
		List<SoftButtonCapabilities> copy = ( (RegisterAppInterfaceResponse) msg ).getSoftButtonCapabilities();
		
		assertNotNull("Soft button capabilties were null.", copy);
		assertNotSame("Soft button capabilities were not defensive copied.", softButtonCapabilities, copy);
		assertTrue("Soft button capabilities didn't match input.", Validator.validateSoftButtonCapabilities(softButtonCapabilities, copy));
	}

	public void testAudioPassThruCapabilities () {
		List<AudioPassThruCapabilities> copy = ( (RegisterAppInterfaceResponse) msg ).getAudioPassThruCapabilities();
		
		assertNotNull("Audio pass thru capabilties were null.", copy);
		assertNotSame("Audio pass thru capabilities were not defensive copied.", audioPassThruCapabilities, copy);
		assertTrue("Audio pass thru capabilities didn't match input.", Validator.validateAudioPassThruCapabilities(audioPassThruCapabilities, copy));
		
	}
	
	public void testHmiZoneCapabilities () {
		List<HmiZoneCapabilities> copy = ( (RegisterAppInterfaceResponse) msg ).getHmiZoneCapabilities();
		
		assertEquals("Data didn't match input data.", HMI_ZONE_CAPABILITIES, copy);
	}
	
	public void testSpeechCapabilities () {
		List<SpeechCapabilities> copy = ( (RegisterAppInterfaceResponse) msg ).getSpeechCapabilities();
		
		assertEquals("Data didn't match input data.", SPEECH_CAPABILITIES, copy);
	}
	
	public void testVrCapabilities () {
		List<VrCapabilities> copy = ( (RegisterAppInterfaceResponse) msg ).getVrCapabilities();
		
		assertEquals("Data didn't match input data.", VR_CAPABILITIES, copy);
	}
	
	public void testPrerecordedSpeech () {
		List<PrerecordedSpeech> copy = ( (RegisterAppInterfaceResponse) msg ).getPrerecordedSpeech();
		
		assertEquals("Data didn't match input data.", PRERECORDED_SPEECH, copy);
	}
	
	public void testSupportedDiagModes () {
		List<Integer> copy = ( (RegisterAppInterfaceResponse) msg ).getSupportedDiagModes();
		
		assertEquals("Data didn't match input data.", SUPPORTED_DIAG_MODES, copy);
	}
	
	public void testNull() {
		RegisterAppInterfaceResponse msg = new RegisterAppInterfaceResponse();
		assertNotNull("Null object creation failed.", msg);

		testNullBase(msg);

		assertNull("Sdl message version wasn't set, but getter method returned an object.", msg.getSdlMsgVersion());
		assertNull("Language wasn't set, but getter method returned an object.", msg.getLanguage());
		assertNull("Hmi language wasn't set, but getter method returned an object.", msg.getHmiDisplayLanguage());
		assertNull("Display capabilities wasn't set, but getter method returned an object.", msg.getDisplayCapabilities());
		assertNull("Preset bank capabilities wasn't set, but getter method returned an object.", msg.getPresetBankCapabilities());
		assertNull("Vehicle type wasn't set, but getter method returned an object.", msg.getVehicleType());
		assertNull("Button capabilities wasn't set, but getter method returned an object.", msg.getButtonCapabilities());
		assertNull("Soft button capabilities wasn't set, but getter method returned an object.", msg.getSoftButtonCapabilities());
		assertNull("Audio pass thru capabilities wasn't set, but getter method returned an object.", msg.getAudioPassThruCapabilities());
		assertNull("Hmi zone capabilities wasn't set, but getter method returned an object.", msg.getHmiZoneCapabilities());
		assertNull("Speech capabilities wasn't set, but getter method returned an object.", msg.getSpeechCapabilities());
		assertNull("Vr capabilities wasn't set, but getter method returned an object.", msg.getVrCapabilities());
		assertNull("Prerecorded speech wasn't set, but getter method returned an object.", msg.getPrerecordedSpeech());
		assertNull("Supported diag modes wasn't set, but getter method returned an object.", msg.getSupportedDiagModes());
	}
}
