package com.smartdevicelink.managers;

public interface SdlManagerListener {

	/**
	 * Called when a manager is ready for use
	 */
	void onStart();

	/**
	 * Called when the manager is destroyed
	 */
	void onDestroy();

	/**
	 * Called when the manager encounters an error
	 * @param info info regarding the error
	 * @param e the exception
	 */
	void onError(String info, Exception e);
}
