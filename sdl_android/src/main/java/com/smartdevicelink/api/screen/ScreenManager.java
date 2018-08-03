package com.smartdevicelink.api.screen;

import android.support.annotation.NonNull;
import android.util.Log;

import com.smartdevicelink.api.BaseSubManager;
import com.smartdevicelink.api.CompletionListener;
import com.smartdevicelink.api.FileManager;
import com.smartdevicelink.api.datatypes.SdlArtwork;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	private static String TAG = "ScreenManager";
	private FileManager fileManager;
	private SoftButtonManager softButtonManager;
	private TextAndGraphicManager textAndGraphicManager;

	// Constructors

	public ScreenManager(ISdl internalInterface, FileManager fileManager) {
		super(internalInterface);

		transitionToState(SETTING_UP);

		this.fileManager = fileManager;

		initialize();
	}


	// Sub manager listener
	private final CompletionListener subManagerListener = new CompletionListener() {
		@Override
		public synchronized void onComplete(boolean success) {
			if(!success){
				Log.d(TAG, "Sub manager failed to initialize");
			}
			if(
					softButtonManager != null && softButtonManager.getState() != BaseSubManager.SETTING_UP &&
					textAndGraphicManager != null && textAndGraphicManager.getState() != BaseSubManager.SETTING_UP
					){
				transitionToState(READY);
			}
		}
	};

	private void initialize(){
		this.softButtonManager = new SoftButtonManager(internalInterface, fileManager);
		this.softButtonManager.start(subManagerListener);
		this.textAndGraphicManager = new TextAndGraphicManager(internalInterface, fileManager);
		this.textAndGraphicManager.start(subManagerListener);
	}

	/**
	 * <p>Called when manager is being torn down</p>
	 */
	public void dispose(){
		transitionToState(SHUTDOWN);
	}

	// Setters

	// TODO: IMPORTANT: we have to make sure that ScreenManager informs softButtonManager about all MainField1 updates, otherwise softButtonManager will override textField1 with old values
	public void setTextField1(@NonNull String textField1) {
		this.textAndGraphicManager.setTextField1(textField1);
		this.softButtonManager.setCurrentMainField1(textField1);
	}

	public void setTextField2(String textField2) {
		this.textAndGraphicManager.setTextField2(textField2);
	}

	public void setTextField3(String textField3) {
		this.textAndGraphicManager.setTextField3(textField3);
	}

	public void setTextField4(String textField4) {
		this.textAndGraphicManager.setTextField4(textField4);
	}

	public void setMediaTrackTextField(String mediaTrackTextField) {
		this.textAndGraphicManager.setMediaTrackTextField(mediaTrackTextField);
	}

	public void setPrimaryGraphic(SdlArtwork primaryGraphic) {
		if (primaryGraphic == null){
			primaryGraphic = textAndGraphicManager.getBlankArtwork();
		}
		this.textAndGraphicManager.setPrimaryGraphic(primaryGraphic);
	}

	public void setSecondaryGraphic(SdlArtwork secondaryGraphic) {
		if (secondaryGraphic == null){
			secondaryGraphic = textAndGraphicManager.getBlankArtwork();
		}
		this.textAndGraphicManager.setSecondaryGraphic(secondaryGraphic);
	}

	public void setTextAlignment(TextAlignment textAlignment) {
		this.textAndGraphicManager.setTextAlignment(textAlignment);
	}

	public void setTextField1Type(MetadataType textField1Type) {
		this.textAndGraphicManager.setTextField1Type(textField1Type);
	}

	public void setTextField2Type(MetadataType textField2Type) {
		this.textAndGraphicManager.setTextField2Type(textField2Type);
	}

	public void setTextField3Type(MetadataType textField3Type) {
		this.textAndGraphicManager.setTextField3Type(textField3Type);
	}

	public void setTextField4Type(MetadataType textField4Type) {
		this.textAndGraphicManager.setTextField4Type(textField4Type);
	}

	public void setSoftButtonObjects(@NonNull List<SoftButtonObject> softButtonObjects) {
		softButtonManager.setSoftButtonObjects(softButtonObjects);
	}

	// Getters

	public String getTextField1() {
		return this.textAndGraphicManager.getTextField1();
	}

	public String getTextField2() {
		return this.textAndGraphicManager.getTextField2();
	}

	public String getTextField3() {
		return this.textAndGraphicManager.getTextField3();
	}

	public String getTextField4() {
		return this.textAndGraphicManager.getTextField4();
	}

	public String getMediaTrackTextField() {
		return this.textAndGraphicManager.getMediaTrackTextField();
	}

	public SdlArtwork getPrimaryGraphic() {
		return this.textAndGraphicManager.getPrimaryGraphic();
	}

	public SdlArtwork getSecondaryGraphic() {
		return this.textAndGraphicManager.getSecondaryGraphic();
	}

	public TextAlignment getTextAlignment() {
		return this.textAndGraphicManager.getTextAlignment();
	}

	public MetadataType getTextField1Type() {
		return this.textAndGraphicManager.getTextField1Type();
	}

	public MetadataType getTextField2Type() {
		return this.textAndGraphicManager.getTextField2Type();
	}

	public MetadataType getTextField3Type() {
		return this.textAndGraphicManager.getTextField3Type();
	}

	public MetadataType getTextField4Type() {
		return this.textAndGraphicManager.getTextField4Type();
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

	/**
	 * Add an OnRPCNotificationListener for button press notifications
	 * @param listener a listener that will be called when a button is pressed
	 */
	public void addOnButtonPressListener(OnRPCNotificationListener listener){
		softButtonManager.addOnButtonPressListener(listener);
	}

	// Updates

	public void beginTransaction(){
		softButtonManager.setBatchUpdates(true);
		textAndGraphicManager.setBatchUpdates(true);
	}

	public void commit(final CompletionListener listener){
		// This map stores the update completion status for each SubManager.
		// The key is the SubManager, and the value is the status for that SubManager
		// null means the SubManager didn't finished the update yet, true means it finished with success, and false means it finished with failure
		final Map<BaseSubManager, Boolean> subManagersCompletionListenersStatus = new HashMap<>();


		// SoftButtonManager
		subManagersCompletionListenersStatus.put(softButtonManager, null);
		softButtonManager.setBatchUpdates(false);
		softButtonManager.update(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				subManagersCompletionListenersStatus.put(softButtonManager, success);
				Boolean allFinishedSuccessfully = allSubManagersFinishedUpdatingSuccessfully(subManagersCompletionListenersStatus);
				if (allFinishedSuccessfully != null){
					listener.onComplete(allFinishedSuccessfully);
				}
			}
		});

