package com.smartdevicelink.api.screen;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.smartdevicelink.R;
import com.smartdevicelink.api.BaseSubManager;
import com.smartdevicelink.api.CompletionListener;
import com.smartdevicelink.api.FileManager;
import com.smartdevicelink.api.MultipleFileCompletionListener;
import com.smartdevicelink.api.datatypes.SdlArtwork;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.interfaces.OnSystemCapabilityListener;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.Image;
import com.smartdevicelink.proxy.rpc.MetadataTags;
import com.smartdevicelink.proxy.rpc.OnHMIStatus;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.HMILevel;
import com.smartdevicelink.proxy.rpc.enums.ImageType;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCNotificationListener;
import com.smartdevicelink.proxy.rpc.enums.TextAlignment;
import com.smartdevicelink.proxy.rpc.listeners.OnRPCResponseListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
class TextAndGraphicManager extends BaseSubManager {

	private static final String TAG = "TextAndGraphicManager";
	private static final String MAIN_FIELD_1 = "mainField1";
	private static final String MAIN_FIELD_2 = "mainField2";
	private static final String MAIN_FIELD_3 = "mainField3";
	private static final String MAIN_FIELD_4 = "mainField4";
	protected boolean isDirty, hasQueuedUpdate;
	protected Show currentScreenData, inProgressUpdate, queuedImageUpdate;
	protected DisplayCapabilities displayCapabilities;
	protected HMILevel currentHMILevel;
	private boolean pendingHMIFull;
	private FileManager fileManager;
	private CompletionListener queuedUpdateListener, inProgressListener, pendingHMIListener;
	private Context context;
	private SdlArtwork blankArtwork;
	private OnRPCNotificationListener hmiListener;
	private OnSystemCapabilityListener onDisplayCapabilitiesListener;
	private SdlArtwork primaryGraphic, secondaryGraphic;
	private TextAlignment textAlignment;
	private String textField1, textField2, textField3, textField4, mediaTrackTextField;
	private MetadataType textField1Type, textField2Type, textField3Type, textField4Type;

	protected boolean batchingUpdates;

	//Constructors

	TextAndGraphicManager(ISdl internalInterface, FileManager fileManager, Context context) {
		// set class vars
		super(internalInterface);
		this.fileManager = fileManager;
		this.context = context;
		batchingUpdates = false;
		isDirty = false;
		pendingHMIFull = false;
		textAlignment = CENTERED;
		currentHMILevel = HMILevel.HMI_NONE;
		currentScreenData = new Show();
		addListeners();
		getBlankArtwork();
		transitionToState(READY);
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
		textAlignment = null;
		primaryGraphic = null;
		secondaryGraphic = null;
		blankArtwork = null;
		displayCapabilities = null;
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
		internalInterface.removeOnSystemCapabilityListener(SystemCapabilityType.DISPLAY, onDisplayCapabilitiesListener);

		// transition state
		transitionToState(SHUTDOWN);
	}

	private void addListeners() {
		// add listener
		hmiListener = new OnRPCNotificationListener() {
			@Override
			public void onNotified(RPCNotification notification) {
				currentHMILevel = ((OnHMIStatus)notification).getHmiLevel();
				if (currentHMILevel == HMILevel.HMI_FULL){
					if (pendingHMIFull){
						Log.v(TAG, "Acquired HMI_FULL with pending update. Sending now");
						pendingHMIFull = false;
						sdl_update(pendingHMIListener);
						pendingHMIListener = null;
					}
				}
			}
		};
		internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);

