package com.smartdevicelink.protocol.enums;

import java.util.HashMap;
import java.util.Map;

public class FunctionID {
    private static Map<String, Integer> functionIDMap = null;
    public static final String SYNC_P_DATA = "SyncPData";
	public static final String SHOW_CONSTANT_TBT = "ShowConstantTBT";
	public static final String ALERT_MANEUVER = "AlertManeuver";
	public static final String UPDATE_TURN_LIST = "UpdateTurnList";
	public static final String ON_SYNC_P_DATA = "OnSyncPData";
	public static final String REGISTER_APP_INTERFACE = "RegisterAppInterface";
	public static final String UNREGISTER_APP_INTERFACE = "UnregisterAppInterface";
	public static final String ALERT = "Alert";
	public static final String SHOW = "Show";
	public static final String SPEAK = "Speak";
	public static final String ADD_COMMAND = "AddCommand";
	public static final String DELETE_COMMAND = "DeleteCommand";
	public static final String ADD_SUB_MENU = "AddSubMenu";
	public static final String DELETE_SUB_MENU = "DeleteSubMenu";
	public static final String CREATE_INTERACTION_CHOICE_SET = "CreateInteractionChoiceSet";
	public static final String DELETE_INTERACTION_CHOICE_SET = "DeleteInteractionChoiceSet";
	public static final String PERFORM_INTERACTION = "PerformInteraction";
	public static final String ENCODED_SYNC_P_DATA = "EncodedSyncPData";
	public static final String SUBSCRIBE_BUTTON = "SubscribeButton";
	public static final String UNSUBSCRIBE_BUTTON = "UnsubscribeButton";
	public static final String SUBSCRIBE_VEHICLE_DATA = "SubscribeVehicleData";
	public static final String UNSUBSCRIBE_VEHICLE_DATA = "UnsubscribeVehicleData";
	public static final String SET_MEDIA_CLOCK_TIMER = "SetMediaClockTimer";
	public static final String SET_GLOBAL_PROPERTIES = "SetGlobalProperties";
	public static final String GENERIC_RESPONSE = "GenericResponse";
	public static final String SCROLLABLE_MESSAGE = "ScrollableMessage";
	public static final String GET_DTCS = "GetDTCs";
	public static final String DIAGNOSTIC_MESSAGE = "DiagnosticMessage";
	public static final String SYSTEM_REQUEST = "SystemRequest";
	public static final String READ_DID = "ReadDID";
	public static final String ON_VEHICLE_DATA = "OnVehicleData";
	public static final String PUT_FILE = "PutFile";
	public static final String DELETE_FILE = "DeleteFile";
	public static final String LIST_FILES = "ListFiles";
	public static final String GET_VEHICLE_DATA = "GetVehicleData";
	public static final String RESET_GLOBAL_PROPERTIES = "ResetGlobalProperties";
	public static final String SET_APP_ICON = "SetAppIcon";
	public static final String CHANGE_REGISTRATION = "ChangeRegistration";
	public static final String SET_DISPLAY_LAYOUT = "SetDisplayLayout";
	public static final String ON_LANGUAGE_CHANGE = "OnLanguageChange";
	public static final String PERFORM_AUDIO_PASS_THRU = "PerformAudioPassThru";
	public static final String END_AUDIO_PASS_THRU = "EndAudioPassThru";
	public static final String ON_AUDIO_PASS_THRU = "OnAudioPassThru";
	public static final String ON_COMMAND = "OnCommand";
	public static final String ON_BUTTON_PRESS = "OnButtonPress";
	public static final String ON_BUTTON_EVENT = "OnButtonEvent";
	public static final String ON_HMI_STATUS = "OnHMIStatus";
	public static final String ON_TBT_CLIENT_STATE = "OnTBTClientState";
	public static final String ON_ENCODED_SYNC_P_DATA = "OnEncodedSyncPData";
	public static final String ON_DRIVER_DISTRACTION = "OnDriverDistraction";
	public static final String ON_APP_INTERFACE_UNREGISTERED = "OnAppInterfaceUnregistered";
	public static final String ON_KEYBOARD_INPUT = "OnKeyboardInput";
	public static final String ON_TOUCH_EVENT = "OnTouchEvent";
	public static final String ON_SYSTEM_REQUEST = "OnSystemRequest";
	public static final String ON_HASH_CHANGE = "OnHashChange";
	public static final String ON_PERMISSIONS_CHANGE = "OnPermissionsChange";
	public static final String SLIDER = "Slider";
	public static final String ON_LOCK_SCREEN_STATUS = "OnLockScreenStatus";
	public static final String ON_SDL_CHOICE_CHOSEN = "OnSdlChoiceChosen";
	
	public static final String SEND_LOCATION = "SendLocation";
	public static final String DIAL_NUMBER = "DialNumber";

    public FunctionID() {
    }

    static public String getFunctionName(int i) {
        if (null == functionIDMap) {
            initFunctionIds();
        }

        for (Map.Entry<String, Integer> entry : functionIDMap.entrySet()) {
            if (i == entry.getValue()) {
                return entry.getKey();
            }
        }

        return null;
    }

