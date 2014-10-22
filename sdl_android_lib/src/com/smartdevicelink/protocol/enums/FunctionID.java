package com.smartdevicelink.protocol.enums;

import java.util.HashMap;
import java.util.Map;

public class FunctionID {
    private static Map<String, Integer> functionIDMap = null;
    public static final String SyncPData = "SyncPData";
	public static final String ShowConstantTBT = "ShowConstantTBT";
	public static final String AlertManeuver = "AlertManeuver";
	public static final String UpdateTurnList = "UpdateTurnList";
	public static final String OnSyncPData = "OnSyncPData";
	public static final String RegisterAppInterface = "RegisterAppInterface";
	public static final String UnregisterAppInterface = "UnregisterAppInterface";
	public static final String Alert = "Alert";
	public static final String Show = "Show";
	public static final String Speak = "Speak";
	public static final String AddCommand = "AddCommand";
	public static final String DeleteCommand = "DeleteCommand";
	public static final String AddSubMenu = "AddSubMenu";
	public static final String DeleteSubMenu = "DeleteSubMenu";
	public static final String CreateInteractionChoiceSet = "CreateInteractionChoiceSet";
	public static final String DeleteInteractionChoiceSet = "DeleteInteractionChoiceSet";
	public static final String PerformInteraction = "PerformInteraction";
	public static final String EncodedSyncPData = "EncodedSyncPData";
	public static final String SubscribeButton = "SubscribeButton";
	public static final String UnsubscribeButton = "UnsubscribeButton";
	public static final String SubscribeVehicleData = "SubscribeVehicleData";
	public static final String UnsubscribeVehicleData = "UnsubscribeVehicleData";
	public static final String SetMediaClockTimer = "SetMediaClockTimer";
	public static final String SetGlobalProperties = "SetGlobalProperties";
	public static final String GenericResponse = "GenericResponse";
	public static final String ScrollableMessage = "ScrollableMessage";
	public static final String GetDTCs = "GetDTCs";
	public static final String DiagnosticMessage = "DiagnosticMessage";
	public static final String SystemRequest = "SystemRequest";
	public static final String ReadDID = "ReadDID";
	public static final String OnVehicleData = "OnVehicleData";
	public static final String PutFile = "PutFile";
	public static final String DeleteFile = "DeleteFile";
	public static final String ListFiles = "ListFiles";
	public static final String GetVehicleData = "GetVehicleData";
	public static final String ResetGlobalProperties = "ResetGlobalProperties";
	public static final String SetAppIcon = "SetAppIcon";
	public static final String ChangeRegistration = "ChangeRegistration";
	public static final String SetDisplayLayout = "SetDisplayLayout";
	public static final String OnLanguageChange = "OnLanguageChange";
	public static final String PerformAudioPassThru = "PerformAudioPassThru";
	public static final String EndAudioPassThru = "EndAudioPassThru";
	public static final String OnAudioPassThru = "OnAudioPassThru";
	public static final String OnCommand = "OnCommand";
	public static final String OnButtonPress = "OnButtonPress";
	public static final String OnButtonEvent = "OnButtonEvent";
	public static final String OnHMIStatus = "OnHMIStatus";
	public static final String OnTBTClientState = "OnTBTClientState";
	public static final String OnEncodedSyncPData = "OnEncodedSyncPData";
	public static final String OnDriverDistraction = "OnDriverDistraction";
	public static final String OnAppInterfaceUnregistered = "OnAppInterfaceUnregistered";
	public static final String OnKeyboardInput = "OnKeyboardInput";
	public static final String OnTouchEvent = "OnTouchEvent";
	public static final String OnSystemRequest = "OnSystemRequest";
	public static final String OnHashChange = "OnHashChange";
	public static final String OnPermissionsChange = "OnPermissionsChange";
	public static final String Slider = "Slider";

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

        functionIDMap = new HashMap<String, Integer>(60) {{
            /*
                Base Request / Response RPCs
                Range = 0x 0000 0001 - 0x 0000 7FFF
             */
            put(FunctionID.RegisterAppInterface, 1);
            put(FunctionID.UnregisterAppInterface, 2);
            put(FunctionID.SetGlobalProperties, 3);
            put(FunctionID.ResetGlobalProperties, 4);
            put(FunctionID.AddCommand, 5);
            put(FunctionID.DeleteCommand, 6);
            put(FunctionID.AddSubMenu, 7);
            put(FunctionID.DeleteSubMenu, 8);
            put(FunctionID.CreateInteractionChoiceSet, 9);
            put(FunctionID.PerformInteraction, 10);
            put(FunctionID.DeleteInteractionChoiceSet, 11);
            put(FunctionID.Alert, 12);
            put(FunctionID.Show, 13);
            put(FunctionID.Speak, 14);
            put(FunctionID.SetMediaClockTimer, 15);
            put(FunctionID.PerformAudioPassThru, 16);
            put(FunctionID.EndAudioPassThru, 17);
            put(FunctionID.SubscribeButton, 18);
            put(FunctionID.UnsubscribeButton, 19);
            put(FunctionID.SubscribeVehicleData, 20);
            put(FunctionID.UnsubscribeVehicleData, 21);
            put(FunctionID.GetVehicleData, 22);
            put(FunctionID.ReadDID, 23);
            put(FunctionID.GetDTCs, 24);
            put(FunctionID.ScrollableMessage, 25);
            put(FunctionID.Slider, 26);
            put(FunctionID.ShowConstantTBT, 27);
            put(FunctionID.AlertManeuver, 28);
            put(FunctionID.UpdateTurnList, 29);
            put(FunctionID.ChangeRegistration, 30);
            put(FunctionID.GenericResponse, 31);
            put(FunctionID.PutFile, 32);
            put(FunctionID.DeleteFile, 33);
            put(FunctionID.ListFiles, 34);
            put(FunctionID.SetAppIcon, 35);
            put(FunctionID.SetDisplayLayout, 36);
            put(FunctionID.DiagnosticMessage, 37);
            put(FunctionID.SystemRequest, 38);

            /*
                Base Notifications
                Range = 0x 0000 8000 - 0x 0000 FFFF
             */
            put(FunctionID.OnHMIStatus, 32768);
            put(FunctionID.OnAppInterfaceUnregistered, 32769);
            put(FunctionID.OnButtonEvent, 32770);
            put(FunctionID.OnButtonPress, 32771);
            put(FunctionID.OnVehicleData, 32772);
            put(FunctionID.OnCommand, 32773);
            put(FunctionID.OnTBTClientState, 32774);
            put(FunctionID.OnDriverDistraction, 32775);
            put(FunctionID.OnPermissionsChange, 32776);
            put(FunctionID.OnAudioPassThru, 32777);
            put(FunctionID.OnLanguageChange, 32778);
            put(FunctionID.OnSystemRequest, 32781);
            put(FunctionID.OnHashChange, 32782);

            /*
                Ford Specific Request / Response RPCs
                Range = 0x 0001 0000 - 0x 0001 7FFF
             */
            put(FunctionID.EncodedSyncPData, 65536);
            put(FunctionID.SyncPData, 65537);

            /*
                Ford Specific Notifications
                Range = 0x 0001 8000 - 0x 0001 FFFF
             */
            put(FunctionID.OnEncodedSyncPData, 98304);
            put(FunctionID.OnSyncPData, 98305);

            // OnKeyboardInput
            put(FunctionID.OnKeyboardInput, 32779);
            // OnTouchEvent
            put(FunctionID.OnTouchEvent, 32780);
        }};
    }
}
