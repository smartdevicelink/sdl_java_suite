package com.smartdevicelink.managers.screen;

import android.support.annotation.NonNull;
import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.proxy.interfaces.ISdl;
import com.smartdevicelink.proxy.rpc.enums.FileType;

/**
 * <strong>TextAndGraphicManager</strong> <br>
 *
 * Note: This class must be accessed through the SdlManager. Do not instantiate it by itself. <br>
 *
 */
class TextAndGraphicManager extends BaseTextAndGraphicManager {

	TextAndGraphicManager(@NonNull ISdl internalInterface, @NonNull FileManager fileManager, @NonNull SoftButtonManager softButtonManager) {
		super(internalInterface, fileManager, softButtonManager);
	}

	@Override
	SdlArtwork getBlankArtwork(){
		if (blankArtwork == null){
			blankArtwork = new SdlArtwork();
			blankArtwork.setType(FileType.GRAPHIC_PNG);
			blankArtwork.setName("blankArtwork");
			blankArtwork.setFileData(new byte[50]);
			//FIXME blankArtwork.setResourceId(R.drawable.transparent);
		}
		return blankArtwork;
	}
}
