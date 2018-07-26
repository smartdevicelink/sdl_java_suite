package com.smartdevicelink.api.screen;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.smartdevicelink.R;
import com.smartdevicelink.api.BaseSubManager;
import com.smartdevicelink.api.FileManager;
import com.smartdevicelink.api.SdlArtwork;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.DisplayCapabilities;
import com.smartdevicelink.proxy.rpc.MetadataTags;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.TextField;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;
import com.smartdevicelink.proxy.rpc.enums.SystemCapabilityType;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
	private FileManager fileManager;
	private Show currentScreenData;
	private Context context;
	private SdlArtwork blankArtwork;
	private DisplayCapabilities displayCapabilities;

	protected boolean batchUpdates;
	protected SdlArtwork primaryGraphic, secondaryGraphic;
	protected TextAlignment textAlignment;
	protected String textField1, textField2, textField3, textField4, mediaTrackTextField;
	protected MetadataType textField1Type, textField2Type, textField3Type, textField4Type;

	//Constructors

	TextAndGraphicManager(ISdl internalInterface, FileManager fileManager, Context context) {
		// set class vars
		super(internalInterface);
		this.fileManager = fileManager;
		this.context = context;
		batchUpdates = false;
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

		// transition state
		transitionToState(SHUTDOWN);
	}

	// Upload / Send

	protected void update() {

		// check if is batch update

		// make sure hmi is not none

		// create show

		// check if only text

		// if image is already there, send

		// if image not there, upload

		// send show

	}

	// Images

	private void uploadImages() {
		//ArrayList<SDLArtwork> artworksToUpload = new ArrayList<>();

		// add primary image

		// add secondary image

		// use file manager to upload art
	}

	private Show assembleShowImages(Show show){

		// attach primary and secondary images if there

		return show;
	}

	// Text

	private Show assembleShowText(Show show){

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

	private Show extractImageFromShow(Show show){

		Show newShow = new Show();
		newShow.setGraphic(show.getGraphic());

		return newShow;
	}

	private Show setBlankTextFields(){

		Show newShow = new Show();
		newShow.setMainField1("");
		newShow.setMainField2("");
		newShow.setMainField3("");
		newShow.setMainField4("");
		newShow.setMediaTrack("");

		return newShow;
	}

	private void updateCurrentScreenDataFromShow(Show show){

		// set all of the show fields, make sure items are not null before setting in the show

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

	// Equality


}
