package com.smartdevicelink.managers.screen.choiceset;

import java.util.HashSet;

public interface DeleteChoicesCompletionListener {

    /**
     * Returns whether an DeleteChoices operation was successful or not along with which choice cells are loaded
     * @param success - Boolean that is True if Operation was a success, False otherwise.
     * @param loadedChoiceCells - A set of the ChoiceCells that are loaded
     */
    void onComplete(boolean success, HashSet<ChoiceCell> loadedChoiceCells);
}
