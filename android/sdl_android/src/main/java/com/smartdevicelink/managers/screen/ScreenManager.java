package com.smartdevicelink.managers.screen;

import android.support.annotation.NonNull;

import com.smartdevicelink.managers.file.FileManager;
import com.smartdevicelink.proxy.interfaces.ISdl;

/**
 * <strong>ScreenManager</strong> <br>
 *
 * Note: This class must be accessed through the SdlManager. Do not instantiate it by itself. <br>
*/
public class ScreenManager extends BaseScreenManager {

	public ScreenManager(@NonNull ISdl internalInterface, @NonNull FileManager fileManager) {
		super(internalInterface, fileManager);
	}
}