    static public int getFunctionID(String functionName) {
        if (null == functionIDMap) {
            initFunctionIds();
        }

        final Integer functionID = functionIDMap.get(functionName);
        return (functionID != null) ? functionID : -1;
    }

    static public void initFunctionIds() {

        functionIDMap = new HashMap<String, Integer>(60) {/**
             * 
             */
            private static final long serialVersionUID = 6301013743706264910L;

        {
            /*
                Base Request / Response RPCs
                Range = 0x 0000 0001 - 0x 0000 7FFF
             */
            put(FunctionID.REGISTER_APP_INTERFACE, 1);
            put(FunctionID.UNREGISTER_APP_INTERFACE, 2);
            put(FunctionID.SET_GLOBAL_PROPERTIES, 3);
            put(FunctionID.RESET_GLOBAL_PROPERTIES, 4);
            put(FunctionID.ADD_COMMAND, 5);
            put(FunctionID.DELETE_COMMAND, 6);
            put(FunctionID.ADD_SUB_MENU, 7);
            put(FunctionID.DELETE_SUB_MENU, 8);
            put(FunctionID.CREATE_INTERACTION_CHOICE_SET, 9);
            put(FunctionID.PERFORM_INTERACTION, 10);
            put(FunctionID.DELETE_INTERACTION_CHOICE_SET, 11);
            put(FunctionID.ALERT, 12);
            put(FunctionID.SHOW, 13);
            put(FunctionID.SPEAK, 14);
            put(FunctionID.SET_MEDIA_CLOCK_TIMER, 15);
            put(FunctionID.PERFORM_AUDIO_PASS_THRU, 16);
            put(FunctionID.END_AUDIO_PASS_THRU, 17);
            put(FunctionID.SUBSCRIBE_BUTTON, 18);
            put(FunctionID.UNSUBSCRIBE_BUTTON, 19);
            put(FunctionID.SUBSCRIBE_VEHICLE_DATA, 20);
            put(FunctionID.UNSUBSCRIBE_VEHICLE_DATA, 21);
            put(FunctionID.GET_VEHICLE_DATA, 22);
            put(FunctionID.READ_DID, 23);
            put(FunctionID.GET_DTCS, 24);
            put(FunctionID.SCROLLABLE_MESSAGE, 25);
            put(FunctionID.SLIDER, 26);
            put(FunctionID.SHOW_CONSTANT_TBT, 27);
            put(FunctionID.ALERT_MANEUVER, 28);
            put(FunctionID.UPDATE_TURN_LIST, 29);
            put(FunctionID.CHANGE_REGISTRATION, 30);
            put(FunctionID.GENERIC_RESPONSE, 31);
            put(FunctionID.PUT_FILE, 32);
            put(FunctionID.DELETE_FILE, 33);
            put(FunctionID.LIST_FILES, 34);
            put(FunctionID.SET_APP_ICON, 35);
            put(FunctionID.SET_DISPLAY_LAYOUT, 36);
            put(FunctionID.DIAGNOSTIC_MESSAGE, 37);
            put(FunctionID.SYSTEM_REQUEST, 38);
            put(FunctionID.SEND_LOCATION, 39);
            put(FunctionID.DIAL_NUMBER, 40);
            /*
                Base Notifications
                Range = 0x 0000 8000 - 0x 0000 FFFF
             */
            put(FunctionID.ON_HMI_STATUS, 32768);
            put(FunctionID.ON_APP_INTERFACE_UNREGISTERED, 32769);
            put(FunctionID.ON_BUTTON_EVENT, 32770);
            put(FunctionID.ON_BUTTON_PRESS, 32771);
            put(FunctionID.ON_VEHICLE_DATA, 32772);
            put(FunctionID.ON_COMMAND, 32773);
            put(FunctionID.ON_TBT_CLIENT_STATE, 32774);
            put(FunctionID.ON_DRIVER_DISTRACTION, 32775);
            put(FunctionID.ON_PERMISSIONS_CHANGE, 32776);
            put(FunctionID.ON_AUDIO_PASS_THRU, 32777);
            put(FunctionID.ON_LANGUAGE_CHANGE, 32778);
            put(FunctionID.ON_SYSTEM_REQUEST, 32781);
            put(FunctionID.ON_HASH_CHANGE, 32782);

            /*
                Ford Specific Request / Response RPCs
                Range = 0x 0001 0000 - 0x 0001 7FFF
             */
            put(FunctionID.ENCODED_SYNC_P_DATA, 65536);
            put(FunctionID.SYNC_P_DATA, 65537);

            /*
                Ford Specific Notifications
                Range = 0x 0001 8000 - 0x 0001 FFFF
             */
            put(FunctionID.ON_ENCODED_SYNC_P_DATA, 98304);
            put(FunctionID.ON_SYNC_P_DATA, 98305);

            // OnKeyboardInput
            put(FunctionID.ON_KEYBOARD_INPUT, 32779);
            // OnTouchEvent
            put(FunctionID.ON_TOUCH_EVENT, 32780);
        }};
    }
}
