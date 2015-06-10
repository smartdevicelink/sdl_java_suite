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
import com.smartdevicelink.proxy.rpc.HMIPermissions;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.ImageField;
import com.smartdevicelink.proxy.rpc.ImageResolution;
import com.smartdevicelink.proxy.rpc.KeyboardProperties;
import com.smartdevicelink.proxy.rpc.MenuParams;
import com.smartdevicelink.proxy.rpc.ParameterPermissions;
import com.smartdevicelink.proxy.rpc.PermissionItem;
import com.smartdevicelink.proxy.rpc.PresetBankCapabilities;
import com.smartdevicelink.proxy.rpc.ScreenParams;
import com.smartdevicelink.proxy.rpc.SdlMsgVersion;
import com.smartdevicelink.proxy.rpc.SoftButton;
import com.smartdevicelink.proxy.rpc.SoftButtonCapabilities;
import com.smartdevicelink.proxy.rpc.StartTime;
import com.smartdevicelink.proxy.rpc.TTSChunk;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.TouchCoord;
import com.smartdevicelink.proxy.rpc.TouchEvent;
import com.smartdevicelink.proxy.rpc.TouchEventCapabilities;
import com.smartdevicelink.proxy.rpc.Turn;
import com.smartdevicelink.proxy.rpc.VehicleDataResult;
import com.smartdevicelink.proxy.rpc.VehicleType;
import com.smartdevicelink.proxy.rpc.VrHelpItem;
import com.smartdevicelink.proxy.rpc.enums.AmbientLightStatus;
import com.smartdevicelink.proxy.rpc.enums.AppHMIType;
import com.smartdevicelink.proxy.rpc.enums.AppInterfaceUnregisteredReason;
import com.smartdevicelink.proxy.rpc.enums.AudioStreamingState;
import com.smartdevicelink.proxy.rpc.enums.AudioType;
import com.smartdevicelink.proxy.rpc.enums.BitsPerSample;
import com.smartdevicelink.proxy.rpc.enums.ButtonEventMode;
import com.smartdevicelink.proxy.rpc.enums.ButtonName;
import com.smartdevicelink.proxy.rpc.enums.ButtonPressMode;
import com.smartdevicelink.proxy.rpc.enums.CarModeStatus;
import com.smartdevicelink.proxy.rpc.enums.CharacterSet;
import com.smartdevicelink.proxy.rpc.enums.CompassDirection;
import com.smartdevicelink.proxy.rpc.enums.ComponentVolumeStatus;
import com.smartdevicelink.proxy.rpc.enums.DeviceLevelStatus;
import com.smartdevicelink.proxy.rpc.enums.Dimension;
import com.smartdevicelink.proxy.rpc.enums.DisplayType;
import com.smartdevicelink.proxy.rpc.enums.ECallConfirmationStatus;
import com.smartdevicelink.proxy.rpc.enums.EmergencyEventType;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.FuelCutoffStatus;
import com.smartdevicelink.proxy.rpc.enums.GlobalProperty;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.HmiZoneCapabilities;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStableStatus;
import com.smartdevicelink.proxy.rpc.enums.IgnitionStatus;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.InteractionMode;
import com.smartdevicelink.proxy.rpc.enums.KeyboardEvent;
import com.smartdevicelink.proxy.rpc.enums.KeyboardLayout;
import com.smartdevicelink.proxy.rpc.enums.KeypressMode;
import com.smartdevicelink.proxy.rpc.enums.Language;
import com.smartdevicelink.proxy.rpc.enums.LayoutMode;
import com.smartdevicelink.proxy.rpc.enums.LockScreenStatus;
import com.smartdevicelink.proxy.rpc.enums.MediaClockFormat;
import com.smartdevicelink.proxy.rpc.enums.PowerModeQualificationStatus;
import com.smartdevicelink.proxy.rpc.enums.PowerModeStatus;
import com.smartdevicelink.proxy.rpc.enums.PrerecordedSpeech;
import com.smartdevicelink.proxy.rpc.enums.PrimaryAudioSource;
import com.smartdevicelink.proxy.rpc.enums.RequestType;
import com.smartdevicelink.proxy.rpc.enums.SamplingRate;
import com.smartdevicelink.proxy.rpc.enums.SoftButtonType;
import com.smartdevicelink.proxy.rpc.enums.SpeechCapabilities;
import com.smartdevicelink.proxy.rpc.enums.SystemAction;
import com.smartdevicelink.proxy.rpc.enums.SystemContext;
import com.smartdevicelink.proxy.rpc.enums.TBTState;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.proxy.rpc.enums.TouchType;
import com.smartdevicelink.proxy.rpc.enums.TriggerSource;
import com.smartdevicelink.proxy.rpc.enums.UpdateMode;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataEventStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataNotificationStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataResultCode;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataStatus;
import com.smartdevicelink.proxy.rpc.enums.VehicleDataType;
import com.smartdevicelink.proxy.rpc.enums.VrCapabilities;
import com.smartdevicelink.proxy.rpc.enums.WarningLightStatus;

public class Test {
	
	// Test Failure Messages
	public static final String NULL      = "Value should be null.";
	public static final String MATCH     = "Values should match.";
	public static final String ARRAY     = "Array values should match.";
	public static final String TRUE      = "Value should be true.";
	public static final String FALSE     = "Value should be false.";
	public static final String NOT_NULL  = "Value should not be null.";
	public static final String JSON_FAIL = "Json testing failed.";
	
