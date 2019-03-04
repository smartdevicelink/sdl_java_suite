package com.smartdevicelink.managers;

public interface SdlManagerListener extends BaseSdlManagerListener {

	/**
	 * Called when a manager is ready for use
	 */
	void onStart(SdlManager manager);

	/**
	 * Called when the manager is destroyed
	 */
	void onDestroy(SdlManager manager);

	/**
	 * Called when the manager encounters an error
	 * @param info info regarding the error
	 * @param e the exception
	 */
	void onError(SdlManager manager, String info, Exception e);
}
