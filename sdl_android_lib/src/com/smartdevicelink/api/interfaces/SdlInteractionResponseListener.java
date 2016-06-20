package com.smartdevicelink.api.interfaces;

/**
 * Created by mschwerz on 6/17/16.
 */
public interface SdlInteractionResponseListener {
    void onSuccess();

    void onTimeout();

    void onAborted();

    void onError();
}
