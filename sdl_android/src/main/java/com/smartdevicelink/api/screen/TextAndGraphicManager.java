package com.smartdevicelink.api.screen;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.smartdevicelink.R;
import com.smartdevicelink.api.BaseSubManager;
import com.smartdevicelink.api.CompletionListener;
import com.smartdevicelink.api.FileManager;
import com.smartdevicelink.api.MultipleFileCompletionListener;
import com.smartdevicelink.api.SdlArtwork;
import com.smartdevicelink.protocol.enums.FunctionID;
import com.smartdevicelink.proxy.RPCNotification;
import com.smartdevicelink.proxy.RPCResponse;
import com.smartdevicelink.proxy.interfaces.ISdl;
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

import org.json.JSONException;

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
 * The TextAndGraphicManager  <br>
 *
 * It is broken down to these areas: <br>
 *
 * 1.  <br>
 */
class TextAndGraphicManager extends BaseSubManager {

	private static final String TAG = "TextAndGraphicManager";
	private boolean isDirty, hasQueuedUpdate;
	private FileManager fileManager;
	private Show currentScreenData, inProgressUpdate, queuedImageUpdate;
	private CompletionListener queuedUpdateListener, inProgressListener;
	private Context context;
	private SdlArtwork blankArtwork;
	private DisplayCapabilities displayCapabilities;
	private HMILevel currentHMILevel;
	private OnRPCNotificationListener hmiListener;
	private SdlArtwork primaryGraphic, secondaryGraphic;
	private com.smartdevicelink.proxy.rpc.enums.TextAlignment textAlignment;
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
		textAlignment = CENTERED;
		currentHMILevel = HMILevel.HMI_NONE;
		addListeners();
		getBlankArtwork();
		getDisplayCapabilities();
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
		inProgressListener = null;
		hasQueuedUpdate = false;
		isDirty = false;

