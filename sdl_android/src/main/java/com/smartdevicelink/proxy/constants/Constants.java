package com.smartdevicelink.proxy.constants;

/**
 * Created by mat on 5/25/17.
 */

public class Constants {


    public static final String SDL_LIB_TRACE_KEY = "42baba60-eb57-11df-98cf-0800200c9a66";

//  AddCommand
    public static final String KEY_CMD_ICON = "cmdIcon";
    public static final String KEY_MENU_PARAMS = "menuParams";
    public static final String KEY_CMD_ID = "cmdID";
    public static final String KEY_VR_COMMANDS = "vrCommands";
    public static final String KEY_POSITION = "position";
    public static final String KEY_MENU_NAME = "menuName";
    public static final String KEY_MENU_ID = "menuID";

//  Airbag status
    public static final String KEY_DRIVER_AIRBAG_DEPLOYED = "driverAirbagDeployed";
    public static final String KEY_DRIVER_SIDE_AIRBAG_DEPLOYED = "driverSideAirbagDeployed";
    public static final String KEY_DRIVER_CURTAIN_AIRBAG_DEPLOYED = "driverCurtainAirbagDeployed";
    public static final String KEY_DRIVER_KNEE_AIRBAG_DEPLOYED = "driverKneeAirbagDeployed";
    public static final String KEY_PASSENGER_AIRBAG_DEPLOYED = "passengerAirbagDeployed";
    public static final String KEY_PASSENGER_SIDE_AIRBAG_DEPLOYED = "passengerSideAirbagDeployed";
    public static final String KEY_PASSENGER_CURTAIN_AIRBAG_DEPLOYED = "passengerCurtainAirbagDeployed";
    public static final String KEY_PASSENGER_KNEE_AIRBAG_DEPLOYED = "passengerKneeAirbagDeployed";

//  Alert
    public static final String KEY_PLAY_TONE = "playTone";
    public static final String KEY_DURATION = "duration";
    public static final String KEY_ALERT_TEXT_1 = "alertText1";
    public static final String KEY_ALERT_TEXT_2 = "alertText2";
    public static final String KEY_ALERT_TEXT_3 = "alertText3";
    public static final String KEY_PROGRESS_INDICATOR = "progressIndicator";
    public static final String KEY_TTS_CHUNKS = "ttsChunks";
    public static final String KEY_SOFT_BUTTONS = "softButtons";
    public static final String KEY_TRY_AGAIN_TIME = "tryAgainTime";

// AudioPassThruCapabilities
    public static final String KEY_SAMPLING_RATE = "samplingRate";
    public static final String KEY_AUDIO_TYPE = "audioType";
    public static final String KEY_BITS_PER_SAMPLE = "bitsPerSample";

//  Belt Status
    public static final String KEY_DRIVER_BELT_DEPLOYED = "driverBeltDeployed";
    public static final String KEY_PASSENGER_BELT_DEPLOYED = "passengerBeltDeployed";
    public static final String KEY_PASSENGER_BUCKLE_BELTED = "passengerBuckleBelted";
    public static final String KEY_DRIVER_BUCKLE_BELTED = "driverBuckleBelted";
    public static final String KEY_LEFT_ROW_2_BUCKLE_BELTED = "leftRow2BuckleBelted";
    public static final String KEY_PASSENGER_CHILD_DETECTED = "passengerChildDetected";
    public static final String KEY_RIGHT_ROW_2_BUCKLE_BELTED = "rightRow2BuckleBelted";
    public static final String KEY_MIDDLE_ROW_2_BUCKLE_BELTED = "middleRow2BuckleBelted";
    public static final String KEY_MIDDLE_ROW_3_BUCKLE_BELTED = "middleRow3BuckleBelted";
    public static final String KEY_LEFT_ROW_3_BUCKLE_BELTED = "leftRow3BuckleBelted";
    public static final String KEY_RIGHT_ROW_3_BUCKLE_BELTED = "rightRow3BuckleBelted";
    public static final String KEY_REAR_INFLATABLE_BELTED = "rearInflatableBelted";
    public static final String KEY_RIGHT_REAR_INFLATABLE_BELTED = "rightRearInflatableBelted";
    public static final String KEY_MIDDLE_ROW_1_BELT_DEPLOYED = "middleRow1BeltDeployed";
    public static final String KEY_MIDDLE_ROW_1_BUCKLE_BELTED = "middleRow1BuckleBelted";


//   BodyInformation
    public static final String KEY_PARK_BRAKE_ACTIVE = "parkBrakeActive";
    public static final String KEY_IGNITION_STABLE_STATUS = "ignitionStableStatus";
    public static final String KEY_IGNITION_STATUS = "ignitionStatus";
    public static final String KEY_DRIVER_DOOR_AJAR = "driverDoorAjar";
    public static final String KEY_PASSENGER_DOOR_AJAR = "passengerDoorAjar";
    public static final String KEY_REAR_LEFT_DOOR_AJAR = "rearLeftDoorAjar";
    public static final String KEY_REAR_RIGHT_DOOR_AJAR = "rearRightDoorAjar";

//    Button capabilities
    public static final String KEY_NAME = "name";
    public static final String KEY_SHORT_PRESS_AVAILABLE = "shortPressAvailable";
    public static final String KEY_LONG_PRESS_AVAILABLE = "longPressAvailable";
    public static final String KEY_UP_DOWN_AVAILABLE = "upDownAvailable";

//    ChangeRegistration
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_HMI_DISPLAY_LANGUAGE = "hmiDisplayLanguage";
    public static final String KEY_APP_NAME = "appName";
    public static final String KEY_TTS_NAME = "ttsName";
    public static final String KEY_NGN_MEDIA_SCREEN_NAME = "ngnMediaScreenAppName";
    public static final String KEY_VR_SYNONYMS = "vrSynonyms";

//    Choice
    public static final String KEY_SECONDARY_TEXT = "secondaryText";
    public static final String KEY_TERTIARY_TEXT = "tertiaryText";
    public static final String KEY_SECONDARY_IMAGE = "secondaryImage";
    public static final String KEY_MENU_NAME = "menuName";
    public static final String KEY_VR_COMMANDS = "vrCommands";
    public static final String KEY_CHOICE_ID = "choiceID";
    public static final String KEY_IMAGE = "image";

//  ClusterModeStatuds
    public static final String KEY_POWER_MODE_ACTIVE = "powerModeActive";
    public static final String KEY_POWER_MODE_QUALIFICATION_STATUS = "powerModeQualificationStatus";
    public static final String KEY_CAR_MODE_STATUS = "carModeStatus";
    public static final String KEY_POWER_MODE_STATUS = "powerModeStatus";

//  Coordinate
    public static final String KEY_LATITUDE_DEGREES = "latitudeDegrees";
    public static final String KEY_LONGITUDE_DEGREES = "longitudeDegrees";

//    CreateInteractionChoiceSet
    public static final String KEY_CHOICE_SET = "choiceSet";
    public static final String KEY_INTERACTION_CHOICE_SET_ID = "interactionChoiceSetID";

//    DateTime
    public static final String KEY_MILLISECOND = "millisecond";
    public static final String KEY_SECOND = "second";
    public static final String KEY_MINUTE = "minute";
    public static final String KEY_HOUR = "hour";
    public static final String KEY_DAY = "day";
    public static final String KEY_MONTH = "month";
    public static final String KEY_YEAR = "year";
    public static final String KEY_TZ_HOUR = "tz_hour";
    public static final String KEY_TZ_MINUTE = "tz_minute";

//  DeleteCommand
    public static final String KEY_CMD_ID = "cmdID";

//  DeleteFile
    public static final String KEY_SDL_FILE_NAME = "syncFileName";

//  DeleteFileResponse
    public static final String KEY_SPACE_AVAILABLE = "spaceAvailable";

//  DeleteInteractionChoiceSet
    public static final String KEY_INTERACTION_CHOICE_SET_ID = "interactionChoiceSetID";

//  DeleteSubMenu
    public static final String KEY_MENU_ID = "menuID";

//  DeviceInfo
    public static final String KEY_HARDWARE = "hardware";
    public static final String KEY_FIRMWARE_REV = "firmwareRev";
    public static final String KEY_OS = "os";
    public static final String KEY_OS_VERSION = "osVersion";
    public static final String KEY_CARRIER = "carrier";
    public static final String KEY_MAX_NUMBER_RFCOMM_PORTS = "maxNumberRFCOMMPorts";
    public static final String DEVICE_OS = "Android";

//  DeviceStatus
    public static final String KEY_VOICE_REC_ON = "voiceRecOn";
    public static final String KEY_BT_ICON_ON = "btIconOn";
    public static final String KEY_CALL_ACTIVE = "callActive";
    public static final String KEY_PHONE_ROAMING = "phoneRoaming";
    public static final String KEY_TEXT_MSG_AVAILABLE = "textMsgAvailable";
    public static final String KEY_BATT_LEVEL_STATUS = "battLevelStatus";
    public static final String KEY_STEREO_AUDIO_OUTPUT_MUTED = "stereoAudioOutputMuted";
    public static final String KEY_MONO_AUDIO_OUTPUT_MUTED = "monoAudioOutputMuted";
    public static final String KEY_SIGNAL_LEVEL_STATUS = "signalLevelStatus";
    public static final String KEY_PRIMARY_AUDIO_SOURCE = "primaryAudioSource";
    public static final String KEY_E_CALL_EVENT_ACTIVE = "eCallEventActive";

//  DiagnosticMessage
    public static final String KEY_TARGET_ID = "targetID";
    public static final String KEY_MESSAGE_LENGTH = "messageLength";
    public static final String KEY_MESSAGE_DATA = "messageData";

//  DiagnosticMessageResponse
    public static final String KEY_MESSAGE_DATA_RESULT = "messageDataResult";

//  DialNumber
    public static final String KEY_NUMBER = "number";

//  DIDResult
    public static final String KEY_RESULT_CODE = "resultCode";
    public static final String KEY_DATA = "data";
    public static final String KEY_DID_LOCATION = "didLocation";

//  DisplayCapabilities
    public static final String KEY_DISPLAY_TYPE = "displayType";
    public static final String KEY_MEDIA_CLOCK_FORMATS = "mediaClockFormats";
    public static final String KEY_TEXT_FIELDS = "textFields";
    public static final String KEY_IMAGE_FIELDS = "imageFields";
    public static final String KEY_GRAPHIC_SUPPORTED = "graphicSupported";
    public static final String KEY_SCREEN_PARAMS = "screenParams";
    public static final String KEY_TEMPLATES_AVAILABLE = "templatesAvailable";
    public static final String KEY_NUM_CUSTOM_PRESETS_AVAILABLE = "numCustomPresetsAvailable";


//  DTC
    public static final String KEY_IDENTIFIER = "identifier";
    public static final String KEY_STATUS_BYTE = "statusByte";

//  ECallInfo
    public static final String KEY_E_CALL_NOTIFICATION_STATUS = "eCallNotificationStatus";
    public static final String KEY_AUX_E_CALL_NOTIFICATION_STATUS = "auxECallNotificationStatus";
    public static final String KEY_E_CALL_CONFIRMATION_STATUS = "eCallConfirmationStatus";

//  EmergencyEvent
    public static final String KEY_EMERGENCY_EVENT_TYPE = "emergencyEventType";
    public static final String KEY_FUEL_CUTOFF_STATUS = "fuelCutoffStatus";
    public static final String KEY_ROLLOVER_EVENT = "rolloverEvent";
    public static final String KEY_MAXIMUM_CHANGE_VELOCITY = "maximumChangeVelocity";
    public static final String KEY_MULTIPLE_EVENTS = "multipleEvents";

//  GetDTCs
    public static final String KEY_DTC_MASK = "dtcMask";
    public static final String KEY_ECU_NAME = "ecuName";

//  GetDTCsResponse
    public static final String KEY_ECU_HEADER = "ecuHeader";
    public static final String KEY_DTC = "dtc";

//  GetVehicleData
    public static final String KEY_SPEED = "speed";
    public static final String KEY_RPM = "rpm";
    public static final String KEY_EXTERNAL_TEMPERATURE = "externalTemperature";
    public static final String KEY_FUEL_LEVEL = "fuelLevel";
    public static final String KEY_VIN = "vin";
    public static final String KEY_PRNDL = "prndl";
    public static final String KEY_TIRE_PRESSURE = "tirePressure";
    public static final String KEY_ENGINE_TORQUE = "engineTorque";
    public static final String KEY_ODOMETER = "odometer";
    public static final String KEY_GPS = "gps";
    public static final String KEY_FUEL_LEVEL_STATE = "fuelLevel_State";
    public static final String KEY_INSTANT_FUEL_CONSUMPTION = "instantFuelConsumption";
    public static final String KEY_BELT_STATUS = "beltStatus";
    public static final String KEY_BODY_INFORMATION = "bodyInformation";
    public static final String KEY_DEVICE_STATUS = "deviceStatus";
    public static final String KEY_DRIVER_BRAKING = "driverBraking";
    public static final String KEY_WIPER_STATUS = "wiperStatus";
    public static final String KEY_HEAD_LAMP_STATUS = "headLampStatus";
    public static final String KEY_ACC_PEDAL_POSITION = "accPedalPosition";
    public static final String KEY_STEERING_WHEEL_ANGLE = "steeringWheelAngle";
    public static final String KEY_E_CALL_INFO = "eCallInfo";
    public static final String KEY_AIRBAG_STATUS = "airbagStatus";
    public static final String KEY_EMERGENCY_EVENT = "emergencyEvent";
    public static final String KEY_CLUSTER_MODE_STATUS = "clusterModeStatus";
    public static final String KEY_MY_KEY = "myKey";

//  GetVehicleDataResponse
    public static final String KEY_SPEED = "speed";
    public static final String KEY_RPM = "rpm";
    public static final String KEY_EXTERNAL_TEMPERATURE = "externalTemperature";
    public static final String KEY_FUEL_LEVEL = "fuelLevel";
    public static final String KEY_VIN = "vin";
    public static final String KEY_PRNDL = "prndl";
    public static final String KEY_TIRE_PRESSURE = "tirePressure";
    public static final String KEY_ENGINE_TORQUE = "engineTorque";
    public static final String KEY_ODOMETER = "odometer";
    public static final String KEY_GPS = "gps";
    public static final String KEY_FUEL_LEVEL_STATE = "fuelLevel_State";
    public static final String KEY_INSTANT_FUEL_CONSUMPTION = "instantFuelConsumption";
    public static final String KEY_BELT_STATUS = "beltStatus";
    public static final String KEY_BODY_INFORMATION = "bodyInformation";
    public static final String KEY_DEVICE_STATUS = "deviceStatus";
    public static final String KEY_DRIVER_BRAKING = "driverBraking";
    public static final String KEY_WIPER_STATUS = "wiperStatus";
    public static final String KEY_HEAD_LAMP_STATUS = "headLampStatus";
    public static final String KEY_ACC_PEDAL_POSITION = "accPedalPosition";
    public static final String KEY_STEERING_WHEEL_ANGLE = "steeringWheelAngle";
    public static final String KEY_E_CALL_INFO = "eCallInfo";
    public static final String KEY_AIRBAG_STATUS = "airbagStatus";
    public static final String KEY_EMERGENCY_EVENT = "emergencyEvent";
    public static final String KEY_CLUSTER_MODE_STATUS = "clusterModeStatus";
    public static final String KEY_MY_KEY = "myKey";
    public static final String FAILED_TO_PARSE = "Failed to parse ";

//  GetWayPoints
    public static final String KEY_WAY_POINT_TYPE = "wayPointType";

//  GetWayPointsResponse
    public static final String KEY_WAY_POINTS = "wayPoints";

//  GPSData
    public static final String KEY_LONGITUDE_DEGREES = "longitudeDegrees";
    public static final String KEY_LATITUDE_DEGREES = "latitudeDegrees";
    public static final String KEY_UTC_YEAR = "utcYear";
    public static final String KEY_UTC_MONTH = "utcMonth";
    public static final String KEY_UTC_DAY = "utcDay";
    public static final String KEY_UTC_HOURS = "utcHours";
    public static final String KEY_UTC_MINUTES = "utcMinutes";
    public static final String KEY_UTC_SECONDS = "utcSeconds";
    public static final String KEY_COMPASS_DIRECTION = "compassDirection";
    public static final String KEY_PDOP = "pdop";
    public static final String KEY_VDOP = "vdop";
    public static final String KEY_HDOP = "hdop";
    public static final String KEY_ACTUAL = "actual";
    public static final String KEY_SATELLITES = "satellites";
    public static final String KEY_DIMENSION = "dimension";
    public static final String KEY_ALTITUDE = "altitude";
    public static final String KEY_HEADING = "heading";
    public static final String KEY_SPEED = "speed";

//  Headers
    public static final String KEY_CONTENT_TYPE = "ContentType";
    public static final String KEY_CONNECT_TIMEOUT = "ConnectTimeout";
    public static final String KEY_DO_OUTPUT = "DoOutput";
    public static final String KEY_DO_INPUT  = "DoInput";
    public static final String KEY_USE_CACHES = "UseCaches";
    public static final String KEY_REQUEST_METHOD = "RequestMethod";
    public static final String KEY_READ_TIMEOUT = "ReadTimeout";
    public static final String KEY_INSTANCE_FOLLOW_REDIRECTS = "InstanceFollowRedirects";
    public static final String KEY_CHARSET = "charset";
    public static final String KEY_CONTENT_LENGTH = "Content-Length";

//  HeadLampStatus
    public static final String KEY_AMBIENT_LIGHT_SENSOR_STATUS = "ambientLightSensorStatus";
    public static final String KEY_HIGH_BEAMS_ON = "highBeamsOn";
    public static final String KEY_LOW_BEAMS_ON = "lowBeamsOn";

//  HMICapabilities
    public static final String KEY_NAVIGATION = "navigation";
    public static final String KEY_PHONE_CALL = "phoneCall";

//  HMIPermissions
    public static final String KEY_ALLOWED = "allowed";
    public static final String KEY_USER_DISALLOWED = "userDisallowed";

//  HttpRequestTask
    public static final String REQUEST_TYPE_POST = "POST";
    public static final String REQUEST_TYPE_GET = "GET";
    public static final String REQUEST_TYPE_DELETE = "DELETE";

//  Image
    public static final String KEY_VALUE = "value";
    public static final String KEY_IMAGE_TYPE = "imageType";

//  ImageField
    public static final String KEY_IMAGE_TYPE_SUPPORTED = "imageTypeSupported";
    public static final String KEY_IMAGE_RESOLUTION = "imageResolution";
    public static final String KEY_NAME = "name";

//  ImageResolution
    public static final String KEY_RESOLUTION_WIDTH = "resolutionWidth";
    public static final String KEY_RESOLUTION_HEIGHT = "resolutionHeight";

//  InternalProxyMessage
    public static final String OnProxyError = "OnProxyError";
    public static final String OnProxyOpened = "OnProxyOpened";
    public static final String OnProxyClosed = "OnProxyClosed";
    public static final String OnServiceEnded = "OnServiceEnded";
    public static final String OnServiceNACKed = "OnServiceNACKed";

//  Jingles
    public static final String POSITIVE_JINGLE = "POSITIVE_JINGLE";
    public static final String NEGATIVE_JINGLE = "NEGATIVE_JINGLE";
    public static final String INITIAL_JINGLE = "INITIAL_JINGLE";
    public static final String LISTEN_JINGLE = "LISTEN_JINGLE";
    public static final String HELP_JINGLE = "HELP_JINGLE";

//  JSONFileReader
    private static final String PATH = "json/";
    private static final String EXT = ".json";

//  KeyboardProperties
    public static final String KEY_KEYPRESS_MODE = "keypressMode";
    public static final String KEY_KEYBOARD_LAYOUT = "keyboardLayout";
    public static final String KEY_LIMITED_CHARACTER_LIST = "limitedCharacterList";
    public static final String KEY_AUTO_COMPLETE_TEXT = "autoCompleteText";
    public static final String KEY_LANGUAGE = "language";

//  ListFilesResponse
    public static final String KEY_FILENAMES = "filenames";
    public static final String KEY_SPACE_AVAILABLE = "spaceAvailable";

//  LocationDetails
    public static final String KEY_LOCATION_NAME = "locationName";
    public static final String KEY_ADDRESS_LINES = "addressLines";
    public static final String KEY_LOCATION_DESCRIPTION = "locationDescription";
    public static final String KEY_PHONE_NUMBER = "phoneNumber";
    public static final String KEY_LOCATION_IMAGE = "locationImage";
    public static final String KEY_SEARCH_ADDRESS = "searchAddress";

//  MenuParams
    public static final String KEY_PARENT_ID = "parentID";
    public static final String KEY_POSITION = "position";
    public static final String KEY_MENU_NAME = "menuName";

//  Mime
    public static final String BASE_64_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";

//  MultiplexBluetoothTransport
    public static final String NAME_SECURE =" SdlRouterService";
    public static final String SHARED_PREFS = "sdl.bluetoothprefs";

//  MyKey
    public static final String KEY_E_911_OVERRIDE = "e911Override";

//  OasisAddress
    public static final String KEY_COUNTRY_NAME = "countryName";
    public static final String KEY_COUNTRY_CODE = "countryCode";
    public static final String KEY_POSTAL_CODE = "postalCode";
    public static final String KEY_ADMINISTRATIVE_AREA = "administrativeArea";
    public static final String KEY_SUB_ADMINISTRATIVE_AREA = "subAdministrativeArea";
    public static final String KEY_LOCALITY = "locality";
    public static final String KEY_SUB_LOCALITY = "subLocality";
    public static final String KEY_THOROUGH_FARE = "thoroughfare";
    public static final String KEY_SUB_THOROUGH_FARE = "subThoroughfare";

//  OnAppInterfaceUnregistered
    public static final String KEY_REASON = "reason";

//  OnButtonEvent
    public static final String KEY_BUTTON_EVENT_MODE = "buttonEventMode";
    public static final String KEY_BUTTON_NAME = "buttonName";
    public static final String KEY_CUSTOM_BUTTON_ID = "customButtonID";

//  OnButtonPress
    public static final String KEY_BUTTON_PRESS_MODE = "buttonPressMode";
    public static final String KEY_BUTTON_NAME = "buttonName";
    public static final String KEY_CUSTOM_BUTTON_ID = "customButtonID";

//  OnCommand
    public static final String KEY_CMD_ID = "cmdID";
    public static final String KEY_TRIGGER_SOURCE = "triggerSource";

//  OnDriverDistraction
    public static final String KEY_STATE = "state";

//  OnHashChange
    public static final String KEY_HASH_ID = "hashID";

//  OnHMIStatus
    public static final String KEY_AUDIO_STREAMING_STATE = "audioStreamingState";
    public static final String KEY_SYSTEM_CONTEXT = "systemContext";
    public static final String KEY_HMI_LEVEL = "hmiLevel";

//  OnKeyboardInput
    public static final String KEY_DATA = "data";
    public static final String KEY_EVENT = "event";

//  OnLanguageChange
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_HMI_DISPLAY_LANGUAGE = "hmiDisplayLanguage";

//  OnLockScreenStatus
    public static final String KEY_DRIVER_DISTRACTION = "driverDistraction";
    public static final String KEY_SHOW_LOCK_SCREEN = "showLockScreen";
    public static final String KEY_USER_SELECTED = "userSelected";

//  OnPermissionChange
    public static final String KEY_PERMISSION_ITEM = "permissionItem";

//  OnSdlChoiceChosen
    public static final String KEY_SDL_CHOICE = "sdlChoice";
    public static final String KEY_TRIGGER_SOURCE = "triggerSource";

//  OnStreamRPC
    public static final String KEY_FILENAME = "fileName";
    public static final String KEY_BYTESCOMPLETE = "bytesComplete";
    public static final String KEY_FILESIZE = "fileSize";

//  OnSystemRequest
    public static final String KEY_URL_V1 = "URL";
    public static final String KEY_URL = "url";
    public static final String KEY_TIMEOUT_V1 = "Timeout";
    public static final String KEY_TIMEOUT = "timeout";
    public static final String KEY_HEADERS = "headers";
    public static final String KEY_BODY = "body";
    public static final String KEY_FILE_TYPE = "fileType";
    public static final String KEY_REQUEST_TYPE = "requestType";
    public static final String KEY_DATA = "data";
    public static final String KEY_OFFSET = "offset";
    public static final String KEY_LENGTH = "length";

//  OnTBTClientState
    public static final String KEY_STATE = "state";

//  OnTouchEvent
    public static final String KEY_EVENT = "event";
    public static final String KEY_TYPE = "type";

//  OnVehicleData
    public static final String KEY_SPEED = "speed";
    public static final String KEY_RPM = "rpm";
    public static final String KEY_EXTERNAL_TEMPERATURE = "externalTemperature";
    public static final String KEY_FUEL_LEVEL = "fuelLevel";
    public static final String KEY_VIN = "vin";
    public static final String KEY_PRNDL = "prndl";
    public static final String KEY_TIRE_PRESSURE = "tirePressure";
    public static final String KEY_ENGINE_TORQUE = "engineTorque";
    public static final String KEY_ODOMETER = "odometer";
    public static final String KEY_GPS = "gps";
    public static final String KEY_FUEL_LEVEL_STATE = "fuelLevel_State";
    public static final String KEY_INSTANT_FUEL_CONSUMPTION = "instantFuelConsumption";
    public static final String KEY_BELT_STATUS = "beltStatus";
    public static final String KEY_BODY_INFORMATION = "bodyInformation";
    public static final String KEY_DEVICE_STATUS = "deviceStatus";
    public static final String KEY_DRIVER_BRAKING = "driverBraking";
    public static final String KEY_WIPER_STATUS = "wiperStatus";
    public static final String KEY_HEAD_LAMP_STATUS = "headLampStatus";
    public static final String KEY_ACC_PEDAL_POSITION = "accPedalPosition";
    public static final String KEY_STEERING_WHEEL_ANGLE = "steeringWheelAngle";
    public static final String KEY_E_CALL_INFO = "eCallInfo";
    public static final String KEY_AIRBAG_STATUS = "airbagStatus";
    public static final String KEY_EMERGENCY_EVENT = "emergencyEvent";
    public static final String KEY_CLUSTER_MODE_STATUS = "clusterModeStatus";
    public static final String KEY_MY_KEY = "myKey";

//  OnWayPointChange
    public static final String KEY_WAY_POINTS = "wayPoints";

//  ParametersPermissions
    public static final String KEY_ALLOWED = "allowed";
    public static final String KEY_USER_DISALLOWED = "userDisallowed";

//  PerformAudioPassThru
    public static final String KEY_MAX_DURATION = "maxDuration";
    public static final String KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_1 = "audioPassThruDisplayText1";
    public static final String KEY_AUDIO_PASS_THRU_DISPLAY_TEXT_2 = "audioPassThruDisplayText2";
    public static final String KEY_MUTE_AUDIO = "muteAudio";
    public static final String KEY_SAMPLING_RATE = "samplingRate";
    public static final String KEY_AUDIO_TYPE = "audioType";
    public static final String KEY_INITIAL_PROMPT = "initialPrompt";
    public static final String KEY_BITS_PER_SAMPLE = "bitsPerSample";

//  PerformInteraction
    public static final String KEY_INITIAL_TEXT = "initialText";
    public static final String KEY_INTERACTION_MODE = "interactionMode";
    public static final String KEY_INTERACTION_CHOICE_SET_ID_LIST = "interactionChoiceSetIDList";
    public static final String KEY_INTERACTION_LAYOUT = "interactionLayout";
    public static final String KEY_INITIAL_PROMPT = "initialPrompt";
    public static final String KEY_HELP_PROMPT = "helpPrompt";
    public static final String KEY_TIMEOUT_PROMPT = "timeoutPrompt";
    public static final String KEY_TIMEOUT = "timeout";
    public static final String KEY_VR_HELP = "vrHelp";

//  PerformInteractionResponse
    public static final String KEY_MANUAL_TEXT_ENTRY = "manualTextEntry";
    public static final String KEY_TRIGGER_SOURCE = "triggerSource";
    public static final String KEY_CHOICE_ID = "choiceID";

//  PermissionItem
    public static final String KEY_RPC_NAME = "rpcName";
    public static final String KEY_HMI_PERMISSIONS = "hmiPermissions";
    public static final String KEY_PARAMETER_PERMISSIONS = "parameterPermissions";

//  PresetBankCapabilities
    public static final String KEY_ON_SCREEN_PRESETS_AVAILABLE = "OnScreenPresetsAvailable";



































}
