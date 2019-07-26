package com.smartdevicelink.managers.screen.choiceset;

import java.util.List;

public interface KeyboardCharacterSetCompletionListener {

	/**
	 * This listener is called when you wish to update your keyboard's limitedCharacterSet in response to the user's input
	 * @param updatedCharacterSet - The new set of characters to use
	 */
	void onUpdatedCharacterSet(List<String> updatedCharacterSet);
}
