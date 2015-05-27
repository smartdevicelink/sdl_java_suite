package com.smartdevicelink.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.smartdevicelink.proxy.TTSChunkFactory;
import com.smartdevicelink.proxy.rpc.AudioPassThruCapabilities;
import com.smartdevicelink.proxy.rpc.ButtonCapabilities;
import com.smartdevicelink.proxy.rpc.Choice;
import com.smartdevicelink.proxy.rpc.DIDResult;
import com.smartdevicelink.proxy.rpc.DeviceInfo;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.KeyboardProperties;
import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.proxy.rpc.PresetBankCapabilities;
import com.smartdevicelink.proxy.rpc.ScreenParams;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.StartTime;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.TouchEventCapabilities;
import com.smartdevicelink.proxy.rpc.Turn;
import com.smartdevicelink.proxy.rpc.VehicleDataResult;
import com.smartdevicelink.proxy.rpc.VehicleType;
import com.smartdevicelink.proxy.rpc.VrHelpItem;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.CharacterSet;
import com.smartdevicelink.proxy.rpc.enums.DisplayType;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.GlobalProperty;
import com.smartdevicelink.proxy.rpc.enums.HmiZoneCapabilities;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;
import com.smartdevicelink.proxy.rpc.enums.KeypressMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;
import com.smartdevicelink.proxy.rpc.enums.MediaClockFormat;
import com.smartdevicelink.proxy.rpc.enums.PrerecordedSpeech;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.proxy.rpc.enums.UpdateMode;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataType;
import com.smartdevicelink.proxy.rpc.enums.VrCapabilities;

public class Test {
	
	// Test Failure Messages
	public static final String NULL      = "Value should be null.";
	public static final String MATCH     = "Values should match.";
	public static final String ARRAY     = "Array values should match.";
	public static final String TRUE      = "Value should be true.";
	public static final String FALSE     = "Value should be false.";
	public static final String NOT_NULL  = "Value should not be null.";
	public static final String JSON_FAIL = "Json testing failed.";

	// Rpc Test Values
	public static final int                    GENERAL_INT                    = 100;
	public static final float                  GENERAL_FLOAT                  = 100f;
	public static final Image                  GENERAL_IMAGE                  = new Image();	
	public static final String                 GENERAL_STRING                 = "test";
	public static final Double                 GENERAL_DOUBLE                 = 10.01;
	public static final boolean                GENERAL_BOOLEAN                = true;
	public static final FileType               GENERAL_FILETYPE               = FileType.BINARY;
	public static final Language               GENERAL_LANGUAGE               = Language.EN_US;
	public static final AudioType              GENERAL_AUDIOTYPE              = AudioType.PCM;
	public static final StartTime              GENERAL_STARTTIME              = new StartTime();
	public static final DeviceInfo	           GENERAL_DEVICEINFO	          = new DeviceInfo();
	public static final LayoutMode             GENERAL_LAYOUTMODE             = LayoutMode.LIST_ONLY;
	public static final MenuParams             GENERAL_MENUPARAMS             = new MenuParams();
	public static final ButtonName             GENERAL_BUTTONNAME             = ButtonName.OK;
	public static final UpdateMode             GENERAL_UPDATEMODE             = UpdateMode.RESUME;
	public static final VehicleType            GENERAL_VEHICLETYPE            = new VehicleType();
	public static final RequestType            GENERAL_REQUESTTYPE            = RequestType.AUTH_REQUEST;
	public static final SamplingRate           GENERAL_SAMPLINGRATE        	  = SamplingRate._8KHZ;
	public static final ScreenParams           GENERAL_SCREENPARAMS			  = new ScreenParams();
	public static final TriggerSource          GENERAL_TRIGGERSOURCE      	  = TriggerSource.TS_VR;
	public static final BitsPerSample          GENERAL_BITSPERSAMPLE          = BitsPerSample._8_BIT;
	public static final TextAlignment          GENERAL_TEXTALIGNMENT          = TextAlignment.CENTERED;
	public static final SdlMsgVersion          GENERAL_SDLMSGVERSION          = new SdlMsgVersion();
	public static final InteractionMode        GENERAL_INTERACTIONMODE        = InteractionMode.BOTH;
	public static final KeyboardProperties     GENERAL_KEYBOARDPROPERTIES     = new KeyboardProperties();	
	public static final DisplayCapabilities    GENERAL_DISPLAYCAPABILITIES    = new DisplayCapabilities();
	public static final PresetBankCapabilities GENERAL_PRESETBANKCAPABILITIES = new PresetBankCapabilities();
	