	// RPC Request/Response/Notification/Datatype Test Values
	public static final int                            GENERAL_INT                            = 100;
	public static final Long                           GENERAL_LONG                           = 100L;
	public static final Turn                           GENERAL_TURN                           = new Turn();
	public static final float                          GENERAL_FLOAT                          = 100f;
	public static final Image                          GENERAL_IMAGE                          = new Image();	
	public static final Choice                         GENERAL_CHOICE                         = new Choice();
	public static final String                         GENERAL_STRING                         = "test";
	public static final Double                         GENERAL_DOUBLE                         = 10.01;
	public static final boolean                        GENERAL_BOOLEAN                        = true;
	public static final TBTState                       GENERAL_TBTSTATE                       = TBTState.NEXT_TURN_REQUEST;
	public static final FileType                       GENERAL_FILETYPE                       = FileType.BINARY;
	public static final Language                       GENERAL_LANGUAGE                       = Language.EN_US;
	public static final HMILevel                       GENERAL_HMILEVEL                       = HMILevel.HMI_FULL;
	public static final DIDResult                      GENERAL_DIDRESULT                      = new DIDResult();
	public static final TextField                      GENERAL_TEXTFIELD                      = new TextField();
	public static final Dimension                      GENERAL_DIMENSION                      = Dimension._2D;
	public static final ImageType                      GENERAL_IMAGETYPE                      = ImageType.DYNAMIC;
	public static final AudioType                      GENERAL_AUDIOTYPE                      = AudioType.PCM;
	public static final StartTime                      GENERAL_STARTTIME                      = new StartTime();
	public static final TouchType                      GENERAL_TOUCHTYPE                      = TouchType.BEGIN;
	public static final TouchEvent                     GENERAL_TOUCHEVENT                     = new TouchEvent();
	public static final VrHelpItem                     GENERAL_VRHELPITEM                     = new VrHelpItem();
	public static final ImageField                     GENERAL_IMAGEFIELD                     = new ImageField();
	public static final DeviceInfo	                   GENERAL_DEVICEINFO	                  = new DeviceInfo();
	public static final LayoutMode                     GENERAL_LAYOUTMODE                     = LayoutMode.LIST_ONLY;
	public static final MenuParams                     GENERAL_MENUPARAMS                     = new MenuParams();
	public static final SoftButton                     GENERAL_SOFTBUTTON                     = new SoftButton();
	public static final ButtonName                     GENERAL_BUTTONNAME                     = ButtonName.OK;
	public static final UpdateMode                     GENERAL_UPDATEMODE                     = UpdateMode.RESUME;
	public static final TouchCoord                     GENERAL_TOUCHCOORD                     = new TouchCoord();
	public static final DisplayType                    GENERAL_DISPLAYTYPE                    = DisplayType.CID;
	public static final VehicleType                    GENERAL_VEHICLETYPE                    = new VehicleType();
	public static final RequestType                    GENERAL_REQUESTTYPE                    = RequestType.AUTH_REQUEST;	
	public static final SystemAction                   GENERAL_SYSTEMACTION                   = SystemAction.DEFAULT_ACTION;
	public static final CharacterSet                   GENERAL_CHARACTERSET                   = CharacterSet.CID1SET;
	public static final SamplingRate                   GENERAL_SAMPLINGRATE                	  = SamplingRate._8KHZ;
	public static final ScreenParams                   GENERAL_SCREENPARAMS		        	  = new ScreenParams();
	public static final KeypressMode                   GENERAL_KEYPRESSMODE                   = KeypressMode.QUEUE_KEYPRESSES;
	public static final SystemContext                  GENERAL_SYSTEMCONTEXT                  = SystemContext.SYSCTXT_MAIN;
	public static final KeyboardEvent                  GENERAL_KEYBOARDEVENT                  = KeyboardEvent.ENTRY_SUBMITTED;
	public static final CarModeStatus                  GENERAL_CARMODESTATUS                  = CarModeStatus.NORMAL;
	public static final TextFieldName                  GENERAL_TEXTFIELDNAME                  = TextFieldName.ETA;
	public static final TriggerSource                  GENERAL_TRIGGERSOURCE              	  = TriggerSource.TS_VR;
	public static final BitsPerSample                  GENERAL_BITSPERSAMPLE                  = BitsPerSample._8_BIT;
	public static final TextAlignment                  GENERAL_TEXTALIGNMENT                  = TextAlignment.CENTERED;
	public static final SdlMsgVersion                  GENERAL_SDLMSGVERSION                  = new SdlMsgVersion();
	public static final PermissionItem                 GENERAL_PERMISSIONITEM                 = new PermissionItem();
	public static final SoftButtonType                 GENERAL_SOFTBUTTONTYPE                 = SoftButtonType.SBT_BOTH;
	public static final KeyboardLayout                 GENERAL_KEYBOARDLAYOUT                 = KeyboardLayout.QWERTY;
	public static final ImageFieldName                 GENERAL_IMAGEFIELDNAME                 = ImageFieldName.graphic;
	public static final HMIPermissions                 GENERAL_HMIPERMISSIONS                 = new HMIPermissions();
	public static final IgnitionStatus                 GENERAL_IGNITIONSTATUS                 = IgnitionStatus.RUN;
	public static final ButtonEventMode                GENERAL_BUTTONEVENTMODE                = ButtonEventMode.BUTTONUP;
    public static final ButtonPressMode                GENERAL_BUTTONPRESSMODE                = ButtonPressMode.SHORT;
	public static final PowerModeStatus                GENERAL_POWERMODESTATUS                = PowerModeStatus.RUNNING_2;
	public static final VehicleDataType                GENERAL_VEHICLEDATATYPE                = VehicleDataType.VEHICLEDATA_BRAKING;
	public static final InteractionMode                GENERAL_INTERACTIONMODE                = InteractionMode.BOTH;
	public static final ImageResolution                GENERAL_IMAGERESOLUTION                = new ImageResolution();	
	public static final FuelCutoffStatus               GENERAL_FUELCUTOFFSTATUS               = FuelCutoffStatus.NORMAL_OPERATION;
	public static final CompassDirection               GENERAL_COMPASSDIRECTION               = CompassDirection.EAST;
	public static final LockScreenStatus               GENERAL_LOCKSCREENSTATUS               = LockScreenStatus.REQUIRED;
	public static final VehicleDataStatus              GENERAL_VEHICLEDATASTATUS              = VehicleDataStatus.ON;
	public static final DeviceLevelStatus              GENERAL_DEVICELEVELSTATUS              = DeviceLevelStatus.FOUR_LEVEL_BARS;
	public static final ButtonCapabilities             GENERAL_BUTTONCAPABILITIES             = new ButtonCapabilities();
	public static final EmergencyEventType             GENERAL_EMERGENCYEVENTTYPE             = EmergencyEventType.FAULT;
	public static final AmbientLightStatus             GENERAL_AMBIENTLIGHTSTATUS             = AmbientLightStatus.NIGHT; 
	public static final SpeechCapabilities             GENERAL_SPEECHCAPABILITIES             = SpeechCapabilities.TEXT;
	public static final WarningLightStatus        	   GENERAL_WARNINGLIGHTSTATUS        	  = WarningLightStatus.OFF;
	public static final KeyboardProperties             GENERAL_KEYBOARDPROPERTIES             = new KeyboardProperties();	
	public static final PrimaryAudioSource             GENERAL_PRIMARYAUDIOSOURCE             = PrimaryAudioSource.BLUETOOTH_STEREO_BTST;
	public static final AudioStreamingState            GENERAL_AUDIOSTREAMINGSTATE            = AudioStreamingState.AUDIBLE;
	public static final DisplayCapabilities            GENERAL_DISPLAYCAPABILITIES            = new DisplayCapabilities();
	public static final ParameterPermissions           GENERAL_PARAMETERPERMISSIONS           = new ParameterPermissions();  
	public static final IgnitionStableStatus           GENERAL_IGNITIONSTABLESTATUS           = IgnitionStableStatus.IGNITION_SWITCH_STABLE;	
	public static final VehicleDataResultCode          GENERAL_VEHICLEDATARESULTCODE          = VehicleDataResultCode.IGNORED;
	public static final ComponentVolumeStatus          GENERAL_COMPONENTVOLUMESTATUS          = ComponentVolumeStatus.LOW;
	public static final PresetBankCapabilities         GENERAL_PRESETBANKCAPABILITIES         = new PresetBankCapabilities();
	public static final VehicleDataEventStatus         GENERAL_VEHCILEDATAEVENTSTATUS         = VehicleDataEventStatus.YES;
	public static final TouchEventCapabilities         GENERAL_TOUCHEVENTCAPABILITIES         = new TouchEventCapabilities();
	public static final SoftButtonCapabilities         GENERAL_SOFTBUTTONCAPABILITIES         = new SoftButtonCapabilities();
	public static final ECallConfirmationStatus        GENERAL_ECALLCONFIRMATIONSTATUS        = ECallConfirmationStatus.CALL_IN_PROGRESS;	
	public static final AudioPassThruCapabilities      GENERAL_AUDIOPASSTHRUCAPABILITIES      = new AudioPassThruCapabilities();
	public static final PowerModeQualificationStatus   GENERAL_POWERMODEQUALIFICATIONSTATUS   = PowerModeQualificationStatus.POWER_MODE_OK;
	public static final VehicleDataNotificationStatus  GENERAL_VEHICLEDATANOTIFICATIONSTATUS  = VehicleDataNotificationStatus.NORMAL;
	public static final AppInterfaceUnregisteredReason GENERAL_APPINTERFACEUNREGISTEREDREASON = AppInterfaceUnregisteredReason.BLUETOOTH_OFF;
	
