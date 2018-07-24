package com.smartdevicelink.api.screen;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.smartdevicelink.R;
import com.smartdevicelink.api.BaseSubManager;
import com.smartdevicelink.api.FileManager;
import com.smartdevicelink.api.SdlArtwork;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.enums.FileType;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
public class TextAndGraphicManager extends BaseSubManager {

	private static final String TAG = "TextAndGraphicManager";
	private FileManager fileManager;
	private Show currentScreenData;
	private Context context;
	private SdlArtwork blankArtwork;

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

		// transition state
		transitionToState(SHUTDOWN);
	}

	// Upload / Send

	public void update() {

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

		// this will call further sub functions to assemble the show text

		int numberOfLines =  4; //get capabilities and see max number of lines

		if (numberOfLines == 1){

			assembleOneLineShowText(show);

		}else if (numberOfLines == 2){

			assembleTwoLineShowText(show);

		}else if (numberOfLines == 3){

			assembleThreeLineShowText(show);

		}else if (numberOfLines == 4){

			assembleFourLineShowText(show);
		}
		return show;
	}

	private Show assembleOneLineShowText(Show show){



		return show;
	}

	private Show assembleTwoLineShowText(Show show){



		return show;
	}

	private Show assembleThreeLineShowText(Show show){



		return show;
	}

	private Show assembleFourLineShowText(Show show){



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

	private void updateCurrentScreenDataFromShow(Show show){

		// set all of the show fields, make sure items are not null before setting in the show

	}

	// Helpers

	private SdlArtwork getBlankArtwork(){

		if (blankArtwork != null){
			return blankArtwork;
		}

		blankArtwork = new SdlArtwork();
		blankArtwork.setType(FileType.GRAPHIC_PNG);
		blankArtwork.setName("blankArtwork");
		blankArtwork.setFileData(contentsOfResource(R.drawable.transparent));

		return blankArtwork;
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
