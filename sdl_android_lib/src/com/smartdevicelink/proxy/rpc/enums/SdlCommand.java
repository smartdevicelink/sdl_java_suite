package com.smartdevicelink.proxy.rpc.enums;

import java.util.EnumSet;
import java.util.HashMap;

import android.view.MenuItem;

import com.smartdevicelink.proxy.RPCMessage;
import com.smartdevicelink.proxy.rpc.AddCommand;
import com.smartdevicelink.proxy.rpc.AddCommandResponse;
import com.smartdevicelink.proxy.rpc.AddSubMenu;
import com.smartdevicelink.proxy.rpc.AddSubMenuResponse;
import com.smartdevicelink.proxy.rpc.Alert;
import com.smartdevicelink.proxy.rpc.AlertResponse;
import com.smartdevicelink.proxy.rpc.ChangeRegistration;
import com.smartdevicelink.proxy.rpc.ChangeRegistrationResponse;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.CreateInteractionChoiceSetResponse;
import com.smartdevicelink.proxy.rpc.DeleteCommand;
import com.smartdevicelink.proxy.rpc.DeleteCommandResponse;
import com.smartdevicelink.proxy.rpc.DeleteFile;
import com.smartdevicelink.proxy.rpc.DeleteFileResponse;
import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSet;
import com.smartdevicelink.proxy.rpc.DeleteInteractionChoiceSetResponse;
import com.smartdevicelink.proxy.rpc.DeleteSubMenu;
import com.smartdevicelink.proxy.rpc.DeleteSubMenuResponse;
import com.smartdevicelink.proxy.rpc.EndAudioPassThru;
import com.smartdevicelink.proxy.rpc.EndAudioPassThruResponse;
import com.smartdevicelink.proxy.rpc.GenericResponse;
import com.smartdevicelink.proxy.rpc.GetDTCs;
import com.smartdevicelink.proxy.rpc.GetDTCsResponse;
import com.smartdevicelink.proxy.rpc.GetVehicleData;
import com.smartdevicelink.proxy.rpc.GetVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.ListFiles;
import com.smartdevicelink.proxy.rpc.ListFilesResponse;
import com.smartdevicelink.proxy.rpc.OnAppInterfaceUnregistered;
import com.smartdevicelink.proxy.rpc.OnAudioPassThru;
import com.smartdevicelink.proxy.rpc.OnButtonEvent;
import com.smartdevicelink.proxy.rpc.OnButtonPress;
import com.smartdevicelink.proxy.rpc.OnCommand;
import com.smartdevicelink.proxy.rpc.OnDriverDistraction;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.OnLanguageChange;
import com.smartdevicelink.proxy.rpc.OnPermissionsChange;
import com.smartdevicelink.proxy.rpc.OnSdlChoiceChosen;
import com.smartdevicelink.proxy.rpc.OnTBTClientState;
import com.smartdevicelink.proxy.rpc.OnVehicleData;
import com.smartdevicelink.proxy.rpc.PerformAudioPassThru;
import com.smartdevicelink.proxy.rpc.PerformAudioPassThruResponse;
import com.smartdevicelink.proxy.rpc.PerformInteraction;
import com.smartdevicelink.proxy.rpc.PerformInteractionResponse;
import com.smartdevicelink.proxy.rpc.PutFile;
import com.smartdevicelink.proxy.rpc.PutFileResponse;
import com.smartdevicelink.proxy.rpc.ReadDID;
import com.smartdevicelink.proxy.rpc.ReadDIDResponse;
import com.smartdevicelink.proxy.rpc.RegisterAppInterface;
import com.smartdevicelink.proxy.rpc.RegisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.ResetGlobalProperties;
import com.smartdevicelink.proxy.rpc.ResetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.ScrollableMessage;
import com.smartdevicelink.proxy.rpc.ScrollableMessageResponse;
import com.smartdevicelink.proxy.rpc.SetAppIcon;
import com.smartdevicelink.proxy.rpc.SetAppIconResponse;
import com.smartdevicelink.proxy.rpc.SetDisplayLayout;
import com.smartdevicelink.proxy.rpc.SetDisplayLayoutResponse;
import com.smartdevicelink.proxy.rpc.SetGlobalProperties;
import com.smartdevicelink.proxy.rpc.SetGlobalPropertiesResponse;
import com.smartdevicelink.proxy.rpc.SetMediaClockTimer;
import com.smartdevicelink.proxy.rpc.SetMediaClockTimerResponse;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.ShowResponse;
import com.smartdevicelink.proxy.rpc.Slider;
import com.smartdevicelink.proxy.rpc.SliderResponse;
import com.smartdevicelink.proxy.rpc.Speak;
import com.smartdevicelink.proxy.rpc.SpeakResponse;
import com.smartdevicelink.proxy.rpc.SubscribeButton;
import com.smartdevicelink.proxy.rpc.SubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.SubscribeVehicleData;
import com.smartdevicelink.proxy.rpc.SubscribeVehicleDataResponse;
import com.smartdevicelink.proxy.rpc.UnregisterAppInterface;
import com.smartdevicelink.proxy.rpc.UnregisterAppInterfaceResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeButton;
import com.smartdevicelink.proxy.rpc.UnsubscribeButtonResponse;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleData;
import com.smartdevicelink.proxy.rpc.UnsubscribeVehicleDataResponse;
import com.smartdevicelink.util.JsonUtils.JsonInterfaces.JsonName;