		// Add OnDisplayCapabilitiesListener to keep displayCapabilities updated
		onDisplayCapabilitiesListener = new OnSystemCapabilityListener() {
			@Override
			public void onCapabilityRetrieved(Object capability) {
				displayCapabilities = (DisplayCapabilities)capability;
			}

			@Override
			public void onError(String info) {
				Log.w(TAG, "DISPLAY Capability cannot be retrieved:");
				displayCapabilities = null;
			}
		};
		this.internalInterface.addOnSystemCapabilityListener(SystemCapabilityType.DISPLAY, onDisplayCapabilitiesListener);
	}

	// Upload / Send

	protected void update(CompletionListener listener) {

		// check if is batch update
		if (batchingUpdates) {
			return;
		}

		if (isDirty){
			isDirty = false;
			sdl_update(listener);
		}
	}
	
	private void sdl_update(CompletionListener listener){

		// make sure hmi is not none
		if (currentHMILevel == null || currentHMILevel == HMILevel.HMI_NONE){
			Log.v(TAG, "Trying to send show on HMI_NONE, waiting for full");
			pendingHMIFull = true;
			if (listener != null){
				pendingHMIListener = listener;
			}
			return;
		}

		Log.v(TAG, "Updating Text and Graphics");
		if (inProgressUpdate != null){

			Log.v(TAG, "In progress update exists, queueing update");
			if (queuedUpdateListener != null){

				Log.v(TAG, "Queued update already exists, superseding previous queued update");
				queuedUpdateListener.onComplete(false);
				queuedUpdateListener = null;
			}

			if (listener != null){
				queuedUpdateListener = listener;
			}else{
				hasQueuedUpdate = true;
			}
			return;
		}

		Show fullShow = new Show();
		fullShow.setAlignment(textAlignment);
		fullShow = assembleShowText(fullShow);
		fullShow = assembleShowImages(fullShow);

		inProgressListener = listener;

		if (!shouldUpdatePrimaryImage() && !shouldUpdateSecondaryImage()){

			Log.v(TAG, "No Images to send, only sending text");
			inProgressUpdate = extractTextFromShow(fullShow);
			sendShow();

		}else if (isArtworkUploadedOrDoesntExist(primaryGraphic) && ( secondaryGraphic == blankArtwork || isArtworkUploadedOrDoesntExist(secondaryGraphic))){

			Log.v(TAG, "Images already uploaded, sending full update");
			// The files to be updated are already uploaded, send the full show immediately
			inProgressUpdate = fullShow;
			sendShow();
		} else{

			Log.v(TAG, "Images need to be uploaded, sending text and uploading images");
			// We need to upload or queue the upload of the images
			// start uploading images
			inProgressUpdate = fullShow;
			final Show thisUpdate = fullShow;

			uploadImages(new CompletionListener() {
				@Override
				public void onComplete(boolean success) {
					if (!success){
						Log.e(TAG, "Error uploading image");
						inProgressUpdate = extractTextFromShow(inProgressUpdate);
						sendShow();
					}
					// Check if queued image update still matches our images (there could have been a new Show in the meantime)
					// and send a new update if it does. Since the images will already be on the head unit, the whole show will be sent
					if ((thisUpdate.getGraphic() != null && queuedImageUpdate.getGraphic() != null) && thisUpdate.getGraphic().equals(queuedImageUpdate.getGraphic()) ||
							(thisUpdate.getSecondaryGraphic() != null && queuedImageUpdate.getSecondaryGraphic() != null) && thisUpdate.getSecondaryGraphic().equals(queuedImageUpdate.getSecondaryGraphic())){
						Log.v(TAG, "Queued image update matches the images we need, sending update");
						sendShow();
					} else {
						Log.v(TAG, "Queued image update does not match the images we need, skipping update");
					}
				}
			});
			queuedImageUpdate = fullShow;
		}
	}

	private void sendShow(){
		inProgressUpdate.setOnRPCResponseListener(new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				if (response.getSuccess()){
					Log.v(TAG, "Show Successful");
					updateCurrentScreenDataFromShow(inProgressUpdate);
				}

				inProgressUpdate = null;
				if (inProgressListener != null){
					inProgressListener.onComplete(true);
					inProgressListener = null;
				}

				if (hasQueuedUpdate){
					Log.v(TAG, "Queued update exists, sending another update");
					sdl_update(queuedUpdateListener);
					queuedUpdateListener = null;
					hasQueuedUpdate = false;
				}
			}
		});

		internalInterface.sendRPCRequest(inProgressUpdate);
	}

	// Images

	private void uploadImages(final CompletionListener listener) {

		List<SdlArtwork> artworksToUpload = new ArrayList<>();

		// add primary image
		if (shouldUpdatePrimaryImage()){
			artworksToUpload.add(primaryGraphic);
		}

		// add secondary image
		if (shouldUpdateSecondaryImage()){
			artworksToUpload.add(secondaryGraphic);
		}
		Log.i(TAG, "Artworks to upload: "+ artworksToUpload.toString());
		// use file manager to upload art
		fileManager.uploadArtworks(artworksToUpload, new MultipleFileCompletionListener() {
			@Override
			public void onComplete(Map<String, String> errors) {
				if (errors != null) {
					Log.e(TAG, "Error Uploading Artworks. Error: " + errors.toString());
					listener.onComplete(false);
				}else{
					Log.v(TAG, "Successfully uploaded Artworks");
					listener.onComplete(true);
				}
			}
		});
	}

	private Show assembleShowImages(Show show){

		if (!shouldUpdatePrimaryImage() && ! shouldUpdateSecondaryImage()){
			return show;
		}

		if (shouldUpdatePrimaryImage()){
			Image primaryImage = new Image();
			primaryImage.setImageType(ImageType.DYNAMIC);
			primaryImage.setValue(primaryGraphic.getName());
			show.setGraphic(primaryImage);
		}

		if (shouldUpdateSecondaryImage()){
			Image secondaryImage = new Image();
			secondaryImage.setImageType(ImageType.DYNAMIC);
			secondaryImage.setValue(secondaryGraphic.getName());
			show.setSecondaryGraphic(secondaryImage);
		}

		return show;
	}

	// Text

	protected Show assembleShowText(Show show){

		show = setBlankTextFields(show);

		if (mediaTrackTextField != null){
			show.setMediaTrack(mediaTrackTextField);
		} else {
			show.setMediaTrack("");
		}

		List<String> nonNullFields = findNonNullMainTextFields();
		if (nonNullFields.size() == 0){
			return show;
		}

		int numberOfLines = getNumberOfLines();

		if (numberOfLines == 1){
			show = assembleOneLineShowText(show, nonNullFields);
		}else if (numberOfLines == 2){
			show = assembleTwoLineShowText(show);
		}else if (numberOfLines == 3){
			show = assembleThreeLineShowText(show);
		}else if (numberOfLines == 4){
			show = assembleFourLineShowText(show);
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

	protected Show extractTextFromShow(Show show){

		Show newShow = new Show();
		newShow.setMainField1(show.getMainField1());
		newShow.setMainField2(show.getMainField2());
		newShow.setMainField3(show.getMainField3());
		newShow.setMainField4(show.getMainField4());

		return newShow;
	}

	private Show setBlankTextFields(Show newShow){

		newShow.setMainField1("");
		newShow.setMainField2("");
		newShow.setMainField3("");
		newShow.setMainField4("");
		newShow.setMediaTrack("");

		return newShow;
	}

	private void updateCurrentScreenDataFromShow(Show show){

		if (show == null){
			Log.e(TAG, "can not updateCurrentScreenDataFromShow from null show");
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

	private List<String> findNonNullMainTextFields(){
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

	protected SdlArtwork getBlankArtwork(){

		if (blankArtwork != null){
			blankArtwork = new SdlArtwork();
			blankArtwork.setType(FileType.GRAPHIC_PNG);
			blankArtwork.setName("blankArtwork");
			blankArtwork.setResourceId(R.drawable.transparent);
		}
		return blankArtwork;
	}

	private boolean isArtworkUploadedOrDoesntExist(SdlArtwork artwork){
		return artwork != null && fileManager.hasUploadedFile(artwork);
	}

	private boolean shouldUpdatePrimaryImage() {
		if (displayCapabilities == null || displayCapabilities.getGraphicSupported()) {
			if (currentScreenData.getGraphic() == null && primaryGraphic != null) {
				return true;
			} else if (currentScreenData.getGraphic() == null && primaryGraphic == null) {
				return false;
			}
			return currentScreenData != null && (primaryGraphic != null && !currentScreenData.getGraphic().getValue().equalsIgnoreCase(primaryGraphic.getName()));
		}
		return false;
	}

	private boolean shouldUpdateSecondaryImage() {
		// Cannot detect if there is a secondary image, so we'll just try to detect if there's a primary image and allow it if there is.
		if (displayCapabilities == null || displayCapabilities.getGraphicSupported()) {
			if (currentScreenData.getGraphic() == null && secondaryGraphic != null) {
				return true;
			} else if (currentScreenData.getGraphic() == null && secondaryGraphic == null) {
				return false;
			}
			return currentScreenData != null && (secondaryGraphic != null && !currentScreenData.getGraphic().getValue().equalsIgnoreCase(secondaryGraphic.getName()));
		}
		return false;
	}

	protected int getNumberOfLines() {

		if (displayCapabilities == null){
			return 4;
		}

		int highestFound = 0;

		List<TextField> textFields = displayCapabilities.getTextFields();

		for (TextField field : textFields) {
			if (field.getName() != null) {
				String name = field.getName().toString();
				if (name.equalsIgnoreCase(MAIN_FIELD_1) || name.equalsIgnoreCase(MAIN_FIELD_2) || name.equalsIgnoreCase(MAIN_FIELD_3) || name.equalsIgnoreCase(MAIN_FIELD_4)) {
					highestFound += 1;
				}
			}
		}

		return highestFound;
	}

	// SCREEN ITEM SETTERS AND GETTERS

	protected void setTextAlignment(TextAlignment textAlignment){
		this.textAlignment = textAlignment;
		// If we aren't batching, send the update immediately, if we are, set ourselves as dirty (so we know we should send an update after the batch ends)
		if (!batchingUpdates){
			sdl_update(null);
		}else{
			isDirty = true;
		}
	}

	protected TextAlignment getTextAlignment(){
		return textAlignment;
	}

	protected void setMediaTrackTextField(String mediaTrackTextField){
		this.mediaTrackTextField = mediaTrackTextField;
		if (!batchingUpdates){
			sdl_update(null);
		}else{
			isDirty = true;
		}
	}

	protected String getMediaTrackTextField(){
		return mediaTrackTextField;
	}

	protected void setTextField1(String textField1){
		this.textField1 = textField1;
		if (!batchingUpdates){
			sdl_update(null);
		}else{
			isDirty = true;
		}
	}

	protected String getTextField1(){
		return textField1;
	}

	protected void setTextField2(String textField2){
		this.textField2 = textField2;
		if (!batchingUpdates){
			sdl_update(null);
		}else{
			isDirty = true;
		}
	}

	protected String getTextField2(){
		return textField2;
	}

	protected void setTextField3(String textField3){
		this.textField3 = textField3;
		if (!batchingUpdates){
			sdl_update(null);
		}else{
			isDirty = true;
		}
	}

	protected String getTextField3(){
		return textField3;
	}

	protected void setTextField4(String textField4){
		this.textField4 = textField4;
		if (!batchingUpdates){
			sdl_update(null);
		}else{
			isDirty = true;
		}
	}

	protected String getTextField4(){
		return textField4;
	}

	protected void setTextField1Type(MetadataType textField1Type){
		this.textField1Type = textField1Type;
		if (!batchingUpdates){
			sdl_update(null);
		}else{
			isDirty = true;
		}
	}

	protected MetadataType getTextField1Type(){
		return textField1Type;
	}

	protected void setTextField2Type(MetadataType textField2Type){
		this.textField2Type = textField2Type;
		if (!batchingUpdates){
			sdl_update(null);
		}else{
			isDirty = true;
		}
	}

	protected MetadataType getTextField2Type(){
		return textField2Type;
	}

	protected void setTextField3Type(MetadataType textField3Type){
		this.textField3Type = textField3Type;
		if (!batchingUpdates){
			sdl_update(null);
		}else{
			isDirty = true;
		}
	}

	protected MetadataType getTextField3Type(){
		return textField3Type;
	}

	protected void setTextField4Type(MetadataType textField4Type){
		this.textField4Type = textField4Type;
		if (!batchingUpdates){
			sdl_update(null);
		}else{
			isDirty = true;
		}
	}

	protected MetadataType getTextField4Type(){
		return textField4Type;
	}

	protected void setPrimaryGraphic(SdlArtwork primaryGraphic){
		this.primaryGraphic = primaryGraphic;
		if (!batchingUpdates){
			sdl_update(null);
		}else{
			isDirty = true;
		}
	}

	protected SdlArtwork getPrimaryGraphic(){
		return primaryGraphic;
	}

	protected void setSecondaryGraphic(SdlArtwork secondaryGraphic){
		this.secondaryGraphic = secondaryGraphic;
		if (!batchingUpdates){
			sdl_update(null);
		}else{
			isDirty = true;
		}
	}

	protected SdlArtwork getSecondaryGraphic(){
		return secondaryGraphic;
	}
	
}
