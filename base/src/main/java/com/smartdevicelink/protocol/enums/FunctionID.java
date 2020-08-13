/*
 * Copyright (c) 2017 - 2019, SmartDeviceLink Consortium, Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of the SmartDeviceLink Consortium, Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from this 
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
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

    RESERVED(0, "RESERVED"),

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

    BUTTON_PRESS(41, "ButtonPress"),
    GET_INTERIOR_VEHICLE_DATA(43, "GetInteriorVehicleData"),
    SET_INTERIOR_VEHICLE_DATA(44, "SetInteriorVehicleData"),

    GET_WAY_POINTS(45, "GetWayPoints"),
    SUBSCRIBE_WAY_POINTS(46, "SubscribeWayPoints"),
    UNSUBSCRIBE_WAY_POINTS(47, "UnsubscribeWayPoints"),
    GET_SYSTEM_CAPABILITY(48, "GetSystemCapability"),
	SEND_HAPTIC_DATA(49, "SendHapticData"),
    SET_CLOUD_APP_PROPERTIES(50, "SetCloudAppProperties"),
	GET_CLOUD_APP_PROPERTIES(51, "GetCloudAppProperties"),
    PUBLISH_APP_SERVICE(52, "PublishAppService"),
    GET_APP_SERVICE_DATA(53, "GetAppServiceData"),
    GET_FILE(54, "GetFile"),
    PERFORM_APP_SERVICES_INTERACTION(55, "PerformAppServiceInteraction"),
    UNPUBLISH_APP_SERVICE(56, "UnpublishAppService"),
    CANCEL_INTERACTION(57, "CancelInteraction"),
    CLOSE_APPLICATION(58, "CloseApplication"),
    SHOW_APP_MENU(59, "ShowAppMenu"),
    CREATE_WINDOW(60, "CreateWindow"),
    DELETE_WINDOW(61, "DeleteWindow"),
    GET_INTERIOR_VEHICLE_DATA_CONSENT(62, "GetInteriorVehicleDataConsent"),
    RELEASE_INTERIOR_VEHICLE_MODULE(63, "ReleaseInteriorVehicleDataModule"),
    SUBTLE_ALERT(64, "SubtleAlert"),
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
    ON_INTERIOR_VEHICLE_DATA(32783, "OnInteriorVehicleData"),
    ON_WAY_POINT_CHANGE(32784, "OnWayPointChange"),
    ON_RC_STATUS(32785, "OnRCStatus"),
    ON_APP_SERVICE_DATA(32786, "OnAppServiceData"),
    ON_SYSTEM_CAPABILITY_UPDATED(32787, "OnSystemCapabilityUpdated"),
    ON_SUBTLE_ALERT_PRESSED(32788, "OnSubtleAlertPressed"),
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

    /**
     * This method gives the corresponding FunctionID enum value for a string RPC 
     * @param name String value represents the name of the RPC
     * @return FunctionID represents the equivalent enum value for the provided string
     */
    public static FunctionID getEnumForString(String name){
        for(FunctionID value : EnumSet.allOf(FunctionID.class)) {
            if(value.JSON_NAME.equals(name)){
                return value;
            }
        }
        return null;
    }
}