//		// TextAndGraphicManager
		subManagersCompletionListenersStatus.put(textAndGraphicManager, null);
		textAndGraphicManager.setBatchUpdates(false);
		textAndGraphicManager.update(new CompletionListener() {
			@Override
			public void onComplete(boolean success) {
				subManagersCompletionListenersStatus.put(textAndGraphicManager, success);
				Boolean allSubManagersFinishedSuccessfully = allSubManagersFinishedUpdatingSuccessfully(subManagersCompletionListenersStatus);
				if (allSubManagersFinishedSuccessfully != null){
					listener.onComplete(allSubManagersFinishedSuccessfully);
				}
			}
		});
	}


	// null means not all SubManagers finished the update
	// true means all SubManagers finished the update with success
	// false means all SubManagers finished the update but some finished with failure
	private Boolean allSubManagersFinishedUpdatingSuccessfully(Map<BaseSubManager, Boolean> subManagersCompletionListenersStatus){
		boolean allSubManagersCompleted = true;
		boolean allCompletedSubManagersFinishedWithSuccess = true;
		for (BaseSubManager subManager : subManagersCompletionListenersStatus.keySet()) {
			Boolean listenerStatus = subManagersCompletionListenersStatus.get(subManager);
			if (listenerStatus != null){
				if (!listenerStatus){
					allCompletedSubManagersFinishedWithSuccess = false;
				}
			} else {
				allSubManagersCompleted = false;
				break;
			}
		}
		if (!allSubManagersCompleted){
			return null;
		} else {
			return allCompletedSubManagersFinishedWithSuccess;
		}
	}

}
