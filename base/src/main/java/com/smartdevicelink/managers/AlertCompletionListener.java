package com.smartdevicelink.managers;

public interface AlertCompletionListener {

    /**
     * Returns whether an Alert operation was successful or not along with tryAgainTime
     *
     * @param success      - Boolean that is True if Operation was a success, False otherwise.
     * @param tryAgainTime - Amount of time (in seconds) that an app must wait before resending an alert.
     */
    void onComplete(boolean success, Integer tryAgainTime);
}
