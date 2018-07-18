package com.smartdevicelink.api;

public interface InitializationListener {
	/**
	 * Returns whether a initialization was successful or not
	 * @param success - success or fail
	 */
	void OnInitialized(boolean success);
}