	public static final List<Long>                      GENERAL_LONG_LIST                      = Arrays.asList(new Long[]{ 1L, 2L });
	public static final List<Turn>                      GENERAL_TURN_LIST                      = new ArrayList<Turn>();
	public static final List<Choice>                    GENERAL_CHOICE_LIST                    = new ArrayList<Choice>();
	public static final List<String>                    GENERAL_STRING_LIST                    = Arrays.asList(new String[] { "a", "b"});
	public static final List<Integer>                   GENERAL_INTEGER_LIST                   = Arrays.asList(new Integer[]{ -1, -2});
	public static final List<TTSChunk>                  GENERAL_TTSCHUNK_LIST                  = new ArrayList<TTSChunk>(2);
	public static final List<HMILevel>                  GENERAL_HMILEVEL_LIST                  = Arrays.asList(new HMILevel[]{HMILevel.HMI_FULL, HMILevel.HMI_BACKGROUND});
	public static final List<FileType>                  GENERAL_FILETYPE_LIST                  = new ArrayList<FileType>(1);
	public static final List<TextField>                 GENERAL_TEXTFIELD_LIST                 = new ArrayList<TextField>(1);
	public static final List<DIDResult>                 GENERAL_DIDRESULT_LIST                 = new ArrayList<DIDResult>(1);
	public static final List<TouchCoord>                GENERAL_TOUCHCOORD_LIST                = new ArrayList<TouchCoord>(1);
	public static final List<AppHMIType>                GENERAL_APPHMITYPE_LIST                = new ArrayList<AppHMIType>(2);
	public static final List<VrHelpItem>                GENERAL_VRHELPITEM_LIST                = new ArrayList<VrHelpItem>(2);
	public static final List<SoftButton>                GENERAL_SOFTBUTTON_LIST                = new ArrayList<SoftButton>(1);
	public static final List<ImageField> 			    GENERAL_IMAGEFIELD_LIST				   = new ArrayList<ImageField>(1);
	public static final List<TouchEvent>			    GENERAL_TOUCHEVENT_LIST                = new ArrayList<TouchEvent>(1);
	public static final List<PermissionItem>            GENERAL_PERMISSIONITEM_LIST            = new ArrayList<PermissionItem>(1);
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
	public static final JSONArray  JSON_HMILEVELS                 = new JSONArray();
	public static final JSONArray  JSON_TTSCHUNKS                 = new JSONArray();
	public static final JSONArray  JSON_DIDRESULTS                = new JSONArray();
	public static final JSONArray  JSON_TEXTFIELDS                = new JSONArray();
	public static final JSONArray  JSON_TOUCHCOORDS               = new JSONArray();
	public static final JSONArray  JSON_VRHELPITEMS               = new JSONArray();
	public static final JSONArray  JSON_SOFTBUTTONS               = new JSONArray();	
	public static final JSONArray  JSON_IMAGEFIELDS				  = new JSONArray();
	public static final JSONArray  JSON_TOUCHEVENTS               = new JSONArray();
	public static final JSONArray  JSON_PERMISSIONITEMS           = new JSONArray();
	public static final JSONArray  JSON_BUTTONCAPABILITIES        = new JSONArray();
	public static final JSONArray  JSON_SOFTBUTTONCAPABILITIES    = new JSONArray();
	public static final JSONArray  JSON_AUDIOPASSTHRUCAPABILITIES = new JSONArray();
	
