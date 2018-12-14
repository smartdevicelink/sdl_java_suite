package com.smartdevicelink.managers.screen;

import android.support.annotation.NonNull;
import android.util.Log;

import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.SetDisplayLayout;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;
import com.smartdevicelink.proxy.rpc.enums.PredefinedLayout;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * <strong>ScreenManager</strong> <br>
 *
 * Note: This class must be accessed through the SdlManager. Do not instantiate it by itself. <br>
*/
public class ScreenManager extends BaseSubManager {

	private static String TAG = "ScreenManager";
	private final WeakReference<FileManager> fileManager;
	private SoftButtonManager softButtonManager;
	private TextAndGraphicManager textAndGraphicManager;

	// Sub manager listener
	private final CompletionListener subManagerListener = new CompletionListener() {
		@Override
		public synchronized void onComplete(boolean success) {
			if (softButtonManager != null && textAndGraphicManager != null) {
				if (softButtonManager.getState() == BaseSubManager.READY && textAndGraphicManager.getState() == BaseSubManager.READY) {
					DebugTool.logInfo("Starting screen manager, all sub managers are in ready state");
					transitionToState(READY);
				} else if (softButtonManager.getState() == BaseSubManager.ERROR && textAndGraphicManager.getState() == BaseSubManager.ERROR) {
					Log.e(TAG, "ERROR starting screen manager, both sub managers in error state");
					transitionToState(ERROR);
				} else if (textAndGraphicManager.getState() == BaseSubManager.SETTING_UP || softButtonManager.getState() == BaseSubManager.SETTING_UP) {
					DebugTool.logInfo("SETTING UP screen manager, one sub manager is still setting up");
					transitionToState(SETTING_UP);
				} else {
					Log.w(TAG, "LIMITED starting screen manager, one sub manager in error state and the other is ready");
					transitionToState(LIMITED);
				}
			} else {
				// We should never be here, but somehow one of the sub-sub managers is null
				Log.e(TAG, "ERROR one of the screen sub managers is null");
				transitionToState(ERROR);
			}
		}
	};

	public ScreenManager(@NonNull ISdl internalInterface, @NonNull FileManager fileManager) {
		super(internalInterface);
		this.fileManager = new WeakReference<>(fileManager);
		initialize();
	}

	@Override
	public void start(CompletionListener listener) {
		super.start(listener);
		this.softButtonManager.start(subManagerListener);
		this.textAndGraphicManager.start(subManagerListener);
	}

	private void initialize(){
		if (fileManager.get() != null) {
			this.softButtonManager = new SoftButtonManager(internalInterface, fileManager.get());
			this.textAndGraphicManager = new TextAndGraphicManager(internalInterface, fileManager.get(), softButtonManager);
		}
	}

	/**
	 * <p>Called when manager is being torn down</p>
	 */
	@Override
	public void dispose() {
		softButtonManager.dispose();
		textAndGraphicManager.dispose();
		super.dispose();
	}

	/**
	 * Set the textField1 on the head unit screen
	 * Sending an empty String "" will clear the field
	 * @param textField1 String value represents the textField1
	 */
	public void setTextField1(String textField1) {
		this.softButtonManager.setCurrentMainField1(textField1);
		this.textAndGraphicManager.setTextField1(textField1);
	}

	/**
	 * Get the current textField1 value
	 * @return a String value represents the current textField1 value
	 */
	public String getTextField1() {
		return this.textAndGraphicManager.getTextField1();
	}

	/**
	 * Set the textField2 on the head unit screen
	 * Sending an empty String "" will clear the field
	 * @param textField2 String value represents the textField1
	 */
	public void setTextField2(String textField2) {
		this.textAndGraphicManager.setTextField2(textField2);
	}

	/**
	 * Get the current textField2 value
	 * @return a String value represents the current textField2 value
	 */
	public String getTextField2() {
		return this.textAndGraphicManager.getTextField2();
	}

	/**
	 * Set the textField3 on the head unit screen
	 * Sending an empty String "" will clear the field
	 * @param textField3 String value represents the textField1
	 */
	public void setTextField3(String textField3) {
		this.textAndGraphicManager.setTextField3(textField3);
	}

	/**
	 * Get the current textField3 value
	 * @return a String value represents the current textField3 value
	 */
	public String getTextField3() {
		return this.textAndGraphicManager.getTextField3();
	}

	/**
	 * Set the textField4 on the head unit screen
	 * Sending an empty String "" will clear the field
	 * @param textField4 String value represents the textField1
	 */
	public void setTextField4(String textField4) {
		this.textAndGraphicManager.setTextField4(textField4);
	}

	/**
	 * Get the current textField4 value
	 * @return a String value represents the current textField4 value
	 */
	public String getTextField4() {
		return this.textAndGraphicManager.getTextField4();
	}

	/**
	 * Set the mediaTrackTextField on the head unit screen
	 * @param mediaTrackTextField String value represents the mediaTrackTextField
	 */
	public void setMediaTrackTextField(String mediaTrackTextField) {
		this.textAndGraphicManager.setMediaTrackTextField(mediaTrackTextField);
	}

	/**
	 * Get the current mediaTrackTextField value
	 * @return a String value represents the current mediaTrackTextField
	 */
	public String getMediaTrackTextField() {
		return this.textAndGraphicManager.getMediaTrackTextField();
	}

	/**
	 * Set the primaryGraphic on the head unit screen
	 * @param primaryGraphic an SdlArtwork object represents the primaryGraphic
	 */
	public void setPrimaryGraphic(SdlArtwork primaryGraphic) {
		if (primaryGraphic == null){
			primaryGraphic = textAndGraphicManager.getBlankArtwork();
		}
		this.textAndGraphicManager.setPrimaryGraphic(primaryGraphic);
	}

