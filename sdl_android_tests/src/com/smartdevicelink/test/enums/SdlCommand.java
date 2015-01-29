package com.smartdevicelink.test.enums;


/**
 * This is an enumerated list of SDL commands, complete with world-readable names that can be used
 * in dialogs and menus.
 * 
 * @author Mike Burke
 *
 */
public enum SdlCommand{
	/**
	 * SDL Alert command shows a pop-up text and/or voice alert on the head-unit.
	 * @see Alert
	 */
	ALERT ("Alert"),
	/**
	 * SDL Speak command performs a Text-to-Speech action on the input text and speaks the input to the user over the vehicle speakers.
	 * @see Speak
	 */
	SPEAK ("Speak"),
	/**
	 * SDL Show command updates the main HMI template used for the application.  Show is capable of showing up to 4 lines of text.
	 * @see Show
	 */
	SHOW ("Show"),
	/**
	 * SDL SubscribeButton command subscribes your application to receive hardware button-presses.  For example, when the application is in-use on the head-unit,
	 * and the user presses the PRESET_0 button, your app can intercept that button press and take action on it.
	 * @see SubscribeButton
	 * @see UnsubscribeButton
	 * @see SdlButton
	 * @see ButtonName
	 */
	SUBSCRIBE_BUTTON ("Subscribe to Buttons"),
	/**
	 * SDL UnsubscribeButton command unsubscribes your application from any buttons that have been subscribed to.
	 * @see UnsubscribeButton
	 * @see SdlButton
	 * @see ButtonName
	 */
	UNSUBSCRIBE_BUTTON ("Unsubscribe from Buttons"),
	/**
	 * SDL AddCommand command creates a software button that will be added into the app's menu on the head-unit.  Commands can be added to the root-level
	 * menu or to submenus that have been added by the application.
	 * @see AddCommand
	 * @see DeleteCommand
	 * @see MenuItem
	 * @see CommandButton
	 */
	ADD_COMMAND ("Add a Command"),
	/**
	 * SDL DeleteCommand command deletes a command menu item that has been added by the application.
	 * @see DeleteCommand
	 * @see AddCommand
	 */
	DELETE_COMMAND ("Delete a Command"),
	/**
	 * SDL AddSubmenu command creates a menu object inside the application's main root-level menu on the head-unit.  Submenus can only be added to the
	 * root-level menu, they cannot be nested.
	 * @see AddSubmenu
	 * @see DeleteSubmenu
	 * @see MenuItem
	 * @see SubmenuButton
	 */
	ADD_SUBMENU ("Add a Submenu"),
	/**
	 * SDL DeleteSubmenu command deletes a submenu menu item that has been added by the application.
	 * @see DeleteSubmenu
	 * @see AddSubmenu
	 */
	DELETE_SUB_MENU ("Delete a Submenu"),
	/**
	 * SDL SetGlobalProperties command manually sets global voice properties for your application.  For example,
	 * this command allows the application to change voice prompts for when the user needs help or when a timeout occurs.
	 * @see SetGlobalProperties
	 * @see ResetGlobalProperties  
	 */
//	SET_GLOBAL_PROPERTIES ("Set Global Properties"),
	/**
	 * SDL ResetGlobalProperties command resets global voice properties for your application to the vehicle's default settings.
	 * @see ResetGlobalProperties
	 * @see SetGlobalProperties
	 */
//	RESET_GLOBAL_PROPERTIES ("Reset Global Properties"),
	/**
	 * SDL SetMediaClockTimer command allows media applications to set a counter that automatically counts up or counts down on the head-unit.  The command
	 * also allows the application to pause, resume or clear a previously-existing counter.
	 * @see SetMediaClockTimer
	 */
	SET_MEDIA_CLOCK_TIMER ("Set Media Clock Timer"),
	/**
	 * SDL CreateInteractionChoiceSet command allows the application to create a pop-up menu on the head-unit.  The set must add the various choices
	 * available for the pop-up menu, including text and an image.
	 * @see CreateInteractionChoiceSet
	 * @see PerformInteraction
	 * @see DeleteInteractionChoiceSet
	 */
	CREATE_INTERACTION_CHOICE_SET ("Create Interaction Choice Set"),
	/**
	 * SDL DeleteInteractionChoiceSet command allows the application to delete a choice set that has previously been added through the CreateInteractionChoiceSet command.
	 * @see DeleteInteractionChoiceSet
	 * @see CreateInteractionChoiceSet
	 */
	DELETE_INTERACTION_CHOICE_SET ("Delete Interaction Choice Set"),
	/**
	 * SDL PerformInteraction command allows the application to show 1 or more choice sets that have been added through the CreateInteractionChoiceSet command.
	 * @see PerformInteraction
	 * @see CreateInteractionChoiceSet
	 */
	PERFORM_INTERACTION ("Perform Interaction"),
	/**
	 * SDL Slider command allows an application to show a volume-style slider element on the head-unit.  The application can select how many ticks the slider should have,
	 * the default selection for the timer and a timeout.
	 * @see Slider
	 */
	SLIDER ("Slider"),
	/**
	 * SDL ScrollableMessage command allows an application to show a long message that the user may need to scroll through.  The message has the ability to show soft buttons
	 * alongside the message.
	 * @see ScrollableMessage
	 */
	SCROLLABLE_MESSAGE ("Scrollable Message"),
	/**
	 * SDL ChangeRegistration command allows the application to change the default language for the application on the head-unit.
	 * @see ChangeRegistration
	 */
	CHANGE_REGISTRATION ("Change Registration"),
	/**
	 * SDL PutFile command allows the application to send files to be stored on the head-unit.  Bitmap, JPEG and PNG images can be sent, in addition to WAV or MP3 formatted audio.
	 * @see PutFile
	 * @see DeleteFile
	 * @see ListFiles
	 * @see SetAppIcon
	 */
	PUT_FILE ("Put File"),
	/**
	 * SDL DeleteFile command allows the application to delete files from the head-unit that were previously added to the head-unit through the PutFile command.
	 * @see DeleteFile
	 * @see PutFile
	 * @see ListFiles
	 */
	DELETE_FILE ("Delete File"),
	/**
	 * SDL ListFiles command allows the application to retreive a list of files that have been added to the head-unit through the PutFile command.
	 * @see ListFiles
	 * @see PutFile
	 * @see DeleteFile
	 */
	LIST_FILES ("List Files"),
	/**
	 * SDL SetAppIcon command allows the application to set an image to be associated with the application.  Desired image must first be sent through the PutFile command.
	 * @see SetAppIcon
	 * @see PutFile
	 */
	SET_APP_ICON ("Set App Icon"),
	/**
	 * SDL PerformAudioPassThru command allows vehicle voice-inputs from the user to be forwarded to the application.  This is useful if the application wants to perform
	 * its own voice-rec implementation to decode user voice-inputs.
	 * @see PerformAudioPassThru
	 * @see EndAudioPassThru
	 */
	//PERFORM_AUDIO_PASSTHRU ("Perform Audio Pass-through"),
	/**
	 * SDL EndAudioPassThru command ends a voice-input session previously started through the PerformAudioPassThru command.
	 */
	//END_AUDIO_PASSTHRU ("End Audio Pass-through"),
	/**
	 * SDL SubscribeVehicleData command allows the application to subscribe for updates about various vehicle data.  For example, fuel state, tire pressure, airbag status and much
	 * more information can be retrieved from the vehicle head-unit and used in the application.
	 * @see SubscribeVehicleData
	 * @see UnsubscribeVehicleData
	 * @see GetVehicleData
	 */
//	SUBSCRIBE_VEHICLE_DATA ("Subscribe to Vehicle Data"),
	/**
	 * SDL UnsubscribeVehicleData command allows the application to unsubscribe from any vehicle data that has been subscribed to through the SubscribeVehicleData command.
	 * @see UnsubscribeVehicleData
	 * @see SubscribeVehicleData
	 * @see GetVehicleData
	 */
//	UNSUBSCRIBE_VEHICLE_DATA ("Unsubscribe from Vehicle Data"),
	/**
	 * SDL GetVehicleData command allows the application to retrieve vehicle data from the head-unit.  Prior to sending this command, the application must first subscribe
	 * for vehicle data through the SubscribeVehicleData command.
	 * @see GetVehicleData
	 * @see SubscribeVehicleData
	 * @see UnsubcribeVehicleData
	 */
//	GET_VEHICLE_DATA ("Get Vehicle Data"),
	/**
	 * SDL ReadDID command allows the application to query DIDs for a particular module.  DIDs contain useful information for each module, such as part numbers.
	 * @see ReadDID
	 */
	READ_DIDS ("Read DIDs"),
	/**
	 * SDL GetDTCs command allows the application to query DTCs for a particular module.  DTCs contain diagnostic trouble codes that are set when something
	 * goes wrong in a module.
	 * @see GetDTCs
	 */
	GET_DTCS ("Get DTCs"),
	//SHOW_CONSTANT_TBT ("Show Constant TBT"), //TODO - this command doesn't work on SDL core as of 1/23/2014
	//ALERT_MANEUVER ("Alert Maneuver"), //TODO - this command doesn't work on SDL core as of 1/23/2014
	//UPDATE_TURN_LIST ("Update Turn List"), //TODO - this command doesn't work on SDL core as of 1/23/2014
	//DIAL_NUMBER ("Dial Number"), //TODO - this command doesn't work on SDL core as of 1/23/2014
	
	//Future commands go here.
	
	;
	
	// THIS IS AN ENUM, SO BASICALLY EVERYTHING SHOULD BE FINAL.
	private final String friendlyName;
	
	//constructor
	private SdlCommand(String readableName){
		this.friendlyName = readableName;
	}
	
	@Override
	public String toString(){
		return this.friendlyName;
	}
}

