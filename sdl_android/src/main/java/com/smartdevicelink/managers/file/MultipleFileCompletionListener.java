package com.smartdevicelink.managers.file;

import java.util.Map;

public interface MultipleFileCompletionListener {

	/**
	 * @param errors - a dictionary (map) property, of type <String: String>, and contains information
	 * on all failed uploads. The key is the name of the file that did not upload properly,
	 * the value is an error String describing what went wrong on that particular upload attempt.
	 * If all files are uploaded successfully, errors is null
	 */
	void onComplete(Map<String, String> errors);
}