	/**
	 * Get the current primaryGraphic value
	 * @return an SdlArtwork object represents the current primaryGraphic
	 */
	public SdlArtwork getPrimaryGraphic() {
		return this.textAndGraphicManager.getPrimaryGraphic();
	}

	/**
	 * Set the secondaryGraphic on the head unit screen
	 * @param secondaryGraphic an SdlArtwork object represents the secondaryGraphic
	 */
	public void setSecondaryGraphic(SdlArtwork secondaryGraphic) {
		if (secondaryGraphic == null){
			secondaryGraphic = textAndGraphicManager.getBlankArtwork();
		}
		this.textAndGraphicManager.setSecondaryGraphic(secondaryGraphic);
	}

	/**
	 * Get the current secondaryGraphic value
	 * @return an SdlArtwork object represents the current secondaryGraphic
	 */
	public SdlArtwork getSecondaryGraphic() {
		return this.textAndGraphicManager.getSecondaryGraphic();
	}

	/**
	 * Set the alignment for the text fields
	 * @param textAlignment TextAlignment value represents the alignment for the text fields
	 */
	public void setTextAlignment(TextAlignment textAlignment) {
		this.textAndGraphicManager.setTextAlignment(textAlignment);
	}

	/**
	 * Get the alignment for the text fields
	 * @return a TextAlignment value represents the alignment for the text fields
	 */
	public TextAlignment getTextAlignment() {
		return this.textAndGraphicManager.getTextAlignment();
	}

	/**
	 * Set the metadata type for the textField1
	 * @param textField1Type a MetadataType value represents the metadata for textField1
	 */
	public void setTextField1Type(MetadataType textField1Type) {
		this.textAndGraphicManager.setTextField1Type(textField1Type);
	}

	/**
	 * Get the metadata type for textField1
	 * @return a MetadataType value represents the metadata for textField1
	 */
	public MetadataType getTextField1Type() {
		return this.textAndGraphicManager.getTextField1Type();
	}

	/**
	 * Set the metadata type for the textField2
	 * @param textField2Type a MetadataType value represents the metadata for textField2
	 */
	public void setTextField2Type(MetadataType textField2Type) {
		this.textAndGraphicManager.setTextField2Type(textField2Type);
	}

	/**
	 * Get the metadata type for textField2
	 * @return a MetadataType value represents the metadata for textField2
	 */
	public MetadataType getTextField2Type() {
		return this.textAndGraphicManager.getTextField2Type();
	}

	/**
	 * Set the metadata type for the textField3
	 * @param textField3Type a MetadataType value represents the metadata for textField3
	 */
	public void setTextField3Type(MetadataType textField3Type) {
		this.textAndGraphicManager.setTextField3Type(textField3Type);
	}

	/**
	 * Get the metadata type for textField3
	 * @return a MetadataType value represents the metadata for textField3
	 */
	public MetadataType getTextField3Type() {
		return this.textAndGraphicManager.getTextField3Type();
	}

	/**
	 * Set the metadata type for the textField4
	 * @param textField4Type a MetadataType value represents the metadata for textField4
	 */
	public void setTextField4Type(MetadataType textField4Type) {
		this.textAndGraphicManager.setTextField4Type(textField4Type);
	}

	/**
	 * Get the metadata type for textField4
	 * @return a MetadataType value represents the metadata for textField4
	 */
	public MetadataType getTextField4Type() {
		return this.textAndGraphicManager.getTextField4Type();
	}

	/**
	 * Set softButtonObjects list and upload the images to the head unit
	 * @param softButtonObjects the list of the SoftButtonObject values that should be displayed on the head unit
	 */
	public void setSoftButtonObjects(@NonNull List<SoftButtonObject> softButtonObjects) {
		softButtonManager.setSoftButtonObjects(softButtonObjects);
	}

	/**
	 * Get the soft button objects list
	 * @return a List<SoftButtonObject>
	 */
	public List<SoftButtonObject> getSoftButtonObjects() {
		return softButtonManager.getSoftButtonObjects();
	}

	/**
	 * Get the SoftButtonObject that has the provided name
	 * @param name a String value that represents the name
	 * @return a SoftButtonObject
	 */
	public SoftButtonObject getSoftButtonObjectByName(@NonNull String name){
		return softButtonManager.getSoftButtonObjectByName(name);
	}

	/**
	 * Get the SoftButtonObject that has the provided buttonId
	 * @param buttonId a int value that represents the id of the button
	 * @return a SoftButtonObject
	 */
	public SoftButtonObject getSoftButtonObjectById(int buttonId){
		return softButtonManager.getSoftButtonObjectById(buttonId);
	}

	/**
	 * Begin a multiple updates transaction. The updates will be applied when commit() is called<br>
	 * Note: if we don't use beginTransaction & commit, every update will be sent individually.
	 */
	public void beginTransaction(){
		softButtonManager.setBatchUpdates(true);
		textAndGraphicManager.setBatchUpdates(true);
	}

	/**
	 * Send the updates that were started after beginning the transaction
	 * @param listener a CompletionListener that has a callback that will be called when the updates are finished
	 */
	public void commit(final CompletionListener listener){
		softButtonManager.setBatchUpdates(false);
		softButtonManager.update(new CompletionListener() {
			boolean updateSuccessful = true;
			@Override
			public void onComplete(boolean success) {
				if (!success){
					updateSuccessful = false;
				}
				textAndGraphicManager.setBatchUpdates(false);
				textAndGraphicManager.update(new CompletionListener() {
					@Override
					public void onComplete(boolean success) {
						if (!success){
							updateSuccessful = false;
						}
						listener.onComplete(updateSuccessful);
					}
				});
			}
		});
	}

}