	public static final JSONObject JSON_TURN                      = new JSONObject();
	public static final JSONObject JSON_IMAGE                     = new JSONObject();
	public static final JSONObject JSON_CHOICE                    = new JSONObject();
	public static final JSONObject JSON_DIDRESULT                 = new JSONObject();
	public static final JSONObject JSON_STARTTIME                 = new JSONObject();
	public static final JSONObject JSON_TEXTFIELD                 = new JSONObject();
	public static final JSONObject JSON_TOUCHCOORD                = new JSONObject();
	public static final JSONObject JSON_TOUCHEVENT                = new JSONObject();
	public static final JSONObject JSON_IMAGEFIELD                = new JSONObject();
	public static final JSONObject JSON_SOFTBUTTON                = new JSONObject();
	public static final JSONObject JSON_MENUPARAMS                = new JSONObject();
	public static final JSONObject JSON_DEVICEINFO                = new JSONObject();
	public static final JSONObject JSON_VRHELPITEM                = new JSONObject();
	public static final JSONObject JSON_SCREENPARAMS              = new JSONObject();	
	public static final JSONObject JSON_SDLMSGVERSION             = new JSONObject();
	public static final JSONObject JSON_PERMISSIONITEM            = new JSONObject();
	public static final JSONObject JSON_HMIPERMISSIONS            = new JSONObject();
	public static final JSONObject JSON_IMAGERESOLUTION           = new JSONObject();	
	public static final JSONObject JSON_KEYBOARDPROPERTIES        = new JSONObject();
	public static final JSONObject JSON_DISPLAYCAPABILITIES       = new JSONObject();
	public static final JSONObject JSON_PARAMETERPERMISSIONS      = new JSONObject();
	public static final JSONObject JSON_PRESETBANKCAPABILITIES    = new JSONObject();
	public static final JSONObject JSON_TOUCHEVENTCAPABILITIES    = new JSONObject();
	
