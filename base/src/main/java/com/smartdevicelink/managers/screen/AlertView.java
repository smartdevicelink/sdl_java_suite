package com.smartdevicelink.managers.screen;

import com.smartdevicelink.managers.file.filetypes.SdlArtwork;
import com.smartdevicelink.util.DebugTool;

import java.util.List;

public class AlertView implements Cloneable{

    private static final String TAG = "AlertView";
    private static final int DEFAULT_TIMEOUT = 5;
    private static int defaultTimeout = 5;
    private static final int TIMEOUT_MIN = 3;
    private static final int TIMEOUT_MAX = 10;


    private String text, secondaryText, tertiaryText;
    private Integer timeout;
    private AlertAudioData audio;
    private boolean showWaitIndicator;
    private List<SoftButtonObject> softButtons;
    private SdlArtwork icon;
    AlertCanceledListener canceledListener;


    private AlertView(){
    }


    /**
     * Creates a deep copy of the object
     *
     * @return deep copy of the object, null if an exception occurred
     */
    @Override
    public AlertView clone() {
        try {
            AlertView alertView = (AlertView) super.clone();
            if (alertView != null && alertView.getAudio() != null) {
                alertView.audio = audio.clone();
            }
            return alertView;
        } catch (CloneNotSupportedException e) {
            if (DebugTool.isDebugEnabled()) {
                throw new RuntimeException("Clone not supported by super class");
            }
        }
        return null;
    }

    public static class Builder {

        AlertView alertView;

        public Builder(){
            alertView = new AlertView();
        }

        public Builder setText(String text){
            this.alertView.text = text;
            return this;
        }

        public Builder setSecondaryText(String secondaryText){
            alertView.secondaryText = secondaryText;
            return this;
        }

        public Builder setTertiaryText(String tertiaryText){
            alertView.tertiaryText = tertiaryText;
            return this;
        }

        public Builder setTimeout(Integer timeout){
            alertView.timeout = timeout;
            return this;
        }

        public Builder setShowWaitIndicator(boolean showWaitIndicator){
            alertView.showWaitIndicator = showWaitIndicator;
            return this;
        }

        public Builder setSoftButtons(List<SoftButtonObject> softButtons){
            alertView.softButtons = softButtons;
            return this;
        }

        public Builder setAudio(AlertAudioData audio){
            alertView.audio = audio;
            return this;
        }

        public Builder setIcon(SdlArtwork icon){
            alertView.icon = icon;
            return this;
        }

        public Builder setDefaultTimeOut(int defaultTimeOut) {
            alertView.defaultTimeout = defaultTimeOut;
            return this;
        }

        public AlertView build(){
            return alertView;
        }
    }

    // All vars have get / set as well to match iOS read / write

    public void cancel(){
        if (canceledListener == null) {
            return;
        }
        canceledListener.onAlertCanceled();
    }

    public Integer getTimeout() {
        if (timeout == null) {
            timeout = defaultTimeout;
        } else if (timeout == defaultTimeout) {
            return defaultTimeout;
        } else if (timeout < TIMEOUT_MIN) {
            return TIMEOUT_MIN;
        } else if (timeout > TIMEOUT_MAX) {
            return TIMEOUT_MAX;
        }
        return timeout;
    }

    public AlertAudioData getAudio() {
        return audio;
    }

    public void setAudio(AlertAudioData audio) {
        this.audio = audio;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isShowWaitIndicator() {
        return showWaitIndicator;
    }

    public String getSecondaryText() {
        return secondaryText;
    }

    public void setSecondaryText(String secondaryText) {
        this.secondaryText = secondaryText;
    }

    public String getTertiaryText() {
        return tertiaryText;
    }

    public void setTertiaryText(String tertiaryText) {
        this.tertiaryText = tertiaryText;
    }

    public List<SoftButtonObject> getSoftButtons() {
        return softButtons;
    }

    public void setSoftButtons(List<SoftButtonObject> softButtons) {
        this.softButtons = softButtons;
    }

    public SdlArtwork getIcon() {
        return icon;
    }

    public static int getDefaultTimeout() {
        return AlertView.defaultTimeout;
    }

    public static void setDefaultTimeout(int defaultTimeout) {
        if (defaultTimeout <= TIMEOUT_MIN) {
            AlertView.defaultTimeout = TIMEOUT_MIN;
            return;
        } else if (defaultTimeout >= TIMEOUT_MAX) {
            AlertView.defaultTimeout = TIMEOUT_MAX;
            return;
        }
        AlertView.defaultTimeout = defaultTimeout;
    }
}