		// remove listeners
		internalInterface.removeOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);

		// transition state
		transitionToState(SHUTDOWN);
	}

	private void addListeners() {
		// add listener
		hmiListener = new OnRPCNotificationListener() {
			@Override
			public void onNotified(RPCNotification notification) {
				currentHMILevel = ((OnHMIStatus)notification).getHmiLevel();
			}
		};
		internalInterface.addOnRPCNotificationListener(FunctionID.ON_HMI_STATUS, hmiListener);
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
			return;
		}

		Log.d(TAG, "Updating Text and Graphics");
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
		fullShow.setMetadataTags(new MetadataTags());
		fullShow = assembleShowText(fullShow);
		fullShow = assembleShowImages(fullShow);

		inProgressListener = listener;

		if (!shouldUpdatePrimaryImage() || !shouldUpdateSecondaryImage()){

			Log.v(TAG, "No Images to send, only sending text");
			inProgressUpdate = extractTextFromShow(fullShow);

		}else if (isArtworkUploadedOrDoesntExist(primaryGraphic) && isArtworkUploadedOrDoesntExist(secondaryGraphic)){

			Log.v(TAG, "Images already uploaded, sending full update");
			// The files to be updated are already uploaded, send the full show immediately
			inProgressUpdate = fullShow;
		} else{

			Log.v(TAG, "Images need to be uploaded, sending text and uploading images");
			// We need to upload or queue the upload of the images
			// Send the text immediately
			inProgressUpdate = extractTextFromShow(fullShow);
			// start uploading images
			final Show thisUpdate = fullShow;

			uploadImages(new CompletionListener() {
				@Override
				public void onComplete(boolean success) {
					if (!success){
						Log.e(TAG, "Error uploading text and graphic image");
					}

					// Check if queued image update still matches our images (there could have been a new Show in the meantime)
					// and send a new update if it does. Since the images will already be on the head unit, the whole show will be sent
					if (thisUpdate.getGraphic().equals(queuedImageUpdate.getGraphic()) &&
							thisUpdate.getSecondaryGraphic().equals(queuedImageUpdate.getSecondaryGraphic())){
						Log.v(TAG, "Queued image update matches the images we need, sending update");
						sdl_update(inProgressListener);
					} else {
						Log.v(TAG, "Queued image update does not match the images we need, skipping update");
					}
				}
			});
			queuedImageUpdate = fullShow;
		}

		fullShow.setOnRPCResponseListener(new OnRPCResponseListener() {
			@Override
			public void onResponse(int correlationId, RPCResponse response) {
				if (response.getSuccess()){
					updateCurrentScreenDataFromShow(queuedImageUpdate);
				}

				inProgressUpdate = null;
				if (inProgressListener != null){
					inProgressListener.onComplete(false);
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

		internalInterface.sendRPCRequest(fullShow);
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

		// use file manager to upload art
		fileManager.uploadArtworks(artworksToUpload, new MultipleFileCompletionListener() {
			@Override
			public void onComplete(Map<String, String> errors) {
				if (errors != null) {
					Log.e(TAG, "Error Uploading Artworks. Error: " + errors.toString());
					listener.onComplete(false);
				}else{
					Log.d(TAG, "Successfully uploaded Artworks");
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

	private Show assembleShowText(Show show){

		show = setBlankTextFields(show);

		if (mediaTrackTextField != null){
			show.setMediaTrack(mediaTrackTextField);
		} else {
			show.setMediaTrack("");
		}

		List<String> nonNullFields = findNonNullTextFields();
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
		for (int i = 1; i < showFields.size(); i++) {
			showString1.append(" - ").append(showFields.get(i));
		}
		show.setMainField1(showString1.toString());

		MetadataTags tags = new MetadataTags();
		tags.setMainField1(findNonNullMetadataFields());

		return show;
	}

	private Show assembleTwoLineShowText(Show show){

		StringBuilder tempString = new StringBuilder();
		MetadataTags tags = show.getMetadataTags();

		if (textField1 != null && textField1.length() > 0) {
			tempString.append(textField1);
			if (textField1Type != null){
				tags.setMainField1(textField1Type);
			}
		}

		if (textField2 != null && textField2.length() > 0) {

			if (textField3 == null || !(textField3.length() > 0) || textField4 == null || !(textField4.length() > 0)){
				// text does not exist in slots 3 or 4, put i slot 2
				show.setMainField2(textField2);
				if (textField2Type != null){
					tags.setMainField2(textField2Type);
				}
			} else if (textField1 != null && textField1.length() > 0) {
				// If text 1 exists, put it in slot 1 formatted
				tempString.append(" - ").append(textField2);
				if (textField2Type != null){
					List<MetadataType> tags2 = tags.getMainField1();
					tags2.add(textField2Type);
					tags.setMainField1(tags2);
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
				tags.setMainField2(textField3Type);
			}
		}

		if (textField4 != null && textField4.length() > 0){
			if (textField3 != null && textField3.length() > 0){
				// If text 3 exists, put it in slot 2 formatted
				tempString.append(" - ").append(textField4);
				if (textField4Type != null){
					List<MetadataType> tags3 = tags.getMainField1();
					tags3.add(textField4Type);
					tags.setMainField2(tags3);
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

		MetadataTags tags = show.getMetadataTags();

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
				tags.setMainField2(textField3Type);
			}
		}

		if (textField4 != null && textField4.length() > 0) {
			if (textField3 != null && textField3.length() > 0) {
				// If text 3 exists, put it in slot 3 formatted
				tempString.append(" - ").append(textField4);
				if (textField4Type != null){
					List<MetadataType> tags4 = tags.getMainField3();
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

		MetadataTags tags = show.getMetadataTags();

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
				tags.setMainField2(textField4Type);
			}
		}

		show.setMetadataTags(tags);
		return show;
	}

	// Extraction

	private Show extractTextFromShow(Show show){

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

	private List<String> findNonNullTextFields(){
		List<String> array = new ArrayList<>();

		if (textField1.length() > 0) {
			array.add(textField1);
		}

		if (textField2.length() > 0) {
			array.add(textField2);
		}

		if (textField3.length() > 0) {
			array.add(textField3);
		}

		if (textField4.length() > 0) {
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

	private void getBlankArtwork(){

		if (blankArtwork != null){
			blankArtwork = new SdlArtwork();
			blankArtwork.setType(FileType.GRAPHIC_PNG);
			blankArtwork.setName("blankArtwork");
			blankArtwork.setFileData(contentsOfResource(R.drawable.transparent));
		}
	}

	private boolean isArtworkUploadedOrDoesntExist(SdlArtwork artwork){
		//return ((artwork != null) || fileManager.hasUploadedFile(artwork));
		return false;
	}

	private boolean shouldUpdatePrimaryImage() {
		boolean hasGraphic = displayCapabilities.getGraphicSupported();
		return (hasGraphic && primaryGraphic != null && currentScreenData.getGraphic().getValue().equalsIgnoreCase(primaryGraphic.getName()));
	}

	private boolean shouldUpdateSecondaryImage() {
		// Cannot detect if there is a secondary image, so we'll just try to detect if there's a primary image and allow it if there is.
		boolean hasGraphic = displayCapabilities.getGraphicSupported();
		return (hasGraphic && secondaryGraphic != null && currentScreenData.getSecondaryGraphic().getValue().equalsIgnoreCase(secondaryGraphic.getName()));
	}

	private void getDisplayCapabilities() {

		if (displayCapabilities != null) {
			displayCapabilities = (DisplayCapabilities) internalInterface.getCapability(SystemCapabilityType.DISPLAY);
			try {
				Log.i(TAG, "DISPLAY CAPABILITIES: "+ displayCapabilities.serializeJSON().toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	private int getNumberOfLines() {
		if (displayCapabilities == null){
			return 4;
		}
		List<TextField> textFields = displayCapabilities.getTextFields();
		return textFields.size();
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
	/**
	 * Helper method to take resource files and turn them into byte arrays
	 * @param resource Resource file id
	 * @return Resulting byte array
	 */
	private byte[] contentsOfResource(int resource) {
		InputStream is = null;
		try {
			is = context.getResources().openRawResource(resource);
			return contentsOfInputStream(is);
		} catch (Resources.NotFoundException e) {
			Log.w(TAG, "Can't read from resource", e);
			return null;
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Helper method to take InputStream and turn it into byte array
	 * @param is valid InputStream
	 * @return Resulting byte array
	 */
	private byte[] contentsOfInputStream(InputStream is){
		if(is == null){
			return null;
		}
		try{
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			final int bufferSize = 4096;
			final byte[] buffer = new byte[bufferSize];
			int available;
			while ((available = is.read(buffer)) >= 0) {
				os.write(buffer, 0, available);
			}
			return os.toByteArray();
		} catch (IOException e){
			Log.w(TAG, "Can't read from InputStream", e);
			return null;
		}
	}
}