	static {
		GENERAL_TOUCHEVENTCAPABILITIES.setDoublePressAvailable(GENERAL_BOOLEAN);
		GENERAL_TOUCHEVENTCAPABILITIES.setMultiTouchAvailable(GENERAL_BOOLEAN);
		GENERAL_TOUCHEVENTCAPABILITIES.setPressAvailable(GENERAL_BOOLEAN);
		
		GENERAL_IMAGERESOLUTION.setResolutionHeight(GENERAL_INT);
		GENERAL_IMAGERESOLUTION.setResolutionWidth(GENERAL_INT);		
		
		GENERAL_CHOICE.setMenuName(GENERAL_STRING);
		GENERAL_CHOICE.setSecondaryText(GENERAL_STRING);
		GENERAL_CHOICE.setTertiaryText(GENERAL_STRING);
		GENERAL_CHOICE.setChoiceID(GENERAL_INT);
		GENERAL_CHOICE.setImage(GENERAL_IMAGE);
		GENERAL_CHOICE.setSecondaryImage(GENERAL_IMAGE);
		GENERAL_CHOICE.setVrCommands(GENERAL_STRING_LIST);
		
		GENERAL_TOUCHCOORD.setX(GENERAL_INT);
		GENERAL_TOUCHCOORD.setY(GENERAL_INT);		
		GENERAL_TOUCHCOORD_LIST.add(GENERAL_TOUCHCOORD);
		
		GENERAL_TOUCHEVENT.setId(GENERAL_INT);
		GENERAL_TOUCHEVENT.setTs(GENERAL_LONG_LIST);
		GENERAL_TOUCHEVENT.setC(GENERAL_TOUCHCOORD_LIST);		
		GENERAL_TOUCHEVENT_LIST.add(GENERAL_TOUCHEVENT);
		
		GENERAL_TEXTFIELD.setName(GENERAL_TEXTFIELDNAME);
		GENERAL_TEXTFIELD.setRows(GENERAL_INT);
		GENERAL_TEXTFIELD.setWidth(GENERAL_INT);
		GENERAL_TEXTFIELD.setCharacterSet(GENERAL_CHARACTERSET);
		GENERAL_TEXTFIELD_LIST.add(GENERAL_TEXTFIELD);
		
		GENERAL_FILETYPE_LIST.add(GENERAL_FILETYPE);
		
		GENERAL_IMAGEFIELD.setImageResolution(GENERAL_IMAGERESOLUTION);
		GENERAL_IMAGEFIELD.setName(GENERAL_IMAGEFIELDNAME);
		GENERAL_IMAGEFIELD.setImageTypeSupported(GENERAL_FILETYPE_LIST);
		GENERAL_IMAGEFIELD_LIST.add(GENERAL_IMAGEFIELD);
		
		GENERAL_SCREENPARAMS.setImageResolution(GENERAL_IMAGERESOLUTION);
		GENERAL_SCREENPARAMS.setTouchEventAvailable(GENERAL_TOUCHEVENTCAPABILITIES);
		
		GENERAL_MEDIACLOCKFORMAT_LIST.add(MediaClockFormat.CLOCK1);
		GENERAL_MEDIACLOCKFORMAT_LIST.add(MediaClockFormat.CLOCK2);
		
		GENERAL_IMAGE.setValue(GENERAL_STRING);
		GENERAL_IMAGE.setImageType(GENERAL_IMAGETYPE);
		
		GENERAL_SOFTBUTTON.setIsHighlighted(GENERAL_BOOLEAN);
		GENERAL_SOFTBUTTON.setSoftButtonID(GENERAL_INT);
		GENERAL_SOFTBUTTON.setSystemAction(SystemAction.STEAL_FOCUS);
		GENERAL_SOFTBUTTON.setText(GENERAL_STRING);
		GENERAL_SOFTBUTTON.setType(SoftButtonType.SBT_TEXT);
		GENERAL_SOFTBUTTON.setImage(GENERAL_IMAGE);
		GENERAL_SOFTBUTTON_LIST.add(GENERAL_SOFTBUTTON);
		
		GENERAL_TURN.setNavigationText(GENERAL_STRING);
		GENERAL_TURN.setTurnIcon(GENERAL_IMAGE);
		GENERAL_TURN_LIST.add(GENERAL_TURN);
		
		GENERAL_MENUPARAMS.setMenuName(GENERAL_STRING);
		GENERAL_MENUPARAMS.setParentID(GENERAL_INT);
		GENERAL_MENUPARAMS.setPosition(GENERAL_INT);
				
		GENERAL_VRHELPITEM.setText(GENERAL_STRING);
		GENERAL_VRHELPITEM.setImage(GENERAL_IMAGE);
		GENERAL_VRHELPITEM.setPosition(100);	    	
    	GENERAL_VRHELPITEM_LIST.add(GENERAL_VRHELPITEM);
    	
    	GENERAL_TTSCHUNK_LIST.add(TTSChunkFactory.createChunk(SpeechCapabilities.TEXT, "Welcome to the jungle"));
    	GENERAL_TTSCHUNK_LIST.add(TTSChunkFactory.createChunk(SpeechCapabilities.TEXT, "Say a command"));
		
    	GENERAL_KEYBOARDPROPERTIES.setAutoCompleteText(GENERAL_STRING);
    	GENERAL_KEYBOARDPROPERTIES.setKeypressMode(KeypressMode.SINGLE_KEYPRESS);
    	GENERAL_KEYBOARDPROPERTIES.setKeyboardLayout(KeyboardLayout.QWERTY);
    	GENERAL_KEYBOARDPROPERTIES.setLanguage(Language.EN_US);
    	GENERAL_KEYBOARDPROPERTIES.setLimitedCharacterList(Test.GENERAL_STRING_LIST);
    	
    	GENERAL_STARTTIME.setHours(GENERAL_INT);
		GENERAL_STARTTIME.setMinutes(GENERAL_INT);
		GENERAL_STARTTIME.setSeconds(GENERAL_INT);
		
        GENERAL_CHOICE_LIST.add(GENERAL_CHOICE);
        
        GENERAL_DEVICEINFO.setCarrier(GENERAL_STRING);
        GENERAL_DEVICEINFO.setFirmwareRev(GENERAL_STRING);
        GENERAL_DEVICEINFO.setHardware(GENERAL_STRING);
        GENERAL_DEVICEINFO.setMaxNumberRFCOMMPorts(GENERAL_INT);
        GENERAL_DEVICEINFO.setOs(GENERAL_STRING);
        GENERAL_DEVICEINFO.setOsVersion(GENERAL_STRING);
        
        GENERAL_SDLMSGVERSION.setMajorVersion(GENERAL_INT);
        GENERAL_SDLMSGVERSION.setMinorVersion(GENERAL_INT);
        
        GENERAL_APPHMITYPE_LIST.add(AppHMIType.BACKGROUND_PROCESS);
        GENERAL_APPHMITYPE_LIST.add(AppHMIType.COMMUNICATION);
        
        GENERAL_GLOBALPROPERTY_LIST.add(GlobalProperty.HELPPROMPT);
        GENERAL_GLOBALPROPERTY_LIST.add(GlobalProperty.MENUICON);
        
        for (VehicleDataType data : VehicleDataType.values()) {
        	VehicleDataResult result = new VehicleDataResult();
            result.setResultCode(VehicleDataResultCode.SUCCESS);
            result.setDataType(data);
        	GENERAL_VEHICLEDATARESULT_LIST.add(result);
        }
        
        GENERAL_DIDRESULT.setData(GENERAL_STRING);
        GENERAL_DIDRESULT.setDidLocation(GENERAL_INT);
        GENERAL_DIDRESULT.setResultCode(VehicleDataResultCode.SUCCESS);
        GENERAL_DIDRESULT_LIST.add(GENERAL_DIDRESULT);
        
        GENERAL_DISPLAYCAPABILITIES.setDisplayType(GENERAL_DISPLAYTYPE);
        GENERAL_DISPLAYCAPABILITIES.setGraphicSupported(GENERAL_BOOLEAN);
        GENERAL_DISPLAYCAPABILITIES.setImageFields(GENERAL_IMAGEFIELD_LIST);
        GENERAL_DISPLAYCAPABILITIES.setMediaClockFormats(GENERAL_MEDIACLOCKFORMAT_LIST);
        GENERAL_DISPLAYCAPABILITIES.setNumCustomPresetsAvailable(GENERAL_INT);
        GENERAL_DISPLAYCAPABILITIES.setScreenParams(GENERAL_SCREENPARAMS);
        GENERAL_DISPLAYCAPABILITIES.setTemplatesAvailable(GENERAL_STRING_LIST);
        GENERAL_DISPLAYCAPABILITIES.setTextFields(GENERAL_TEXTFIELD_LIST);
		
        GENERAL_PRESETBANKCAPABILITIES.setOnScreenPresetsAvailable(GENERAL_BOOLEAN);
        
        GENERAL_BUTTONCAPABILITIES.setLongPressAvailable(false);
        GENERAL_BUTTONCAPABILITIES.setShortPressAvailable(true);
        GENERAL_BUTTONCAPABILITIES.setUpDownAvailable(true);
        GENERAL_BUTTONCAPABILITIES.setName(ButtonName.SEEKRIGHT);        
        GENERAL_BUTTONCAPABILITIES_LIST.add(GENERAL_BUTTONCAPABILITIES);
        
        GENERAL_SOFTBUTTONCAPABILITIES.setLongPressAvailable(GENERAL_BOOLEAN);
        GENERAL_SOFTBUTTONCAPABILITIES.setShortPressAvailable(GENERAL_BOOLEAN);
        GENERAL_SOFTBUTTONCAPABILITIES.setUpDownAvailable(GENERAL_BOOLEAN);
        GENERAL_SOFTBUTTONCAPABILITIES.setImageSupported(GENERAL_BOOLEAN);
        GENERAL_SOFTBUTTONCAPABILITIES_LIST.add(GENERAL_SOFTBUTTONCAPABILITIES);        
        
        GENERAL_VEHICLETYPE.setMake(GENERAL_STRING);
        GENERAL_VEHICLETYPE.setModel(GENERAL_STRING);
        GENERAL_VEHICLETYPE.setModelYear(GENERAL_STRING);
        GENERAL_VEHICLETYPE.setTrim(GENERAL_STRING);
        
        GENERAL_AUDIOPASSTHRUCAPABILITIES.setAudioType(GENERAL_AUDIOTYPE);
        GENERAL_AUDIOPASSTHRUCAPABILITIES.setBitsPerSample(GENERAL_BITSPERSAMPLE);
        GENERAL_AUDIOPASSTHRUCAPABILITIES.setSamplingRate(GENERAL_SAMPLINGRATE);
        GENERAL_AUDIOPASSTHRUCAPABILITIES_LIST.add(GENERAL_AUDIOPASSTHRUCAPABILITIES);
        
        GENERAL_PRERECORDEDSPEECH_LIST.add(PrerecordedSpeech.HELP_JINGLE);
        GENERAL_PRERECORDEDSPEECH_LIST.add(PrerecordedSpeech.INITIAL_JINGLE);
        
        GENERAL_HMIZONECAPABILITIES_LIST.add(HmiZoneCapabilities.BACK);
        GENERAL_HMIZONECAPABILITIES_LIST.add(HmiZoneCapabilities.FRONT);
        
        GENERAL_SPEECHCAPABILITIES_LIST.add(SpeechCapabilities.SILENCE);
        GENERAL_SPEECHCAPABILITIES_LIST.add(SpeechCapabilities.TEXT);
        
        GENERAL_VRCAPABILITIES_LIST.add(VrCapabilities.TEXT);
        
        GENERAL_HMIPERMISSIONS.setAllowed(GENERAL_HMILEVEL_LIST);
        GENERAL_HMIPERMISSIONS.setUserDisallowed(GENERAL_HMILEVEL_LIST);
        
        GENERAL_PARAMETERPERMISSIONS.setAllowed(GENERAL_STRING_LIST);
        GENERAL_PARAMETERPERMISSIONS.setUserDisallowed(GENERAL_STRING_LIST);
        
        GENERAL_PERMISSIONITEM.setRpcName(GENERAL_STRING);
        GENERAL_PERMISSIONITEM.setHMIPermissions(GENERAL_HMIPERMISSIONS);
        GENERAL_PERMISSIONITEM.setParameterPermissions(GENERAL_PARAMETERPERMISSIONS);
        GENERAL_PERMISSIONITEM_LIST.add(GENERAL_PERMISSIONITEM);
        
		try {	
			JSON_HMIPERMISSIONS.put(HMIPermissions.KEY_ALLOWED, GENERAL_HMILEVEL_LIST);
			JSON_HMIPERMISSIONS.put(HMIPermissions.KEY_USER_DISALLOWED, GENERAL_HMILEVEL_LIST);
			
			JSON_TOUCHEVENTCAPABILITIES.put(TouchEventCapabilities.KEY_DOUBLE_PRESS_AVAILABLE, GENERAL_BOOLEAN);
			JSON_TOUCHEVENTCAPABILITIES.put(TouchEventCapabilities.KEY_MULTI_TOUCH_AVAILABLE, GENERAL_BOOLEAN);
			JSON_TOUCHEVENTCAPABILITIES.put(TouchEventCapabilities.KEY_PRESS_AVAILABLE, GENERAL_BOOLEAN);
			
			JSON_IMAGERESOLUTION.put(ImageResolution.KEY_RESOLUTION_HEIGHT, GENERAL_INT);
			JSON_IMAGERESOLUTION.put(ImageResolution.KEY_RESOLUTION_WIDTH, GENERAL_INT);
			
			JSON_CHOICE.put(Choice.KEY_MENU_NAME, GENERAL_STRING);
			JSON_CHOICE.put(Choice.KEY_SECONDARY_TEXT, GENERAL_STRING);
			JSON_CHOICE.put(Choice.KEY_TERTIARY_TEXT, GENERAL_STRING);
			JSON_CHOICE.put(Choice.KEY_CHOICE_ID, GENERAL_INT);
			JSON_CHOICE.put(Choice.KEY_IMAGE, JSON_IMAGE);
			JSON_CHOICE.put(Choice.KEY_SECONDARY_IMAGE, JSON_IMAGE);
			JSON_CHOICE.put(Choice.KEY_VR_COMMANDS, JsonUtils.createJsonArray(GENERAL_STRING_LIST));
			
			JSON_HMILEVELS.put(HMILevel.HMI_FULL);
			JSON_HMILEVELS.put(HMILevel.HMI_BACKGROUND);
			
			JSON_HMIPERMISSIONS.put(HMIPermissions.KEY_ALLOWED, JSON_HMILEVELS);
			JSON_HMIPERMISSIONS.put(HMIPermissions.KEY_USER_DISALLOWED, JSON_HMILEVELS);
			
			JSON_PARAMETERPERMISSIONS.put(ParameterPermissions.KEY_ALLOWED, JsonUtils.createJsonArray(GENERAL_STRING_LIST));
			JSON_PARAMETERPERMISSIONS.put(ParameterPermissions.KEY_USER_DISALLOWED, JsonUtils.createJsonArray(GENERAL_STRING_LIST));
			
			JSON_PERMISSIONITEM.put(PermissionItem.KEY_HMI_PERMISSIONS, JSON_HMIPERMISSIONS);
			JSON_PERMISSIONITEM.put(PermissionItem.KEY_PARAMETER_PERMISSIONS, JSON_PARAMETERPERMISSIONS);
			JSON_PERMISSIONITEM.put(PermissionItem.KEY_RPC_NAME, GENERAL_STRING);
			JSON_PERMISSIONITEMS.put(JSON_PERMISSIONITEM);
			
			JSON_IMAGE.put(Image.KEY_IMAGE_TYPE, GENERAL_IMAGETYPE);
			JSON_IMAGE.put(Image.KEY_VALUE, GENERAL_STRING);
			
			JSON_SOFTBUTTON.put(SoftButton.KEY_IS_HIGHLIGHTED , GENERAL_BOOLEAN);
			JSON_SOFTBUTTON.put(SoftButton.KEY_SOFT_BUTTON_ID, GENERAL_INT);
			JSON_SOFTBUTTON.put(SoftButton.KEY_SYSTEM_ACTION, SystemAction.STEAL_FOCUS);
			JSON_SOFTBUTTON.put(SoftButton.KEY_TEXT, GENERAL_STRING);
			JSON_SOFTBUTTON.put(SoftButton.KEY_TYPE, SoftButtonType.SBT_TEXT);
			JSON_SOFTBUTTON.put(SoftButton.KEY_IMAGE, GENERAL_IMAGE.serializeJSON());
			JSON_SOFTBUTTONS.put(JSON_SOFTBUTTON);
			
			JSON_TURN.put(Turn.KEY_NAVIGATION_TEXT, GENERAL_STRING);
			JSON_TURN.put(Turn.KEY_TURN_IMAGE, GENERAL_IMAGE.serializeJSON());
			JSON_TURNS.put(JSON_TURN);
			
			JSON_MENUPARAMS.put(MenuParams.KEY_MENU_NAME, GENERAL_STRING);
			JSON_MENUPARAMS.put(MenuParams.KEY_PARENT_ID, GENERAL_INT);
			JSON_MENUPARAMS.put(MenuParams.KEY_POSITION, GENERAL_INT);
			
	    	JSON_VRHELPITEM.put(VrHelpItem.KEY_TEXT, GENERAL_STRING);
	    	JSON_VRHELPITEM.put(VrHelpItem.KEY_IMAGE, JSON_IMAGE);
	    	JSON_VRHELPITEM.put(VrHelpItem.KEY_POSITION, GENERAL_INT);
	    	JSON_VRHELPITEMS.put(JSON_VRHELPITEM);
	    	
	    	JSONObject jsonTtsChunk = new JSONObject();
	    	jsonTtsChunk.put(TTSChunk.KEY_TEXT, "Welcome to the jungle");
	    	jsonTtsChunk.put(TTSChunk.KEY_TYPE, SpeechCapabilities.TEXT);
	    	JSON_TTSCHUNKS.put(jsonTtsChunk);
	    	jsonTtsChunk = new JSONObject();
	    	jsonTtsChunk.put(TTSChunk.KEY_TEXT, "Say a command");
	    	jsonTtsChunk.put(TTSChunk.KEY_TYPE, SpeechCapabilities.TEXT);
	    	JSON_TTSCHUNKS.put(jsonTtsChunk);
	    	
	    	JSON_KEYBOARDPROPERTIES.put(KeyboardProperties.KEY_AUTO_COMPLETE_TEXT, GENERAL_STRING);
	    	JSON_KEYBOARDPROPERTIES.put(KeyboardProperties.KEY_KEYPRESS_MODE, KeypressMode.SINGLE_KEYPRESS);
	    	JSON_KEYBOARDPROPERTIES.put(KeyboardProperties.KEY_KEYBOARD_LAYOUT, KeyboardLayout.QWERTY);
	    	JSON_KEYBOARDPROPERTIES.put(KeyboardProperties.KEY_LANGUAGE, Language.EN_US);
	    	JSON_KEYBOARDPROPERTIES.put(KeyboardProperties.KEY_LIMITED_CHARACTER_LIST, JsonUtils.createJsonArray(GENERAL_STRING_LIST));
	    	
			JSON_STARTTIME.put(StartTime.KEY_HOURS, GENERAL_STARTTIME.getHours());
			JSON_STARTTIME.put(StartTime.KEY_MINUTES, GENERAL_STARTTIME.getMinutes());
			JSON_STARTTIME.put(StartTime.KEY_SECONDS, GENERAL_STARTTIME.getSeconds());
					
			JSON_CHOICES.put(JSON_CHOICE);
			
			JSON_DEVICEINFO.put(DeviceInfo.KEY_CARRIER, GENERAL_STRING);
			JSON_DEVICEINFO.put(DeviceInfo.KEY_FIRMWARE_REV, GENERAL_STRING);
			JSON_DEVICEINFO.put(DeviceInfo.KEY_HARDWARE, GENERAL_STRING);
			JSON_DEVICEINFO.put(DeviceInfo.KEY_MAX_NUMBER_RFCOMM_PORTS, GENERAL_INT);
			JSON_DEVICEINFO.put(DeviceInfo.KEY_OS, GENERAL_STRING);
			JSON_DEVICEINFO.put(DeviceInfo.KEY_OS_VERSION, GENERAL_STRING);
			
			JSON_SDLMSGVERSION.put(SdlMsgVersion.KEY_MAJOR_VERSION, GENERAL_INT);
			JSON_SDLMSGVERSION.put(SdlMsgVersion.KEY_MINOR_VERSION, GENERAL_INT);	
			
			JSON_DIDRESULT.put(DIDResult.KEY_DATA, GENERAL_STRING);
			JSON_DIDRESULT.put(DIDResult.KEY_DID_LOCATION, GENERAL_INT);
			JSON_DIDRESULT.put(DIDResult.KEY_RESULT_CODE, VehicleDataResultCode.SUCCESS);
			JSON_DIDRESULTS.put(JSON_DIDRESULT);
			
			JSON_PRESETBANKCAPABILITIES.put(PresetBankCapabilities.KEY_ON_SCREEN_PRESETS_AVAILABLE, GENERAL_BOOLEAN);
			
			JSONObject jsonButton = new JSONObject();
			jsonButton.put(ButtonCapabilities.KEY_LONG_PRESS_AVAILABLE, false);
			jsonButton.put(ButtonCapabilities.KEY_SHORT_PRESS_AVAILABLE, GENERAL_BOOLEAN);
			jsonButton.put(ButtonCapabilities.KEY_UP_DOWN_AVAILABLE, GENERAL_BOOLEAN);
			jsonButton.put(ButtonCapabilities.KEY_NAME, ButtonName.SEEKRIGHT);
			JSON_BUTTONCAPABILITIES.put(jsonButton);
			
			jsonButton = new JSONObject();
			jsonButton.put(SoftButtonCapabilities.KEY_LONG_PRESS_AVAILABLE, GENERAL_BOOLEAN);
			jsonButton.put(SoftButtonCapabilities.KEY_SHORT_PRESS_AVAILABLE, GENERAL_BOOLEAN);
			jsonButton.put(SoftButtonCapabilities.KEY_UP_DOWN_AVAILABLE, GENERAL_BOOLEAN);
			jsonButton.put(SoftButtonCapabilities.KEY_IMAGE_SUPPORTED, GENERAL_BOOLEAN);
			JSON_SOFTBUTTONCAPABILITIES.put(jsonButton);
			
			jsonButton = new JSONObject();
			jsonButton.put(AudioPassThruCapabilities.KEY_AUDIO_TYPE, GENERAL_AUDIOTYPE);
			jsonButton.put(AudioPassThruCapabilities.KEY_BITS_PER_SAMPLE, GENERAL_BITSPERSAMPLE);
			jsonButton.put(AudioPassThruCapabilities.KEY_SAMPLING_RATE, GENERAL_SAMPLINGRATE);
			JSON_AUDIOPASSTHRUCAPABILITIES.put(jsonButton);
			
			JSON_TEXTFIELD.put(TextField.KEY_CHARACTER_SET, CharacterSet.CID1SET);
			JSON_TEXTFIELD.put(TextField.KEY_NAME, TextFieldName.ETA);
			JSON_TEXTFIELD.put(TextField.KEY_ROWS, GENERAL_INT);
			JSON_TEXTFIELD.put(TextField.KEY_WIDTH, GENERAL_INT);
			JSON_TEXTFIELDS.put(JSON_TEXTFIELD);
			
			JSON_IMAGEFIELD.put(ImageField.KEY_IMAGE_RESOLUTION, JSON_IMAGERESOLUTION);
			JSON_IMAGEFIELD.put(ImageField.KEY_IMAGE_TYPE_SUPPORTED, JsonUtils.createJsonArray(Test.GENERAL_FILETYPE_LIST));
			JSON_IMAGEFIELD.put(ImageField.KEY_NAME, ImageFieldName.graphic);
			JSON_IMAGEFIELDS.put(JSON_IMAGEFIELD);
			
			JSONObject jsonTEC = new JSONObject();
			jsonTEC.put(TouchEventCapabilities.KEY_DOUBLE_PRESS_AVAILABLE, GENERAL_BOOLEAN);
			jsonTEC.put(TouchEventCapabilities.KEY_MULTI_TOUCH_AVAILABLE, GENERAL_BOOLEAN);
			jsonTEC.put(TouchEventCapabilities.KEY_PRESS_AVAILABLE, GENERAL_BOOLEAN);
			
			JSON_SCREENPARAMS.put(ScreenParams.KEY_RESOLUTION, JSON_IMAGERESOLUTION);
			JSON_SCREENPARAMS.put(ScreenParams.KEY_TOUCH_EVENT_AVAILABLE, jsonTEC);
			
			JSON_DISPLAYCAPABILITIES.put(DisplayCapabilities.KEY_DISPLAY_TYPE, GENERAL_DISPLAYTYPE);
			JSON_DISPLAYCAPABILITIES.put(DisplayCapabilities.KEY_GRAPHIC_SUPPORTED, GENERAL_BOOLEAN);
			JSON_DISPLAYCAPABILITIES.put(DisplayCapabilities.KEY_IMAGE_FIELDS, JSON_IMAGEFIELDS);
			JSON_DISPLAYCAPABILITIES.put(DisplayCapabilities.KEY_MEDIA_CLOCK_FORMATS, JsonUtils.createJsonArray(GENERAL_MEDIACLOCKFORMAT_LIST));
			JSON_DISPLAYCAPABILITIES.put(DisplayCapabilities.KEY_NUM_CUSTOM_PRESETS_AVAILABLE, GENERAL_INT);
			JSON_DISPLAYCAPABILITIES.put(DisplayCapabilities.KEY_SCREEN_PARAMS, JSON_SCREENPARAMS);
			JSON_DISPLAYCAPABILITIES.put(DisplayCapabilities.KEY_TEMPLATES_AVAILABLE, JsonUtils.createJsonArray(GENERAL_STRING_LIST));
			JSON_DISPLAYCAPABILITIES.put(DisplayCapabilities.KEY_TEXT_FIELDS, JSON_TEXTFIELDS);
			
			JSON_TOUCHCOORD.put(TouchCoord.KEY_X, GENERAL_INT);
			JSON_TOUCHCOORD.put(TouchCoord.KEY_Y, GENERAL_INT);
			JSON_TOUCHCOORDS.put(JSON_TOUCHCOORD);
			
			JSON_TOUCHEVENT.put(TouchEvent.KEY_C, JSON_TOUCHCOORDS);
			JSON_TOUCHEVENT.put(TouchEvent.KEY_ID, GENERAL_INT);
			JSON_TOUCHEVENT.put(TouchEvent.KEY_TS, JsonUtils.createJsonArray(GENERAL_LONG_LIST));
			JSON_TOUCHEVENTS.put(JSON_TOUCHEVENT);
			
		} catch (JSONException e) {
			Log.e("Test", "Static Json Construction Failed.", e);
		}
	}	
}