/*
 * Copyright (c) 2019 Livio, Inc.
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
 * Neither the name of the Livio Inc. nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
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
package com.smartdevicelink.managers.screen;

import android.support.annotation.NonNull;

import com.smartdevicelink.managers.BaseSubManager;
import com.smartdevicelink.managers.CompletionListener;
import com.smartdevicelink.managers.ManagerUtility;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.MultipleFileCompletionListener;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.SystemCapabilityManager;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.DisplayCapability;
import com.smartdevicelink.proxy.rpc.MetadataTags;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.WindowCapability;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.ImageFieldName;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;
import com.smartdevicelink.proxy.rpc.enums.PredefinedWindows;
import com.smartdevicelink.proxy.rpc.enums.Result;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.proxy.rpc.enums.TextFieldName;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;
import com.smartdevicelink.util.CompareUtils;
import com.smartdevicelink.util.DebugTool;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.smartdevicelink.proxy.rpc.enums.TextAlignment.CENTERED;

/**
 * <strong>TextAndGraphicManager</strong> <br>
 *
 * Note: This class must be accessed through the SdlManager. Do not instantiate it by itself. <br>
 *
 */
abstract class BaseTextAndGraphicManager extends BaseSubManager {

	private static final String TAG = "TextAndGraphicManager";

	boolean isDirty, hasQueuedUpdate;
	volatile Show inProgressUpdate;
	Show currentScreenData, queuedImageUpdate;
	HMILevel currentHMILevel;
	WindowCapability defaultMainWindowCapability;
	private boolean pendingHMIFull, batchingUpdates;
	private final WeakReference<FileManager> fileManager;
	private final WeakReference<SoftButtonManager> softButtonManager;
	private CompletionListener queuedUpdateListener, inProgressListener, pendingHMIListener;
	SdlArtwork blankArtwork;
	private OnRPCNotificationListener hmiListener;
	private OnSystemCapabilityListener onDisplaysCapabilityListener;
	private SdlArtwork primaryGraphic, secondaryGraphic;
	private TextAlignment textAlignment;
	private String textField1, textField2, textField3, textField4, mediaTrackTextField, title;
	private MetadataType textField1Type, textField2Type, textField3Type, textField4Type;

	//Constructors

	BaseTextAndGraphicManager(@NonNull ISdl internalInterface, @NonNull FileManager fileManager, @NonNull SoftButtonManager softButtonManager) {
		// set class vars
		super(internalInterface);
		this.fileManager = new WeakReference<>(fileManager);
		this.softButtonManager = new WeakReference<>(softButtonManager);
		batchingUpdates = false;
		isDirty = false;
		pendingHMIFull = false;
		textAlignment = CENTERED;
		currentHMILevel = HMILevel.HMI_NONE;
		currentScreenData = new Show();
		addListeners();
		getBlankArtwork();
	}

	@Override
	public void start(CompletionListener listener) {
		transitionToState(READY);
		super.start(listener);
	}

	@Override
	public void dispose(){

		textField1 = null;
		textField1Type = null;
		textField2 = null;
		textField2Type = null;
		textField3 = null;
		textField3Type = null;
		textField4 = null;
		textField4Type = null;
		mediaTrackTextField = null;
		title = null;
		textAlignment = null;
		primaryGraphic = null;
		secondaryGraphic = null;
		blankArtwork = null;
		defaultMainWindowCapability = null;
		inProgressUpdate = null;
		queuedImageUpdate = null;
		currentScreenData = null;
		queuedUpdateListener = null;
		pendingHMIListener = null;
		inProgressListener = null;
		hasQueuedUpdate = false;
		isDirty = false;
		pendingHMIFull = false;

		// remove listeners
		internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);
		internalInterface.removeOnSystemCapabilityListener(SystemCapabilityType.DISPLAYS, onDisplaysCapabilityListener);

