package com.smartdevicelink.api;

public interface ManagerListener {

	/**
	 * Called when a manager is ready for use or failed setup
	 * @param success - success or fail
	 */
	void onStart(boolean success);

	/**
	 * Called when the manager is destroyed
	 */
	void onDestroy();
}
