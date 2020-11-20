package com.smartdevicelink.managers;

public interface AlertCompletionListener {

    /**
     * Returns whether a specific operation was successful or not
     *
     * @param success - success or fail
     */
    void onComplete(boolean success, Integer tryAgainTime);
}
