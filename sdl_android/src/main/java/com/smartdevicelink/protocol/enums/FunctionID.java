package com.smartdevicelink.protocol.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public enum FunctionID{
    // DEPRECATED FUNCTIONS
    SYNC_P_DATA(65537, "SyncPData"),
    ON_SYNC_P_DATA(98305, "OnSyncPData"),
    ENCODED_SYNC_P_DATA(65536, "EncodedSyncPData"),
    ON_ENCODED_SYNC_P_DATA(98304, "OnEncodedSyncPData"),

    // REQUESTS & RESPONSES
    REGISTER_APP_INTERFACE(1, "RegisterAppInterface"),
    UNREGISTER_APP_INTERFACE(2, "UnregisterAppInterface"),
    SET_GLOBAL_PROPERTIES(3, "SetGlobalProperties"),
    RESET_GLOBAL_PROPERTIES(4, "ResetGlobalProperties"),
    ADD_COMMAND(5, "AddCommand"),
    DELETE_COMMAND(6, "DeleteCommand"),
    ADD_SUB_MENU(7, "AddSubMenu"),
    DELETE_SUB_MENU(8, "DeleteSubMenu"),
    CREATE_INTERACTION_CHOICE_SET(9, "CreateInteractionChoiceSet"),
    PERFORM_INTERACTION(10, "PerformInteraction"),
    DELETE_INTERACTION_CHOICE_SET(11, "DeleteInteractionChoiceSet"),
    ALERT(12, "Alert"),
    SHOW(13, "Show"),
    SPEAK(14, "Speak"),
    SET_MEDIA_CLOCK_TIMER(15, "SetMediaClockTimer"),
    PERFORM_AUDIO_PASS_THRU(16, "PerformAudioPassThru"),
    END_AUDIO_PASS_THRU(17, "EndAudioPassThru"),
    SUBSCRIBE_BUTTON(18, "SubscribeButton"),
    UNSUBSCRIBE_BUTTON(19, "UnsubscribeButton"),
    SUBSCRIBE_VEHICLE_DATA(20, "SubscribeVehicleData"),
    UNSUBSCRIBE_VEHICLE_DATA(21, "UnsubscribeVehicleData"),
    GET_VEHICLE_DATA(22, "GetVehicleData"),
    READ_DID(23, "ReadDID"),
    GET_DTCS(24, "GetDTCs"),
    SCROLLABLE_MESSAGE(25, "ScrollableMessage"),
    SLIDER(26, "Slider"),
    SHOW_CONSTANT_TBT(27, "ShowConstantTBT"),
    ALERT_MANEUVER(28, "AlertManeuver"),
    UPDATE_TURN_LIST(29, "UpdateTurnList"),
    CHANGE_REGISTRATION(30, "ChangeRegistration"),
    GENERIC_RESPONSE(31, "GenericResponse"),
    PUT_FILE(32, "PutFile"),
    DELETE_FILE(33, "DeleteFile"),
    LIST_FILES(34, "ListFiles"),
    SET_APP_ICON(35, "SetAppIcon"),
    SET_DISPLAY_LAYOUT(36, "SetDisplayLayout"),
    DIAGNOSTIC_MESSAGE(37, "DiagnosticMessage"),
    SYSTEM_REQUEST(38, "SystemRequest"),
    SEND_LOCATION(39, "SendLocation"),
    DIAL_NUMBER(40, "DialNumber"),
    GET_WAY_POINTS(45, "GetWayPoints"),
    SUBSCRIBE_WAY_POINTS(46, "SubscribeWayPoints"),
    UNSUBSCRIBE_WAY_POINTS(47, "UnsubscribeWayPoints"),

    // NOTIFICATIONS
    ON_HMI_STATUS(32768, "OnHMIStatus"),
    ON_APP_INTERFACE_UNREGISTERED(32769, "OnAppInterfaceUnregistered"),
    ON_BUTTON_EVENT(32770, "OnButtonEvent"),
    ON_BUTTON_PRESS(32771, "OnButtonPress"),
    ON_VEHICLE_DATA(32772, "OnVehicleData"),
    ON_COMMAND(32773, "OnCommand"),
    ON_TBT_CLIENT_STATE(32774, "OnTBTClientState"),
    ON_DRIVER_DISTRACTION(32775, "OnDriverDistraction"),
    ON_PERMISSIONS_CHANGE(32776, "OnPermissionsChange"),
    ON_AUDIO_PASS_THRU(32777, "OnAudioPassThru"),
    ON_LANGUAGE_CHANGE(32778, "OnLanguageChange"),
    ON_KEYBOARD_INPUT(32779, "OnKeyboardInput"),
    ON_TOUCH_EVENT(32780, "OnTouchEvent"),
    ON_SYSTEM_REQUEST(32781, "OnSystemRequest"),
    ON_HASH_CHANGE(32782, "OnHashChange"),
    ON_WAY_POINT_CHANGE(32784, "OnWayPointChange"),

    // MOCKED FUNCTIONS (NOT SENT FROM HEAD-UNIT)
    ON_LOCK_SCREEN_STATUS(-1, "OnLockScreenStatus"),
    ON_SDL_CHOICE_CHOSEN(-1, "OnSdlChoiceChosen"),
    ON_STREAM_RPC(-1, "OnStreamRPC"),
    STREAM_RPC(-1, "StreamRPC"),

    ;

    public static final int                 INVALID_ID = -1;

    private static HashMap<String, Integer> functionMap;

    private final int                       ID;
    private final String                    JSON_NAME;

    private FunctionID(int id, String jsonName){
        this.ID = id;
        this.JSON_NAME = jsonName;
    }

    public int getId(){
        return this.ID;
    }

    @Override
    public String toString(){
        return this.JSON_NAME;
    }

    private static void initFunctionMap(){
        functionMap = new HashMap<String, Integer>(values().length);

        for(FunctionID value : EnumSet.allOf(FunctionID.class)){
            functionMap.put(value.toString(), value.getId());
        }
    }

    public static String getFunctionName(int i){
        if(functionMap == null){
            initFunctionMap();
        }

        Iterator<Entry<String, Integer>> iterator = functionMap.entrySet().iterator();
        while(iterator.hasNext()){
            Entry<String, Integer> thisEntry = iterator.next();
            if(Integer.valueOf(i).equals(thisEntry.getValue())){
                return thisEntry.getKey();
            }
        }

        return null;
    }

    public static int getFunctionId(String functionName){
        if(functionMap == null){
            initFunctionMap();
        }

        Integer result = functionMap.get(functionName);
        return ( result == null ) ? INVALID_ID : result;
    }
}
