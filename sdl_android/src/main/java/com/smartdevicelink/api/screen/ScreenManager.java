package com.smartdevicelink.api.screen;

import com.smartdevicelink.api.BaseSubManager;
import com.smartdevicelink.api.CompletionListener;
import com.smartdevicelink.api.FileManager;
import com.smartdevicelink.api.SdlArtwork;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;

import android.content.Context;

/**
 * <strong>ScreenManager</strong> <br>
 *
 * Note: This class must be accessed through the SdlManager. Do not instantiate it by itself. <br>
 *
 * The ScreenManager <br>
 *
 * We need to add the following structs and their enums: SDLTextAlignment, SDLArtwork <br>
 *
 * It is broken down to these areas: <br>
 *
 * 1. Constructor(s) <br>
 * 2. Setters <br>
 * 3. Getters <br>
 * 4. Update Methods <br>
 */
public class ScreenManager extends BaseSubManager {

//	private SoftButtonManager softButtonManager;
 	private TextAndGraphicManager textAndGraphicManager;
	private HMILevel hmiLevel;

	// Constructors

	public ScreenManager(ISdl internalInterface, FileManager fileManager, Context context) {

		// set class vars
		super(internalInterface);

		hmiLevel = HMILevel.HMI_NONE;

		// add listener
		OnRPCNotificationListener hmiListener = new OnRPCNotificationListener() {
			@Override
			public void onNotified(RPCNotification notification) {
				hmiLevel = ((OnHMIStatus)notification).getHmiLevel();
			}
		};
		internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);

		// init sub managers
		//this.softButtonManager = new SoftButtonManager(internalInterface,fileManager);
		this.textAndGraphicManager = new TextAndGraphicManager(internalInterface,fileManager, context);
		transitionToState(READY);
	}

	// Setters

	/*******
	 *
	 * THESE SETTERS / GETTERS WILL END UP CALLING THEIR SPECIFIC SUB-SUB MANAGERS
	 *
	 * FOR EXAMPLE FOR SETTEXTFIELD1 IT WILL CALL: this.textAndGraphicManager.textField1 = textField1;
	 * (SAME FOR THE GETTERS)
	 *
	 */

	public void setTextField1(String textField1) {
		this.textAndGraphicManager.textField1 = textField1;
	}

	public void setTextField2(String textField2) {
		this.textAndGraphicManager.textField2 = textField2;
	}

	public void setTextField3(String textField3) {
		this.textAndGraphicManager.textField3 = textField3;
	}

	public void setTextField4(String textField4) {
		this.textAndGraphicManager.textField4 = textField4;
	}

	public void setMediaTrackTextField(String mediaTrackTextField) {
		this.textAndGraphicManager.mediaTrackTextField = mediaTrackTextField;
	}

	public void setPrimaryGraphic(SdlArtwork primaryGraphic) {
		this.textAndGraphicManager.primaryGraphic = primaryGraphic;
	}

	public void setSecondaryGraphic(SdlArtwork secondaryGraphic) {
		this.textAndGraphicManager.secondaryGraphic = secondaryGraphic;
	}

	public void setTextAlignment(TextAlignment textAlignment) {
		this.textAndGraphicManager.textAlignment = textAlignment;
	}

	public void setTextField1Type(MetadataType textField1Type) {
		this.textAndGraphicManager.textField1Type = textField1Type;
	}

	public void setTextField2Type(MetadataType textField2Type) {
		this.textAndGraphicManager.textField2Type = textField2Type;
	}

	public void setTextField3Type(MetadataType textField3Type) {
		this.textAndGraphicManager.textField3Type = textField3Type;
	}

	public void setTextField4Type(MetadataType textField4Type) {
		this.textAndGraphicManager.textField4Type = textField4Type;
	}

	/*public void setSoftButtonObjects(ArrayList<SoftButtonObject> softButtonObjects) {
		this.softButtonObjects = softButtonObjects;
	}*/

	// Getters

	public String getTextField1() {
		return this.textAndGraphicManager.textField1;
	}

	public String getTextField2() {
		return this.textAndGraphicManager.textField2;
	}

	public String getTextField3() {
		return this.textAndGraphicManager.textField3;
	}

	public String getTextField4() {
		return this.textAndGraphicManager.textField4;
	}

	public String getMediaTrackTextField() {
		return this.textAndGraphicManager.mediaTrackTextField;
	}

	public SdlArtwork getPrimaryGraphic() {
		return this.textAndGraphicManager.primaryGraphic;
	}

	public SdlArtwork getSecondaryGraphic() {
		return this.textAndGraphicManager.secondaryGraphic;
	}

	public TextAlignment getTextAlignment() {
		return this.textAndGraphicManager.textAlignment;
	}

	public MetadataType getTextField1Type() {
		return this.textAndGraphicManager.textField1Type;
	}

	public MetadataType getTextField2Type() {
		return this.textAndGraphicManager.textField2Type;
	}

	public MetadataType getTextField3Type() {
		return this.textAndGraphicManager.textField3Type;
	}

	public MetadataType getTextField4Type() {
		return this.textAndGraphicManager.textField4Type;
	}

	/*public ArrayList<SoftButtonObject> getSoftButtonObjects() {
		return softButtonObjects;
	}*/

	// Updates

	public void beginUpdates(){

//		softButtonManager.batchUpdates = true;
		textAndGraphicManager.batchUpdates = true;
	}

	public void endUpdates(CompletionListener listener){

//		softButtonManager.batchUpdates = false;
		textAndGraphicManager.batchUpdates = false;
//		softButtonManager.update();
		textAndGraphicManager.update();
	}

}
