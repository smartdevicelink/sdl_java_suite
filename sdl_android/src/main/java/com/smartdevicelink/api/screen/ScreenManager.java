package com.smartdevicelink.api.screen;

import android.support.annotation.NonNull;

import com.smartdevicelink.api.BaseSubManager;
import com.smartdevicelink.api.CompletionListener;
import com.smartdevicelink.api.FileManager;
import com.smartdevicelink.api.datatypes.SdlArtwork;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;

import java.util.List;

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

	private SoftButtonManager softButtonManager;
//	private TextAndGraphicManager textAndGraphicManager;
	private HMILevel hmiLevel;

	// Screen stuff
	private String textField1, textField2, textField3, textField4, mediaTrackTextField;
	private SdlArtwork primaryGraphic, secondaryGraphic;
	private TextAlignment textAlignment;
	private MetadataType textField1Type, textField2Type, textField3Type, textField4Type;
//	private ArrayList<SoftButtonObject> softButtonObjects;

	// Constructors

	public ScreenManager(ISdl internalInterface, FileManager fileManager) {

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
		this.softButtonManager = new SoftButtonManager(internalInterface, fileManager);
		//this.textAndGraphicManager = new TextAndGraphicManager(internalInterface,fileManager);
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

	public void setTextField1(@NonNull String textField1) {
		//textAndGraphicManager.setMainField1(textField1);
		softButtonManager.setCurrentMainField1(textField1);
	}

	public void setTextField2(String textField2) {
		this.textField2 = textField2;
	}

	public void setTextField3(String textField3) {
		this.textField3 = textField3;
	}

	public void setTextField4(String textField4) {
		this.textField4 = textField4;
	}

	public void setMediaTrackTextField(String mediaTrackTextField) {
		this.mediaTrackTextField = mediaTrackTextField;
	}

	public void setPrimaryGraphic(SdlArtwork primaryGraphic) {
		this.primaryGraphic = primaryGraphic;
	}

	public void setSecondaryGraphic(SdlArtwork secondaryGraphic) {
		this.secondaryGraphic = secondaryGraphic;
	}

	public void setTextAlignment(TextAlignment textAlignment) {
		this.textAlignment = textAlignment;
	}

	public void setTextField1Type(MetadataType textField1Type) {
		this.textField1Type = textField1Type;
	}

	public void setTextField2Type(MetadataType textField2Type) {
		this.textField2Type = textField2Type;
	}

	public void setTextField3Type(MetadataType textField3Type) {
		this.textField3Type = textField3Type;
	}

	public void setTextField4Type(MetadataType textField4Type) {
		this.textField4Type = textField4Type;
	}

	public void setSoftButtonObjects(@NonNull List<SoftButtonObject> softButtonObjects) {
		softButtonManager.setSoftButtonObjects(softButtonObjects);
	}

	// Getters

	public String getTextField1() {
		return textField1;
	}

	public String getTextField2() {
		return textField2;
	}

	public String getTextField3() {
		return textField3;
	}

	public String getTextField4() {
		return textField4;
	}

	public String getMediaTrackTextField() {
		return mediaTrackTextField;
	}

	public SdlArtwork getPrimaryGraphic() {
		return primaryGraphic;
	}

	public SdlArtwork getSecondaryGraphic() {
		return secondaryGraphic;
	}

	public TextAlignment getTextAlignment() {
		return textAlignment;
	}

	public MetadataType getTextField1Type() {
		return textField1Type;
	}

	public MetadataType getTextField2Type() {
		return textField2Type;
	}

	public MetadataType getTextField3Type() {
		return textField3Type;
	}

	public MetadataType getTextField4Type() {
		return textField4Type;
	}

	public List<SoftButtonObject> getSoftButtonObjects() {
		return softButtonManager.getSoftButtonObjects();
	}

    public SoftButtonObject getSoftButtonObjectByName(@NonNull String name){
        return softButtonManager.getSoftButtonObjectByName(name);
    }

    public SoftButtonObject getSoftButtonObjectById(int buttonId){
        return softButtonManager.getSoftButtonObjectById(buttonId);
    }

	// Updates

	public void beginUpdates(){
		softButtonManager.setBatchUpdates(true);
//		textAndGraphicManager.batchUpdates = true;
	}

	public void endUpdates(CompletionListener listener){

		softButtonManager.setBatchUpdates(false);
//		textAndGraphicManager.batchUpdates = false;
		softButtonManager.update(listener);
//		textAndGraphicManager.update();
	}

}