/**
 * This is an enumerated list of SDL commands, complete with world-readable names that can be used
 * in dialogs and menus.
 * 
 * @author Mike Burke
 *
 */
public enum SdlCommand implements JsonName{
    /**
     * SDL Alert command shows a pop-up text and/or voice alert on the head-unit.
     * @see Alert
     */
    ALERT ("Alert", Alert.class, AlertResponse.class, null),
    /**
     * SDL Speak command performs a Text-to-Speech action on the input text and speaks the input to the user over the vehicle speakers.
     * @see Speak
     */
    SPEAK ("Speak", Speak.class, SpeakResponse.class, null),
    /**
     * SDL Show command updates the main HMI template used for the application.  Show is capable of showing up to 4 lines of text.
     * @see Show
     */
    SHOW ("Show", Show.class, ShowResponse.class, null),
    /**
     * SDL SubscribeButton command subscribes your application to receive hardware button-presses.  For example, when the application is in-use on the head-unit,
     * and the user presses the PRESET_0 button, your app can intercept that button press and take action on it.
     * @see SubscribeButton
     * @see UnsubscribeButton
     * @see SdlButton
     * @see ButtonName
     */
    SUBSCRIBE_BUTTON ("SubscribeButton", SubscribeButton.class, SubscribeButtonResponse.class, null),
    /**
     * SDL UnsubscribeButton command unsubscribes your application from any buttons that have been subscribed to.
     * @see UnsubscribeButton
     * @see SdlButton
     * @see ButtonName
     */
    UNSUBSCRIBE_BUTTON ("UnsubscribeButton", UnsubscribeButton.class, UnsubscribeButtonResponse.class, null),
    /**
     * SDL AddCommand command creates a software button that will be added into the app's menu on the head-unit.  Commands can be added to the root-level
     * menu or to submenus that have been added by the application.
     * @see AddCommand
     * @see DeleteCommand
     * @see MenuItem
     * @see CommandButton
     */
    ADD_COMMAND ("AddCommand", AddCommand.class, AddCommandResponse.class, null),
    /**
     * SDL DeleteCommand command deletes a command menu item that has been added by the application.
     * @see DeleteCommand
     * @see AddCommand
     */
    DELETE_COMMAND ("DeleteCommand", DeleteCommand.class, DeleteCommandResponse.class, null),
    /**
     * SDL AddSubmenu command creates a menu object inside the application's main root-level menu on the head-unit.  Submenus can only be added to the
     * root-level menu, they cannot be nested.
     * @see AddSubmenu
     * @see DeleteSubmenu
     * @see MenuItem
     * @see SubmenuButton
     */
    ADD_SUBMENU ("AddSubMenu", AddSubMenu.class, AddSubMenuResponse.class, null),
    /**
     * SDL DeleteSubmenu command deletes a submenu menu item that has been added by the application.
     * @see DeleteSubmenu
     * @see AddSubmenu
     */
    DELETE_SUB_MENU ("DeleteSubMenu", DeleteSubMenu.class, DeleteSubMenuResponse.class, null),
    /**
     * SDL SetGlobalProperties command manually sets global voice properties for your application.  For example,
     * this command allows the application to change voice prompts for when the user needs help or when a timeout occurs.
     * @see SetGlobalProperties
     * @see ResetGlobalProperties
     */
    SET_GLOBAL_PROPERTIES ("SetGlobalProperties", SetGlobalProperties.class, SetGlobalPropertiesResponse.class, null),
    /**
     * SDL ResetGlobalProperties command resets global voice properties for your application to the vehicle's default settings.
     * @see ResetGlobalProperties
     * @see SetGlobalProperties
     */
    RESET_GLOBAL_PROPERTIES ("ResetGlobalProperties", ResetGlobalProperties.class, ResetGlobalPropertiesResponse.class, null),
    /**
     * SDL SetMediaClockTimer command allows media applications to set a counter that automatically counts up or counts down on the head-unit.  The command
     * also allows the application to pause, resume or clear a previously-existing counter.
     * @see SetMediaClockTimer
     */
    SET_MEDIA_CLOCK_TIMER ("SetMediaClockTimer", SetMediaClockTimer.class, SetMediaClockTimerResponse.class, null),
    /**
     * SDL CreateInteractionChoiceSet command allows the application to create a pop-up menu on the head-unit.  The set must add the various choices
     * available for the pop-up menu, including text and an image.
     * @see CreateInteractionChoiceSet
     * @see PerformInteraction
     * @see DeleteInteractionChoiceSet
     */
    CREATE_INTERACTION_CHOICE_SET ("CreateInteractionChoiceSet", CreateInteractionChoiceSet.class, CreateInteractionChoiceSetResponse.class, null),
    /**
     * SDL DeleteInteractionChoiceSet command allows the application to delete a choice set that has previously been added through the CreateInteractionChoiceSet command.
     * @see DeleteInteractionChoiceSet
     * @see CreateInteractionChoiceSet
     */
    DELETE_INTERACTION_CHOICE_SET ("DeleteInteractionChoiceSet", DeleteInteractionChoiceSet.class, DeleteInteractionChoiceSetResponse.class, null),
    /**
     * SDL PerformInteraction command allows the application to show 1 or more choice sets that have been added through the CreateInteractionChoiceSet command.
     * @see PerformInteraction
     * @see CreateInteractionChoiceSet
     */
    PERFORM_INTERACTION ("PerformInteraction", PerformInteraction.class, PerformInteractionResponse.class, null),
    /**
     * SDL Slider command allows an application to show a volume-style slider element on the head-unit.  The application can select how many ticks the slider should have,
     * the default selection for the timer and a timeout.
     * @see Slider
     */
    SLIDER ("Slider", Slider.class, SliderResponse.class, null),
    /**
     * SDL ScrollableMessage command allows an application to show a long message that the user may need to scroll through.  The message has the ability to show soft buttons
     * alongside the message.
     * @see ScrollableMessage
     */
    SCROLLABLE_MESSAGE ("ScrollableMessage", ScrollableMessage.class, ScrollableMessageResponse.class, null),
    /**
     * SDL ChangeRegistration command allows the application to change the default language for the application on the head-unit.
     * @see ChangeRegistration
     */
    CHANGE_REGISTRATION ("ChangeRegistration", ChangeRegistration.class, ChangeRegistrationResponse.class, null),
    /**
     * SDL PutFile command allows the application to send files to be stored on the head-unit.  Bitmap, JPEG and PNG images can be sent, in addition to WAV or MP3 formatted audio.
     * @see PutFile
     * @see DeleteFile
     * @see ListFiles
     * @see SetAppIcon
     */
    PUT_FILE ("PutFile", PutFile.class, PutFileResponse.class, null),
    /**
     * SDL DeleteFile command allows the application to delete files from the head-unit that were previously added to the head-unit through the PutFile command.
     * @see DeleteFile
     * @see PutFile
     * @see ListFiles
     */
    DELETE_FILE ("DeleteFile", DeleteFile.class, DeleteFileResponse.class, null),
    /**
     * SDL ListFiles command allows the application to retrieve a list of files that have been added to the head-unit through the PutFile command.
     * @see ListFiles
     * @see PutFile
     * @see DeleteFile
     */
    LIST_FILES ("ListFiles", ListFiles.class, ListFilesResponse.class, null),
    /**
     * SDL SetAppIcon command allows the application to set an image to be associated with the application.  Desired image must first be sent through the PutFile command.
     * @see SetAppIcon
     * @see PutFile
     */
    SET_APP_ICON ("SetAppIcon", SetAppIcon.class, SetAppIconResponse.class, null),
    /**
     * SDL PerformAudioPassThru command allows vehicle voice-inputs from the user to be forwarded to the application.  This is useful if the application wants to perform
     * its own voice-rec implementation to decode user voice-inputs.
     * @see PerformAudioPassThru
     * @see EndAudioPassThru
     */
    PERFORM_AUDIO_PASSTHRU ("PerformAudioPassThru", PerformAudioPassThru.class, PerformAudioPassThruResponse.class, null),
    /**
     * SDL EndAudioPassThru command ends a voice-input session previously started through the PerformAudioPassThru command.
     */
    END_AUDIO_PASSTHRU ("EndAudioPassThru", EndAudioPassThru.class, EndAudioPassThruResponse.class, null),
    /**
     * SDL SubscribeVehicleData command allows the application to subscribe for updates about various vehicle data.  For example, fuel state, tire pressure, airbag status and much
     * more information can be retrieved from the vehicle head-unit and used in the application.
     * @see SubscribeVehicleData
     * @see UnsubscribeVehicleData
     * @see GetVehicleData
     */
    SUBSCRIBE_VEHICLE_DATA ("SubscribeVehicleData", SubscribeVehicleData.class, SubscribeVehicleDataResponse.class, null),
    /**
     * SDL UnsubscribeVehicleData command allows the application to unsubscribe from any vehicle data that has been subscribed to through the SubscribeVehicleData command.
     * @see UnsubscribeVehicleData
     * @see SubscribeVehicleData
     * @see GetVehicleData
     */
    UNSUBSCRIBE_VEHICLE_DATA ("UnsubscribeVehicleData", UnsubscribeVehicleData.class, UnsubscribeVehicleDataResponse.class, null),
    /**
     * SDL GetVehicleData command allows the application to retrieve vehicle data from the head-unit.  Prior to sending this command, the application must first subscribe
     * for vehicle data through the SubscribeVehicleData command.
     * @see GetVehicleData
     * @see SubscribeVehicleData
     * @see UnsubcribeVehicleData
     */
    GET_VEHICLE_DATA ("GetVehicleData", GetVehicleData.class, GetVehicleDataResponse.class, null),
    /**
     * SDL ReadDID command allows the application to query DIDs for a particular module.  DIDs contain useful information for each module, such as part numbers.
     * @see ReadDID
     */
    READ_DIDS ("ReadDID", ReadDID.class, ReadDIDResponse.class, null),
    /**
     * SDL GetDTCs command allows the application to query DTCs for a particular module.  DTCs contain diagnostic trouble codes that are set when something
     * goes wrong in a module.
     * @see GetDTCs
     */
    GET_DTCS ("GetDTCs", GetDTCs.class, GetDTCsResponse.class, null),
    GENERIC_RESPONSE("GenericResponse", null, GenericResponse.class, null),
    ON_APP_INTERFACE_UNREGISTERED("OnAppInterfaceUnregistered", null, null, OnAppInterfaceUnregistered.class),
    ON_AUDIO_PASS_THRU("OnAudioPassThru", null, null, OnAudioPassThru.class),
    ON_BUTTON_EVENT("OnButtonEvent", null, null, OnButtonEvent.class),
    ON_BUTTON_PRESS("OnButtonPress", null, null, OnButtonPress.class),
    ON_COMMAND("OnCommand", null, null, OnCommand.class),
    ON_DRIVER_DISTRACTION("OnDriverDistraction", null, null, OnDriverDistraction.class),
    ON_HMI_STATUS("OnHMIStatus", null, null, OnHMIStatus.class),
    ON_LANGUAGE_CHANGE("OnLanguageChange", null, null, OnLanguageChange.class),
    ON_PERMISSIONS_CHANGE("OnPermissionsChange", null, null, OnPermissionsChange.class),
    ON_SDL_CHOICE_CHOSEN("OnSmartDeviceLinkChoiceChosen", null, null, OnSdlChoiceChosen.class),
    ON_TBT_CLIENT_STATE("OnTBTClientState", null, null, OnTBTClientState.class),
    ON_VEHICLE_DATA("OnVehicleData", null, null, OnVehicleData.class),
    REGISTER_APP_INTERFACE("RegisterAppInterface", RegisterAppInterface.class, RegisterAppInterfaceResponse.class, null),
    UNREGISTER_APP_INTERFACE("UnregisterAppInterface", UnregisterAppInterface.class, UnregisterAppInterfaceResponse.class, null),
    SET_DISPLAY_LAYOUT("SetDisplayLayout", SetDisplayLayout.class, SetDisplayLayoutResponse.class, null),
    
    
    //Future commands go here.
    