	public static final List<Turn>                      GENERAL_TURN_LIST                      = new ArrayList<Turn>();
	public static final List<Choice>                    GENERAL_CHOICE_LIST                    = new ArrayList<Choice>();
	public static final List<String>                    GENERAL_STRING_LIST                    = Arrays.asList(new String[] { "a", "b"});
	public static final List<Integer>                   GENERAL_INTEGER_LIST                   = Arrays.asList(new Integer[]{ -1, -2});
	public static final List<TTSChunk>                  GENERAL_TTSCHUNK_LIST                  = new ArrayList<TTSChunk>(2);
	public static final List<TextField>                 GENERAL_TEXTFIELD_LIST                 = new ArrayList<TextField>(1);
	public static final List<DIDResult>                 GENERAL_DIDRESULT_LIST                 = new ArrayList<DIDResult>(1);
	public static final List<AppHMIType>                GENERAL_APPHMITYPE_LIST                = new ArrayList<AppHMIType>(2);
	public static final List<VrHelpItem>                GENERAL_VRHELPITEM_LIST                = new ArrayList<VrHelpItem>(2);
	public static final List<SoftButton>                GENERAL_SOFTBUTTON_LIST                = new ArrayList<SoftButton>(1);
	public static final List<ImageField> 			    GENERAL_IMAGEFIELD_LIST				   = new ArrayList<ImageField>(1);
	public static final List<GlobalProperty>            GENERAL_GLOBALPROPERTY_LIST            = new ArrayList<GlobalProperty>(2);
	public static final List<VrCapabilities>		    GENERAL_VRCAPABILITIES_LIST            = new ArrayList<VrCapabilities>(1);
	public static final List<MediaClockFormat>          GENERAL_MEDIACLOCKFORMAT_LIST		   = new ArrayList<MediaClockFormat>(2);
	public static final List<VehicleDataResult>         GENERAL_VEHICLEDATARESULT_LIST         = new ArrayList<VehicleDataResult>(VehicleDataType.values().length);
	public static final List<PrerecordedSpeech>         GENERAL_PRERECORDEDSPEECH_LIST		   = new ArrayList<PrerecordedSpeech>(2);
	public static final List<SpeechCapabilities>        GENERAL_SPEECHCAPABILITIES_LIST        = new ArrayList<SpeechCapabilities>(2);
	public static final List<ButtonCapabilities>        GENERAL_BUTTONCAPABILITIES_LIST        = new ArrayList<ButtonCapabilities>(2);
	public static final List<HmiZoneCapabilities>       GENERAL_HMIZONECAPABILITIES_LIST       = new ArrayList<HmiZoneCapabilities>(2);
	public static final List<SoftButtonCapabilities>    GENERAL_SOFTBUTTONCAPABILITIES_LIST    = new ArrayList<SoftButtonCapabilities>(1);
	public static final List<AudioPassThruCapabilities> GENERAL_AUDIOPASSTHRUCAPABILITIES_LIST = new ArrayList<AudioPassThruCapabilities>(1);
	
	public static final JSONArray  JSON_TURNS                     = new JSONArray();
	public static final JSONArray  JSON_CHOICES                   = new JSONArray();	
	public static final JSONArray  JSON_TTSCHUNKS                 = new JSONArray();
	public static final JSONArray  JSON_DIDRESULTS                = new JSONArray();
	public static final JSONArray  JSON_TEXTFIELDS                = new JSONArray();
	public static final JSONArray  JSON_VRHELPITEMS               = new JSONArray();
	public static final JSONArray  JSON_SOFTBUTTONS               = new JSONArray();	
	public static final JSONArray  JSON_IMAGEFIELDS				  = new JSONArray();
	public static final JSONArray  JSON_BUTTONCAPABILITIES        = new JSONArray();
	public static final JSONArray  JSON_SOFTBUTTONCAPABILITIES    = new JSONArray();
	public static final JSONArray  JSON_AUDIOPASSTHRUCAPABILITIES = new JSONArray();
	
