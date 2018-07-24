package com.smartdevicelink.api.screen;

import com.smartdevicelink.api.BaseSubManager;
import com.smartdevicelink.api.FileManager;
import com.smartdevicelink.api.SdlArtwork;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.Show;
import com.smartdevicelink.proxy.rpc.enums.MetadataType;

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

	private FileManager fileManager;

	protected boolean batchUpdates;
	private boolean isDirty;
	protected SdlArtwork blankArtwork, primaryGraphic, secondaryGraphic;
	protected TextAlignment textAlignment;
	private Show currentScreenData;
	protected String textField1, textField2, textField3, textField4, mediaTrackTextField;
	protected MetadataType textField1Type, textField2Type, textField3Type, textField4Type;

	//Constructors

	public TextAndGraphicManager(ISdl internalInterface, FileManager fileManager) {
		// set class vars
		super(internalInterface);
		this.fileManager = fileManager;
		batchUpdates = false;
		transitionToState(READY);
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


	// Equality


}