    ;
    
    // THIS IS AN ENUM, SO EVERYTHING SHOULD BE FINAL.
    private final String jsonName;
    private final Class<?> requestClass, responseClass, notificationClass;
    
    //constructor
    private SdlCommand(String jsonName, Class<?> requestClass, Class<?> responseClass, Class<?> notificationClass){
        this.jsonName = jsonName;
        this.requestClass = requestClass;
        this.responseClass = responseClass;
        this.notificationClass = notificationClass;
    }
    
    @Override
    public String toString(){
        return this.jsonName;
    }
    
    @Override
    public String getJsonName(int version){
        switch(version){
        default:
            return this.jsonName;
        }
    }
    
    public Class<?> getRequestClass(){
        return this.requestClass;
    }
    
    public Class<?> getResponseClass(){
        return this.responseClass;
    }
    
    public Class<?> getNotificationClass(){
        return this.notificationClass;
    }

    private static HashMap<String, SdlCommand> cacheMap = null;

    /**
     * Performs a reverse-lookup based on the JSON name of the object.
     * 
     * @param jsonName The name to look up
     * @return The SdlCommand if found, null otherwise
     */
    public static SdlCommand valueForJsonName(String jsonName, int version) {
        if(jsonName == null) return null;
        
        if(cacheMap == null){
            initCacheMap(version);
        }
        
        return cacheMap.get(jsonName);
    }
    
    // lazily instantiate cache map for quick lookups for enum values from JSON
    private static void initCacheMap(int version){
        // TODO: handle different SDL versions
        
        cacheMap = new HashMap<String, SdlCommand>();
        
        for (SdlCommand anEnum : EnumSet.allOf(SdlCommand.class)) {
            cacheMap.put(anEnum.getJsonName(version), anEnum);
        }
    }
    
    public static Class<?> getClassType(SdlCommand command, String type){
        if(RPCMessage.KEY_REQUEST.equals(type)){
            return command.getRequestClass();
        }
        else if(RPCMessage.KEY_RESPONSE.equals(type)){
            return command.getResponseClass();
        }
        else if(RPCMessage.KEY_NOTIFICATION.equals(type)){
            return command.getNotificationClass();
        }
        return null;
    }
}