	public static final JSONObject JSON_IMAGE                     = new JSONObject();
	public static final JSONObject JSON_STARTTIME                 = new JSONObject();
	public static final JSONObject JSON_MENUPARAMS                = new JSONObject();
	public static final JSONObject JSON_DEVICEINFO                = new JSONObject();
	public static final JSONObject JSON_SCREENPARAMS              = new JSONObject();
	public static final JSONObject JSON_SDLMSGVERSION             = new JSONObject();
	public static final JSONObject JSON_KEYBOARDPROPERTIES        = new JSONObject();
	public static final JSONObject JSON_DISPLAYCAPABILITIES       = new JSONObject();
	public static final JSONObject JSON_PRESETBANKCAPABILITIES    = new JSONObject();
		
	static {
		// TextField List Setup
		TextField item = new TextField();
		item.setName(TextFieldName.ETA);
		item.setRows(GENERAL_INT);
		item.setWidth(GENERAL_INT);
		item.setCharacterSet(CharacterSet.CID1SET);
		GENERAL_TEXTFIELD_LIST.add(item);
		
		// ImageField List Setup
		ImageResolution imageResolution = new ImageResolution();
		imageResolution.setResolutionHeight(GENERAL_INT);
		imageResolution.setResolutionWidth(GENERAL_INT);
		
		List<FileType> fileTypes = new ArrayList<FileType>();
		fileTypes.add(GENERAL_FILETYPE);
		
		ImageField imageField = new ImageField();
		imageField.setImageResolution(imageResolution);
		imageField.setName(ImageFieldName.graphic);
		imageField.setImageTypeSupported(fileTypes);
		GENERAL_IMAGEFIELD_LIST.add(imageField);
		
		// ScreenParams Setup
		TouchEventCapabilities touchEC = new TouchEventCapabilities();
		touchEC.setDoublePressAvailable(GENERAL_BOOLEAN);
		touchEC.setMultiTouchAvailable(GENERAL_BOOLEAN);
		touchEC.setPressAvailable(GENERAL_BOOLEAN);
		
		GENERAL_SCREENPARAMS.setImageResolution(imageResolution);
		GENERAL_SCREENPARAMS.setTouchEventAvailable(touchEC);
		
		// MediaClockFormat List Setup		
		GENERAL_MEDIACLOCKFORMAT_LIST.add(MediaClockFormat.CLOCK1);
		GENERAL_MEDIACLOCKFORMAT_LIST.add(MediaClockFormat.CLOCK2);
		
		// Image Setup
		GENERAL_IMAGE.setValue(GENERAL_STRING);
		GENERAL_IMAGE.setImageType(ImageType.STATIC);
		
		// SoftButton List Setup
		SoftButton softButton = new SoftButton();
		softButton.setIsHighlighted(GENERAL_BOOLEAN);
		softButton.setSoftButtonID(GENERAL_INT);
		softButton.setSystemAction(SystemAction.STEAL_FOCUS);
		softButton.setText(GENERAL_STRING);
		softButton.setType(SoftButtonType.SBT_TEXT);
		softButton.setImage(GENERAL_IMAGE);
		GENERAL_SOFTBUTTON_LIST.add(softButton);
		
		// Turn List Setup
		Turn turn = new Turn();
		turn.setNavigationText(GENERAL_STRING);
		turn.setTurnIcon(GENERAL_IMAGE);
		GENERAL_TURN_LIST.add(turn);
		
		// MenuParams Setup
		GENERAL_MENUPARAMS.setMenuName(GENERAL_STRING);
		GENERAL_MENUPARAMS.setParentID(GENERAL_INT);
		GENERAL_MENUPARAMS.setPosition(GENERAL_INT);
				
		// VrHelpItem List Setup
		VrHelpItem vrItem1 = new VrHelpItem();
    	vrItem1.setText(GENERAL_STRING);
    	vrItem1.setImage(GENERAL_IMAGE);
    	vrItem1.setPosition(100);	    	
    	GENERAL_VRHELPITEM_LIST.add(vrItem1);
    	vrItem1 = new VrHelpItem();
    	
    	// TTSChunk List Setup
    	GENERAL_TTSCHUNK_LIST.add(TTSChunkFactory.createChunk(SpeechCapabilities.TEXT, "Welcome to the jungle"));
    	GENERAL_TTSCHUNK_LIST.add(TTSChunkFactory.createChunk(SpeechCapabilities.TEXT, "Say a command"));
		
    	// KeyboardProperties Setup
    	GENERAL_KEYBOARDPROPERTIES.setAutoCompleteText(GENERAL_STRING);
    	GENERAL_KEYBOARDPROPERTIES.setKeypressMode(KeypressMode.SINGLE_KEYPRESS);
    	GENERAL_KEYBOARDPROPERTIES.setKeyboardLayout(KeyboardLayout.QWERTY);
    	GENERAL_KEYBOARDPROPERTIES.setLanguage(Language.EN_US);
    	GENERAL_KEYBOARDPROPERTIES.setLimitedCharacterList(Test.GENERAL_STRING_LIST);
    	
    	// StartTime Setup
    	GENERAL_STARTTIME.setHours(0);
		GENERAL_STARTTIME.setMinutes(0);
		GENERAL_STARTTIME.setSeconds(0);
		
		// Choice List Setup
		Choice choice = new Choice();
        choice.setChoiceID(GENERAL_INT);
        choice.setMenuName(GENERAL_STRING);
        choice.setSecondaryText(GENERAL_STRING);
        choice.setImage(GENERAL_IMAGE);
        choice.setSecondaryImage(GENERAL_IMAGE);
        choice.setTertiaryText(GENERAL_STRING);
        choice.setVrCommands(GENERAL_STRING_LIST);
        GENERAL_CHOICE_LIST.add(choice);
        
        // DeviceInfo Setup
        GENERAL_DEVICEINFO.setCarrier(GENERAL_STRING);
        GENERAL_DEVICEINFO.setFirmwareRev(GENERAL_STRING);
        GENERAL_DEVICEINFO.setHardware(GENERAL_STRING);
        GENERAL_DEVICEINFO.setMaxNumberRFCOMMPorts(GENERAL_INT);
        GENERAL_DEVICEINFO.setOs(GENERAL_STRING);
        GENERAL_DEVICEINFO.setOsVersion(GENERAL_STRING);
        
        // SdlMsgVersion Setup
        GENERAL_SDLMSGVERSION.setMajorVersion(GENERAL_INT);
        GENERAL_SDLMSGVERSION.setMinorVersion(GENERAL_INT);
        
        // AppHMIType List Setup
        GENERAL_APPHMITYPE_LIST.add(AppHMIType.BACKGROUND_PROCESS);
        GENERAL_APPHMITYPE_LIST.add(AppHMIType.COMMUNICATION);
        
        // GlobalProperty List Setup
        GENERAL_GLOBALPROPERTY_LIST.add(GlobalProperty.HELPPROMPT);
        GENERAL_GLOBALPROPERTY_LIST.add(GlobalProperty.MENUICON);
        
        // VehicleDataResult List Setup
        for (VehicleDataType data : VehicleDataType.values()) {
        	VehicleDataResult result = new VehicleDataResult();
            result.setResultCode(VehicleDataResultCode.SUCCESS);
            result.setDataType(data);
        	GENERAL_VEHICLEDATARESULT_LIST.add(result);
        }
        
        // DidResult List Setup
        DIDResult testResult = new DIDResult();
        testResult.setData(GENERAL_STRING);
        testResult.setDidLocation(GENERAL_INT);
        testResult.setResultCode(VehicleDataResultCode.SUCCESS);
        GENERAL_DIDRESULT_LIST.add(testResult);
        
        // DisplayCapabilities Setup
        GENERAL_DISPLAYCAPABILITIES.setDisplayType(DisplayType.TYPE2);
        GENERAL_DISPLAYCAPABILITIES.setGraphicSupported(GENERAL_BOOLEAN);
        GENERAL_DISPLAYCAPABILITIES.setImageFields(GENERAL_IMAGEFIELD_LIST);
        GENERAL_DISPLAYCAPABILITIES.setMediaClockFormats(GENERAL_MEDIACLOCKFORMAT_LIST);
        GENERAL_DISPLAYCAPABILITIES.setNumCustomPresetsAvailable(GENERAL_INT);
        GENERAL_DISPLAYCAPABILITIES.setScreenParams(GENERAL_SCREENPARAMS);
        GENERAL_DISPLAYCAPABILITIES.setTemplatesAvailable(GENERAL_STRING_LIST);
        GENERAL_DISPLAYCAPABILITIES.setTextFields(GENERAL_TEXTFIELD_LIST);
		
        // PresetBankCapabilities Setup
        GENERAL_PRESETBANKCAPABILITIES.setOnScreenPresetsAvailable(GENERAL_BOOLEAN);
        
        // ButtonCapabilities List Setup
        ButtonCapabilities testButton = new ButtonCapabilities();
        testButton.setLongPressAvailable(false);
        testButton.setShortPressAvailable(true);
        testButton.setUpDownAvailable(true);
        testButton.setName(ButtonName.SEEKRIGHT);        
        GENERAL_BUTTONCAPABILITIES_LIST.add(testButton);
        
        // SoftButtonCapabilities List Setup
        SoftButtonCapabilities testSoftButton = new SoftButtonCapabilities();
        testSoftButton.setLongPressAvailable(GENERAL_BOOLEAN);
        testSoftButton.setShortPressAvailable(GENERAL_BOOLEAN);
        testSoftButton.setUpDownAvailable(GENERAL_BOOLEAN);
        testSoftButton.setImageSupported(GENERAL_BOOLEAN);
        GENERAL_SOFTBUTTONCAPABILITIES_LIST.add(testSoftButton);        
        
        // VehicleType Setup
        GENERAL_VEHICLETYPE.setMake(GENERAL_STRING);
        GENERAL_VEHICLETYPE.setModel(GENERAL_STRING);
        GENERAL_VEHICLETYPE.setModelYear(GENERAL_STRING);
        GENERAL_VEHICLETYPE.setTrim(GENERAL_STRING);
        
        // AudioPassThruCapabilities List Setup
        AudioPassThruCapabilities testAptc = new AudioPassThruCapabilities();
        testAptc.setAudioType(GENERAL_AUDIOTYPE);
        testAptc.setBitsPerSample(GENERAL_BITSPERSAMPLE);
        testAptc.setSamplingRate(GENERAL_SAMPLINGRATE);
        GENERAL_AUDIOPASSTHRUCAPABILITIES_LIST.add(testAptc);
        
        // PrerecordedSpeech List Setup
        GENERAL_PRERECORDEDSPEECH_LIST.add(PrerecordedSpeech.HELP_JINGLE);
        GENERAL_PRERECORDEDSPEECH_LIST.add(PrerecordedSpeech.INITIAL_JINGLE);
        
        // HmiZoneCapabilities List Setup
        GENERAL_HMIZONECAPABILITIES_LIST.add(HmiZoneCapabilities.BACK);
        GENERAL_HMIZONECAPABILITIES_LIST.add(HmiZoneCapabilities.FRONT);
        
        // SpeechCapabilities List Setup
        GENERAL_SPEECHCAPABILITIES_LIST.add(SpeechCapabilities.SILENCE);
        GENERAL_SPEECHCAPABILITIES_LIST.add(SpeechCapabilities.TEXT);
        
        // VrCapabilities List Setup
        GENERAL_VRCAPABILITIES_LIST.add(VrCapabilities.TEXT);
        
		try {			
			// Json Image Setup
			JSON_IMAGE.put(Image.KEY_IMAGE_TYPE, ImageType.STATIC);
			JSON_IMAGE.put(Image.KEY_VALUE, GENERAL_STRING);
			
			// Json SoftButton List Setup
			JSONObject jsonSoftButton = new JSONObject();
			jsonSoftButton.put(SoftButton.KEY_IS_HIGHLIGHTED , GENERAL_BOOLEAN);
			jsonSoftButton.put(SoftButton.KEY_SOFT_BUTTON_ID, GENERAL_INT);
			jsonSoftButton.put(SoftButton.KEY_SYSTEM_ACTION, SystemAction.STEAL_FOCUS);
			jsonSoftButton.put(SoftButton.KEY_TEXT, GENERAL_STRING);
			jsonSoftButton.put(SoftButton.KEY_TYPE, SoftButtonType.SBT_TEXT);
			jsonSoftButton.put(SoftButton.KEY_IMAGE, GENERAL_IMAGE.serializeJSON());
			JSON_SOFTBUTTONS.put(jsonSoftButton);
			
			// Json Turn List Setup
			JSONObject jsonTurn = new JSONObject();
			jsonTurn.put(Turn.KEY_NAVIGATION_TEXT, GENERAL_STRING);
			jsonTurn.put(Turn.KEY_TURN_IMAGE, GENERAL_IMAGE.serializeJSON());
			JSON_TURNS.put(jsonTurn);
			
			// Json MenuParams Setup
			JSON_MENUPARAMS.put(MenuParams.KEY_MENU_NAME, GENERAL_STRING);
			JSON_MENUPARAMS.put(MenuParams.KEY_PARENT_ID, GENERAL_INT);
			JSON_MENUPARAMS.put(MenuParams.KEY_POSITION, GENERAL_INT);
			
			// Json VrHelpItem Setup
	    	JSONObject jsonVrHelpItem = new JSONObject();
	    	jsonVrHelpItem.put(VrHelpItem.KEY_TEXT, GENERAL_STRING);
	    	jsonVrHelpItem.put(VrHelpItem.KEY_IMAGE, JSON_IMAGE);
	    	jsonVrHelpItem.put(VrHelpItem.KEY_POSITION, GENERAL_INT);
	    	JSON_VRHELPITEMS.put(jsonVrHelpItem);
	    	
	    	// Json TTSChunk Setup
	    	JSONObject jsonTtsChunk = new JSONObject();
	    	jsonTtsChunk.put(TTSChunk.KEY_TEXT, "Welcome to the jungle");
	    	jsonTtsChunk.put(TTSChunk.KEY_TYPE, SpeechCapabilities.TEXT);
	    	JSON_TTSCHUNKS.put(jsonTtsChunk);
	    	jsonTtsChunk = new JSONObject();
	    	jsonTtsChunk.put(TTSChunk.KEY_TEXT, "Say a command");
	    	jsonTtsChunk.put(TTSChunk.KEY_TYPE, SpeechCapabilities.TEXT);
	    	JSON_TTSCHUNKS.put(jsonTtsChunk);
	    	
	    	// Json KeyboardProperties Setup
	    	JSON_KEYBOARDPROPERTIES.put(KeyboardProperties.KEY_AUTO_COMPLETE_TEXT, GENERAL_STRING);
	    	JSON_KEYBOARDPROPERTIES.put(KeyboardProperties.KEY_KEYPRESS_MODE, KeypressMode.SINGLE_KEYPRESS);
	    	JSON_KEYBOARDPROPERTIES.put(KeyboardProperties.KEY_KEYBOARD_LAYOUT, KeyboardLayout.QWERTY);
	    	JSON_KEYBOARDPROPERTIES.put(KeyboardProperties.KEY_LANGUAGE, Language.EN_US);
	    	JSON_KEYBOARDPROPERTIES.put(KeyboardProperties.KEY_LIMITED_CHARACTER_LIST, JsonUtils.createJsonArray(GENERAL_STRING_LIST));
	    	
	    	// Json StartTime Setup
			JSON_STARTTIME.put(StartTime.KEY_HOURS, GENERAL_STARTTIME.getHours());
			JSON_STARTTIME.put(StartTime.KEY_MINUTES, GENERAL_STARTTIME.getMinutes());
			JSON_STARTTIME.put(StartTime.KEY_SECONDS, GENERAL_STARTTIME.getSeconds());
			
			// Json Choice List Setup
			JSONObject jsonChoice = new JSONObject();
			jsonChoice.put(Choice.KEY_CHOICE_ID, GENERAL_INT);
			jsonChoice.put(Choice.KEY_IMAGE, JSON_IMAGE);
			jsonChoice.put(Choice.KEY_MENU_NAME, GENERAL_STRING);
			jsonChoice.put(Choice.KEY_VR_COMMANDS, JsonUtils.createJsonArray(GENERAL_STRING_LIST));
			jsonChoice.put(Choice.KEY_SECONDARY_IMAGE, JSON_IMAGE);
			jsonChoice.put(Choice.KEY_SECONDARY_TEXT, GENERAL_STRING);
			jsonChoice.put(Choice.KEY_TERTIARY_TEXT, GENERAL_STRING);			
			JSON_CHOICES.put(jsonChoice);
			
			// Json DeviceInfo Setup
			JSON_DEVICEINFO.put(DeviceInfo.KEY_CARRIER, GENERAL_STRING);
			JSON_DEVICEINFO.put(DeviceInfo.KEY_FIRMWARE_REV, GENERAL_STRING);
			JSON_DEVICEINFO.put(DeviceInfo.KEY_HARDWARE, GENERAL_STRING);
			JSON_DEVICEINFO.put(DeviceInfo.KEY_MAX_NUMBER_RFCOMM_PORTS, GENERAL_INT);
			JSON_DEVICEINFO.put(DeviceInfo.KEY_OS, GENERAL_STRING);
			JSON_DEVICEINFO.put(DeviceInfo.KEY_OS_VERSION, GENERAL_STRING);
			
			// Json SdlMessageVersion Setup
			JSON_SDLMSGVERSION.put(SdlMsgVersion.KEY_MAJOR_VERSION, GENERAL_INT);
			JSON_SDLMSGVERSION.put(SdlMsgVersion.KEY_MINOR_VERSION, GENERAL_INT);	
			
			// Json DidResult List Setup
			JSONObject jsonResult = new JSONObject();
			jsonResult.put(DIDResult.KEY_DATA, GENERAL_STRING);
			jsonResult.put(DIDResult.KEY_DID_LOCATION, GENERAL_INT);
			jsonResult.put(DIDResult.KEY_RESULT_CODE, VehicleDataResultCode.SUCCESS);
			JSON_DIDRESULTS.put(jsonResult);
			
			// Json PresetBankCapabilities Setup
			JSON_PRESETBANKCAPABILITIES.put(PresetBankCapabilities.KEY_ON_SCREEN_PRESETS_AVAILABLE, GENERAL_BOOLEAN);
			
			// Json ButtonCapabilities List Setup
			JSONObject jsonButton = new JSONObject();
			jsonButton.put(ButtonCapabilities.KEY_LONG_PRESS_AVAILABLE, false);
			jsonButton.put(ButtonCapabilities.KEY_SHORT_PRESS_AVAILABLE, GENERAL_BOOLEAN);
			jsonButton.put(ButtonCapabilities.KEY_UP_DOWN_AVAILABLE, GENERAL_BOOLEAN);
			jsonButton.put(ButtonCapabilities.KEY_NAME, ButtonName.SEEKRIGHT);
			JSON_BUTTONCAPABILITIES.put(jsonButton);
			
			// Json SoftButtonCapabilities List Setup
			jsonButton = new JSONObject();
			jsonButton.put(SoftButtonCapabilities.KEY_LONG_PRESS_AVAILABLE, GENERAL_BOOLEAN);
			jsonButton.put(SoftButtonCapabilities.KEY_SHORT_PRESS_AVAILABLE, GENERAL_BOOLEAN);
			jsonButton.put(SoftButtonCapabilities.KEY_UP_DOWN_AVAILABLE, GENERAL_BOOLEAN);
			jsonButton.put(SoftButtonCapabilities.KEY_IMAGE_SUPPORTED, GENERAL_BOOLEAN);
			JSON_SOFTBUTTONCAPABILITIES.put(jsonButton);
			
			// Json AudioPassThruCapabilities List Setup
			jsonButton = new JSONObject();
			jsonButton.put(AudioPassThruCapabilities.KEY_AUDIO_TYPE, GENERAL_AUDIOTYPE);
			jsonButton.put(AudioPassThruCapabilities.KEY_BITS_PER_SAMPLE, GENERAL_BITSPERSAMPLE);
			jsonButton.put(AudioPassThruCapabilities.KEY_SAMPLING_RATE, GENERAL_SAMPLINGRATE);
			JSON_AUDIOPASSTHRUCAPABILITIES.put(jsonButton);
			
			// Json TextField List Setup
			JSONObject jsonItem = new JSONObject();
			jsonItem.put(TextField.KEY_CHARACTER_SET, CharacterSet.CID1SET);
			jsonItem.put(TextField.KEY_NAME, TextFieldName.ETA);
			jsonItem.put(TextField.KEY_ROWS, GENERAL_INT);
			jsonItem.put(TextField.KEY_WIDTH, GENERAL_INT);
			JSON_TEXTFIELDS.put(jsonItem);
			
			// Json ImageField List Setup
			JSONObject jsonImageResolution = new JSONObject();
			jsonImageResolution.put(ImageResolution.KEY_RESOLUTION_HEIGHT, GENERAL_INT);
			jsonImageResolution.put(ImageResolution.KEY_RESOLUTION_WIDTH, GENERAL_INT);
			
			jsonItem = new JSONObject();
			jsonItem.put(ImageField.KEY_IMAGE_RESOLUTION, jsonImageResolution);
			jsonItem.put(ImageField.KEY_IMAGE_TYPE_SUPPORTED, JsonUtils.createJsonArray(fileTypes));
			jsonItem.put(ImageField.KEY_NAME, ImageFieldName.graphic);
			JSON_IMAGEFIELDS.put(jsonItem);
			
			// Json ScreenParams Setup
			JSONObject jsonTEC = new JSONObject();
			jsonTEC.put(TouchEventCapabilities.KEY_DOUBLE_PRESS_AVAILABLE, GENERAL_BOOLEAN);
			jsonTEC.put(TouchEventCapabilities.KEY_MULTI_TOUCH_AVAILABLE, GENERAL_BOOLEAN);
			jsonTEC.put(TouchEventCapabilities.KEY_PRESS_AVAILABLE, GENERAL_BOOLEAN);
			
			JSON_SCREENPARAMS.put(ScreenParams.KEY_RESOLUTION, jsonImageResolution);
			JSON_SCREENPARAMS.put(ScreenParams.KEY_TOUCH_EVENT_AVAILABLE, jsonTEC);
			
			// Json DisplayCapabilities Setup
			JSON_DISPLAYCAPABILITIES.put(DisplayCapabilities.KEY_DISPLAY_TYPE, DisplayType.TYPE2);
			JSON_DISPLAYCAPABILITIES.put(DisplayCapabilities.KEY_GRAPHIC_SUPPORTED, GENERAL_BOOLEAN);
			JSON_DISPLAYCAPABILITIES.put(DisplayCapabilities.KEY_IMAGE_FIELDS, JSON_IMAGEFIELDS);
			JSON_DISPLAYCAPABILITIES.put(DisplayCapabilities.KEY_MEDIA_CLOCK_FORMATS, JsonUtils.createJsonArray(GENERAL_MEDIACLOCKFORMAT_LIST));
			JSON_DISPLAYCAPABILITIES.put(DisplayCapabilities.KEY_NUM_CUSTOM_PRESETS_AVAILABLE, GENERAL_INT);
			JSON_DISPLAYCAPABILITIES.put(DisplayCapabilities.KEY_SCREEN_PARAMS, JSON_SCREENPARAMS);
			JSON_DISPLAYCAPABILITIES.put(DisplayCapabilities.KEY_TEMPLATES_AVAILABLE, JsonUtils.createJsonArray(GENERAL_STRING_LIST));
			JSON_DISPLAYCAPABILITIES.put(DisplayCapabilities.KEY_TEXT_FIELDS, JSON_TEXTFIELDS);
			
		} catch (JSONException e) {
			Log.e("Test", "Static Json Construction Failed.", e);
		}
	}	
}