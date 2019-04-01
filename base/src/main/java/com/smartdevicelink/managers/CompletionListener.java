package com.smartdevicelink.managers;

public interface CompletionListener {

	/**
	 * Returns whether a specific operation was successful or not
	 * @param success - success or fail
	 */
	void onComplete(boolean success);
}