		super.dispose();
	}

	private void addListeners() {
		// add listener
		hmiListener = new OnRPCNotificationListener() {
			@Override
			public void onNotified(RPCNotification notification) {
				OnHMIStatus onHMIStatus = (OnHMIStatus)notification;
				if (onHMIStatus.getWindowID() != null && onHMIStatus.getWindowID() != PredefinedWindows.DEFAULT_WINDOW.getValue()) {
					return;
				}
				currentHMILevel = onHMIStatus.getHmiLevel();
				if (currentHMILevel == HMILevel.HMI_FULL){
					if (pendingHMIFull){
						DebugTool.logInfo(TAG, "Acquired HMI_FULL with pending update. Sending now");
						pendingHMIFull = false;
						sdlUpdate(pendingHMIListener);
						pendingHMIListener = null;
					}
				}
			}
		};
		internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);


		onDisplaysCapabilityListener = new OnSystemCapabilityListener() {
			@Override
			public void onCapabilityRetrieved(Object capability) {
				// instead of using the parameter it's more safe to use the convenience method
				List<DisplayCapability> capabilities = SystemCapabilityManager.convertToList(capability, DisplayCapability.class);
				if (capabilities == null || capabilities.size() == 0) {
					DebugTool.logError(TAG, "TextAndGraphic Manager - Capabilities sent here are null or empty");
				}else {
					DisplayCapability display = capabilities.get(0);
					for (WindowCapability windowCapability : display.getWindowCapabilities()) {
						int currentWindowID = windowCapability.getWindowID() != null ? windowCapability.getWindowID() : PredefinedWindows.DEFAULT_WINDOW.getValue();
						if (currentWindowID == PredefinedWindows.DEFAULT_WINDOW.getValue()) {
							defaultMainWindowCapability = windowCapability;
						}
					}
				}
			}

			@Override
			public void onError(String info) {
				DebugTool.logError(TAG, "Display Capability cannot be retrieved");
				defaultMainWindowCapability = null;
			}
		};
		this.internalInterface.addOnSystemCapabilityListener(SystemCapabilityType.DISPLAYS, onDisplaysCapabilityListener);
	}

	// Upload / Send

	protected void update(CompletionListener listener) {

		// check if is batch update
		if (batchingUpdates) {
			return;
		}

		if (isDirty){
			isDirty = false;
			sdlUpdate(listener);
		} else if (listener != null) {
			listener.onComplete(true);
		}
	}

	private synchronized void sdlUpdate(CompletionListener listener){

		// make sure hmi is not none
		if (currentHMILevel == null || currentHMILevel == HMILevel.HMI_NONE){
			//Trying to send show on HMI_NONE, waiting for full
			pendingHMIFull = true;
			if (listener != null){
				pendingHMIListener = listener;
			}
			return;
		}

		//Updating Text and Graphics
		if (inProgressUpdate != null){

			//In progress update exists, queueing update
			if (queuedUpdateListener != null){

				//Queued update already exists, superseding previous queued update
				queuedUpdateListener.onComplete(false);
				queuedUpdateListener = null;
			}

			if (listener != null){
				queuedUpdateListener = listener;
			}

			hasQueuedUpdate = true;

			return;
		}

		Show fullShow = new Show();
		fullShow.setAlignment(textAlignment);
		fullShow = assembleShowText(fullShow);
		fullShow = assembleShowImages(fullShow);

		inProgressListener = listener;

		if (!shouldUpdatePrimaryImage() && !shouldUpdateSecondaryImage()){

			//No Images to send, only sending text
			inProgressUpdate = extractTextFromShow(fullShow);
			sendShow();

		}else if (!sdlArtworkNeedsUpload(primaryGraphic) && (secondaryGraphic == blankArtwork || !sdlArtworkNeedsUpload(secondaryGraphic))){

			//Images already uploaded, sending full update
			// The files to be updated are already uploaded, send the full show immediately
			inProgressUpdate = fullShow;
			sendShow();
		} else{

			// Images need to be uploaded, sending text and uploading images
			inProgressUpdate = fullShow;
			final Show thisUpdate = fullShow;

			uploadImages(new CompletionListener() {
				@Override
				public void onComplete(boolean success) {
					if (!success){
						DebugTool.logError(TAG, "Error uploading image");
						inProgressUpdate = extractTextFromShow(inProgressUpdate);
						sendShow();
					}
					// Check if queued image update still matches our images (there could have been a new Show in the meantime)
					// and send a new update if it does. Since the images will already be on the head unit, the whole show will be sent
					if (thisUpdate.getGraphic() != null && thisUpdate.getGraphic().equals(queuedImageUpdate.getGraphic()) ||
							(thisUpdate.getSecondaryGraphic() != null && queuedImageUpdate.getSecondaryGraphic() != null) && thisUpdate.getSecondaryGraphic().equals(queuedImageUpdate.getSecondaryGraphic())){
						// Queued image update matches the images we need, sending update
						sendShow();
					}
					// Else, Queued image update does not match the images we need, skipping update
				}
			});
			queuedImageUpdate = fullShow;
		}
	}

	private void sendShow(){
		inProgressUpdate.setOnRPCResponseListener(new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				handleResponse(response.getSuccess());
			}

			@Override
			public void onError(int correlationId, Result resultCode, String info) {
				handleResponse(false);
			}

			private void handleResponse(boolean success){
				if (success){
					updateCurrentScreenDataState(inProgressUpdate);
				}

				inProgressUpdate = null;
				if (inProgressListener != null){
					inProgressListener.onComplete(success);
					inProgressListener = null;
				}

				if (hasQueuedUpdate){
					//Queued update exists, sending another update
					hasQueuedUpdate = false;
					CompletionListener temp = queuedUpdateListener;
					queuedUpdateListener = null;
					sdlUpdate(temp);
				}
			}
		});

		if (this.softButtonManager.get() != null) {
			this.softButtonManager.get().setCurrentMainField1(inProgressUpdate.getMainField1());
		}
		internalInterface.sendRPC(inProgressUpdate);
	}

	// Images

	private void uploadImages(final CompletionListener listener) {

		List<SdlArtwork> artworksToUpload = new ArrayList<>();

		// add primary image
		if (shouldUpdatePrimaryImage() && !primaryGraphic.isStaticIcon()){
			artworksToUpload.add(primaryGraphic);
		}

		// add secondary image
		if (shouldUpdateSecondaryImage() && !secondaryGraphic.isStaticIcon()){
			artworksToUpload.add(secondaryGraphic);
		}

		if (artworksToUpload.size() == 0 && (primaryGraphic.isStaticIcon() || secondaryGraphic.isStaticIcon())){
			DebugTool.logInfo(TAG, "Upload attempted on static icons, sending them without upload instead");
			listener.onComplete(true);
		}

		// use file manager to upload art
		if (fileManager.get() != null) {
			fileManager.get().uploadArtworks(artworksToUpload, new MultipleFileCompletionListener() {
				@Override
				public void onComplete(Map<String, String> errors) {
					if (errors != null) {
						DebugTool.logError(TAG, "Error Uploading Artworks. Error: " + errors.toString());
						listener.onComplete(false);
					} else {
						listener.onComplete(true);
					}
				}
			});
		}
	}

	private Show assembleShowImages(Show show){

		if (shouldUpdatePrimaryImage()){
			show.setGraphic(primaryGraphic.getImageRPC());
		}

		if (shouldUpdateSecondaryImage()){
			show.setSecondaryGraphic(secondaryGraphic.getImageRPC());
		}

		return show;
	}

	// Text

	Show assembleShowText(Show show){

		show = setBlankTextFields(show);

		if (mediaTrackTextField != null && shouldUpdateMediaTrackField()) {
			show.setMediaTrack(mediaTrackTextField);
		}

		if (title != null && shouldUpdateTitleField()) {
			show.setTemplateTitle(title);
		}

		List<String> nonNullFields = findValidMainTextFields();
		if (nonNullFields.isEmpty()){
			return show;
		}

		int numberOfLines = defaultMainWindowCapability != null ? ManagerUtility.WindowCapabilityUtility.getMaxNumberOfMainFieldLines(defaultMainWindowCapability) : 4;

		switch (numberOfLines) {
			case 1: show = assembleOneLineShowText(show, nonNullFields);
				break;
			case 2: show = assembleTwoLineShowText(show);
				break;
			case 3: show = assembleThreeLineShowText(show);
				break;
			case 4: show = assembleFourLineShowText(show);
				break;
		}

		return show;
	}

	private Show assembleOneLineShowText(Show show, List<String> showFields){

		StringBuilder showString1 = new StringBuilder();
		for (int i = 0; i < showFields.size(); i++) {
			if (i > 0) {
				showString1.append(" - ").append(showFields.get(i));
			}else{
				showString1.append(showFields.get(i));
			}
		}
		show.setMainField1(showString1.toString());

		MetadataTags tags = new MetadataTags();
		tags.setMainField1(findNonNullMetadataFields());

		show.setMetadataTags(tags);

		return show;
	}

	private Show assembleTwoLineShowText(Show show){

		StringBuilder tempString = new StringBuilder();
		MetadataTags tags = new MetadataTags();

		if (textField1 != null && textField1.length() > 0) {
			tempString.append(textField1);
			if (textField1Type != null){
				tags.setMainField1(textField1Type);
			}
		}

		if (textField2 != null && textField2.length() > 0) {
			if (( textField3 == null || !(textField3.length() > 0)) && (textField4 == null || !(textField4.length() > 0))){
				// text does not exist in slots 3 or 4, put text2 in slot 2
				show.setMainField2(textField2);
				if (textField2Type != null){
					tags.setMainField2(textField2Type);
				}
			} else if (textField1 != null && textField1.length() > 0) {
				// If text 1 exists, put it in slot 1 formatted
				tempString.append(" - ").append(textField2);
				if (textField2Type != null){
					List<MetadataType> typeList = new ArrayList<>();
					typeList.add(textField2Type);
					if (textField1Type != null){
						typeList.add(textField1Type);
					}
					tags.setMainField1(typeList);
				}
			}else {
				// If text 1 does not exist, put it in slot 1 unformatted
				tempString.append(textField2);
				if (textField2Type != null){
					tags.setMainField1(textField2Type);
				}
			}
		}

		// set mainfield 1
		show.setMainField1(tempString.toString());

		// new stringbuilder object
		tempString = new StringBuilder();

		if (textField3 != null && textField3.length() > 0){
			// If text 3 exists, put it in slot 2
			tempString.append(textField3);
			if (textField3Type != null){
				List<MetadataType> typeList = new ArrayList<>();
				typeList.add(textField3Type);
				tags.setMainField2(typeList);
			}
		}

		if (textField4 != null && textField4.length() > 0){
			if (textField3 != null && textField3.length() > 0){
				// If text 3 exists, put it in slot 2 formatted
				tempString.append(" - ").append(textField4);
				if (textField4Type != null){
					List<MetadataType> typeList = new ArrayList<>();
					typeList.add(textField4Type);
					if (textField3Type != null){
						typeList.add(textField3Type);
					}
					tags.setMainField2(typeList);
				}
			} else {
				// If text 3 does not exist, put it in slot 3 unformatted
				tempString.append(textField4);
				if (textField4Type != null){
					tags.setMainField2(textField4Type);
				}
			}
		}

		if (tempString.toString().length() > 0){
			show.setMainField2(tempString.toString());
		}

		show.setMetadataTags(tags);
		return show;
	}

	private Show assembleThreeLineShowText(Show show){

		MetadataTags tags = new MetadataTags();

		if (textField1 != null && textField1.length() > 0) {
			show.setMainField1(textField1);
			if (textField1Type != null){
				tags.setMainField1(textField1Type);
			}
		}

		if (textField2 != null && textField2.length() > 0) {
			show.setMainField2(textField2);
			if (textField2Type != null){
				tags.setMainField2(textField2Type);
			}
		}

		StringBuilder tempString = new StringBuilder();

		if (textField3 != null && textField3.length() > 0){
			tempString.append(textField3);
			if (textField3Type != null){
				tags.setMainField3(textField3Type);
			}
		}

		if (textField4 != null && textField4.length() > 0) {
			if (textField3 != null && textField3.length() > 0) {
				// If text 3 exists, put it in slot 3 formatted
				tempString.append(" - ").append(textField4);
				if (textField4Type != null){
					List<MetadataType> tags4 = new ArrayList<>();
					if (textField3Type != null){
						tags4.add(textField3Type);
					}
					tags4.add(textField4Type);
					tags.setMainField3(tags4);
				}
			} else {
				// If text 3 does not exist, put it in slot 3 formatted
				tempString.append(textField4);
				if (textField4Type != null){
					tags.setMainField3(textField4Type);
				}
			}
		}

		show.setMainField3(tempString.toString());
		show.setMetadataTags(tags);
		return show;
	}

	private Show assembleFourLineShowText(Show show){

		MetadataTags tags = new MetadataTags();

		if (textField1 != null && textField1.length() > 0) {
			show.setMainField1(textField1);
			if (textField1Type != null){
				tags.setMainField1(textField1Type);
			}
		}

		if (textField2 != null && textField2.length() > 0) {
			show.setMainField2(textField2);
			if (textField2Type != null){
				tags.setMainField2(textField2Type);
			}
		}

		if (textField3 != null && textField3.length() > 0) {
			show.setMainField3(textField3);
			if (textField3Type != null){
				tags.setMainField3(textField3Type);
			}
		}

		if (textField4 != null && textField4.length() > 0) {
			show.setMainField4(textField4);
			if (textField4Type != null){
				tags.setMainField4(textField4Type);
			}
		}

		show.setMetadataTags(tags);
		return show;
	}

	// Extraction

	Show extractTextFromShow(Show show){

		Show newShow = new Show();
		newShow.setMainField1(show.getMainField1());
		newShow.setMainField2(show.getMainField2());
		newShow.setMainField3(show.getMainField3());
		newShow.setMainField4(show.getMainField4());
		newShow.setTemplateTitle(show.getTemplateTitle());
		newShow.setMediaTrack(show.getMediaTrack());

		return newShow;
	}

	private Show setBlankTextFields(Show newShow){

		newShow.setMainField1("");
		newShow.setMainField2("");
		newShow.setMainField3("");
		newShow.setMainField4("");
		newShow.setMediaTrack("");
		newShow.setTemplateTitle("");

		return newShow;
	}

	private void updateCurrentScreenDataState(Show show){

		if (show == null){
			DebugTool.logError(TAG, "can not updateCurrentScreenDataFromShow from null show");
			return;
		}

		// If the items are null, they were not updated, so we can't just set it directly
		if (show.getMainField1() != null){
			currentScreenData.setMainField1(show.getMainField1());
		}
		if (show.getMainField2() != null){
			currentScreenData.setMainField2(show.getMainField2());
		}
		if (show.getMainField3() != null){
			currentScreenData.setMainField3(show.getMainField3());
		}
		if (show.getMainField4() != null){
			currentScreenData.setMainField4(show.getMainField4());
		}
		if (show.getTemplateTitle() != null){
			currentScreenData.setTemplateTitle(show.getTemplateTitle());
		}
		if (show.getMediaTrack() != null){
			currentScreenData.setMediaTrack(show.getMediaTrack());
		}
		if (show.getMetadataTags() != null){
			currentScreenData.setMetadataTags(show.getMetadataTags());
		}
		if (show.getAlignment() != null){
			currentScreenData.setAlignment(show.getAlignment());
		}
		if (show.getGraphic() != null){
			currentScreenData.setGraphic(show.getGraphic());
		}
		if (show.getSecondaryGraphic() != null){
			currentScreenData.setSecondaryGraphic(show.getSecondaryGraphic());
		}
	}

	// Helpers

	private List<String> findValidMainTextFields(){
		List<String> array = new ArrayList<>();

		if (textField1 != null && textField1.length() > 0) {
			array.add(textField1);
		}

		if (textField2 != null && textField2.length() > 0) {
			array.add(textField2);
		}

		if (textField3 != null && textField3.length() > 0) {
			array.add(textField3);
		}

		if (textField4 != null && textField4.length() > 0) {
			array.add(textField4);
		}

		return array;
	}


	private List<MetadataType> findNonNullMetadataFields(){
		List<MetadataType> array = new ArrayList<>();

		if (textField1Type != null) {
			array.add(textField1Type);
		}

		if (textField2Type != null) {
			array.add(textField2Type);
		}

		if (textField3Type != null) {
			array.add(textField3Type);
		}

		if (textField4Type != null) {
			array.add(textField4Type);
		}

		return array;
	}

	abstract SdlArtwork getBlankArtwork();

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	private boolean sdlArtworkNeedsUpload(SdlArtwork artwork){
		if (fileManager.get() != null) {
			return artwork != null && !fileManager.get().hasUploadedFile(artwork) && !artwork.isStaticIcon();
		}
		return false;
	}

	/**
	 * Check to see if primaryGraphic should be updated
	 * @return true if primaryGraphic should be updated, false if not
	 */
	private boolean shouldUpdatePrimaryImage() {
		boolean templateSupportsPrimaryArtwork = templateSupportsImageField(ImageFieldName.graphic);

		String currentScreenDataPrimaryGraphicName = (currentScreenData != null && currentScreenData.getGraphic() != null) ? currentScreenData.getGraphic().getValue() : null;
		String primaryGraphicName = primaryGraphic != null ? primaryGraphic.getName() : null;
		return templateSupportsPrimaryArtwork
				&& !CompareUtils.areStringsEqual(currentScreenDataPrimaryGraphicName, primaryGraphicName, true, true)
				&& primaryGraphic != null;
	}

	/**
	 * Check to see if secondaryGraphic should be updated
	 * @return true if secondaryGraphic should be updated, false if not
	 */
	private boolean shouldUpdateSecondaryImage() {
		boolean templateSupportsSecondaryArtwork = (templateSupportsImageField(ImageFieldName.graphic) || templateSupportsImageField(ImageFieldName.secondaryGraphic));

		String currentScreenDataSecondaryGraphicName = (currentScreenData != null && currentScreenData.getSecondaryGraphic() != null) ? currentScreenData.getSecondaryGraphic().getValue() : null;
		String secondaryGraphicName = secondaryGraphic != null ? secondaryGraphic.getName() : null;
		return templateSupportsSecondaryArtwork
				&& !CompareUtils.areStringsEqual(currentScreenDataSecondaryGraphicName, secondaryGraphicName, true, true)
				&& secondaryGraphic != null;
	}

	/**
	 * Check to see if template supports the specified image field
	 * @return true if image field is supported, false if not
	 */
	private boolean templateSupportsImageField(ImageFieldName name) {
		return defaultMainWindowCapability == null || ManagerUtility.WindowCapabilityUtility.hasImageFieldOfName(defaultMainWindowCapability, name);
	}

	/**
	 * Check to see if mediaTrackTextField should be updated
	 * @return true if mediaTrackTextField should be updated, false if not
	 */
	private boolean shouldUpdateMediaTrackField() {
		return templateSupportsTextField(TextFieldName.mediaTrack);
	}

	/**
	 * Check to see if title should be updated
	 * @return true if title should be updated, false if not
	 */
	private boolean shouldUpdateTitleField() {
		return templateSupportsTextField(TextFieldName.templateTitle);
	}

	/**
	 * Check to see if field should be updated
	 * @return true if field should be updated, false if not
	 */
	private boolean templateSupportsTextField(TextFieldName name) {
		return defaultMainWindowCapability == null || ManagerUtility.WindowCapabilityUtility.hasTextFieldOfName(defaultMainWindowCapability, name);
	}

	// SCREEN ITEM SETTERS AND GETTERS

	void setTextAlignment(TextAlignment textAlignment){
		this.textAlignment = textAlignment;
		// If we aren't batching, send the update immediately, if we are, set ourselves as dirty (so we know we should send an update after the batch ends)
		if (!batchingUpdates){
			sdlUpdate(null);
		}else{
			isDirty = true;
		}
	}

	TextAlignment getTextAlignment(){
		return textAlignment;
	}

	void setMediaTrackTextField(String mediaTrackTextField){
		this.mediaTrackTextField = mediaTrackTextField;
		if (!batchingUpdates){
			sdlUpdate(null);
		}else{
			isDirty = true;
		}
	}

	String getMediaTrackTextField(){
		return mediaTrackTextField;
	}

	void setTextField1(String textField1){
		this.textField1 = textField1;
		if (!batchingUpdates){
			sdlUpdate(null);
		}else{
			isDirty = true;
		}
	}

	String getTextField1(){
		return textField1;
	}

	void setTextField2(String textField2){
		this.textField2 = textField2;
		if (!batchingUpdates){
			sdlUpdate(null);
		}else{
			isDirty = true;
		}
	}

	String getTextField2(){
		return textField2;
	}

	void setTextField3(String textField3){
		this.textField3 = textField3;
		if (!batchingUpdates){
			sdlUpdate(null);
		}else{
			isDirty = true;
		}
	}

	String getTextField3(){
		return textField3;
	}

	void setTextField4(String textField4){
		this.textField4 = textField4;
		if (!batchingUpdates){
			sdlUpdate(null);
		}else{
			isDirty = true;
		}
	}

	String getTextField4(){
		return textField4;
	}

	void setTextField1Type(MetadataType textField1Type){
		this.textField1Type = textField1Type;
		if (!batchingUpdates){
			sdlUpdate(null);
		}else{
			isDirty = true;
		}
	}

	MetadataType getTextField1Type(){
		return textField1Type;
	}

	void setTextField2Type(MetadataType textField2Type){
		this.textField2Type = textField2Type;
		if (!batchingUpdates){
			sdlUpdate(null);
		}else{
			isDirty = true;
		}
	}

	MetadataType getTextField2Type(){
		return textField2Type;
	}

	void setTextField3Type(MetadataType textField3Type){
		this.textField3Type = textField3Type;
		if (!batchingUpdates){
			sdlUpdate(null);
		}else{
			isDirty = true;
		}
	}

	MetadataType getTextField3Type(){
		return textField3Type;
	}

	void setTextField4Type(MetadataType textField4Type){
		this.textField4Type = textField4Type;
		if (!batchingUpdates){
			sdlUpdate(null);
		}else{
			isDirty = true;
		}
	}

	MetadataType getTextField4Type(){
		return textField4Type;
	}

	void setTitle(String title){
		this.title = title;
		if (!batchingUpdates){
			sdlUpdate(null);
		}else{
			isDirty = true;
		}
	}

	String getTitle(){
		return title;
	}

	void setPrimaryGraphic(SdlArtwork primaryGraphic){
		this.primaryGraphic = primaryGraphic;
		if (!batchingUpdates){
			sdlUpdate(null);
		}else{
			isDirty = true;
		}
	}

	SdlArtwork getPrimaryGraphic(){
		return primaryGraphic;
	}

	void setSecondaryGraphic(SdlArtwork secondaryGraphic){
		this.secondaryGraphic = secondaryGraphic;
		if (!batchingUpdates){
			sdlUpdate(null);
		}else{
			isDirty = true;
		}
	}

	SdlArtwork getSecondaryGraphic(){
		return secondaryGraphic;
	}

	void setBatchUpdates(boolean batching){
		this.batchingUpdates = batching;
	}

}
