package com.smartdevicelink.api;

public interface SdlManagerListener {

	/**
	 * Called when a manager is ready for use or failed setup
	 * @param success - success or fail
	 */
	void onStart(boolean success);

	/**
	 * Called when the manager is destroyed
	 */
	void onDestroy();

	/**
	 * Called when there is an error
	 * @param info info regarding the error
	 * @param e the exception
	 */
	void onError(String info, Exception e);
}
